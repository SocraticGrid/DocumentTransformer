/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer;

import org.socraticgrid.documenttransformer.interfaces.SingleSourcePipeline;
import org.socraticgrid.documenttransformer.interfaces.CumulativeTransformStep;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.springframework.core.io.Resource;

/**
 * The Cumulative Pipeline operates by providing each step with the
 * original input (unless swapped by a step) and the output of the prior step to
 * @author Jerry Goodnough
 */
public class CumulativePipeline implements SingleSourcePipeline
{

    private List<CumulativeTransformStep> TransformChain;

    /**
     * Get the value of TransformChain
     *
     * @return the value of TransformChain
     */
    public List<CumulativeTransformStep> getTransformChain()
    {
        return TransformChain;
    }

    /**
     * Set the value of TransformChain
     *
     * @param TransformChain new value of TransformChain
     */
    public void setTransformChain(List<CumulativeTransformStep> TransformChain)
    {
        this.TransformChain = TransformChain;
    }

    private Resource BaseTemplate;

    /**
     * Get the value of BaseTemplate
     *
     * @return the value of BaseTemplate
     */
    public Resource getBaseTemplate()
    {
        return BaseTemplate;
    }

    /**
     * Set the value of BaseTemplate
     *
     * @param BaseTemplate new value of BaseTemplate
     */
    public void setBaseTemplate(Resource BaseTemplate)
    {
        this.BaseTemplate = BaseTemplate;
    }

    @Override
    public InputStream transformAsInputStream(InputStream inStr)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public InputStream transformAsInputStream(InputStream inStr, Properties props)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String transform(InputStream inStr)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String transform(InputStream inStr, Properties props)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
