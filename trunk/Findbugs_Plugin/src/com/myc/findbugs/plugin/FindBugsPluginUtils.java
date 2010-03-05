/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myc.findbugs.plugin;

import java.util.ResourceBundle;
import org.apache.bcel.classfile.Method;

/**
 * ���߷�����.
 * @author MaYichao
 */
public class FindBugsPluginUtils {

    //TODO ������ϢҪ��Ϊ�����ļ�,�Ա�����չ.
    /** ��Դ���ö��� */
    private static final ResourceBundle resourceBundle =
            ResourceBundle.getBundle(
            //FindBugsPluginUtils.class.getResource(
            //"com.myc.findbugs.plugin.config").getFile()
            "com_myc_findbugs_plugin_config"
            );
    /** ����� */
    private static final String TOP_PACKAGE = resourceBundle.getString("util.package.top");
    /** ������ */
    public static final int FW_LAYER_UNKNOWN = 0;
    public static final int FW_LAYER_DAO_IMPL = 10;
    public static final int FW_LAYER_DAO = 20;
    public static final int FW_LAYER_SERVICE = 30;
    public static final int FW_LAYER_ACTION = 50;
    public static final int FW_LAYER_FORM = 60;
    public static final int FW_LAYER_UTIL = 100;
    
    /** �����׺���� */
    private static final String PK_POST_ACTION = resourceBundle.getString("util.package.post.action");
    private static final String PK_POST_FORM = resourceBundle.getString("util.package.post.form");
    private static final String PK_POST_SERVICE = resourceBundle.getString("util.package.post.service");
    private static final String PK_POST_DAO = resourceBundle.getString("util.package.post.dao");
    private static final String PK_POST_DAO_IMPL = resourceBundle.getString("util.package.post.impl");
    private static final StringBuffer AUTOMETHOD_FLAG = new StringBuffer("$");

    /**
     * �����
     * @return the TOP_PACKAGE
     */
    public static String getTOP_PACKAGE() {
        return TOP_PACKAGE;
    }

    private FindBugsPluginUtils() {
    }

    /** ���ݰ�ȷ��һ�����ڿ���еĲ�� */
    public static int getLayerByPackageName(String pkName) {
        int frameworkLayer = FW_LAYER_UNKNOWN;
        if (pkName != null && pkName.startsWith(getTOP_PACKAGE())) {
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
     * �Ƿ�������Զ����ɵķ���
     */
    public static boolean isJVMAutoCreateMethod(Method method) {
        //��鷽�������Ƿ���$.
        //1.4��,JVM���������Զ������ڲ�����������ĳЩ����.��Щ������������'$'
        return method.getName().contains(AUTOMETHOD_FLAG);
    }
}
