/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.fw.findbugs.plugin;

import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.ba.XField;

/**
 *检查代码中使用System.out.print的地方.
 * @author Ma Yichao
 */
public class FindSysOutPrint extends AbstractFindbugsPlugin {

    public FindSysOutPrint(BugReporter br) {
        super(br);
    }

    @Override
    public void sawOpcode(int seen) {
        //检查代码中使用System.out或err的地方.
        if (seen == GETSTATIC
                && getDottedClassConstantOperand().equals("java.lang.System") 
                && ("out".equals(getNameConstantOperand()) || "err".equals(getNameConstantOperand()))
                //&& getDottedMethodSig().equals("out")
                ) {
            reportBug("SPSOFT_SYSTEM_OUT_PRINT", NORMAL_PRIORITY);
        }
    }
}
