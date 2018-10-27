package com.ebfs.executionEngine;

import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;

import com.ebfs.config.ActionKeywords;
import com.ebfs.config.Constants;
import com.ebfs.utility.ExcelUtils;
import com.ebfs.utility.Log;
 
public class DriverScript {
	
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];
		
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	
	
	public DriverScript() throws NoSuchMethodException, SecurityException{
		actionKeywords = new ActionKeywords();
		method = actionKeywords.getClass().getMethods();	
	}
	
    public static void main(String[] args) throws Exception {
    	ExcelUtils.setExcelFile(Constants.PATH_TEST_DATA);
    	DOMConfigurator.configure("log4j.xml");
    	String Path_OR = Constants.PATH_OBJECT_REPO;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
		
		DriverScript startEngine = new DriverScript();
		startEngine.execute_TestCase();
		
    }
		
    private void execute_TestCase() throws Exception {
	    	int iTotalTestCases = ExcelUtils.getRowCount(Constants.SHEET_TEST_CASES);
			for(int iTestcase=1;iTestcase<iTotalTestCases;iTestcase++){
				bResult = true;
				sTestCaseID = ExcelUtils.getCellData(iTestcase, Constants.COL_TESTCASE_ID, Constants.SHEET_TEST_CASES); 
				sRunMode = ExcelUtils.getCellData(iTestcase, Constants.COL_RUNMODE,Constants.SHEET_TEST_CASES);
				if (sRunMode.equals("Yes")){
					Log.startTestCase(sTestCaseID);
					iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.COL_TESTCASE_ID, Constants.SHEET_TEST_STEPS);
					iTestLastStep = ExcelUtils.getTestStepsCount(Constants.SHEET_TEST_STEPS, sTestCaseID, iTestStep);
					bResult=true;
					for (;iTestStep<iTestLastStep;iTestStep++){
			    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.COL_ACTION_KEYWORD,Constants.SHEET_TEST_STEPS);
			    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.COL_PAGE_OBJECT, Constants.SHEET_TEST_STEPS);
			    		sData = ExcelUtils.getCellData(iTestStep, Constants.COL_DATASET, Constants.SHEET_TEST_STEPS);
			    		execute_Actions();
						if(bResult==false){
							ExcelUtils.setCellData(Constants.KEYWORD_FAIL,iTestcase,Constants.COL_RESULT,Constants.SHEET_TEST_CASES);
							Log.endTestCase(sTestCaseID);
							break;
							}						
						}
					if(bResult==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS,iTestcase,Constants.COL_RESULT,Constants.SHEET_TEST_CASES);
					Log.endTestCase(sTestCaseID);	
						}					
					}
				}
    		}	
     
     private static void execute_Actions() throws Exception {
	
		for(int i=0;i<method.length;i++){
			
			if(method[i].getName().equals(sActionKeyword)){
				method[i].invoke(actionKeywords,sPageObject, sData);
				if(bResult==true){
					ExcelUtils.setCellData(Constants.KEYWORD_PASS, iTestStep, Constants.COL_TEST_STEP_RESULT, Constants.SHEET_TEST_STEPS);
					break;
				}else{
					ExcelUtils.setCellData(Constants.KEYWORD_FAIL, iTestStep, Constants.COL_TEST_STEP_RESULT, Constants.SHEET_TEST_STEPS);
					ActionKeywords.closeBrowser("","");
					break;
					}
				}
			}
     }
     
}