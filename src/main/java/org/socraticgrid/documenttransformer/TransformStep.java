/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer;

import java.util.Properties;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Jerry Goodnough
 */
public interface TransformStep {

    public void transform(StreamSource src, StreamResult result) throws TransformerException;
    public void transform(StreamSource src, StreamResult result, Properties props) throws TransformerException;
    //TODO: Add parameter support
}
