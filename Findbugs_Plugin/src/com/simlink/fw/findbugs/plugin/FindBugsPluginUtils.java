/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.fw.findbugs.plugin;

import org.apache.bcel.classfile.Method;

/**
 * 工具方法类.
 * @author MaYichao
 */
public class FindBugsPluginUtils {

    /** 顶层包 */
    private static final String TOP_PACKAGE = "cn.com.jsepc";
    /** 层类型 */
    public static final int FW_LAYER_UNKNOWN = 0;
    public static final int FW_LAYER_DAO_IMPL = 10;
    public static final int FW_LAYER_DAO = 20;
    public static final int FW_LAYER_SERVICE = 30;
    public static final int FW_LAYER_ACTION = 50;
    public static final int FW_LAYER_FORM = 60;
    public static final int FW_LAYER_UTIL = 100;
    /** 层包后缀常量 */
    private static final String PK_POST_ACTION = "action";
    private static final String PK_POST_FORM = "form";
    private static final String PK_POST_SERVICE = "service";
    private static final String PK_POST_DAO = "dao";
    private static final String PK_POST_DAO_IMPL = "dao.impl";

    private static final StringBuffer AUTOMETHOD_FLAG = new StringBuffer("$");

    private FindBugsPluginUtils() {
    }

    /** 根据包确定一个类在框架中的层次 */
    public static int getLayerByPackageName(String pkName) {
        int frameworkLayer = FW_LAYER_UNKNOWN;
        if (pkName != null && pkName.startsWith(TOP_PACKAGE)) {
            if (pkName.endsWith(PK_POST_ACTION)) {
                frameworkLayer = FW_LAYER_ACTION;
            } else if (pkName.endsWith(PK_POST_FORM)) {
                frameworkLayer = FW_LAYER_FORM;
            } else if (pkName.endsWith(PK_POST_SERVICE)) {
                frameworkLayer = FW_LAYER_SERVICE;
            } else if (pkName.endsWith(PK_POST_DAO)) {
                frameworkLayer = FW_LAYER_DAO;
            } else if (pkName.endsWith(PK_POST_DAO_IMPL)) {
                frameworkLayer = FW_LAYER_DAO_IMPL;
            } else {
                frameworkLayer = FW_LAYER_UNKNOWN;
            }
        } else {
            frameworkLayer = FW_LAYER_UNKNOWN;
        }
        return frameworkLayer;
    }

    /**
     * 是否编译器自动生成的方法
     */
    public static boolean isJVMAutoCreateMethod(Method method) {
        //检查方法名中是否有$.
        //1.4中,JVM编译器会自动增加内部方法来处理某些运算.这些方法名都会有'$'
        return method.getName().contains(AUTOMETHOD_FLAG);
    }
}
