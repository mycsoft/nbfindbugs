/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simlink.core.xml;

import com.wutka.jox.JOXConfig;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Ma Yichao
 */
public class XMLUtilTest extends TestCase {
    
    public XMLUtilTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of getDefaultConfig method, of class XMLUtil.
     */
    public void testGetDefaultConfig() {
        System.out.println("getDefaultConfig");
        JOXConfig expResult = null;
        JOXConfig result = XMLUtil.getDefaultConfig();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beanToXmlByte method, of class XMLUtil.
     */
    public void testBeanToXmlByte() throws Exception {
        System.out.println("beanToXmlByte");
        Object ho = null;
        byte[] expResult = null;
        byte[] result = XMLUtil.beanToXmlByte(ho);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of xmlToByteString method, of class XMLUtil.
     */
    public void testXmlToByteString() {
        System.out.println("xmlToByteString");
        byte[] xmlByte = null;
        String expResult = "";
        String result = XMLUtil.xmlToByteString(xmlByte);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beanToTsValue method, of class XMLUtil.
     */
    public void testBeanToTsValue() throws Exception {
        System.out.println("beanToTsValue");
        Object ho = null;
        String expResult = "";
        String result = XMLUtil.beanToTsValue(ho);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beanToXmlString method, of class XMLUtil.
     */
    public void testBeanToXmlString() throws Exception {
        System.out.println("beanToXmlString");
        HashMap ho = new HashMap();
        ho.put("123", "dasfasd");
        ho.put("abc", "deo");
        HashMap ho2 = new HashMap();
        ho2.put("123", "dasfasd");
        ho2.put("abc", "deo");
        
        ho.put("dsd", ho2);
        
        String expResult = "";
        String result = XMLUtil.beanToXmlString(ho);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of xmlToBean method, of class XMLUtil.
     */
    public void testXmlToBean() throws Exception {
        System.out.println("xmlToBean");
        String xmlByteString = "";
        Object expResult = null;
        Object result = XMLUtil.xmlToBean(xmlByteString);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of beanToDTD method, of class XMLUtil.
     */
    public void testBeanToDTD() throws Exception {
        System.out.println("beanToDTD");
        List<Class> beanClassList = new ArrayList<Class>();
        beanClassList.add(XMLUtil.class);
        //Package p = Package.getPackage("com.simlink.emr.data.ho");
        
        XMLUtil.beanToDTD(beanClassList);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}
