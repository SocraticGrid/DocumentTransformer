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
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.stream.StreamSource;


/**
 * This Class makes a Choice based in a specific property. The property is looked up
 * and checked against the choice map for which choice to return. The default choice
 * is returned when the property is not found or the property does not appear in the
 * choice map
 *
 * @author  Jerry Goodnough
 */
public class PropertyBasedChooser implements Chooser
{
    private static final Logger logger = Logger.getLogger(PropertyBasedChooser.class
            .getName());
    private Map<String, String> choiceMap;
    private String defaultChoice;

    private String propertyName;

    /**
     * Get the value of choiceMap.
     *
     * @return  the value of choiceMap
     */
    public Map<String, String> getChoiceMap()
    {
        return choiceMap;
    }

    /**
     * Get the value of defaultChoice.
     *
     * @return  the value of defaultChoice
     */
    public String getDefaultChoice()
    {
        return defaultChoice;
    }

    /**
     * Get the value of propertyName.
     *
     * @return  the value of propertyName
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    @Override
    public String makeChoice(StreamSource subject, Properties props)
    {
        String out = this.defaultChoice;
        String propVal = props.getProperty(this.propertyName);

        if (propVal != null)
        {

            if (this.choiceMap.containsKey(propVal))
            {
                out = choiceMap.get(propVal);
            }
            else
            {
                logger.log(Level.INFO,
                    "{0} is not mapped to a choice using the default choice",
                    propVal);
            }
        }

        return out;
    }

    /**
     * Set the value of choiceMap.
     *
     * @param  choiceMap  new value of choiceMap
     */
    public void setChoiceMap(Map<String, String> choiceMap)
    {
        this.choiceMap = choiceMap;
    }

    /**
     * Set the value of defaultChoice.
     *
     * @param  defaultChoice  new value of defaultChoice
     */
    public void setDefaultChoice(String defaultChoice)
    {
        this.defaultChoice = defaultChoice;
    }

    /**
     * Set the value of propertyName.
     *
     * @param  propertyName  new value of propertyName
     */
    public void setPropertyName(String propertyName)
    {
        this.propertyName = propertyName;
    }

}
