/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myc.findbugs.plugin;

import edu.umd.cs.findbugs.BugReporter;
import java.util.Locale;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantString;

/**
 * �Ӽ��Service���Ƿ���SQL�ĵ���.
 *
 * @author Ma Yichao
 */
public class FindServiceSQL extends AbstractFindbugsPlugin {

    /** SQL���ؼ���. ������ĸ����Сд.*/
    private static final String[] SQL_KEY = {"select", "from", "where", "like"};

    public FindServiceSQL(BugReporter br) {
        super(br);
    }

    /** ����Ƿ���Service�� */
    private boolean isService() {
        String pkname = getPackageName();
//        boolean is = false;
//        for (String spk : SERVICE_PACKAGE) {
//            if (pkname != null && pkname.startsWith(spk)) {
//                is = true;
//                break;
//            }
//        }
        //return is;
        return FindBugsPluginUtils.getLayerByPackageName(pkname)
                == FindBugsPluginUtils.FW_LAYER_SERVICE;
    }

    /** ����Ƿ���SQL���. */
    private boolean isSql(String s) {
        boolean is = false;
        if (s != null) {
            for (String key : SQL_KEY) {
                if (s.toLowerCase(Locale.getDefault()).contains(key)) {
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
                    reportBug("MYCSOFT_SERVICE_SQL", HIGH_PRIORITY);
                }
            }
        }
    }
}
