/*
 * To change this template, choose Tools | Templates and open the template in the
 * editor.
 */
package org.socraticgrid.documenttransformer;

import junit.framework.TestCase;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

import org.junit.Test;

import org.junit.runner.RunWith;

import org.socraticgrid.documenttransformer.interfaces.SingleSourcePipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.context.ApplicationContext;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.InputStream;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.TestCase.fail;


/**
 * DOCUMENT ME!
 *
 * @author  Jerry Goodnough
 */
@ContextConfiguration(locations = { "classpath:ITTest-BaseDocumentTransformer.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
// @DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ITTransformerTest extends TestCase
{
    private static final Logger logger = Logger.getLogger(ITTransformerTest.class
            .getName());
    @Autowired
    ApplicationContext ctx;
    @Autowired
    @Qualifier("Xform")
    private Transformer instance;

    public ITTransformerTest()
    {
        super();
    }

    /**
     * Test of transform method, of class Transformer.
     */
    @Test
    public void testMultipathTransform() throws Exception
    {

        if (logger.isLoggable(Level.FINE))
        {
            System.setProperty("jaxp.debug", "1");
        }

        String pipeline = "Medications";
        String pipeline2 = "Medications_Base";
        assertNotNull("Transformer instance is null", instance);
        logger.log(Level.INFO, "Test Multipath transfrom on piplines {0} and {1}",
            new Object[] { pipeline, pipeline2 });

        Resource res = new ClassPathResource("PatientDataRequest_meds_10013.xml");
        assertNotNull("Failed to load sample data", res);

        InputStream inStr = res.getInputStream();
        assertNotNull("Failed to get inputStream", inStr);

        String result = instance.transform(pipeline, inStr);
        inStr = res.getInputStream();
        assertNotNull(pipeline + " returned a null stream", result);
        assertFalse(pipeline + " returned an empty stream", result.isEmpty());

        String result2 = instance.transform(pipeline2, inStr);

        if (result2.contains("PrescriptionList") == false)
        {
            logger.info(" -- Text of failing item --");
            logger.info(result2);
            fail(pipeline2 + " returned something other then a PrescriptionList");
        }
       if (result.contains("\"facts\":[") == false)
        {
            logger.info(" -- Text of failing item --");
            logger.info(result);
            fail(pipeline + " returned something other then a json object");

        }

        if (logger.isLoggable(Level.FINE))
        {
            logger.fine("--- FHIR to ---");
            logger.fine(result2);
            logger.fine("--- Transforms to ---");
            logger.fine(result);
        }
    }

    /**
     * Test of setTransformPipeline method, of class Transformer.
     */
    public void testSetTransformPipeline()
    {
        logger.info("Test setTransfromPipeline");
        assertNotNull("Transformer instance is null", instance);

        HashMap<String, SingleSourcePipeline> transformPipeline = null;
        instance.setTransformPipeline(transformPipeline);
    }

    @Test
    public void testStaticTransform() throws Exception
    {

        if (logger.isLoggable(Level.FINE))
        {
            System.setProperty("jaxp.debug", "1");
        }

        logger.info("Test StaticTransform with Allergies");
        assertNotNull("Transformer instance is null", instance);

        String pipeline = "Allergies_FHIR";
        String pipeline2 = "Allergies_JSON";
        Resource res = new ClassPathResource("PatientDataRequest_meds_10013.xml");
        assertNotNull("Resource is null", res);

        InputStream inStr = res.getInputStream();
        String result = instance.transform(pipeline, inStr);
        assertNotNull("Transform returned null", result);
        assertFalse("Transform returned an empty string", result.isEmpty());

        if (logger.isLoggable(Level.FINE))
        {
            logger.fine("--- Static FHIR to ---");
            logger.fine(result);
            assertNotNull(result);
            inStr = res.getInputStream();

            String result2 = instance.transform(pipeline2, inStr);
            logger.fine("--- Transforms to ---");
            logger.fine(result2);
            assertNotNull(result2);
        }
    }
}
