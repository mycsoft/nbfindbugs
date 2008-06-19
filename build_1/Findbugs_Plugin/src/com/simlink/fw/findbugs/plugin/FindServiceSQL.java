/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.fw.findbugs.plugin;

import edu.umd.cs.findbugs.BugReporter;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantString;

/**
 * �Ӽ��Service���Ƿ���SQL�ĵ���.
 *
 * @author Ma Yichao
 */
public class FindServiceSQL extends AbstractFindbugsPlugin {

    /** Service��. */
    private static final String[] SERVICE_PACKAGE = {
        "com.simlink.ehr.service",
        "com.simlink.emr.service"
    };
    /** SQL���ؼ���. ������ĸ����Сд.*/
    private static final String[] SQL_KEY = {"select", "from", "where", "like"};

    public FindServiceSQL(BugReporter br) {
        super(br);
    }

    /** ����Ƿ���Service�� */
    private boolean isService() {
        String pkname = getPackageName();
        boolean is = false;
        for (String spk : SERVICE_PACKAGE) {
            if (pkname != null && pkname.startsWith(spk)) {
                is = true;
                break;
            }
        }
        return is;
    }

    /** ����Ƿ���SQL���. */
    private boolean isSql(String s) {
        boolean is = false;
        if (s != null) {
            for (String key : SQL_KEY) {
                if (s.toLowerCase().contains(key)) {
                    is = true;
                    break;
                }
            }
        }
        return is;
    }

    @Override
    public void sawOpcode(int seen) {
        //���Service�е�SQL
        if (isService() && seen == LDC) {
            Constant c = getConstantRefOperand();
            if (c instanceof ConstantString) {
                //System.out.print("   \"" + getStringConstantOperand() + "\"");
                //ConstantString cs = (ConstantString) c;
                String s = getStringConstantOperand();
                if (isSql(s)){
                    reportBug("SIMLINK_SERVICE_SQL", HIGH_PRIORITY);
                }
            }
        }
    }
}
