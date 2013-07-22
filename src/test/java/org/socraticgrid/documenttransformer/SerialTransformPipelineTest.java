/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.socraticgrid.documenttransformer;

import org.socraticgrid.documenttransformer.interfaces.SimpleTransformStep;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.fail;
import junit.framework.TestSuite;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;
/**
 *
 * @author Jerry Goodnough
 */

//@RunWith(SpringJUnit4ClassRunner.class)
// ApplicationContext will be loaded from "/applicationContext.xml" and "/applicationContext-test.xml"
// in the root of the classpath
//@ContextConfiguration(locations={"classpath:Test-BaseDocumentTransformer.xml"})
public class SerialTransformPipelineTest extends TestCase
{
   // @Autowired 
  //  private ApplicationContext ctx;
    private final static Logger logger = Logger.getLogger(SerialTransformPipelineTest.class.getName());
    
    public SerialTransformPipelineTest()
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
        logger.info("setTransformChain");
        //LinkedList<SimpleTransformStep> transformChain = new LinkedList<SimpleTransformStep>() ;
        
        LinkedList<SimpleTransformStep> transformChain = mock(LinkedList.class) ;
        transformChain.add(mock(SimpleTransformStep.class));
        
        SerialTransformPipeline instance = new SerialTransformPipeline();
        instance.setTransformChain(transformChain);

    }

    /**
     * Test of transform method, of class SerialTransformPipeline.
     */
    public void testTransformNoProperties()
    {
        logger.info("transformNoProperitees");
        InputStream inStr = IOUtils.toInputStream("Test Data");
        SerialTransformPipeline instance = new SerialTransformPipeline();
             
        LinkedList<SimpleTransformStep> transformChain =   new LinkedList<>();
        SimpleTransformStep mockTS = mock(SimpleTransformStep.class);
        try
        {
            when(mockTS.transform(any(StreamSource.class),any(StreamResult.class))).thenReturn(false);
        }
        catch (TransformerException ex)
        {
            Logger.getLogger(SerialTransformPipelineTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
 
        transformChain.add(mockTS);
        
        instance.setTransformChain(transformChain);
        //The transform step of the mock will output nothing
        String expResult = "Test Data";
        String result = instance.transform(inStr);
        
        

        try
        {
            verify(mockTS,times(1)).transform(any(StreamSource.class),any(StreamResult.class));
        }
        catch(Exception exp)
        {
                    
            fail(exp.getMessage());
        }
        
        assertEquals(expResult, result);
 
    }
    /**
     * Test of transform method, of class SerialTransformPipeline.
     */
    public void testTransformWithProperties()
    {
        logger.info("transformNoProperitees");
        InputStream inStr = IOUtils.toInputStream("Test Data");
        SerialTransformPipeline instance = new SerialTransformPipeline();
             
        LinkedList<SimpleTransformStep> transformChain =   new LinkedList<>();
        SimpleTransformStep mockTS = mock(SimpleTransformStep.class);
        try
        {
            when(mockTS.transform(any(StreamSource.class),any(StreamResult.class))).thenReturn(false);
        }
        catch (TransformerException ex)
        {
            Logger.getLogger(SerialTransformPipelineTest.class.getName()).log(Level.SEVERE, null, ex);
            fail(ex.getMessage());
        }
     
        transformChain.add(mockTS);
        
        instance.setTransformChain(transformChain);
        //The transform step of the mock will output nothing
        String expResult = "Test Data";
        Properties props = new Properties();
        String result = instance.transform(inStr,props);
        
        

        try
        {
            verify(mockTS,times(0)).transform(any(StreamSource.class),any(StreamResult.class));
            
            verify(mockTS,times(1)).transform(any(StreamSource.class),any(StreamResult.class),eq(props));
        }
        catch(Exception exp)
        {
                    
            fail(exp.getMessage());
        }
        
        assertEquals(expResult, result);
    }
  
}
