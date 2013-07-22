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


import org.junit.After;
import org.junit.AfterClass;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.socraticgrid.documenttransformer.interfaces.SingleSourcePipeline;

import java.io.InputStream;

import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Logger;
import static junit.framework.TestCase.fail;
import org.apache.commons.io.IOUtils;


import static org.mockito.Mockito.*;
/**
 * DOCUMENT ME!
 *
 * @author  Jerry Goodnough
 */
public class TransformerTest
{
    private static final Logger logger = Logger.getLogger(TransformerTest.class
            .getName());

    public TransformerTest()
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
     * Test of setTransformPipeline method, of class Transformer.
     */
    @Test
    public void testSetTransformPipeline()
    {
        logger.info("setTransformPipeline");

        HashMap<String, SingleSourcePipeline> transformPipeline = new HashMap<>();
        Transformer instance = new Transformer();
        instance.setTransformPipeline(transformPipeline);

    }

    /**
     * Test of transform method, of class Transformer.
     */
    @Test
    public void testTransform_3args()
    {
        logger.info("Transform_3args");

        String pipeline = "Test";
        InputStream inStr = IOUtils.toInputStream("Test Data");;
        Properties props = new Properties();
        Transformer instance = new Transformer();
  
        HashMap<String, SingleSourcePipeline> transformPipeline = new HashMap<>();
        SingleSourcePipeline mockPipeline = mock(SingleSourcePipeline.class);
        transformPipeline.put(pipeline, mockPipeline);
        instance.setTransformPipeline(transformPipeline);
        
        String expResult = null;
        String result = instance.transform(pipeline, inStr, props);
        
        assertEquals(expResult, result);
        
        
        try
        {
            verify(mockPipeline,times(0)).transform(any(InputStream.class));
            verify(mockPipeline,times(1)).transform(any(InputStream.class), any(Properties.class));
            verify(mockPipeline,times(0)).transformAsInputStream(any(InputStream.class),eq(props));
            verify(mockPipeline,times(0)).transformAsInputStream(any(InputStream.class));
        }
        catch(Exception exp)
        {
                    
            fail(exp.getMessage());
        }
        

   }

    /**
     * Test of transform method, of class Transformer.
     */
    @Test
    public void testTransform_String_InputStream()
    {
        logger.info("Transform_String_InputStream");
        
        String pipeline = "Test";
        InputStream inStr = IOUtils.toInputStream("Test Data");;

        Transformer instance = new Transformer();
  
        String expResult = null;



        HashMap<String, SingleSourcePipeline> transformPipeline = new HashMap<>();
        SingleSourcePipeline mockPipeline = mock(SingleSourcePipeline.class);
        transformPipeline.put(pipeline, mockPipeline);
        instance.setTransformPipeline(transformPipeline);
        
        String result = instance.transform(pipeline, inStr);
        
        assertEquals(expResult, result);
        
        
        try
        {
            verify(mockPipeline).transform(any(InputStream.class));
            verify(mockPipeline,never()).transform(any(InputStream.class), any(Properties.class));
            verify(mockPipeline,never()).transformAsInputStream(any(InputStream.class), any(Properties.class));
            verify(mockPipeline,never()).transformAsInputStream(any(InputStream.class));
        }
        catch(Exception exp)
        {
                    
            fail(exp.getMessage());
        }
        
    }

    /**
     * Test of transformAsStream method, of class Transformer.
     */
    @Test
    public void testTransformAsStream_3args()
    {
        logger.info("TransformAsStream_3args");
        logger.info("Transform_3args");

        String pipeline = "Test";
        InputStream inStr = IOUtils.toInputStream("Test Data");;
        Properties props = new Properties();
        Transformer instance = new Transformer();
  
        HashMap<String, SingleSourcePipeline> transformPipeline = new HashMap<>();
        SingleSourcePipeline mockPipeline = mock(SingleSourcePipeline.class);
        transformPipeline.put(pipeline, mockPipeline);
        instance.setTransformPipeline(transformPipeline);
        
        InputStream expResult = null;
        InputStream result = instance.transformAsStream(pipeline, inStr, props);
        assertEquals(expResult, result);
   
        try
        {
            verify(mockPipeline,never()).transform(any(InputStream.class));
            verify(mockPipeline,never()).transform(any(InputStream.class), any(Properties.class));
            verify(mockPipeline).transformAsInputStream(any(InputStream.class), any(Properties.class));
            verify(mockPipeline,never()).transformAsInputStream(any(InputStream.class));
        }
        catch(Exception exp)
        {
                    
            fail(exp.getMessage());
        }
    }

    /**
     * Test of transformAsStream method, of class Transformer.
     */
    @Test
    public void testTransformAsStream_String_InputStream()
    {
        logger.info("transformAsStream");
        String pipeline = "Test";
        InputStream inStr = IOUtils.toInputStream("Test Data");;

        Transformer instance = new Transformer();
  
        String expResult = null;



        HashMap<String, SingleSourcePipeline> transformPipeline = new HashMap<>();
        SingleSourcePipeline mockPipeline = mock(SingleSourcePipeline.class);
        transformPipeline.put(pipeline, mockPipeline);
        instance.setTransformPipeline(transformPipeline);
        
        InputStream result = instance.transformAsStream(pipeline, inStr);
        assertEquals(expResult, result);
   
        try
        {
            verify(mockPipeline,never()).transform(any(InputStream.class));
            verify(mockPipeline,never()).transform(any(InputStream.class), any(Properties.class));
            verify(mockPipeline,never()).transformAsInputStream(any(InputStream.class), any(Properties.class));
            verify(mockPipeline).transformAsInputStream(any(InputStream.class));
        }
        catch(Exception exp)
        {
                    
            fail(exp.getMessage());
        }
    }
}
