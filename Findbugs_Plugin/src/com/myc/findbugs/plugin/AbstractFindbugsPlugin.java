/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myc.findbugs.plugin;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.StatelessDetector;
import java.text.DateFormat;
import java.util.Date;
import org.apache.bcel.classfile.Method;

/**
 * Findbugs ������
 * @author Ma Yichao
 */
public abstract class AbstractFindbugsPlugin extends BytecodeScanningDetector implements StatelessDetector {
    /** ��־���� */
    protected static final Logger log = Logger.getLogger(AbstractFindbugsPlugin.class);

    
    protected BugReporter bugReporter;
    public static final boolean DEBUG = false;

    public AbstractFindbugsPlugin(BugReporter br) {
        //super(br);
        super();
        bugReporter = br;
    }

    protected void reportBug(String tag, int priority) {
        if (log.isDebugEnabled()){
            log.debug("report bug:"+tag);
            log.debug("priority = " + priority);
        }
        bugReporter.reportBug(
                new BugInstance(tag, priority).addClassAndMethod(this).addSourceLine(this));
    }

    /**����bug��ָ��������.
     */
    protected void reportBug(String tag, int priority,int pc) {
        if (log.isDebugEnabled()){
            log.debug("report bug:"+tag);
            log.debug("priority = " + priority);
        }
        bugReporter.reportBug(
                new BugInstance(tag, priority).addClassAndMethod(this).addSourceLine(this,pc));
    }

    /** ��鵱ǰ���Ƿ�ȷ����һ��ѭ��.<b>������ֻ����ǰ��.</b>
     * @param seen ��ǰ����Ϣ.
     * @return �����,�򷵻�ѭ�������;����,����null.
     */
    protected Loop isLoop(int seen){
        Loop loop = null;
        if (isBranch(seen)) {
            int current = getPC();
            int dest = getBranchTarget();
            if (dest < current) {
                loop = new Loop();
                loop.start = dest;
                loop.end = current;
            }
        }

        return loop;
    }

    protected static class Loop{
        public int start;
        public int end;

    }

    protected static class Logger {
        DateFormat format = DateFormat.getTimeInstance(DateFormat.FULL);
        public Logger() {
        }

        public static Logger getLogger(Class c){
            return new Logger();
        }

        public void debug(String string) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("[debug](");
            buffer.append(format.format(
                    new Date(System.currentTimeMillis())));
            buffer.append("):");
            buffer.append(string);
            System.out.println(buffer.toString());
        }

        public boolean isDebugEnabled() {
            return DEBUG;
        }


    }

    /**
     * �Ƿ�������Զ����ɵķ���
     */
    protected static boolean isJVMAutoCreateMethod(Method method) {
        //��鷽�������Ƿ���$.
        //1.4��,JVM���������Զ������ڲ�����������ĳЩ����.
        if (log.isDebugEnabled()) {
            log.debug("================================");
            log.debug(method.getName());
        }
        return FindBugsPluginUtils.isJVMAutoCreateMethod(method);
    }
}
