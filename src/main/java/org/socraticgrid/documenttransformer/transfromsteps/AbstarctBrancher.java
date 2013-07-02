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
package org.socraticgrid.documenttransformer.transfromsteps;

import org.socraticgrid.documenttransformer.interfaces.Chooser;
import org.socraticgrid.documenttransformer.interfaces.CumulativeTransformStep;
import org.socraticgrid.documenttransformer.interfaces.SimpleTransformStep;
import org.socraticgrid.documenttransformer.interfaces.TransformStep;

import java.util.Map;
import java.util.Properties;

import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


/**
 * This concrete subclasses of this class are used to branch transformation paths.
 * The branch logic yields a transformation step.
 *
 * @author  Jerry Goodnough
 */
public abstract class AbstarctBrancher implements TransformStep, SimpleTransformStep,
    CumulativeTransformStep
{

    private String branchOnIntermediate;

    private Chooser chooser;

    private Map<String, TransformStep> choiceMap;

    public abstract String getTransformChoice(StreamSource check, Properties props);

    /**
     * Get the value of branchOnIntermediate When set the intermediate transform form
     * is passed to the branching logic. Ignored for simple transform steps.
     *
     * @return  the value of branchOnIntermediate
     */
    public String getBranchOnIntermediate()
    {
        return branchOnIntermediate;
    }

    /**
     * Get the value of chooser.
     *
     * @return  the value of chooser
     */
    public Chooser getChooser()
    {
        return chooser;
    }

    /**
     * Get the value of choiceMap.
     *
     * @return  the value of choiceMap
     */
    public Map<String, TransformStep> getPipelineMap()
    {
        return choiceMap;
    }

    /**
     * Set the value of branchOnIntermediate.
     *
     * @param  branchOnIntermediate  new value of branchOnIntermediate
     */
    public void setBranchOnIntermediate(String branchOnIntermediate)
    {
        this.branchOnIntermediate = branchOnIntermediate;
    }

    /**
     * Set the value of chooser.
     *
     * @param  chooser  new value of chooser
     */
    public void setChooser(Chooser chooser)
    {
        this.chooser = chooser;
    }

    /**
     * Set the value of choiceMap.
     *
     * @param  choiceMap  new value of choiceMap
     */
    public void setPipelineMap(Map<String, TransformStep> choiceMap)
    {
        this.choiceMap = choiceMap;
    }


    @Override
    public boolean transform(StreamSource src, StreamResult result)
        throws TransformerException
    {
        return this.transform(src, result, null);
    }

    @Override
    public boolean transform(StreamSource src, StreamResult result, Properties props)
        throws TransformerException
    {
        boolean out = false;
        // First we push the request to the chooser
        String choice = chooser.makeChoice(src, props);
        // Then we Vector on the results
        if (choice != null)
        {

            if (this.choiceMap.containsKey(choice))
            {
                TransformStep ts = choiceMap.get(choice);
                if (ts instanceof SimpleTransformStep)
                {
                    SimpleTransformStep sts = (SimpleTransformStep)ts;
                    out = sts.transform(src, result, props);
                }                
            }
        }
        return out;
    }

    @Override
    public boolean transform(StreamSource base, StreamSource src, StreamResult result)
        throws TransformerException
    {
       return this.transform(base, src, result, null);
    }

    @Override
    public boolean transform(StreamSource base, StreamSource src, StreamResult result, Properties props) throws TransformerException
    {
      
        boolean out = false;
        // First we push the request to the chooser
        String choice = chooser.makeChoice(src, props);
        // Then we Vector on the results
        if (choice != null)
        {

            if (this.choiceMap.containsKey(choice))
            {
                TransformStep ts = choiceMap.get(choice);
                if (ts instanceof CumulativeTransformStep)
                {
                    CumulativeTransformStep cts = (CumulativeTransformStep)ts;
                    out = cts.transform(base,src, result, props);
                }                
            }
        }
        return out;
    }
}
