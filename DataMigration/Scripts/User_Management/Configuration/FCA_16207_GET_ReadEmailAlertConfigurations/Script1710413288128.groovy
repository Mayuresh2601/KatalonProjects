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

    ResponseObject queryResponseQA = WS.sendRequest(findTestObject('CommonRequest/GET_Method_QA', [('token') : GlobalVariable.iataTokenQA, ('endPoint') : path]))

    WS.verifyResponseStatusCode(queryResponseQA, 200)

    responseQA = queryResponseQA.getResponseBodyContent()
	
	URL_QAMIG = GlobalVariable.baseURL_QA_MIG+"/"+GlobalVariable.basePathAPI+"/"+path

    ResponseObject queryResponseQAMIG = WS.sendRequest(findTestObject('CommonRequest/GET_Method_QA_MIG', [('token') : GlobalVariable.iataTokenQAMIG
                , ('endPoint') : path]))

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
	
	if(jsonObjectQA.has("parameters") || jsonObjectQAMIG.has("parameters")) {
	
		jsonObjectQA.keySet().each { key ->
			KeywordUtil.logInfo('KEY '+key)
			if(!(jsonObjectQA.get(key) instanceof JSONArray)) {
				
				if(!ignoreNodesList.contains(key)) {
					Object qaValue = jsonObjectQA.get(key)
					Object qamigValue = jsonObjectQAMIG.get(key)
					KeywordUtil.logInfo('===================================')
					KeywordUtil.logInfo('KEY '+key)
					KeywordUtil.logInfo('qaValue '+qaValue)
					KeywordUtil.logInfo('qamigValue '+qamigValue)
					KeywordUtil.logInfo('===================================')
					JSONCompareResult jsonObjectResult = CustomKeywords.'compare.CompareJsonResponse.compareObjectValues'(key, qaValue, qamigValue)
					CustomKeywords.'compare.GetDifferences.setDifferencesFromResult'(jsonObjectResult)
				}			
					
			}else if(key.equals('parameters')) {
				
				JSONArray JSONArrayQA = jsonObjectQA.get(key)
				JSONArray JSONArrayQAMIG = jsonObjectQAMIG.get(key)
				
				if(JSONArrayQA.length()==0) {
					KeywordUtil.markFailedAndStop("There are 0 RECORDS in the Reponse from QA")
				} else if(JSONArrayQAMIG.length()==0) {
					KeywordUtil.markFailedAndStop("There are 0 RECORDS in the Reponse from QAMIG")
				}
				
//				if(JSONArrayQA.length()!=JSONArrayQAMIG.length()) {
//					KeywordUtil.markFailed("There are "+JSONArrayQA.length()+" RECORDS in QA and "+JSONArrayQAMIG.length()+" RECORDS in QAMIG")
//				}
				
				ArrayList<String> id1s = CustomKeywords.'common.Utility.getValueFromResponseJson'(queryResponseQA, 'parameters.'+keyToSort)
				ArrayList<String> id2s = CustomKeywords.'common.Utility.getValueFromResponseJson'(queryResponseQAMIG, 'parameters.'+keyToSort)
								
				KeywordUtil.logInfo("QA ID "+id1s)
				KeywordUtil.logInfo("QAMIG ID "+id2s)
				
				if(id1s.size() != id2s.size()) {
					List<String> missingIds = id2s.size()>id1s.size() ? id2s.stream().filter({list2value -> !(id1s.contains(list2value))}).collect(Collectors.toList()): id1s.stream().filter({list1value -> !(id2s.contains(list1value))}).collect(Collectors.toList());

					missing_IDs = missingIds
					
					KeywordUtil.logInfo("missing "+missingIds)

					List<String> presentIds = id2s.stream().filter({list2value -> id1s.contains(list2value)}).collect(Collectors.toList());

					//remove extra IDs
					missingIds.each{missingId ->
						for(int index=0; index<JSONArrayQAMIG.length(); index++) {
							if(JSONArrayQAMIG.get(index).getAt(keyToSort) == missingId) {
								JSONArrayQAMIG.remove(index)
							}
						}
						for(int index=0; index<JSONArrayQA.length(); index++) {
							if(JSONArrayQA.get(index).getAt(keyToSort) == missingId) {
								JSONArrayQA.remove(index)
							}
						}
					}
				}
				
				KeywordUtil.logInfo("qa after missing removed "+JSONArrayQA.toString())
				
				KeywordUtil.logInfo("qamig after missing removed "+JSONArrayQAMIG.toString())
				
				Object jsonQA = jsonSlurper.parseText(JSONArrayQA.toString())
				
				Object jsonQAMIG = jsonSlurper.parseText(JSONArrayQAMIG.toString())
				
				ignoreNodesList.each { ignoreNode ->
					KeywordUtil.logInfo("node to be removed "+ignoreNode) 
					if(ignoreNode.contains("parameters")) {
						KeywordUtil.logInfo("node is being removed "+ignoreNode)
						CustomKeywords.'compare.CompareJsonResponse.removeNode'(jsonQA, ignoreNode.replace('parameters.',''))
				
						CustomKeywords.'compare.CompareJsonResponse.removeNode'(jsonQAMIG, ignoreNode.replace('parameters.',''))
					}
				}
				
				JSONArray joQA = new JSONArray(jsonQA);
				
				JSONArray joQAMIG = new JSONArray(jsonQAMIG);
				
				KeywordUtil.logInfo("qa before sorting "+joQA.toString())
				
				KeywordUtil.logInfo("qamig before sorting "+joQAMIG.toString())
				
				KeywordUtil.logInfo("sort with "+keyToSort)
				
				JSONArray qaA = CustomKeywords.'compare.CompareJsonResponse.sortJSONArrayAsc'(joQA, keyToSort);
				JSONArray qamigA = CustomKeywords.'compare.CompareJsonResponse.sortJSONArrayAsc'(joQAMIG, keyToSort);
				
				sortedRecordsQA = joQA.toString()
				sortedRecordsQAMIG = joQAMIG.toString()
				
				JSONCompareResult compareResult = compareJSON(qaA, qamigA, JSONCompareMode.STRICT)

				CustomKeywords.'compare.GetDifferences.setDifferencesFromResult'(compareResult)
			}
		}
	} else {
		ignoreNodesList.each { node ->
			CustomKeywords.'compare.CompareJsonResponse.removeNode'(jsonObjectQA, node)
			
			CustomKeywords.'compare.CompareJsonResponse.removeNode'(jsonObjectQAMIG, node)
		}
		JSONCompareResult compareJSON = compareJSON(jsonObjectQA, jsonObjectQAMIG, comparator)
		CustomKeywords.'compare.GetDifferences.setDifferencesFromResult'(compareJSON)
	}
	
	fieldsToBeIgnoredList = ignoreNodesList
	
	failedList = new ArrayList(CustomKeywords.'compare.GetDifferences.getFailedFieldsList'())
	missingList = new ArrayList(CustomKeywords.'compare.GetDifferences.getMissingFieldsList'())
	unexpectedList = new ArrayList(CustomKeywords.'compare.GetDifferences.getUnexpectedFieldsList'())
	
	if(failedList.size() > 0) {
		KeywordUtil.markFailed("Test Case FAILED!! Open After Test Case Step to see the Failed Fields")
	}
}
else {
   KeywordUtil.markWarning("This Test Case is Skipped")
   CustomKeywords.'reporting.ExtentReporting.testStatus'('SKIP',"This Test Case is Skipped")
}
	
	
	
	
	
	


