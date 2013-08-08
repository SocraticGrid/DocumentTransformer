/*-
 *
 * *************************************************************************************************************
 *  Copyright (C) 2013 by Cognitive Medical Systems, Inc
 *  (http://www.cognitivemedciine.com) * * Licensed under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in compliance *
 *  with the License. You may obtain a copy of the License at * *
 *  http://www.apache.org/licenses/LICENSE-2.0 * * Unless required by applicable
 *  law or agreed to in writing, software distributed under the License is *
 *  distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied. * See the License for the specific language
 *  governing permissions and limitations under the License. *
 * *************************************************************************************************************
 *
 * *************************************************************************************************************
 *  Socratic Grid contains components to which third party terms apply. To comply
 *  with these terms, the following * notice is provided: * * TERMS AND
 *  CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION * Copyright (c) 2008,
 *  Nationwide Health Information Network (NHIN) Connect. All rights reserved. *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that * the following conditions are met:
 *  - Redistributions of source code must retain the above copyright notice, this
 *  list of conditions and the *     following disclaimer. * - Redistributions in
 *  binary form must reproduce the above copyright notice, this list of
 *  conditions and the *     following disclaimer in the documentation and/or
 *  other materials provided with the distribution. * - Neither the name of the
 *  NHIN Connect Project nor the names of its contributors may be used to endorse
 *  or *     promote products derived from this software without specific prior
 *  written permission. * * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS
 *  AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED * WARRANTIES, INCLUDING,
 *  BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  A * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER
 *  OR CONTRIBUTORS BE LIABLE FOR * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, *
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 *  OR BUSINESS INTERRUPTION HOWEVER * CAUSED AND ON ANY THEORY OF LIABILITY,
 *  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
 *  OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, * EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. * * END OF TERMS AND CONDITIONS *
 * *************************************************************************************************************
 */
package org.socraticgrid.documenttransformer;

import org.apache.commons.io.IOUtils;

import org.socraticgrid.documenttransformer.interfaces.SimpleTransformStep;
import org.socraticgrid.documenttransformer.interfaces.SingleSourcePipeline;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
 * Stock transformation pipeline - Does a serial transformation where the results of
 * one step are the input to the next step.
 *
 * @author  Jerry Goodnough
 */
public class SerialTransformPipeline implements SingleSourcePipeline
{
    private static final Logger logger = Logger.getLogger(
            SerialTransformPipeline.class.getName());
    private List<SimpleTransformStep> transformChain = null;

    // TODO: Decouple and move to a resource lookup bean in the future.
    public SerialTransformPipeline()
    {
    }

    // Factory
    public void setTransformChain(List<SimpleTransformStep> transformChain)
    {
        this.transformChain = transformChain;
    }

    @Override
    public String transform(InputStream inStr)
    {
        ByteArrayOutputStream outResultStream = this.internalTransform(inStr);

        return (outResultStream == null) ? "" : outResultStream.toString();
    }

    @Override
    public String transform(InputStream inStr, Properties props)
    {
        ByteArrayOutputStream outResultStream = this.internalTransform(inStr, props);

        return (outResultStream == null) ? "" : outResultStream.toString();
    }

    @Override
    public InputStream transformAsInputStream(InputStream inStr)
    {
        ByteArrayOutputStream outResultStream = this.internalTransform(inStr);

        return (outResultStream == null)
            ? null : new ByteArrayInputStream(outResultStream.toByteArray());
    }

    @Override
    public InputStream transformAsInputStream(InputStream inStr, Properties props)
    {
        ByteArrayOutputStream outResultStream = this.internalTransform(inStr, props);

        return (outResultStream == null)
            ? null : new ByteArrayInputStream(outResultStream.toByteArray());
    }

