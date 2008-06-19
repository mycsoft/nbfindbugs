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
 * XMLתΪBean�Ĺ�����.
 * @author Ma Yichao
 */
public class XML2BeanUtil {
    //TODO ����Log
    private XML2BeanUtil() {
    }

    //xml to bean
    /**
     * ����InputStream����Bean����.
     * <br>
     * 
     * @param inputStream
     * @return
     * @throws com.simlink.core.xml.XMLParseException ���������������κδ���ʱ,�׳����쳣.
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
                throw new XMLParseException("����InputStream����Bean����ʱ����", ex);
            }
        }
    }

    /**
     * ����XML��Ϣ����Bean����.
     * <P>
     * ���������ݷֲ����,��һ����SLEHR.�ڶ����ǲ��˻�����Ϣ,ҽԺ��Ϣ.
     * �Ƚ�xml�е�java-class����:����xslt�ķ�ʽ.     
     * 
     * <br>���ݵ�ǰ�Ľڵ�������������,�������Ľ�����ʽ.
     * </P>
     */
    private static Object xml2Bean(Document xml) throws XMLParseException {
        if (xml == null) {
            throw new NullPointerException("XML����Ϊ��.");
        }
        Object xmlBean = null;
        try {
            //Root node.
            Element root = xml.getDocumentElement();
            //Parse ������Ϣ
            //���� ������Ϣ.

            return xmlBean;
        } catch (Exception ex) {
            //TODO log
            if (ex instanceof XMLParseException) {
                throw (XMLParseException) ex;
            } else {
                throw new XMLParseException("����XML��Ϣ����Bean����ʱ����", ex);
            }
        }
    }

    /** ����XML */
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
