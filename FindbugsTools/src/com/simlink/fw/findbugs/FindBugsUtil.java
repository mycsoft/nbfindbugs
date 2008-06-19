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
 * FindBugs������.
 * <P>ҵ������:
 * 1.����ָ���汾.
 * 2.������Ŀ.
 * 3.��Findbugs�����Ŀ.
 * 4.�����ɲ��Խ��,Excel.
 * </P>
 * @author Ma Yichao
 */
public class FindBugsUtil {

    private static final Logger log = Logger.getLogger(FindBugsUtil.class.getName());

    /** ��FindBugs�ļ��������Excel�ļ�. */
    public static void findbugsToExcel(String reportFileName) throws IOException {
        
        //����Findbugs�����.
        FindBugs.setHome("D:/lib/findbugs-1.3.4");          //Findbugs�İ�װĿ¼.
        FindBugs2 main = new FindBugs2();
        
        Project project = Project.readProject(reportFileName);  //������Ŀ
        //XMLBugReporter report = new XMLBugReporter(project);    //�������.
        main.setProject(project);
        BugReporter report = main.getBugReporter();
        
        log.info(project.getProjectName());
        //����Excel.
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
    //log.log(Level.WARNING,"д��Excel�ļ�ʧ��,���ܸ��ļ����ڱ�ʹ��!", e);
    //�����д��Excel.

    }

    public static void main(String[] arg) {
        try {
            String file = "fb_results.xml";
            file = FindBugsUtil.class.getResource(file).getFile();
            findbugsToExcel(file);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Excel����ʧ��", ex);
        }
        
        //FIXME 
        if("ddd"==""){
            
        }
        
    }
}
