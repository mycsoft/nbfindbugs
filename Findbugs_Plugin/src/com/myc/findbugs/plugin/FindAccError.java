/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myc.findbugs.plugin;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.Method;

/**
 * �����Ŀ̨�ʵ������Ƿ����������˻��ܷ���.
 * @author MaYichao
 */
public class FindAccError extends AbstractFindbugsPlugin {

    /** �Ƿ��������Ŀ̨�ʵķ��� */
    private boolean useAccMethod = false;
    /** �Ƿ��������Ŀ̨�ʻ��ܵķ��� */
    private boolean hasSumAcc = false;
    /** ���� */
    //private static final String ACC_SERVICE_PACKAGE = "cn/com/jsepc/epii/business/prj/service/user15/";
    /** ���� */
    //private static final String ACC_SERVICE = ACC_SERVICE_PACKAGE + "PrjAccService";
    /** ���ܷ��� */
    //private static final String ACC_SERVICE_SUM_METHOD = "sumPrjAcc";
    private static final String ACC_SERVICE_PACKAGE = "sample.";
    private static final String ACC_SERVICE = ACC_SERVICE_PACKAGE + "PrjAccService";
    private static final String ACC_SERVICE_SUM_METHOD = "sumPrjAcc";

    public FindAccError(BugReporter br) {
        super(br);
    }

    @Override
    public void visitMethod(Method obj) {
        //��ʼ������
        useAccMethod = false;
        hasSumAcc = false;

        super.visitMethod(obj);
    }

    @Override
    public void sawOpcode(int seen) {
        //super.sawOpcode(seen);
        //try {
        if (seen == INVOKEVIRTUAL &&
                ACC_SERVICE.equals(getDottedClassConstantOperand())) {
            //reportBug("MYCSOFT_PRJACC_UNSUMMED", HIGH_PRIORITY);
            useAccMethod = true;
            hasSumAcc = false;
            if (ACC_SERVICE_SUM_METHOD.equals(getNameConstantOperand())) {
                hasSumAcc = true;
            }
            return;
        }

        if (seen == RETURN) {
            if (useAccMethod == true && hasSumAcc == false) {
                reportBug("MYCSOFT_PRJACC_UNSUMMED", HIGH_PRIORITY);
            }
        }
//        } catch (ClassNotFoundException ex) {
//            //֪ͨ�û�,������û���������.
//            bugReporter.reportMissingClass(ex);
//        }
    }
}
