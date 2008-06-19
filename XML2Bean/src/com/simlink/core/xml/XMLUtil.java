package com.simlink.core.xml;

import com.simlink.core.xml.*;
import com.wutka.dtd.DTD;
import com.wutka.dtd.DTDParser;
import java.io.ByteArrayOutputStream;

import javax.xml.transform.TransformerException;
import org.apache.commons.lang.ArrayUtils;

import com.wutka.jox.JOXBeanOutputStream;
import com.wutka.jox.JOXBeanReader;
import com.wutka.jox.JOXBeanWriter;
import com.wutka.jox.JOXConfig;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

/**
 * Xml��Javabean���໥ת���Ĺ�����.
 * ���õ�JOX�ļ���.
 * <i>���δ�����Դ��com.simlink.emr.ts.util.TsUtil;</i>
 * @author luhao, Ma Yichao
 *
 */
public class XMLUtil {

    private static final String SPLITE_STRING = ",";
    /** XML�ĵ��ĸ��ڵ� */
    public static final String ROOT_TAG = "SLEHR";
    /** XML�ĵ��ı��� */
    private static final String XML_ENCODING = "GBK";
    private static final Log LOG = LogFactory.getLog(XMLUtil.class);
    /** JOX���� */
    private static JOXConfig joxConfig = null;
    /** Ĭ�ϵ�ϵͳXMLBean */
    public static final Class DEFAULT_XML_BEAN_CLASS = Object.class;
    /** ���ڵ�ת����ʽ.
     * JOXĬ�ϵ����ڸ�ʽ�ᶪʧʱ��������λ(���뼶),�������.
     */
    public static final SimpleDateFormat JOX_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS z");

    /** ����Ĭ�ϵ�JOX����
     * @return Ĭ�ϵ�JOX����.������������,���᷵��null.
     */
    public static JOXConfig getDefaultConfig() {
        try {
            if (joxConfig == null) {
                //create default config
                joxConfig = JOXConfig.getDefaultConfig();

                //�Ƿ�д��ʵ����(Bean)������.
                joxConfig.setWriteClassNames(false);

                //Date format
                joxConfig.setDateFormat(JOX_DATE_FORMAT);

                //joxConfig.setAtomsAsAttributes(true);

                //Tag class mapping
                HashMap<String, String> tagClassMap = new HashMap<String, String>();

                //Load tagClassMap Config from file:TagClassMap.properties.
                ResourceBundle bundle = ResourceBundle.getBundle("com.simlink.core.xml.TagClassMap");
                if (bundle != null) {
                    LOG.debug("װ��TagClassMap�ļ��ɹ�.");
                    for (Iterator<String> it = bundle.keySet().iterator(); it.hasNext();) {
                        String key = it.next();
                        tagClassMap.put(key, bundle.getString(key));
                    }
                }
                if (!tagClassMap.isEmpty()) {

                    //tagClassMap.put(ROOT_TAG, XMLUtil);

                    joxConfig.setTagClassMapping(tagClassMap);
                }

                joxConfig.setEncoding(XML_ENCODING);
            }
            return (JOXConfig) joxConfig.clone();
        } catch (CloneNotSupportedException ex) {
            LOG.error("JOX���ô���", ex);
            return null;
        }
    }

