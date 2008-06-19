/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simlink.fw.findbugs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 *
 * @author Ma Yichao
 */
public class FindBugsFile {
    String xmlFile = "";
    ArrayList bugList = new ArrayList();

    public FindBugsFile(String file) {
        xmlFile = "";
    }
    
    
    /** ∂¡»°XML */
    private Document readXML(File xml) throws ParserConfigurationException, SAXException, IOException{
        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(xml);
        
        return doc;
    }
    
}
