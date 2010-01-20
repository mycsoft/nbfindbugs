/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.fw.findbugs.plugin;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.Method;

/**
 *���һ��������,�������й����return.
 * @author MaYichao
 */
public class FindMoreReturn extends AbstractFindbugsPlugin {

    /** �������е�return���� */
    public static final int MAX = 2;
    /** ��ǰ����һ���ж��ٸ�return */
    private int count = 0;

    public FindMoreReturn(BugReporter br) {
        super(br);
    }

    @Override
    public void visitMethod(Method obj) {
        //��ʼ������

        //if (count > MAX) {
        //reportBug("SPSOFT_MORE_RETURN", NORMAL_PRIORITY);
        //}

        count = 0;

        super.visitMethod(obj);


    }



    @Override
    public void sawOpcode(int seen) {

//        switch (seen){
//            case RETURN:
//            case IRETURN:
//            case FRETURN:
//            case DRETURN:
//            case LRETURN:
//            case ARETURN:
//                count++;
//            if (count > MAX) {
//                reportBug("SPSOFT_MORE_RETURN", NORMAL_PRIORITY);
//            }
//        }
        if (isReturn(seen)) {
            count++;
            if (count > MAX) {
                reportBug("SPSOFT_MORE_RETURN", NORMAL_PRIORITY);
            }
        }

    }
}
