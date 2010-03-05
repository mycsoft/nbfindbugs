/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myc.findbugs.plugin;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.Method;

/**
 *
 * @author MaYichao
 */
public class FindMoreLoop extends AbstractFindbugsPlugin {

    /** �������е�return���� */
    private static final int MAX = 3;
    /** ��ǰ����һ���ж��ٸ�return */
    private int count = 0;
    /** ѭ���Ŀ�ʼ����� */
    private int loopstart = -1;
    private int loopend = -1;

    public FindMoreLoop(BugReporter br) {
        super(br);
    }

    @Override
    public void visitMethod(Method obj) {
        //��ʼ������

        //if (count > MAX) {
        //reportBug("MYCSOFT_MORE_RETURN", NORMAL_PRIORITY);
        //}

        count = 0;
        loopstart = -1;
        loopend = -1;
        super.visitMethod(obj);
    }

    @Override
    public void sawOpcode(int seen) {
        //switch (seen) {
        //case GOTO:
        if (isBranch(seen)) {
            int current = getPC();
            int dest = getBranchTarget();
            if (dest < current) {
                if (loopstart < 0 || dest > loopend) {
                    loopstart = dest;
                    loopend = current;
                    count = 1;
                } else {

                    loopstart = Math.min(loopstart, dest);
                    loopend = current;
                    count++;
                    if (count > MAX) {
                        reportBug("MYCSOFT_MORE_LOOP", HIGH_PRIORITY);
                    }
                }
            }


        }

    }
}
