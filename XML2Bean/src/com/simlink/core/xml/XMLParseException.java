/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.core.xml;

/**
 * XML½âÎö´íÎó.
 * @author Ma Yichao
 */
public class XMLParseException extends Exception {

    private static final long serialVersionUID = 9832456324987123l;

    public XMLParseException() {
        super();
    }

    public XMLParseException(String s) {
        super(s);
    }

    public XMLParseException(Throwable throwable) {
        super(throwable);
    }

    public XMLParseException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
