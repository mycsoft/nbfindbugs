/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myc.findbugs.plugin;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.Method;

/**
 * ������е��쳣�Ƿ񶼰��涨�׳�
 * @author MaYichao
 */
public class FindThrowException extends AbstractFindbugsPlugin {

    /** ServiceException */
    private static final String EXCEPTION_NAME = "cn.com.jsepc.epii.framework.exception.ServiceException";
    /** ��¼��һ�������Ƿ�����ȷ���쳣 */
    private boolean lastIsException = false;
    /** ��¼��һ�е����� */
    private String lastLine = null;

    public FindThrowException(BugReporter br) {
        super(br);
    }

    //2009-9-2 ���� FIX:����ȷ���쳣ʹ�÷���Ҳ��Ϊ���������.
    //��Ϊsaw��visit����������,���Խ�����ʹ��saw�����ĵط���Ϊvisit.
    @Override
    public void visit(Method obj) {
        //System.out.println("[debug]:invoke sawMethod()" + getMethodName());
        //��ʼ������
        lastIsException = false;
        lastLine = null;
        super.visit(obj);
    }

    

    @Override
    public void sawOpcode(int seen) {
        //super.sawOpcode(seen);
        //try {

        //TODO ����ֻ�����һ��Ψһ�ĵ��÷�ʽ.
        /* ���������쳣���ʱ�ж�Ϊ�����.Ϊʲô?
         * try {
        response.getWriter().print(res);
        } catch (IOException e) {
        throw new ServiceException(e, GlobalExceptions.RESPONSE_IOEXCEPTION);
        }
         */
        if (seen == INVOKESPECIAL) {
            //System.out.println("[debug1]:"+lastLine);
            lastLine = getDottedClassConstantOperand();
            //System.out.println("[debug2]:"+lastLine);
            if (EXCEPTION_NAME.equals(lastLine)) {
                //reportBug("SPSOFT_PRJACC_UNSUMMED", HIGH_PRIORITY);
                lastIsException = true;
            }
        } else {
            if (seen == ATHROW) {
                
                if (!lastIsException) {
                    //System.out.println("last line:");
                    //System.out.println(lastLine);
                    //System.out.println("=======================");
                    /*��һ���������,�������е��õ�XXXX.classʱ,���������Զ�����һ��
                     * ������class$(String) ����,�����������,���Զ�����һ���쳣����.
                     * ����쳣�ǲ���Ҫ���д������.���,Ҫ��취�ų������෽���еļ��.
                    */
                    if (!isJVMAutoCreateMethod(getMethod())){
                        reportBug("SPSOFT_ERROR_EXCEPTION", NORMAL_PRIORITY);
                    }
                }
            }
            lastIsException = false;
        }


    }
}
