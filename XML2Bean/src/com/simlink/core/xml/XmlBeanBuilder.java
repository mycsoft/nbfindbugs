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
            throw new NullPointerException("无法解析空文档");
        }
        this.xml = xml;
    }

    /** 生成数据对象. */
    public Object getBean() {
        if (bean == null) {
        //TODO create bean
        }
        return bean;
    }

    /** 解析XML */
    protected void parseXML() throws XMLParseException {
        if (xml == null) {
            throw new NullPointerException("无法解析空文档");
        }
        
        Element root = xml.getDocumentElement();

        HashMap hashMap = new HashMap();    //根对象

        NodeList firstLayer = root.getChildNodes(); //第一层的对象.

        for (int i = 0; i < firstLayer.getLength(); i++) {
            try {
                Node node = firstLayer.item(i);
                XmlNodeParser nodeParser = getNodeParser(node);

                Object o = nodeParser.parseNode(node, hashMap);
                

                //======================================================
                NamedNodeMap attributes = node.getAttributes();
                Node classNode = attributes.getNamedItem(JAVA_CLASS);
                if (classNode != null) {
                    //根据java-class判定解析器
                } else {
                    //当没有java-class时,根据tag和位置判定.
                }
            } catch (IOException ex) {
                //java.util.logging.Logger.getLogger(XmlBeanBuilder.class.getName()).log(Level.SEVERE, null, ex);
                throw new XMLParseException(ex);
            }
        }

    }

    /** 根据节点取得解析器 */
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
                log.info("没有找到" + className + "对应的解析器", ex);
            }
        } else {
        //当没有java-class时,根据tag和位置判定.
        }

        return nodeParser;
    }

    /** 节点解析器接口 */
    public interface XmlNodeParser {

        /**
         * 解析一个节点.解析的结果将会自动加入到parent对象中.
         * @param node 节点
         * @param parent 父对象
         * @return 解析后的结果,不包括父对象.
         */
        public Object parseNode(Node node, Object parent) throws IOException;
    }

    /**
     * 默认的节点分析类
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
