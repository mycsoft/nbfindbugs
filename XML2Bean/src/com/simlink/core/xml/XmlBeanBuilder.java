/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.core.xml;

import com.wutka.jox.JOXBeanBuilder;
import com.wutka.jox.JOXConfig;
import com.wutka.jox.JOXUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Ma Yichao
 */
public class XmlBeanBuilder {

    private static Logger log = Logger.getLogger(XmlBeanBuilder.class);
    Document xml = null;
    Object bean = null;
    final String JAVA_CLASS = "java-class";
    JOXConfig xConfig = JOXConfig.getDefaultConfig();

    public XmlBeanBuilder(Document xml) {
        if (xml == null) {
            throw new NullPointerException("�޷��������ĵ�");
        }
        this.xml = xml;
    }

    /** �������ݶ���. */
    public Object getBean() {
        if (bean == null) {
        //TODO create bean
        }
        return bean;
    }

    /** ����XML */
    protected void parseXML() throws XMLParseException {
        if (xml == null) {
            throw new NullPointerException("�޷��������ĵ�");
        }
        
        Element root = xml.getDocumentElement();

        HashMap hashMap = new HashMap();    //������

        NodeList firstLayer = root.getChildNodes(); //��һ��Ķ���.

        for (int i = 0; i < firstLayer.getLength(); i++) {
            try {
                Node node = firstLayer.item(i);
                XmlNodeParser nodeParser = getNodeParser(node);

                Object o = nodeParser.parseNode(node, hashMap);
                

                //======================================================
                NamedNodeMap attributes = node.getAttributes();
                Node classNode = attributes.getNamedItem(JAVA_CLASS);
                if (classNode != null) {
                    //����java-class�ж�������
                } else {
                    //��û��java-classʱ,����tag��λ���ж�.
                }
            } catch (IOException ex) {
                //java.util.logging.Logger.getLogger(XmlBeanBuilder.class.getName()).log(Level.SEVERE, null, ex);
                throw new XMLParseException(ex);
            }
        }

    }

    /** ���ݽڵ�ȡ�ý����� */
    protected XmlNodeParser getNodeParser(Node node) {
        XmlNodeParser nodeParser = null;
        String className = JOXUtils.getClassName((Element) node, xConfig);
        if (className != null) {

//        }
//        NamedNodeMap attributes = node.getAttributes();
//        Node classNode = attributes.getNamedItem(JAVA_CLASS);
//        if (classNode != null) {
//            String className = classNode.getNodeValue();
            try {

                Class javaClass = Class.forName(className);
                nodeParser = new DefaultXmlNodeParser(javaClass);
            } catch (ClassNotFoundException ex) {
                //Logger.getLogger(XmlBeanBuilder.class.getName()).log(Level.SEVERE, null, ex);
                log.info("û���ҵ�" + className + "��Ӧ�Ľ�����", ex);
            }
        } else {
        //��û��java-classʱ,����tag��λ���ж�.
        }

        return nodeParser;
    }

    /** �ڵ�������ӿ� */
    public interface XmlNodeParser {

        /**
         * ����һ���ڵ�.�����Ľ�������Զ����뵽parent������.
         * @param node �ڵ�
         * @param parent ������
         * @return ������Ľ��,������������.
         */
        public Object parseNode(Node node, Object parent) throws IOException;
    }

    /**
     * Ĭ�ϵĽڵ������
     */
    public class DefaultXmlNodeParser implements XmlNodeParser {

        Class c = Object.class;

        public DefaultXmlNodeParser(Class c) {
            this.c = c;
        }

        public Object parseNode(Node node, Object parent) throws IOException{
            //throw new UnsupportedOperationException("Not supported yet.");
            JOXBeanBuilder beanBuilder = new JOXBeanBuilder((Element) node);
            return beanBuilder.readObject(c);
        }
    }
}
