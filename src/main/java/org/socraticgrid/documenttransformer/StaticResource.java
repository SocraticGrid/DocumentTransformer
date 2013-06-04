/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.springframework.core.io.Resource;

/**
 * Provide a static file for a Transformation This helper class can be used in
 * integration testing as either source or return data.
 *
 * @author Jerry Goodnough
 */
public class StaticResource implements TransformStep
{

    private Resource staticResource;

    public Resource getStaticResource()
    {
        return staticResource;
    }

    public void setStaticResource(Resource staticResource)
    {
        this.staticResource = staticResource;
    }

    @Override
    public void transform(StreamSource src, StreamResult result) throws TransformerException
    {
        try
        {
            org.apache.commons.io.IOUtils.copy(staticResource.getInputStream(), result.getOutputStream());
        }
        catch (IOException ex)
        {
            Logger.getLogger(org.socraticgrid.documenttransformer.StaticResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new TransformerException("Error in Static Resource Copy", ex);

        }
    }
        public void transform(StreamSource src, StreamResult result, Properties props) throws TransformerException
        {
             this.transform(src,result);
        }
}
