import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ResponseObject as ResponseObject
import java.util.stream.Collectors

import com.kms.katalon.core.util.KeywordUtil as KeywordUtil
import org.skyscreamer.jsonassert.*
import org.skyscreamer.jsonassert.comparator.*

import static org.junit.Assert.assertEquals
import static org.skyscreamer.jsonassert.JSONAssert.*
import static org.skyscreamer.jsonassert.JSONCompare.*
import static org.skyscreamer.jsonassert.comparator.JSONCompareUtil.*

import org.json.JSONObject
import org.skyscreamer.jsonassert.JSONCompareResult
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder
import org.json.JSONArray
import org.json.JSONException

if (flag == 'Y') {
//    ResponseObject tokenResponseQA = WS.sendRequest(findTestObject('CommonRequest/Get User Token QA', [('username') : Username
//                , ('password') : Password]))
//
//    WS.verifyResponseStatusCode(tokenResponseQA, 200)
//
//    def tokenQA = CustomKeywords.'common.Utility.getValueFromResponseJson'(tokenResponseQA, 'access_token')
//
//    ResponseObject tokenResponseQAMIG = WS.sendRequest(findTestObject('CommonRequest/Get User Token QA MIG', [('username') : Username
//                , ('password') : Password]))
//
//    WS.verifyResponseStatusCode(tokenResponseQAMIG, 200)
//
//    def tokenQAMIG = CustomKeywords.'common.Utility.getValueFromResponseJson'(tokenResponseQAMIG, 'access_token')

    def path = endPoint
	
	URL_QA = GlobalVariable.baseURL_QA+"/"+GlobalVariable.basePathAPI+"/"+path

    ResponseObject queryResponseQA = WS.sendRequest(findTestObject('CommonRequest/GET_Method_QA', [('token') : GlobalVariable.airlineQA_026_6772279, ('endPoint') : path]))

    WS.verifyResponseStatusCode(queryResponseQA, 200)

    responseQA = queryResponseQA.getResponseBodyContent()
	
	URL_QAMIG = GlobalVariable.baseURL_QA_MIG+"/"+GlobalVariable.basePathAPI+"/"+path

    ResponseObject queryResponseQAMIG = WS.sendRequest(findTestObject('CommonRequest/GET_Method_QA_MIG', [('token') : GlobalVariable.airlineQAMIG_026_6772279, ('endPoint') : path]))

    WS.verifyResponseStatusCode(queryResponseQAMIG, 200)

	responseQAMIG = queryResponseQAMIG.getResponseBodyContent()
	
	// till now > 2 responses in string format responseQA and responseQAMIG
	
	List<String> ignoreNodesList = Arrays.asList(ignoreNodes.split(","))
	
	def jsonSlurper = new JsonSlurper()
	
	Object responseQAObject = jsonSlurper.parseText(responseQA)
	
	JSONObject jsonObjectQA = (JSONObject) responseQAObject;
	
	Object responseQAMIGObject = jsonSlurper.parseText(responseQAMIG)
	
	JSONObject jsonObjectQAMIG = (JSONObject) responseQAMIGObject;
	
	JSONComparator comparator = CustomKeywords.'compare.CompareJsonResponse.getComparator'(ignoreNodes)
	
	CustomKeywords.'compare.GetDifferences.clearSetValues'()
	
		jsonObjectQA.keySet().each { key ->
			
			if(!(jsonObjectQA.get(key) instanceof JSONArray)) {
				
				if(!ignoreNodesList.contains(key)) {
					Object qaValue = jsonObjectQA.get(key)
					Object qamigValue = jsonObjectQAMIG.get(key)
					JSONCompareResult jsonObjectResult = CustomKeywords.'compare.CompareJsonResponse.compareObjectValues'(key, qaValue, qamigValue)
					CustomKeywords.'compare.GetDifferences.setDifferencesFromResult'(jsonObjectResult)
				}			
					
			}else {
				
				JSONArray JSONArrayQA = jsonObjectQA.get(key)
				JSONArray JSONArrayQAMIG = jsonObjectQAMIG.get(key)
				
				
				Object jsonQA = jsonSlurper.parseText(JSONArrayQA.toString())
				
				Object jsonQAMIG = jsonSlurper.parseText(JSONArrayQAMIG.toString())
				
				ignoreNodesList.each { ignoreNode ->
					
					CustomKeywords.'compare.CompareJsonResponse.removeNode'(jsonQA, ignoreNode.replace('categories.',''))
			
					CustomKeywords.'compare.CompareJsonResponse.removeNode'(jsonQAMIG, ignoreNode.replace('categories.',''))
				}
				
				JSONArray joQA = new JSONArray(jsonQA);
				
				JSONArray joQAMIG = new JSONArray(jsonQAMIG);
				
//				JSONArray qaA = CustomKeywords.'compare.CompareJsonResponse.sortJSONArrayAsc'(joQA, keyToSort);
//				JSONArray qamigA = CustomKeywords.'compare.CompareJsonResponse.sortJSONArrayAsc'(joQAMIG, keyToSort);
				
				sortedRecordsQA = joQA.toString()
				sortedRecordsQAMIG = joQAMIG.toString()
				
				JSONCompareResult compareResult = compareJSON(joQA, joQAMIG, JSONCompareMode.NON_EXTENSIBLE)
				
//				JSONCompareResult compareResult = CustomKeywords.'compare.CompareJsonResponse.compareObjectValuesInArray'(keyToSort, qaA, qamigA)

				CustomKeywords.'compare.GetDifferences.setDifferencesFromResult'(compareResult)
			}
		}
	
	fieldsToBeIgnoredList = ignoreNodesList
	
	failedList = new ArrayList(CustomKeywords.'compare.GetDifferences.getFailedFieldsList'())
	missingList = new ArrayList(CustomKeywords.'compare.GetDifferences.getMissingFieldsList'())
	unexpectedList = new ArrayList(CustomKeywords.'compare.GetDifferences.getUnexpectedFieldsList'())
	
	if(failedList.size() > 0 || missingList.size() > 0 || unexpectedList.size() > 0) {
		KeywordUtil.markFailed("Test Case FAILED!! Open After Test Case Step to see the Failed Fields")
	}
}
else {
   KeywordUtil.markWarning("This Test Case is Skipped")
}
	
	
	
	
	
	


