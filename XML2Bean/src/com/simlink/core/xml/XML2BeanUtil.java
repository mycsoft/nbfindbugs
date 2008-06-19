/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.core.xml;

import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * XML转为Bean的工具类.
 * @author Ma Yichao
 */
public class XML2BeanUtil {
    //TODO 加入Log
    private XML2BeanUtil() {
    }

    //xml to bean
    /**
     * 根据InputStream生成Bean对象.
     * <br>
     * 
     * @param inputStream
     * @return
     * @throws com.simlink.core.xml.XMLParseException 当解析过程中有任何错误时,抛出此异常.
     */
    public static Object xml2BeanByInputStream(InputStream inputStream)
            throws XMLParseException {
        try {
            return xml2Bean(parseSLEHRXML(inputStream));
        } catch (Exception ex) {
            //TODO log
            if (ex instanceof XMLParseException) {
                throw (XMLParseException) ex;
            } else {
                throw new XMLParseException("根据InputStream生成Bean对象时出错", ex);
            }
        }
    }

    /**
     * 根据XML信息生成Bean对象.
     * <P>
     * 将所有数据分层解析,第一层是SLEHR.第二层是病人基本信息,医院信息.
     * 先将xml中的java-class补齐:采用xslt的方式.     
     * 
     * <br>根据当前的节点的类名与解析器,决定他的解析方式.
     * </P>
     */
    private static Object xml2Bean(Document xml) throws XMLParseException {
        if (xml == null) {
            throw new NullPointerException("XML数据为空.");
        }
        Object xmlBean = null;
        try {
            //Root node.
            Element root = xml.getDocumentElement();
            //Parse 病人信息
            //解析 就诊信息.

            return xmlBean;
        } catch (Exception ex) {
            //TODO log
            if (ex instanceof XMLParseException) {
                throw (XMLParseException) ex;
            } else {
                throw new XMLParseException("根据XML信息生成Bean对象时出错", ex);
            }
        }
    }

    /** 解析XML */
    public static Document parseSLEHRXML(InputStream inputStream)
            throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        //======================================================================
        //Config parser
        factory.setValidating(true);
        //======================================================================
        Document document = factory.newDocumentBuilder().parse(inputStream);
        return document;
    }
    //bean to xml
}