    /**
     * ��һ��Ho����ת����Xml��ʽ��Byte����
     * @param ho  ��Ҫת�������ݶ���
     * @return  Xml��ʽ��Byte����
     * @throws IOException
     */
    public static byte[] beanToXmlByte(Object ho) throws IOException {
        byte[] array = null;
        JOXBeanOutputStream joxOut = null;
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            joxOut = new JOXBeanOutputStream(byteOut, XML_ENCODING);

            joxOut.writeObject(ROOT_TAG, ho);
            //joxOut.close();
            array = byteOut.toByteArray();
            byteOut.close();
        } catch (IOException ex) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("XmlUtil error!", ex);
            }
            throw ex;
        } finally {
            //close all stream;
            if (joxOut != null) {
                joxOut.close();
            }
        }


        return array;
    }

    /**
     * ��Xml�ֽ������ԡ�byte��+������+��byte���ĸ�ʽ��ϳ��ַ���
     * @param xmlByte  ��ת�����ֽ�����
     * @return  ת������ַ���
     */
    public static String xmlToByteString(byte[] xmlByte) {
        StringBuffer xmlByteString = new StringBuffer();
        if (ArrayUtils.isEmpty(xmlByte)) {
            xmlByteString.append("");
        } else {
            for (int i = 0; i < xmlByte.length; i++) {
                byte bt = xmlByte[i];
                xmlByteString.append(bt).append(SPLITE_STRING);
            }
        }
        return xmlByteString.toString();
    }

    /**
     * ��bean����ת��ΪXML�ַ���.
     * @param ho  ��Ҫת�������ݶ���
     * @return  ת������ַ���
     * @throws java.io.IOException �������ַ�������֮��,�׳����쳣.
     * @deprecated �滻Ϊ beanToXmlString(Object);
     * @see beanToXmlString(Object)
     */
    @Deprecated
    public static String beanToTsValue(Object ho) throws IOException {
        return beanToXmlString(ho);
    }

    //============================ beanToXmlString(Start) ======================
    /**
     * ��bean����ת��ΪXML�ַ���.
     * @param ho  ��Ҫת�������ݶ���
     * @return  ת������ַ���
     * @throws java.io.IOException �������ַ�������֮��,�׳����쳣.
     */
    public static String beanToXmlString(Object ho) throws IOException {
        return beanToXmlString(ho, null);
    }

    /**
     * ��bean����ת��ΪXML�ַ���.
     * @param ho  ��Ҫת�������ݶ���
     * @return  ת������ַ���
     * @throws java.io.IOException �������ַ�������֮��,�׳����쳣.
     */
    public static String beanToXmlString(Object ho, String dtdFile) throws IOException {
        return beanToXmlString(ho, dtdFile, getDefaultConfig());
    }

    /**
     * ��bean����ת��ΪXML�ַ���.
     * @param ho  ��Ҫת�������ݶ���
     * @return  ת������ַ���
     * @throws java.io.IOException �������ַ�������֮��,�׳����쳣.
     */
    public static String beanToXmlString(Object ho, String dtdFile, JOXConfig xConfig) throws IOException {
        String xmlString = "";
        JOXBeanWriter writer = null;
        try {
            //xmlString = xmlToByteString(beanToXmlByte(ho));
            //==================================================================
            DTD dtd = dtdFile == null ? null : loadDTD(dtdFile);
            //==================================================================

            StringWriter stringWriter = new StringWriter();
            if (dtd != null) {
                writer = new JOXBeanWriter(dtd, stringWriter, XML_ENCODING);
            } else {
                writer = new JOXBeanWriter(stringWriter, XML_ENCODING);
            }
            writer.setConfig(xConfig);
            //������JOX��һ��Bug. Config�е�����ʱ���ʽ�����Զ�Ӧ�õ���Ӧ���������.
            //���һ��Ҫ�ֶ�д��.
            writer.setDateFormat(JOX_DATE_FORMAT);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Dtd = " + dtdFile);
                LOG.debug("��ʼ����Bean2XMLת��......");
            }
            writer.writeObject(ROOT_TAG, ho);
            xmlString = stringWriter.toString();
            if (LOG.isDebugEnabled()) {
                LOG.debug("====================================================");
                LOG.debug("��Bean����" + ho + "ת��ΪXML:");
                LOG.debug("\n" + xmlString);
                LOG.debug("====================================================");
            }
        } catch (Throwable e) {

            if (e instanceof IOException) {
                LOG.debug("XmlUtil error!", e);
                throw (IOException) e;
            } else {
                LOG.error("XmlUtil error!", e);
            }
        } finally {
            //close all io
            if (writer != null) {
                writer.close();
            }
        }

        return xmlString;
    }

    //============================ beanToXmlString(End) ======================
    /** ȡ��DTD */
    private static DTD loadDTD(String file) throws IOException {
        //DTD
        try {
            FileReader reader = new FileReader(file);
            DTDParser dtdParser = new DTDParser(reader);

            DTD dtd = dtdParser.parse();
            reader.close();
            if (LOG.isDebugEnabled()) {
                LOG.debug("װ��DTD " + file + " �ɹ�!");
            }
            return dtd;
        } catch (IOException ex) {
            LOG.error("װ��DTD " + file + " ʧ��!", ex);
            throw ex;
        }
    }

    /** ��û��java-class��Xml�ĵ�ת��Ϊָ����java-class���ĵ�.
     * ����XSLT����.
     */
    private static Document appendJavaClass(Document doc, String xslt)
            throws TransformerException {
        try {
            Transformer transformer = TransformerFactory.newInstance().
                    newTransformer(new StreamSource(new File(xslt)));
            Document docOut = DocumentBuilderFactory.newInstance().
                    newDocumentBuilder().newDocument();
            DOMResult out = new DOMResult(docOut);
            transformer.transform(new DOMSource(doc.getDocumentElement()), out);
            return docOut;
        } catch (Exception ex) {
            throw new TransformerException("Append Java-Class failed!", ex);
        }
    //return out.getDocument();
    }

    //============================ xmlToBean(Start) ======================
    /**
     * ����Xml�ֽ�������������Ĭ�ϵ�Bean����
     * @param xmlByteString  �ֽ�������
     * @return
     * @throws java.io.IOException �������ַ�������֮��,�׳����쳣.
     * @see xmlToBean(String,Class)
     */
    public static Object xmlToBean(String xmlByteString) throws IOException {
        return xmlToBean(xmlByteString, DEFAULT_XML_BEAN_CLASS);
    }

    /**
     * ����Xml�ֽ�������������ָ����HO�Ķ���
     * @param xmlByteString  �ֽ�������
     * @param hoClass ��Ҫ���ɵ�HO�����Class
     * @return
     * @throws java.io.IOException �������ַ�������֮��,�׳����쳣.
     */
    public static Object xmlToBean(String xmlByteString, Class<?> hoClass)
            throws IOException {
        return xmlToBean(xmlByteString, hoClass, getDefaultConfig());
    }

    /**
     * ����Xml�ֽ�������������ָ����HO�Ķ���
     * @param xmlByteString  �ֽ�������
     * @param hoClass ��Ҫ���ɵ�HO�����Class
     * @return
     * @throws java.io.IOException �������ַ�������֮��,�׳����쳣.
     */
    public static Object xmlToBean(String xmlByteString, Class<?> hoClass, JOXConfig xConfig)
            throws IOException {
        Object bean = null;
        JOXBeanReader reader = null;
        try {

            String[] xmlByteStringS = xmlByteString.split(SPLITE_STRING);
            if (ArrayUtils.isEmpty(xmlByteStringS)) {
                bean = null;
            } else {
                StringReader in = new StringReader(xmlByteString);
                reader = new JOXBeanReader(in);
                reader.setConfig(xConfig);

                bean = reader.readObject(hoClass);
            }

        } catch (IOException exc) {
            //exc.printStackTrace();
            if (LOG.isDebugEnabled()) {
                LOG.debug("===================================================");
                LOG.debug("XML:\n" + xmlByteString);
                LOG.debug("===================================================");
                LOG.debug("xml to Bean(" + hoClass + ") error!", exc);

            }
            throw exc;
        } finally {
            //close all io;
            if (reader != null) {
                reader.close();
            }
        }
        return bean;
    }

    /** Bean to DTD */
    public static void beanToDTD(List<Class> beanClassList) throws IOException {

        PrintWriter out = null;
        BeanToDTD converter = new BeanToDTD();
        //�����Է�ʽ������ֶ�.
        converter.setFieldsAsAttributes(false);
        int caseType = TagStyleType.MIXEDCASE;
        int separator = TagStyleType.NONE;
        converter.setTagStyle(new TagStyleType(caseType, separator));

        try {

//            for (Class c : beanClassList) {
//                StringBuffer className = new StringBuffer(c.getResource("").getFile());
//                className.append(c.getSimpleName());
//                if (LOG.isDebugEnabled()) {
//                    LOG.debug("build DTD for Class:" + c);
//                }
//
//                DTD dtd = converter.makeBeanDTD(c, c.getSimpleName());
//                
//                className.append(".dtd");
//
//                out = new PrintWriter(new FileWriter(className.toString()));
//                dtd.write(out);
//                out.close();
//                out = null;
//                if (LOG.isInfoEnabled()) {
//                    LOG.info("build DTD file:" + className.toString());
//                }
//            }
            DTD dtd = converter.makeBeanDTD(beanClassList);

            StringBuffer fileName = new StringBuffer("e:/temp/XmlBean.dtd");//XMLUtil.class.getResource("").getFile());
            //fileName.append("XMLBean.dtd");

            out = new PrintWriter(new FileWriter(fileName.toString()));
            dtd.write(out);
            out.close();
            out = null;
            if (LOG.isInfoEnabled()) {
                LOG.info("build DTD file:" + fileName.toString());
            }
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }
    //============================ xmlToBean(End) ======================
    /*
    public static void main(String[] args) {
    StringBuffer sb = new StringBuffer();
    sb.append("from Temporarystorage where ygbh = " + 1);
    sb.append(" and brbh = " + 1);
    sb.append(" and pageid = '" + 1 + "'");
    sb.append(" and mxid = '" + 1 + "'");
    System.out.println(sb.toString());
    }*/
}
