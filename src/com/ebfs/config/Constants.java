package com.ebfs.config;

public class Constants {
	
	//System Variables
	public static final String TARGET_URL = "http://ebfs.bruteforcesolution.net/ebfs/index.php";
	public static final String PATH_TEST_DATA = System.getProperty("user.dir") + "\\src\\com\\ebfs\\dataEngine\\DataEngine.xlsx";
	public static final String PATH_OBJECT_REPO = System.getProperty("user.dir") + "\\resources\\elements\\OR.txt";
	public static final String FILE_TEST_DATA = "DataEngine.xlsx";
	public static final String KEYWORD_FAIL = "FAIL";
	public static final String KEYWORD_PASS = "PASS";
	
	//Data Sheet Column Numbers
	public static final int COL_TESTCASE_ID = 0;	
	public static final int COL_TEST_SCENARIO_ID =1 ;
	public static final int COL_PAGE_OBJECT =4 ;
	public static final int COL_ACTION_KEYWORD =5 ;
	public static final int COL_RUNMODE =2 ;
	public static final int COL_RESULT =3 ;
	public static final int COL_DATASET =6 ;
	public static final int COL_TEST_STEP_RESULT =7 ;
		
	// Data Engine Excel sheets
	public static final String SHEET_TEST_STEPS = "Test Steps";
	public static final String SHEET_TEST_CASES = "Test Cases";
	
	// Test Data
	public static final String UserName = "codecamper@outlook.com";
	public static final String Password = "Pass123";


	

}
