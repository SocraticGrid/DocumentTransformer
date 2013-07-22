/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer.interfaces;

import org.socraticgrid.documenttransformer.interfaces.TransformationPipeline;
import java.io.InputStream;
import java.util.Properties;

/**
 * Interface for Pipelines that work from a single data source
 * 
 * @author Jerry Goodnough
 */
public interface SingleSourcePipeline extends TransformationPipeline
{

    /**
     *
     * @param inStr
     * @return
     */
    public InputStream transformAsInputStream(InputStream inStr);

    /**
     *
     * @param inStr
     * @param props
     * @return
     */
    public InputStream transformAsInputStream(InputStream inStr, Properties props);

    /**
     *
     * @param inStr
     * @return
     */
    public String transform(InputStream inStr);

    /**
     *
     * @param inStr
     * @param props
     * @return
     */
    public String transform(InputStream inStr, Properties props);
}
