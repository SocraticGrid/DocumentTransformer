/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer;

import org.socraticgrid.documenttransformer.interfaces.SingleSourcePipeline;
import java.io.InputStream;
import java.lang.String;
import java.util.HashMap;
import java.util.Properties;

/**
 *
 * @author Jerry Goodnough
 */
public class Transformer
{
  
    private HashMap<String, SingleSourcePipeline> transformPipeline; 
    
    //Factory
    //Initialization
    //Transfomation

    //static
    //{
    //    System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
    //}

    public void setTransformPipeline(HashMap<String, SingleSourcePipeline> transformPipeline)
    {
        this.transformPipeline=transformPipeline;
    }
    
    public String transform(String pipeline, InputStream inStr)
    {
        String out = null;
        if (transformPipeline.containsKey(pipeline))
        {
            out = transformPipeline.get(pipeline).transform(inStr);
        }
   
        return out;
    }
    
    public String transform(String pipeline, InputStream inStr, Properties props)
    {
        String out = null;
        if (transformPipeline.containsKey(pipeline))
        {
            out = transformPipeline.get(pipeline).transform(inStr,props);
        }
   
        return out;
    }
    
    public InputStream transformAsStream(String pipeline, InputStream inStr)
    {
        InputStream out = null;
        if (transformPipeline.containsKey(pipeline))
        {
            out = transformPipeline.get(pipeline).transformAsInputStream(inStr);
        }
   
        return out;
    }
    
    public InputStream transformAsStream(String pipeline, InputStream inStr, Properties props)
    {
        InputStream out = null;
        if (transformPipeline.containsKey(pipeline))
        {
            out = transformPipeline.get(pipeline).transformAsInputStream(inStr,props);
        }
   
        return out;
    }
    
}
