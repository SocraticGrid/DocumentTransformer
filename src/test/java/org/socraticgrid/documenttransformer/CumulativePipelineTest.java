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

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.socraticgrid.documenttransformer.interfaces.CumulativeTransformStep;
import org.springframework.core.io.Resource;

/**
 *
 * @author Jerry Goodnough
 */
public class CumulativePipelineTest
{
    
    public CumulativePipelineTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of getTransformChain method, of class CumulativePipeline.
     */
    @Test
    @Ignore
    public void testGetTransformChain()
    {
        System.out.println("getTransformChain");
        CumulativePipeline instance = new CumulativePipeline();
        List expResult = null;
        List result = instance.getTransformChain();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTransformChain method, of class CumulativePipeline.
     */
    @Test
    @Ignore
    public void testSetTransformChain()
    {
        System.out.println("setTransformChain");
        List<CumulativeTransformStep> TransformChain = null;
        CumulativePipeline instance = new CumulativePipeline();
        instance.setTransformChain(TransformChain);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getBaseTemplate method, of class CumulativePipeline.
     */
    @Test
    @Ignore
    public void testGetBaseTemplate()
    {
        System.out.println("getBaseTemplate");
        CumulativePipeline instance = new CumulativePipeline();
        Resource expResult = null;
        Resource result = instance.getBaseTemplate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setBaseTemplate method, of class CumulativePipeline.
     */
    @Test
    @Ignore
    public void testSetBaseTemplate()
    {
        System.out.println("setBaseTemplate");
        Resource BaseTemplate = null;
        CumulativePipeline instance = new CumulativePipeline();
        instance.setBaseTemplate(BaseTemplate);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transformAsInputStream method, of class CumulativePipeline.
     */
    @Test
    @Ignore
    public void testTransformAsInputStream_InputStream()
    {
        System.out.println("transformAsInputStream");
        InputStream inStr = null;
        CumulativePipeline instance = new CumulativePipeline();
        InputStream expResult = null;
        InputStream result = instance.transformAsInputStream(inStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transformAsInputStream method, of class CumulativePipeline.
     */
    @Test
    @Ignore
    public void testTransformAsInputStream_InputStream_Properties()
    {
        System.out.println("transformAsInputStream");
        InputStream inStr = null;
        Properties props = null;
        CumulativePipeline instance = new CumulativePipeline();
        InputStream expResult = null;
        InputStream result = instance.transformAsInputStream(inStr, props);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class CumulativePipeline.
     */
    
    @Test
    @Ignore
    public void testTransform_InputStream()
    {
        System.out.println("transform");
        InputStream inStr = null;
        CumulativePipeline instance = new CumulativePipeline();
        String expResult = "";
        String result = instance.transform(inStr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class CumulativePipeline.
     */
    @Test
    @Ignore
    public void testTransform_InputStream_Properties()
    {
        System.out.println("transform");
        InputStream inStr = null;
        Properties props = null;
        CumulativePipeline instance = new CumulativePipeline();
        String expResult = "";
        String result = instance.transform(inStr, props);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}