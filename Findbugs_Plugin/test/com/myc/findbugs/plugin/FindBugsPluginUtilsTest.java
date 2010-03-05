/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.myc.findbugs.plugin;

import junit.framework.TestCase;

/**
 *
 * @author MaYichao
 */
public class FindBugsPluginUtilsTest extends TestCase {
    
    public FindBugsPluginUtilsTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testConfig(){
        String top = FindBugsPluginUtils.getTOP_PACKAGE();
        if (top == null || top.length() <= 0){
            fail("没有找到顶层包配置,可能是配置文件没有找到.");
        }
    }

}
