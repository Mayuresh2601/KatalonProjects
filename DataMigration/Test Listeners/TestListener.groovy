import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject

import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testobject.TestObject as TestObject

import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile

import internal.GlobalVariable as GlobalVariable

import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.annotation.BeforeTestSuite
import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.AfterTestSuite
import com.kms.katalon.core.context.TestCaseContext
import com.kms.katalon.core.context.TestSuiteContext
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import org.skyscreamer.jsonassert.FieldComparisonFailure

import org.json.JSONArray

import java.text.SimpleDateFormat as SimpleDateFormat
import java.text.DateFormat as DateFormat
import java.text.Format as Format
import java.io.File
import com.kms.katalon.core.configuration.RunConfiguration

class TestListener {
	/**
	 * Executes after every test case ends.
	 * @param testCaseContext related information of the executed test case.
	 */
	
	String testSuiteName
	String reportPath
	String reportFile
	
	@BeforeTestSuite
	void beforeSuite() {
		String exeSrc = RunConfiguration.getProjectDir()
		println(exeSrc)
		Format fDate = new SimpleDateFormat("dd_MM_yyyy");
		Date latestdate = new Date()
		String date = fDate.format(latestdate)
		println(date)
		Format fDateTime = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String dateTime = fDateTime.format(latestdate)
		println(dateTime)
		String testSuiteName = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf("\\")+1,RunConfiguration.getExecutionSource().toString().length()-3)
		println(testSuiteName)
		this.testSuiteName=testSuiteName
		String reportPath = exeSrc + File.separator + 'ExtentReport' + File.separator +'log_'+ date + File.separator + testSuiteName + '_' + dateTime + File.separator + testSuiteName + '_' + dateTime
		this.reportPath=reportPath
		String reportFile= testSuiteName + '_' + dateTime
		this.reportFile=reportFile
		println "reportFile   === "+reportFile
	}
	
	@BeforeTestCase
	void Before(TestCaseContext testCaseContext) {
		String tc_description=testCaseContext.getMessage()
		String tcName=testCaseContext.testCaseId
		println tcName
		CustomKeywords.'reporting.ExtentReporting.startTest'(reportPath,testSuiteName,tcName,tc_description,GlobalVariable.author,"Extent Test",reportFile)
	}
	
	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		
		KeywordUtil.logInfo(testCaseContext.getTestCaseId())
		KeywordUtil.logInfo(testCaseContext.getTestCaseStatus())
		
		
		String responseQA = testCaseContext.getTestCaseVariables().get("responseQA")

		String responseQAMIG = testCaseContext.getTestCaseVariables().get("responseQAMIG")
		
		String sortedQA = testCaseContext.getTestCaseVariables().get("sortedRecordsQA")
		
		String sortedQAMIG = testCaseContext.getTestCaseVariables().get("sortedRecordsQAMIG")
		
		CustomKeywords.'reporting.ExtentReporting.logStringData'('URL_QA',testCaseContext.getTestCaseVariables().get("URL_QA"))
		CustomKeywords.'reporting.ExtentReporting.logStringData'('URL_QAMIG',testCaseContext.getTestCaseVariables().get("URL_QAMIG"))
		CustomKeywords.'reporting.ExtentReporting.logJSONData'('QA Response',responseQA)
		CustomKeywords.'reporting.ExtentReporting.logJSONData'('QAMIG Response',responseQAMIG)
		
		if(testCaseContext.getTestCaseStatus().equals("FAILED")) {
			String responseQAValue = testCaseContext.getTestCaseVariables().get("responseQA")
			KeywordUtil.markFailed(testCaseContext.getTestCaseVariables().get("URL_QA"))
			KeywordUtil.markFailed(responseQAValue)
			String responseQAMIGValue = testCaseContext.getTestCaseVariables().get("responseQAMIG")
			KeywordUtil.markFailed(testCaseContext.getTestCaseVariables().get("URL_QAMIG"))
			KeywordUtil.markFailed(responseQAMIGValue)
			
			String recordsQA = testCaseContext.getTestCaseVariables().get("sortedRecordsQA")
			KeywordUtil.markFailed(recordsQA)
			
			String recordsQAMIG = testCaseContext.getTestCaseVariables().get("sortedRecordsQAMIG")
			KeywordUtil.markFailed(recordsQAMIG)
			
			List<String> missingIDsList = testCaseContext.getTestCaseVariables().get("missing_IDs")
			missingIDsList.each { id ->
				KeywordUtil.markFailed('ID Missing -> '+id)
			}
			
			List<String> fieldsToBeIgnoredList = testCaseContext.getTestCaseVariables().get("fieldsToBeIgnoredList")
			fieldsToBeIgnoredList.each { fieldName ->
				KeywordUtil.markFailed('Field Ignored -> '+fieldName)
			}
			
			String uniqueKey = testCaseContext.getTestCaseVariables().get("keyToSort")
			
			List<FieldComparisonFailure> failedList = testCaseContext.getTestCaseVariables().get("failedList")
			KeywordUtil.markFailed('failedList.size() '+failedList.size())
			
			HashMap<String,String> hashMapF1 = new HashMap()
			HashMap<String,String> hashMapF2 = new HashMap()
			
//			for(int countF = 0 ; countF < 5 && countF < failedList.size(); countF++) {
//				KeywordUtil.markFailed('Failed Field -> ' + failedList.get(countF).getField())
//				KeywordUtil.logInfo('Actual Value QAMIG -> '+failedList.get(countF).getActual())
//				KeywordUtil.logInfo('Expected Value QA  -> '+failedList.get(countF).getExpected())
//				KeywordUtil.logInfo('-----------------------------------')
//			}
			
			failedList.forEach({failedF ->
				if(!hashMapF1.containsValue(failedF.getExpected())) {
					hashMapF1.put(failedF.getField(), failedF.getExpected())
					hashMapF2.put(failedF.getField(), failedF.getActual())
				}
			})
			
			hashMapF1.keySet().forEach({key -> 
				KeywordUtil.markFailed("Key "+key+" | Value from QA -> " + hashMapF1.get(key) + " | Value from QAMIG -> " + hashMapF2.get(key))
//				String valueF = "Value from QA -> " + hashMapF1.get(key) + " | Value from QAMIG -> " + hashMapF2.get(key);
//				CustomKeywords.'reporting.ExtentReporting.logFailedData'('Value mismatched for Key > '+key,valueF)
			})
			
			//print everything in Extent Report
			HashMap<String,String> hashMapFEx1 = new HashMap()
			HashMap<String,String> hashMapFEx2 = new HashMap()
			
			failedList.forEach({failedF ->
//				if(!hashMapFEx1.containsValue(failedF.getExpected())) {
					hashMapFEx1.put(failedF.getField(), failedF.getExpected())
					hashMapFEx2.put(failedF.getField(), failedF.getActual())
//				}
			})
			
			hashMapFEx1.keySet().forEach({key ->
//				KeywordUtil.markFailed("Key "+key+" | Value from QA -> " + hashMapFEx1.get(key) + " | Value from QAMIG -> " + hashMapFEx2.get(key))
				String valueF = "Value from QA -> " + hashMapFEx1.get(key) + " | Value from QAMIG -> " + hashMapFEx2.get(key);
				CustomKeywords.'reporting.ExtentReporting.logFailedData'('Value mismatched for Key > '+key,valueF)
			})

//			----------------------------------------------------------------------------
			
			List<FieldComparisonFailure> missingList = testCaseContext.getTestCaseVariables().get("missingList")
			KeywordUtil.markFailed('missingList.size() '+missingList.size())
			
			HashMap<String,String> hashMapM = new HashMap()
			HashMap<String,String> missingAll = new LinkedHashMap()
			
			int c = 0;
			if(missingList.size()>0) {
				missingList.forEach({missingF ->
					missingAll.put(missingF.getField()+c, missingF.getExpected())
					if(!hashMapM.containsValue(missingF.getExpected())) {
						hashMapM.put(missingF.getField(), missingF.getExpected())
					}	
					c++;
				})
			}
			
			KeywordUtil.markFailed('Missing from QAMIG size -> ' + hashMapM.size())
			
			KeywordUtil.markFailed('Missing from QAMIG -> ' + hashMapM)
			
			if(hashMapM.size()>0) {
				CustomKeywords.'reporting.ExtentReporting.logMissingData'('Missing from QAMIG',""+hashMapM)
				CustomKeywords.'reporting.ExtentReporting.logMissingData'('Missing fields in QAMIG',""+missingAll)
			}
			
//			----------------------------------------------------------------------------
			
			List<FieldComparisonFailure> unexpectedList = testCaseContext.getTestCaseVariables().get("unexpectedList")
			KeywordUtil.markFailed('unexpectedList.size() '+unexpectedList.size())
			
			HashMap<String,String> hashMapU = new HashMap()
			
//			for(int unexpectedI = 0 ; unexpectedI < 50 && unexpectedI < unexpectedList.size() ; unexpectedI++) { 
//					KeywordUtil.markFailed('Unexpected Field -> ' + unexpectedList.get(unexpectedI).getField())
//					KeywordUtil.logInfo('Actual Value QAMIG -> '+unexpectedList.get(unexpectedI).getActual())
//					KeywordUtil.logInfo('Expected Value QA  -> '+unexpectedList.get(unexpectedI).getExpected())
//					KeywordUtil.logInfo('-----------------------------------')
//			}
			
			if(unexpectedList.size()>0) {
				unexpectedList.forEach({unexpectedF ->
					if(!hashMapU.containsValue(unexpectedF.getActual())) {
						hashMapU.put(unexpectedF.getField(), unexpectedF.getActual())
					}
				})
			}
			
			KeywordUtil.markFailed('Missing from QA size -> ' + hashMapU.size())
			
			KeywordUtil.markFailed('Missing from QA -> ' + hashMapU)
			
			List<String> missing_IDs = testCaseContext.getTestCaseVariables().get("missing_IDs")
			
			if(missing_IDs.size() > 0) {
				CustomKeywords.'reporting.ExtentReporting.logMissingData'('Missing IDs',""+missing_IDs)
			}
			
			CustomKeywords.'reporting.ExtentReporting.testStatus'('FAIL',testCaseContext.getMessage())
			
		}
		else if(testCaseContext.getTestCaseStatus().equals("ERROR")) {
			CustomKeywords.'reporting.ExtentReporting.testStatus'('FAIL',testCaseContext.getMessage())
			KeywordUtil.logInfo(testCaseContext.getTestCaseVariables().get("URL_QA"))
			String responseQAValue = testCaseContext.getTestCaseVariables().get("responseQA")
			KeywordUtil.logInfo(responseQAValue)
			KeywordUtil.logInfo(testCaseContext.getTestCaseVariables().get("URL_QAMIG"))
			String responseQAMIGValue = testCaseContext.getTestCaseVariables().get("responseQAMIG")
			KeywordUtil.logInfo(responseQAMIGValue)
			List<String> missingIDsList = testCaseContext.getTestCaseVariables().get("missing_IDs")
			missingIDsList.each { id ->
				KeywordUtil.markFailed('ID Missing -> '+id)
			}
			List<String> fieldsToBeIgnoredList = testCaseContext.getTestCaseVariables().get("fieldsToBeIgnoredList")
			fieldsToBeIgnoredList.each { fieldName ->
				KeywordUtil.logInfo('Field Ignored -> '+fieldName)
			}
		}
		else {
			String flag = testCaseContext.getTestCaseVariables().get("flag")
			if(flag.equalsIgnoreCase('N')) {
				CustomKeywords.'reporting.ExtentReporting.testStatus'('SKIP',testCaseContext.getTestCaseId()+" Skipped")
			}else {				
				CustomKeywords.'reporting.ExtentReporting.testStatus'('PASS',testCaseContext.getTestCaseId()+" Passed")
				KeywordUtil.logInfo(testCaseContext.getTestCaseVariables().get("URL_QA"))
				String responseQAValue = testCaseContext.getTestCaseVariables().get("responseQA")
				KeywordUtil.logInfo(sortedQA)
				KeywordUtil.logInfo(testCaseContext.getTestCaseVariables().get("URL_QAMIG"))
				String responseQAMIGValue = testCaseContext.getTestCaseVariables().get("responseQAMIG")
				KeywordUtil.logInfo(sortedQAMIG)
				List<String> missingIDsList = testCaseContext.getTestCaseVariables().get("missing_IDs")
				missingIDsList.each { id ->
					KeywordUtil.markFailed('ID Missing -> '+id)
				}
				List<String> fieldsToBeIgnoredList = testCaseContext.getTestCaseVariables().get("fieldsToBeIgnoredList");
				
				fieldsToBeIgnoredList.each { fieldName ->
						KeywordUtil.logInfo('Field Ignored -> '+fieldName);
					}
				if(fieldsToBeIgnoredList.size() > 0) {
						CustomKeywords.'reporting.ExtentReporting.logListData'('Ignored Nodes ',fieldsToBeIgnoredList)
					}
					
			}
		}
		if (GlobalVariable.extentFlag.equals("y")){
			CustomKeywords.'reporting.ExtentReporting.endTest'()
		}
		
	}
}