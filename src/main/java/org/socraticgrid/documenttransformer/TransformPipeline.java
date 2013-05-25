/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 *
 * @author Jerry Goodnough
 */
public class TransformPipeline 
{


    private List<TransformStep> transformChain = null;

    //TODO: Decouple and move to a resource lookup bean in the future.
    public TransformPipeline()
    {
    }

    //Factory
    public void setTransformChain(List<TransformStep> transformChain)
    {
        this.transformChain = transformChain;
    }

    public String transform(InputStream inStr)
    {

        StreamResult result = null;
        ByteArrayOutputStream outResultStream = null;
        if (transformChain != null)
        {

            Iterator<TransformStep> itr = transformChain.iterator();
            StreamSource src = new StreamSource(inStr);
            while (itr.hasNext())
            {
                TransformStep tx = itr.next();
                //FUTURE: Provide Parameterz

                ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
                result = new StreamResult(resultStream);
                try
                {
                    tx.transform(src, result);
                    outResultStream = resultStream;
                    
                    if (Logger.getLogger(Transformer.class.getName()).isLoggable(Level.FINEST));
                    {

                        Logger.getLogger(Transformer.class.getName()).log(Level.FINEST, result.toString());
                    }
                    if (itr.hasNext())
                    {
                        //Prepare the next source
                        ByteArrayInputStream bs = new ByteArrayInputStream(resultStream.toByteArray());
                        src = new StreamSource(bs); 
                    }
                    
                }
                catch (TransformerException ex)
                {
                    Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);

                    break;
                }
            }
        }

        return (outResultStream == null) ? "" : outResultStream.toString();
    }
    
      public String transform(InputStream inStr,Properties props)
    {

        StreamResult result = null;
        ByteArrayOutputStream outResultStream = null;
        if (transformChain != null)
        {

            Iterator<TransformStep> itr = transformChain.iterator();
            StreamSource src = new StreamSource(inStr);
            while (itr.hasNext())
            {
                TransformStep tx = itr.next();
                //FUTURE: Provide Parameterz

                ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
                result = new StreamResult(resultStream);
                try
                {
                    tx.transform(src, result, props);
                    outResultStream = resultStream;
                    
                    if (Logger.getLogger(Transformer.class.getName()).isLoggable(Level.FINEST));
                    {

                        Logger.getLogger(Transformer.class.getName()).log(Level.FINEST, result.toString());
                    }
                    if (itr.hasNext())
                    {
                        //Prepare the next source
                        ByteArrayInputStream bs = new ByteArrayInputStream(resultStream.toByteArray());
                        src = new StreamSource(bs); 
                    }
                    
                }
                catch (TransformerException ex)
                {
                    Logger.getLogger(Transformer.class.getName()).log(Level.SEVERE, null, ex);

                    break;
                }
            }
        }

        return (outResultStream == null) ? "" : outResultStream.toString();
    }
}
