/*
 * To change this template, choose Tools | Templates and open the template in the
 * editor.
 */
package org.socraticgrid.documenttransformer.transfromsteps;

import org.socraticgrid.documenttransformer.interfaces.SimpleTransformStep;

import org.springframework.core.io.Resource;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * TODO: Look for solutions to the transform treading issue.
 *
 * @author  Jerry Goodnough
 */
public class XSLTTransformStep implements SimpleTransformStep
{

    private static final Logger logger = Logger.getLogger(XSLTTransformStep.class
            .getName());


    private HashMap<String, Object> styleSheetParameters = null;
    private Templates tTemplate;

    private Resource xsltStyleSheet;

    public XSLTTransformStep()
    {
    }

    public HashMap<String, Object> getStyleSheetParameters()
    {
        return styleSheetParameters;
    }

    public Resource getXsltStyleSheet()
    {
        return xsltStyleSheet;
    }


    @PostConstruct
    public void initialize()
    {
        logger.log(Level.FINER, "Initialize Called");


        // Resource resource = appCtx.getResource(this.styleSheet);
        if (xsltStyleSheet == null)
        {
            logger.log(Level.SEVERE, "xsltStyleSheet resource is not defined");
        }
        else
        {


            String txFact = System.getProperty(
                    "javax.xml.transform.TransformerFactory");


            try
            {
                System.setProperty("javax.xml.transform.TransformerFactory",
                    "net.sf.saxon.TransformerFactoryImpl");

                TransformerFactory tfactory = TransformerFactory.newInstance();

                tTemplate = tfactory.newTemplates((new StreamSource(
                                xsltStyleSheet.getInputStream())));


            }
            catch (IOException | TransformerConfigurationException ex)
            {
                logger.log(Level.SEVERE, null, ex);
            }
            finally
            {

                if (txFact == null)
                {
                    System.clearProperty("javax.xml.transform.TransformerFactory");
                }
                else
                {
                    System.setProperty("javax.xml.transform.TransformerFactory",
                        txFact);
                }

            }
        }

    }

    public void setStyleSheetParameters(HashMap<String, Object> params)
    {
        this.styleSheetParameters = params;
    }

    public void setXsltStyleSheet(Resource xsltStyleSheet)
    {
        this.xsltStyleSheet = xsltStyleSheet;
    }


    public boolean transform(StreamSource src, StreamResult result)
        throws TransformerException
    {
        Transformer tx = this.getTransformer();

        if (styleSheetParameters != null)
        {
            Iterator<String> keyItr = styleSheetParameters.keySet().iterator();

            while (keyItr.hasNext())
            {
                String key = keyItr.next();
                tx.setParameter(key, styleSheetParameters.get(key));
            }
        }

        tx.transform(src, result);
        tx.clearParameters();

        if (logger.isLoggable(Level.FINEST))
        {
            logger.log(Level.FINEST,
                result.toString());
        }

        return true;
    }

    /**
     * Handle the transformation.
     *
      *
     * @param   src
     * @param   result
     * @param   props
     *
     * @return
     *
     * @throws  TransformerException
     */
    @Override
    public  boolean transform(StreamSource src, StreamResult result,
        Properties props) throws TransformerException
    {
        Transformer tx = this.getTransformer();

        if (styleSheetParameters != null)
        {
            Iterator<String> keyItr = styleSheetParameters.keySet().iterator();

            while (keyItr.hasNext())
            {
                String key = keyItr.next();
                tx.setParameter(key, styleSheetParameters.get(key));
            }
        }

        if (props != null)
        {
            Iterator<String> keyItr = props.stringPropertyNames().iterator();

            while (keyItr.hasNext())
            {
                String key = keyItr.next();
                tx.setParameter(key, props.get(key));
            }

        }

        tx.transform(src, result);
        tx.clearParameters();

        if (logger.isLoggable(Level.FINEST))
        {
        
            logger.log(Level.FINEST,
                result.toString());
        }

        return true;
    }

    /**
     * Get a Transformer based on a template for Thread safety.
     */
    protected Transformer getTransformer()
    {
        Transformer tx = null;

        try
        {
            tx = tTemplate.newTransformer();

            if (tx != null)
            {

                if (styleSheetParameters != null)
                {
                    Iterator<String> keyItr = styleSheetParameters.keySet()
                        .iterator();

                    while (keyItr.hasNext())
                    {
                        String key = keyItr.next();
                        tx.setParameter(key, styleSheetParameters.get(key));
                    }
                }
            }
            else
            {
                logger.log(Level.SEVERE, "Unable to load {0}",
                    xsltStyleSheet.getFilename());

            }
        }
        catch (TransformerConfigurationException ex)
        {
            logger.log(Level.SEVERE, null, ex);
        }

        return tx;
    }
}
