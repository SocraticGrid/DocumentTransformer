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
package org.socraticgrid.documenttransformer.multipleinputtransfomsteps;

import org.socraticgrid.documenttransformer.interfaces.MultipleInputTransformStep;
import org.socraticgrid.documenttransformer.TransformInput;
import org.socraticgrid.documenttransformer.interfaces.CumulativeTransformStep;

import org.springframework.beans.factory.annotation.Required;

import java.util.Properties;

import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * Bridge between a Merge Stream and a Cumulative Transform step.
 *
 * @author  Jerry Goodnough
 */
public class CumulativeMergeBridge implements MultipleInputTransformStep
{
    private boolean allowMissing = true;
    private String inputStreamName;
    private CumulativeTransformStep transformStep;

    /**
     * Get the value of inputStreamName.
     *
     * @return  the value of inputStreamName
     */
    public String getInputStreamName()
    {
        return inputStreamName;
    }

    /**
     * Get the value of transformStep.
     *
     * @return  the value of transformStep
     */
    public CumulativeTransformStep getTransformStep()
    {
        return transformStep;
    }

    /**
     * Get the value of allowMissing.
     *
     * @return  the value of allowMissing
     */
    public boolean isAllowMissing()
    {
        return allowMissing;
    }

    /**
     * Set the value of allowMissing. When true the transformation not fail when the
     * bridged stream is not present
     *
     * @param  allowMissing  new value of allowMissing
     */
    public void setAllowMissing(boolean allowMissing)
    {
        this.allowMissing = allowMissing;
    }

    /**
     * Set the value of inputStreamName.
     *
     * @param  inputStreamName  new value of inputStreamName
     */
    @Required
    public void setInputStreamName(String inputStreamName)
    {
        this.inputStreamName = inputStreamName;
    }

    /**
     * Set the value of transformStep.
     *
     * @param  transformStep  new value of transformStep
     */
    @Required
    public void setTransformStep(CumulativeTransformStep transformStep)
    {
        this.transformStep = transformStep;
    }

    @Override
    public boolean transform(StreamSource base, TransformInput input,
        StreamResult result) throws TransformerException
    {

        if (input.containsStream(inputStreamName))
        {
            return transformStep.transform(base, input.getStream(inputStreamName),
                    result);
        }
        else
        {

            if (this.allowMissing)
            {
                return false;
            }
            else
            {
                throw new TransformerException("Not such input stream: " +
                    inputStreamName);
            }
        }
    }

    @Override
    public boolean transform(StreamSource base, TransformInput input,
        StreamResult result, Properties props) throws TransformerException
    {

        if (input.containsStream(inputStreamName))
        {
            return transformStep.transform(base, input.getStream(inputStreamName),
                    result, props);
        }
        else
        {

            if (this.allowMissing)
            {
                return false;
            }
            else
            {
                throw new TransformerException("Not such input stream: " +
                    inputStreamName);
            }
        }
    }
}
