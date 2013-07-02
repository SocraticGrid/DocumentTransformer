/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer.transfromsteps;

import org.socraticgrid.documenttransformer.interfaces.SimpleTransformStep;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

import org.springframework.core.io.Resource;

/**
 *
 * @author Jerry Goodnough
 */
public class XSLTTransformStep implements SimpleTransformStep
{

 
    private HashMap<String, Object> styleSheetParameters = null;
    private Transformer tx;
    

    
    private Resource xsltStyleSheet;

    public Resource getXsltStyleSheet()
    {
        return xsltStyleSheet;
    }

    public void setXsltStyleSheet(Resource xsltStyleSheet)
    {
        this.xsltStyleSheet = xsltStyleSheet;
    }
    
    public XSLTTransformStep()
    {
    }

    public void setStyleSheetParameters(HashMap<String, Object> params)
    {
        this.styleSheetParameters = params;
    }

    public HashMap<String, Object> getStyleSheetParameters()
    {
        return styleSheetParameters;
    }


    public boolean transform(StreamSource src, StreamResult result) throws TransformerException
    {
        tx.transform(src, result);
        if (Logger.getLogger(Transformer.class.getName()).isLoggable(Level.FINEST));
        {

            Logger.getLogger(Transformer.class.getName()).log(Level.FINEST, result.toString());
        }
            return true;
    }
    
    public boolean transform(StreamSource src, StreamResult result, Properties props) throws TransformerException
    {
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
        if (Logger.getLogger(Transformer.class.getName()).isLoggable(Level.FINEST));
        {

            Logger.getLogger(Transformer.class.getName()).log(Level.FINEST, result.toString());
        }
        return true;
    }


    @PostConstruct
    public void initialize()
    {
        Logger.getLogger(Transformer.class.getName()).log(Level.FINER, "Initialize Called");
        //Resource resource = appCtx.getResource(this.styleSheet);
        if (xsltStyleSheet == null)
        {
            Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, "xsltStyleSheet resource is not defined");
        }
        else
        {

            String txFact = System.getProperty("javax.xml.transform.TransformerFactory");


            try
            {
                System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");

                TransformerFactory tfactory = TransformerFactory.newInstance();
                tx = tfactory.newTransformer(new StreamSource(xsltStyleSheet.getInputStream()));
                if (tx != null)
                {
                    if (styleSheetParameters != null)
                    {
                        Iterator<String> keyItr = styleSheetParameters.keySet().iterator();
                        while (keyItr.hasNext())
                        {
                            String key = keyItr.next();
                            tx.setParameter(key, styleSheetParameters.get(key));
                        }
                    }
                }
                else
                {
                    Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, "Unable to load {0}", xsltStyleSheet.getFilename());
                    
                }
            }
            catch (IOException ex)
            {
                Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (TransformerConfigurationException ex)
            {
                Logger.getLogger(XSLTTransformStep.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                if (txFact == null)
                {
                    System.clearProperty("javax.xml.transform.TransformerFactory");
                }
                else
                {
                    System.setProperty("javax.xml.transform.TransformerFactory", txFact);
                }

            }
        }

    }
}
