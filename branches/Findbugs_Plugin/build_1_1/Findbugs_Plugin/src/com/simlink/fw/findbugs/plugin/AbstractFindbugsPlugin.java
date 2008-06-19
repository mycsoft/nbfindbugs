/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.fw.findbugs.plugin;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.StatelessDetector;

/**
 *
 * @author Ma Yichao
 */
public abstract class AbstractFindbugsPlugin extends BytecodeScanningDetector implements StatelessDetector {

    protected BugReporter bugReporter;

    public AbstractFindbugsPlugin(BugReporter br) {
        //super(br);
        super();
        bugReporter = br;

    }

    protected void reportBug(String tag, int priority) {
        bugReporter.reportBug(
                new BugInstance(tag, priority).addClassAndMethod(this).addSourceLine(this));
    }
}
