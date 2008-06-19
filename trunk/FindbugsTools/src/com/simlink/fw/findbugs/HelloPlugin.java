/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.fw.findbugs;

import cbg.app.Logger;
import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import org.apache.bcel.classfile.Code;

/**
 * 实验性插件.
 * @author Ma Yichao
 */
public class HelloPlugin extends BytecodeScanningDetector {
    //private int INVOKESTATIC;
    private int PC;
    private int logBlockStart;
    private int seenGuardClauseAt;
    private int logBlockEnd;
    private BugReporter bugReporter;

    public HelloPlugin(BugReporter br) {
        //super(br);
        bugReporter = br;
    }

    @Override
    public void visit(Code code) {
        seenGuardClauseAt = Integer.MIN_VALUE;
        logBlockStart = 0;
        logBlockEnd = 0;
        super.visit(code);
    }

    @Override
    public void sawOpcode(int seen) {
        if ("cbg/app/Logger".equals(getClassContext()) &&
                seen == INVOKESTATIC &&
                "isLogging".equals(getNameConstantOperand()) && "()Z".equals(getSigConstantOperand())) {
            seenGuardClauseAt = PC;
            return;
        }

        if (seen == IFEQ && (PC >= seenGuardClauseAt + 3 && PC < seenGuardClauseAt + 7)) {
            logBlockStart = getBranchFallThrough();

            logBlockEnd = getBranchTarget();
        }
        if (seen == INVOKEVIRTUAL && "log".equals(getNameConstantOperand())) {
            if (PC < logBlockStart || PC >= logBlockEnd) {
                bugReporter.reportBug(
                        new BugInstance("CBG_UNPROTECTED_LOGGING", HIGH_PRIORITY).addClassAndMethod(this).addSourceLine(this));
            }
        }

        Logger.log("perf", "");

        if (Logger.isLogging()) {
            Logger.log("perf", "");
        }


    }
}
