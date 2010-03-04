/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myc.findbugs.plugin;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.ConstantClass;
import org.apache.bcel.classfile.Method;

/**
 * ����Ƿ��д�ѭ���е������ݿ����.
 * @author MaYichao
 */
public class FindInvokeSqlInLoop extends AbstractFindbugsPlugin {

    /** ��ǰ��Ĳ� */
    private int frameworkLayer = FindBugsPluginUtils.FW_LAYER_UNKNOWN;
    //private ArrayList<Integer> invoikList = null;
    private int lastInvoikPC = -1;

    public FindInvokeSqlInLoop(BugReporter br) {
        super(br);
    }

    @Override
    public void visitConstantClass(ConstantClass obj) {
        super.visitConstantClass(obj);
        //todo ȷ����ǰ��Ĳ�
        String pkName = getPackageName();
        frameworkLayer = FindBugsPluginUtils.getLayerByPackageName(pkName);
    }


    @Override
    public void visitMethod(Method obj) {
        //��ʼ������
        lastInvoikPC = -1;
        super.visitMethod(obj);
    }


    @Override
    @SuppressWarnings("unchecked")
    public void sawOpcode(int seen) {
        /*�����ǰ�е�������һ�����,���¼����.
         * �����ǰ��ȷ����һ��ѭ��,�����¼���²��������û�������ѭ���е�.
         * �����,�򱨴�.
         */

        //todo �����ǰ�е�������һ�����,���¼����.
        if (seen == INVOKESPECIAL || seen == INVOKEVIRTUAL) {
            String cName = getDottedClassConstantOperand();
            if (cName != null) {
                int length = cName.lastIndexOf(".");
                if (length > 0) {
                    String pkName = cName.substring(0, length);
                    int layer = FindBugsPluginUtils.getLayerByPackageName(pkName);
                    switch (layer) {
                        case FindBugsPluginUtils.FW_LAYER_UNKNOWN:
                        case FindBugsPluginUtils.FW_LAYER_UTIL:

                            break;

                        default:
                            if (layer < frameworkLayer) {
                                //invoikList.add(new Integer(getPC()));
                                lastInvoikPC = getPC();
                            }
                    }
                }
            }
        //reportBug("SPSOFT_PRJACC_UNSUMMED", HIGH_PRIORITY);
        //lastIsException = true;

        }

        //�����ǰ��ȷ����һ��ѭ��,�����¼���²��������û�������ѭ���е�.
        Loop loop = isLoop(seen);
        //if (loop != null && !invoikList.isEmpty()) {
        if (loop != null && lastInvoikPC >= 0) {
            if (loop.start < lastInvoikPC) {
                /*FIXME �������ַ�ʽ���ܻ���ֵĴ���,�ǵ�ѭ��Ƕ�����ж���SQL����ʱ,�������ܻ�ֻ����һ�δ���.
                 * ��õķ�����ʹ�ö�������¼���п��ܵĵ���,����,�������ڴ�����Ҫ��ܶ�.
                 */
                

                reportBug("SPSOFT_LOOP_SQL", HIGH_PRIORITY,lastInvoikPC);
                lastInvoikPC = -1;
            }
        }

        //TODO ��μ���ӵ���? ��ѭ�������˷��²�ķ���,���Ǹ÷�������õ�SQL.
        super.sawOpcode(seen);
    }
}
