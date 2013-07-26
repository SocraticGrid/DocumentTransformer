/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer;

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
import org.apache.commons.io.IOUtils;
import org.socraticgrid.documenttransformer.multipleinputtransfomsteps.MultipleInputTransformStep;
import org.springframework.core.io.Resource;

/**
 * The Cumulative Pipeline operates by providing each step with the
 * original input (unless swapped by a step) and the output of the prior step to
 * @author Jerry Goodnough
 */
public class MergePipeline 
{
    private static final Logger logger = Logger.getLogger(MergePipeline.class.getName());
    private List<MultipleInputTransformStep> transformChain;

    /**
     * Get the value of transformChain
     *
     * @return the value of transformChain
     */
    public List<MultipleInputTransformStep> getTransformChain()
    {
        return transformChain;
    }

    /**
     * Set the value of transformChain
     *
     * @param transformChain new value of transformChain
     */
    public void setTransformChain(List<MultipleInputTransformStep> TransformChain)
    {
        this.transformChain = TransformChain;
    }

    private Resource defaultSource;

    /**
     * Get the value of defaultSource
     *
     * @return the value of defaultSource
     */
    public Resource getDefaultSource()
    {
        return defaultSource;
    }

    /**
     * Set the value of defaultSource
     *
     * @param defaultSource new value of defaultSource
     */
    public void setDefaultSource(Resource DefaultSource)
    {
        this.defaultSource = DefaultSource;
    }
    


    public InputStream transformAsInputStream(TransformInput in, Properties props)
    {
        ByteArrayOutputStream outResultStream = this.internalTransform( in, props);

        return (outResultStream == null)
            ? null : new ByteArrayInputStream(outResultStream.toByteArray());
    }

    protected ByteArrayOutputStream internalTransform(TransformInput in,
        Properties props)
    {
        StreamResult result;
        ByteArrayOutputStream outResultStream = null;
        int changes = 0;
        StreamSource src;
        src = new StreamSource(in.getBaseStream().getInputStream());
        
        if (transformChain != null)
        {
            Iterator<MultipleInputTransformStep> itr = transformChain.iterator();
            
            if (this.defaultSource != null)
            {
                logger.fine("Setting default Stream Source");
                try
                {
                    in.setDefaultStream(new StreamSource(defaultSource.getInputStream()));
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
           while (itr.hasNext())
            {
                MultipleInputTransformStep tx = itr.next();

                // FUTURE: Provide Parameterz
                ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
                result = new StreamResult(resultStream);
                
                try
                {
                    boolean changed = tx.transform(src, in, result, props);
                    outResultStream = resultStream;

                    if (Logger.getLogger(javax.xml.transform.Transformer.class.getName()).isLoggable(
                                Level.FINEST))
                    {
                        Logger.getLogger(javax.xml.transform.Transformer.class.getName()).log(
                            Level.FINEST, result.toString());
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

            if (src.getInputStream().markSupported())
            {

                try
                {
                    logger.fine("No changes occured in transfornm - copying input");
                    src.getInputStream().reset();
                    outResultStream = new ByteArrayOutputStream();
                    IOUtils.copyLarge(src.getInputStream(), outResultStream);
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
