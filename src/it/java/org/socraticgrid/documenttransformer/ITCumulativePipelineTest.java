/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer;

import org.socraticgrid.documenttransformer.interfaces.SimpleTransformStep;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
/**
 *
 * @author Jerry Goodnough
 */

@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "/applicationContext.xml" and "/applicationContext-test.xml"
// in the root of the classpath
@ContextConfiguration(locations={"classpath:ITTest-BaseDocumentTransformer.xml"})
public class ITCumulativePipelineTest extends TestCase
{
    
    @Autowired 
    private ApplicationContext ctx;
    
    public ITCumulativePipelineTest()
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
        TestSuite suite = new TestSuite(SerialTransformPipelineTest.class);
        return suite;
    }
 
    /**
     * Test of setTransformChain method, of class SerialTransformPipeline.
     */
    @Test
    public void testSetTransformChain()
    {
        System.out.println("setTransformChain");
        LinkedList<SimpleTransformStep> transformChain = new LinkedList<SimpleTransformStep>() ;
        SerialTransformPipeline instance = new SerialTransformPipeline();
        instance.setTransformChain(transformChain);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of transform method, of class SerialTransformPipeline.
     */
    public void testTransform()
    {
        System.out.println("transform");
        InputStream inStr = null;
        SerialTransformPipeline instance = new SerialTransformPipeline();
        String expResult = "";
        String result = instance.transform(inStr);
        assertEquals(expResult, result);
 
    }

  
}
