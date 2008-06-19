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
 * Xml与Javabean的相互转换的工具类.
 * 采用的JOX的技术.
 * <i>本段代码来源自com.simlink.emr.ts.util.TsUtil;</i>
 * @author luhao, Ma Yichao
 *
 */
public class XMLUtil {

    private static final String SPLITE_STRING = ",";
    /** XML文档的根节点 */
    public static final String ROOT_TAG = "SLEHR";
    /** XML文档的编码 */
    private static final String XML_ENCODING = "GBK";
    private static final Log LOG = LogFactory.getLog(XMLUtil.class);
    /** JOX配置 */
    private static JOXConfig joxConfig = null;
    /** 默认的系统XMLBean */
    public static final Class DEFAULT_XML_BEAN_CLASS = Object.class;
    /** 日期的转换格式.
     * JOX默认的日期格式会丢失时间的最后三位(毫秒级),引起误差.
     */
    public static final SimpleDateFormat JOX_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS z");

    /** 返回默认的JOX配置
     * @return 默认的JOX配置.如果有任务错误,将会返回null.
     */
    public static JOXConfig getDefaultConfig() {
        try {
            if (joxConfig == null) {
                //create default config
                joxConfig = JOXConfig.getDefaultConfig();

                //是否写入实现类(Bean)的名称.
                joxConfig.setWriteClassNames(false);

                //Date format
                joxConfig.setDateFormat(JOX_DATE_FORMAT);

                //joxConfig.setAtomsAsAttributes(true);

                //Tag class mapping
                HashMap<String, String> tagClassMap = new HashMap<String, String>();

                //Load tagClassMap Config from file:TagClassMap.properties.
                ResourceBundle bundle = ResourceBundle.getBundle("com.simlink.core.xml.TagClassMap");
                if (bundle != null) {
                    LOG.debug("装载TagClassMap文件成功.");
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
            LOG.error("JOX配置错误", ex);
            return null;
        }
    }

    /**
     * 把一个Ho对像转换成Xml格式的Byte数组
     * @param ho  需要转换的数据对象
     * @return  Xml格式的Byte数组
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
     * 把Xml字节数组以“byte”+“，”+“byte”的格式组合成字符串
     * @param xmlByte  需转换的字节数组
     * @return  转换后的字符串
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
     * 将bean对象转换为XML字符串.
     * @param ho  需要转换的数据对象
     * @return  转换后的字符串
     * @throws java.io.IOException 当生成字符串出错之后,抛出本异常.
     * @deprecated 替换为 beanToXmlString(Object);
     * @see beanToXmlString(Object)
     */
    @Deprecated
    public static String beanToTsValue(Object ho) throws IOException {
        return beanToXmlString(ho);
    }

    //============================ beanToXmlString(Start) ======================
    /**
     * 将bean对象转换为XML字符串.
     * @param ho  需要转换的数据对象
     * @return  转换后的字符串
     * @throws java.io.IOException 当生成字符串出错之后,抛出本异常.
     */
    public static String beanToXmlString(Object ho) throws IOException {
        return beanToXmlString(ho, null);
    }

    /**
     * 将bean对象转换为XML字符串.
     * @param ho  需要转换的数据对象
     * @return  转换后的字符串
     * @throws java.io.IOException 当生成字符串出错之后,抛出本异常.
     */
    public static String beanToXmlString(Object ho, String dtdFile) throws IOException {
        return beanToXmlString(ho, dtdFile, getDefaultConfig());
    }

    /**
     * 将bean对象转换为XML字符串.
     * @param ho  需要转换的数据对象
     * @return  转换后的字符串
     * @throws java.io.IOException 当生成字符串出错之后,抛出本异常.
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
            //这里是JOX的一个Bug. Config中的日期时间格式不能自动应用到对应的输出器中.
            //因此一定要手动写入.
            writer.setDateFormat(JOX_DATE_FORMAT);

            if (LOG.isDebugEnabled()) {
                LOG.debug("Dtd = " + dtdFile);
                LOG.debug("开始进行Bean2XML转换......");
            }
            writer.writeObject(ROOT_TAG, ho);
            xmlString = stringWriter.toString();
            if (LOG.isDebugEnabled()) {
                LOG.debug("====================================================");
                LOG.debug("将Bean对象" + ho + "转换为XML:");
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
    /** 取得DTD */
    private static DTD loadDTD(String file) throws IOException {
        //DTD
        try {
            FileReader reader = new FileReader(file);
            DTDParser dtdParser = new DTDParser(reader);

            DTD dtd = dtdParser.parse();
            reader.close();
            if (LOG.isDebugEnabled()) {
                LOG.debug("装载DTD " + file + " 成功!");
            }
            return dtd;
        } catch (IOException ex) {
            LOG.error("装载DTD " + file + " 失败!", ex);
            throw ex;
        }
    }

    /** 将没有java-class的Xml文档转换为指定了java-class的文档.
     * 采用XSLT技术.
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
     * 根据Xml字节流的数据生成默认的Bean对象
     * @param xmlByteString  字节流数据
     * @return
     * @throws java.io.IOException 当生成字符串出错之后,抛出本异常.
     * @see xmlToBean(String,Class)
     */
    public static Object xmlToBean(String xmlByteString) throws IOException {
        return xmlToBean(xmlByteString, DEFAULT_XML_BEAN_CLASS);
    }

    /**
     * 根据Xml字节流的数据生成指定的HO的对象
     * @param xmlByteString  字节流数据
     * @param hoClass 需要生成的HO对象的Class
     * @return
     * @throws java.io.IOException 当生成字符串出错之后,抛出本异常.
     */
    public static Object xmlToBean(String xmlByteString, Class<?> hoClass)
            throws IOException {
        return xmlToBean(xmlByteString, hoClass, getDefaultConfig());
    }

    /**
     * 根据Xml字节流的数据生成指定的HO的对象
     * @param xmlByteString  字节流数据
     * @param hoClass 需要生成的HO对象的Class
     * @return
     * @throws java.io.IOException 当生成字符串出错之后,抛出本异常.
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
        //以属性方式保存简单字段.
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
