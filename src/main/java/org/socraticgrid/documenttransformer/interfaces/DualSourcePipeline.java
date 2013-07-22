/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer.interfaces;

import java.io.InputStream;
import java.util.Properties;

/**
 * Interface for Pipelines that work from a single data source
 * 
 * @author Jerry Goodnough
 */
public interface DualSourcePipeline extends TransformationPipeline
{

    /**
     *
     * @param inStr
     * @param props
     * @return
     */
    public InputStream transformAsInputStream(InputStream inStr,InputStream base, Properties props);


}
