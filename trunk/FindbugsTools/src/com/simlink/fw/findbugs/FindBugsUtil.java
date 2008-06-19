/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.simlink.fw.findbugs;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.FindBugs;
import edu.umd.cs.findbugs.FindBugs2;
import edu.umd.cs.findbugs.PackageStats;
import edu.umd.cs.findbugs.Project;
import edu.umd.cs.findbugs.XMLBugReporter;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FindBugs工具类.
 * <P>业务流程:
 * 1.下载指定版本.
 * 2.编译项目.
 * 3.用Findbugs检查项目.
 * 4.用生成测试结果,Excel.
 * </P>
 * @author Ma Yichao
 */
public class FindBugsUtil {

    private static final Logger log = Logger.getLogger(FindBugsUtil.class.getName());

    /** 将FindBugs的检查结果生成Excel文件. */
    public static void findbugsToExcel(String reportFileName) throws IOException {
        
        //导入Findbugs结果集.
        FindBugs.setHome("D:/lib/findbugs-1.3.4");          //Findbugs的安装目录.
        FindBugs2 main = new FindBugs2();
        
        Project project = Project.readProject(reportFileName);  //分析项目
        //XMLBugReporter report = new XMLBugReporter(project);    //分析结果.
        main.setProject(project);
        BugReporter report = main.getBugReporter();
        
        log.info(project.getProjectName());
        //创建Excel.
        String excelFileName = "";

        //UtilExcelCreate t = new UtilExcelCreate();
        //WritableWorkbook wwb = Workbook.createWorkbook(new File(excelFileName));
        //t.setContext(schemaInfo, wwb);
        for (Iterator<PackageStats> it = report.getProjectStats().getPackageStats().iterator(); it.hasNext();) {
            PackageStats packageStats = it.next();
            log.info(packageStats.toString());
        }



    //wwb.write();
    //wwb.close();
    //return true;
    //log.log(Level.WARNING,"写入Excel文件失败,可能该文件正在被使用!", e);
    //将结果写入Excel.

    }

    public static void main(String[] arg) {
        try {
            String file = "fb_results.xml";
            file = FindBugsUtil.class.getResource(file).getFile();
            findbugsToExcel(file);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Excel生成失败", ex);
        }
        
        //FIXME 
        if("ddd"==""){
            
        }
        
    }
}
