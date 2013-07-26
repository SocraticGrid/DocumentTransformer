/*
 * To change this template, choose Tools | Templates and open the template in the
 * editor.
 */
package org.socraticgrid.documenttransformer.multipleinputtransfomsteps;


import org.socraticgrid.documenttransformer.TransformInput;

import org.springframework.core.io.Resource;

import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * DOCUMENT ME!
 *
 * @author  Jerry Goodnough
 */
public class XSLTTransformStep implements MultipleInputTransformStep
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

    public boolean transform(Source base, TransformInput input, StreamResult result)
        throws TransformerException
    {
        return this.transform(base, input, result, null);
    }

    /**
     * DOCUMENT ME!
     *
     * @param   base
     * @param   src
     * @param   result
     * @param   props
     *
     * @return
     *
     * @throws  TransformerException
     */
    public boolean transform(Source base, TransformInput input, StreamResult result,
        Properties props) throws TransformerException
    {
        Transformer tx = this.getTransformer();

        // Create the URIResolver to map document streams to
        ResolveStreams rel = new ResolveStreams(input, tx.getURIResolver());
        tx.setURIResolver(rel);

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

        tx.transform(base, result);
        tx.clearParameters();

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

    class ResolveStreams implements URIResolver
    {
        URIResolver baseResolver;
        String name;
        TransformInput streams;

        public ResolveStreams(TransformInput streams, URIResolver baseResolver)
        {
            this.streams = streams;
            this.baseResolver = baseResolver;
        }

        @Override
        public Source resolve(String href, String base) throws TransformerException
        {
            logger.log(Level.INFO, "Fetch stream {0}", href);

            Source out = streams.getStream(href);

            if (out == null)
            {


                logger.info("Stream not found using base resolver");

                if (this.baseResolver != null)
                {
                    out = baseResolver.resolve(href, base);
                }
            }

            return out;
        }
    }
}
