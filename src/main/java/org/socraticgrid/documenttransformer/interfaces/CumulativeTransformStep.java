/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer.interfaces;

import java.util.Properties;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Jerry Goodnough
 */
public interface CumulativeTransformStep extends TransformStep
{
    public boolean transform(StreamSource base, StreamSource src, StreamResult result) throws TransformerException;
    public boolean transform(StreamSource base, StreamSource src, StreamResult result, Properties props) throws TransformerException;   
}
