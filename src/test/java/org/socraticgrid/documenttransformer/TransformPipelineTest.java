/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer;

import java.io.InputStream;
import java.util.LinkedList;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *
 * @author Jerry Goodnough
 */

@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "/applicationContext.xml" and "/applicationContext-test.xml"
// in the root of the classpath
@ContextConfiguration(locations={"classpath:Test-BaseDocumentTransformer.xml"})
public class TransformPipelineTest extends TestCase
{
    

    public TransformPipelineTest()
    {
        super();
    }

    @BeforeClass
    public static void setUpClass() throws Exception
    {
    }

    @AfterClass
    public static void tearDownClass() throws Exception
    {
    }

    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    public static junit.framework.Test suite()
    {
        TestSuite suite = new TestSuite(TransformPipelineTest.class);
        return suite;
    }
 
    /**
     * Test of setTransformChain method, of class TransformPipeline.
     */
    @Test
    public void testSetTransformChain()
    {
        System.out.println("setTransformChain");
        LinkedList<TransformStep> transformChain = new LinkedList<TransformStep>() ;
        TransformPipeline instance = new TransformPipeline();
        instance.setTransformChain(transformChain);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class TransformPipeline.
     */
    public void testTransform()
    {
        System.out.println("transform");
        InputStream inStr = null;
        TransformPipeline instance = new TransformPipeline();
        String expResult = "";
        String result = instance.transform(inStr);
        assertEquals(expResult, result);
 
    }

  
}