    protected ByteArrayOutputStream internalTransform(InputStream inStr)
    {
        StreamResult result;
        ByteArrayOutputStream outResultStream = null;
        int changes = 0;

        if (transformChain != null)
        {
            Iterator<SimpleTransformStep> itr = transformChain.iterator();
            StreamSource src = new StreamSource(inStr);

            while (itr.hasNext())
            {
                SimpleTransformStep tx = itr.next();

                // FUTURE: Provide Parameterz
                ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
                result = new StreamResult(resultStream);

                try
                {
                    boolean changed = tx.transform(src, result);
                    outResultStream = resultStream;

                    if (logger.isLoggable(Level.FINEST))
                    {
                        logger.log(Level.FINEST, result.toString());
                    }

                    // Count Changes
                    if (changed)
                    {
                        changes++;
                    }

                    if (itr.hasNext())
                    {

                        if (changed == true)
                        {

                            // Prepare the next source
                            ByteArrayInputStream bs = new ByteArrayInputStream(
                                    resultStream.toByteArray());
                            src = new StreamSource(bs);
                        }
                    }
                }
                catch (TransformerException ex)
                {
                    logger.log(Level.SEVERE, null, ex);

                    break;
                }
            }
        }
        if (changes == 0)
        {

            if (inStr.markSupported())
            {

                try
                {
                    logger.fine("No changes occured in transfornm - copying input");
                    inStr.reset();
                    outResultStream = new ByteArrayOutputStream();
                    IOUtils.copyLarge(inStr, outResultStream);
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE,
                        "Exception copying Input Stream when no transform has occured",
                        ex);
                }
            }
            else
            {
                logger.severe(
                    "Transformation did not make a change and inputstream can not reset.");
            }
        }

        return outResultStream;
    }

    protected ByteArrayOutputStream internalTransform(InputStream inStr,
        Properties props)
    {
        StreamResult result;
        ByteArrayOutputStream outResultStream = null;
        int changes = 0;
        boolean changed;

        if (transformChain != null)
        {
            Iterator<SimpleTransformStep> itr = transformChain.iterator();
            StreamSource src = new StreamSource(inStr);

            while (itr.hasNext())
            {
                SimpleTransformStep tx = itr.next();

                // FUTURE: Provide Parameterz
                ByteArrayOutputStream resultStream = new ByteArrayOutputStream();
                result = new StreamResult(resultStream);

                try
                {
                    changed = tx.transform(src, result, props);
                    outResultStream = resultStream;

                    if (Logger.getLogger(Transformer.class.getName()).isLoggable(
                                Level.FINEST))
                    {
                        Logger.getLogger(Transformer.class.getName()).log(
                            Level.FINEST, result.toString());
                    }

                    if (changed == false)
                    {
                        if (src.getInputStream().markSupported())
                        {
                            src.getInputStream().reset();
                        }
                        else
                        {
                            logger.severe(
                                "Transformation Step did not make a change and inputstream can not reset.");

                            break;
                        }

                    }
 
                    if (itr.hasNext())
                    {

                        if (changed == true)
                        {

                            // Prepare the next source
                            ByteArrayInputStream bs = new ByteArrayInputStream(
                                    resultStream.toByteArray());
                            src = new StreamSource(bs);
                        }
                    }
                  
                            
                        
                }
                catch (TransformerException ex)
                {
                    logger.log(Level.SEVERE, null, ex);

                    break;
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE, null, ex);
                }
            }
        }

        if (changes == 0)
        {

            if (inStr.markSupported())
            {

                try
                {
                    logger.fine("No changes occured in transfornm - copying input");
                    inStr.reset();
                    outResultStream = new ByteArrayOutputStream();
                    IOUtils.copyLarge(inStr, outResultStream);
                }
                catch (IOException ex)
                {
                    logger.log(Level.SEVERE,
                        "Exception copying Input Stream when no transform has occured",
                        ex);
                }
            }
            else
            {
                logger.severe(
                    "Transformation did not make a change and inputstream can not reset.");
            }
        }

        return outResultStream;
    }
}
