/*
 * To change this template, choose Tools | Templates and open the template in the
 * editor.
 */
package org.socraticgrid.documenttransformer;

import org.apache.commons.io.IOUtils;

import org.socraticgrid.documenttransformer.interfaces.CumulativeTransformStep;
import org.socraticgrid.documenttransformer.interfaces.DualSourcePipeline;


import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * The Cumulative Pipeline operates by providing each step with the original input
 * (unless swapped by a step) and the output of the prior step to.
 *
 * @author  Jerry Goodnough
 */
public class CumulativePipeline implements DualSourcePipeline
{
    private static final Logger logger = Logger.getLogger(CumulativePipeline.class
            .getName());
    private Resource BaseTemplate;
    private List<CumulativeTransformStep> transformChain;

    /**
     * Get the value of BaseTemplate.
     *
     * @return  the value of BaseTemplate
     */
    public Resource getBaseTemplate()
    {
        return BaseTemplate;
    }

    /**
     * Get the value of transformChain.
     *
     * @return  the value of transformChain
     */
    public List<CumulativeTransformStep> getTransformChain()
    {
        return transformChain;
    }

    /**
     * Set the value of BaseTemplate.
     *
     * @param  BaseTemplate  new value of BaseTemplate
     */
    public void setBaseTemplate(Resource BaseTemplate)
    {
        this.BaseTemplate = BaseTemplate;
    }

    /**
     * Set the value of transformChain.
     *
     * @param  transformChain  new value of transformChain
     */
    public void setTransformChain(List<CumulativeTransformStep> TransformChain)
    {
        this.transformChain = TransformChain;
    }

    @Override
    public InputStream transformAsInputStream(InputStream inStream,
        InputStream baseStream, Properties props)
    {
        ByteArrayOutputStream outResultStream = this.internalTransform(inStream,
                baseStream, props);

        return (outResultStream == null)
            ? null : new ByteArrayInputStream(outResultStream.toByteArray());
    }

    protected ByteArrayOutputStream internalTransform(InputStream inStream,
        InputStream baseStr, Properties props)
    {
        StreamResult result;
        ByteArrayOutputStream outResultStream = null;
        int changes = 0;

        if (transformChain != null)
        {

            Iterator<CumulativeTransformStep> itr = transformChain.iterator();
            StreamSource src = new StreamSource(inStream);
            StreamSource base = new StreamSource(baseStr);

            while (itr.hasNext())
            {
                CumulativeTransformStep tx = itr.next();

                // FUTURE: Provide Parameterz
                ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
                result = new StreamResult(resultStream);

                try
                {
                    boolean changed = tx.transform(src, base, result, props);
                    outResultStream = resultStream;

                    if (Logger.getLogger(
                                javax.xml.transform.Transformer.class.getName())
                            .isLoggable(Level.FINEST))
                    {
                        Logger.getLogger(javax.xml.transform.Transformer.class
                            .getName()).log(Level.FINEST, result.toString());
                    }

                    // Count Changes
                    if (changed)
                    {
                        changes++;
                    }

                    if (itr.hasNext())
                    {

                        if (changed == true)
                        {

                            // Prepare the next source
                            ByteArrayInputStream bs = new ByteArrayInputStream(
                                    resultStream.toByteArray());
                            src = new StreamSource(bs);
                        }
                        else
                        {

                            if (src.getInputStream().markSupported())
                            {
                                src.getInputStream().reset();
                            }
                            else
                            {
                                logger.severe(
                                    "Transformation Step did not make a change and inputstream can not reset.");

                                break;
                            }
                        }
                    }
                }
                catch (TransformerException ex)
                {
                    logger.log(Level.SEVERE, null, ex);

                    break;
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }

        if (changes == 0)
        {

            if (inStream.markSupported())
            {

                try
                {
                    logger.fine("No changes occured in transfornm - copying input");
                    inStream.reset();
                    outResultStream = new ByteArrayOutputStream();
                    IOUtils.copyLarge(inStream, outResultStream);
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE,
                        "Exception copying Input Stream when no transform has occured",
                        ex);
                }
            }
            else
            {
                logger.severe(
                    "Transformation did not make a change and inputstream can not reset.");
            }
        }

        return outResultStream;
    }
}
