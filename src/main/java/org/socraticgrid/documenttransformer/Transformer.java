/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer;

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
  
    private HashMap<String, TransformPipeline> transformPipeline; 
    
    //Factory
    //Initialization
    //Transfomation

    //static
    //{
    //    System.setProperty("javax.xml.transform.TransformerFactory", "net.sf.saxon.TransformerFactoryImpl");
    //}

    public void setTransformPipeline(HashMap<String, TransformPipeline> transformPipeline)
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
            out = transformPipeline.get(pipeline).transform(inStr);
        }
   
        return out;
    }
}
