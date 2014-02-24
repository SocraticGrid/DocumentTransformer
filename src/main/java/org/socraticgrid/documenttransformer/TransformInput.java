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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.stream.StreamSource;


/**
 * Provides a mapping between names and input streams.
 *
 * @author  Jerry Goodnough
 */
public class TransformInput
{
    private static final Logger logger = Logger.getLogger(TransformInput.class
            .getName());
    private String baseStreamName;
    private StreamSource defaultStream = null;
    private byte[] defStreamArray;
    private HashMap<String, StreamSource> streamMap = new HashMap<>();

    public TransformInput()
    {
    }

    public void clear()
    {
        streamMap.clear();
    }

    public boolean containsStream(String name)
    {
        return streamMap.containsKey(name);
    }

    /**
     * Get the value of baseStream.
     *
     * @return  the value of baseStream
     */
    public StreamSource getBaseStream()
    {
        return this.getStream(baseStreamName);
    }

    /**
     * Get the value of baseStreamName.
     *
     * @return  the value of baseStreamName
     */
    public String getBaseStreamName()
    {
        return baseStreamName;
    }

    /**
     * Get the value of defaultStream.
     *
     * @return  the value of defaultStream
     */
    public StreamSource getDefaultStream()
    {
        return defaultStream;
    }

    public byte[] getDefaultStreamAsByteArray()
    {
        logger.fine("Getting default Stream");

        if (defStreamArray == null)
        {

            try
            {
                defStreamArray = IOUtils.toByteArray(defaultStream.getInputStream());
            }
            catch (IOException ex)
            {
                logger.log(Level.SEVERE, null, ex);
            }
        }

        return defStreamArray;
    }

    public StreamSource getStream(String name)
    {

        if (streamMap.containsKey(name))
        {
            return streamMap.get(name);
        }
        else
        {
            return new StreamSource(new ByteArrayInputStream(
                        getDefaultStreamAsByteArray()));
        }
    }

    /**
     * Set the value of baseStreamName.
     *
     * @param  baseStreamName  new value of baseStreamName
     */
    public void setBaseStreamName(String baseStreamName)
    {
        this.baseStreamName = baseStreamName;
    }

    /**
     * Set the value of defaultStream.
     *
     * @param  defaultStream  new value of defaultStream
     */
    public void setDefaultStream(StreamSource defaultStream)
    {
        this.defaultStream = defaultStream;
    }

    public void setStream(String name, StreamSource stream)
    {
        streamMap.put(name, stream);
    }
}
