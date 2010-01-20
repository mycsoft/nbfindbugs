/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.fw.findbugs.plugin;

import edu.umd.cs.findbugs.BugReporter;
import java.util.Locale;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantString;

/**
 * 从检查Service中是否有SQL的调用.
 *
 * @author Ma Yichao
 */
public class FindServiceSQL extends AbstractFindbugsPlugin {

    /** Service包. 
     * @todo 这里要改成对应项目的内容
     */
    private static final String[] SERVICE_PACKAGE = {
        "cn.com.jsepc.epii.business.prj.service"
    };
    /** SQL语句关键字. 所有字母必须小写.*/
    private static final String[] SQL_KEY = {"select", "from", "where", "like"};

    public FindServiceSQL(BugReporter br) {
        super(br);
    }

    /** 检查是否是Service类 */
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

    /** 检查是否是SQL语句. */
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
        //检查Service中的SQL
        if (isService() && seen == LDC) {
            Constant c = getConstantRefOperand();
            if (c instanceof ConstantString) {
                //System.out.print("   \"" + getStringConstantOperand() + "\"");
                //ConstantString cs = (ConstantString) c;
                String s = getStringConstantOperand();
                if (isSql(s)){
                    reportBug("SPSOFT_SERVICE_SQL", HIGH_PRIORITY);
                }
            }
        }
    }
}
