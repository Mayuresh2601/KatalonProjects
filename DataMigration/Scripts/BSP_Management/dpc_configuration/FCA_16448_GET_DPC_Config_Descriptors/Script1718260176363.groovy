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

    ResponseObject queryResponseQA = WS.sendRequest(findTestObject('CommonRequest/GET_Method_QA', [('token') : GlobalVariable.dpcIN_QA, ('endPoint') : path]))

    WS.verifyResponseStatusCode(queryResponseQA, 200)

    responseQA = queryResponseQA.getResponseBodyContent()
	
	URL_QAMIG = GlobalVariable.baseURL_QA_MIG+"/"+GlobalVariable.basePathAPI+"/"+path

    ResponseObject queryResponseQAMIG = WS.sendRequest(findTestObject('CommonRequest/GET_Method_QA_MIG', [('token') : GlobalVariable.dpcIN_QAMIG, ('endPoint') : path]))

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
	
//	if(jsonObjectQA.has("parameters") || jsonObjectQAMIG.has("parameters")) {
	
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
					
			}
			else if(key.equals('categories')) {
				
				JSONArray JSONArrayQAC = jsonObjectQA.get(key)
				JSONArray JSONArrayQAMIGC = jsonObjectQAMIG.get(key)
				
				JSONObject jsonObjectQA1 = (JSONObject) JSONArrayQAC.get(0);
				JSONObject jsonObjectQAMIG1 = (JSONObject) JSONArrayQAMIGC.get(0);
				
				KeywordUtil.logInfo('KEY QA '+jsonObjectQA1.keySet())
				KeywordUtil.logInfo('KEY QAMIG '+jsonObjectQAMIG1.keySet())
				
				jsonObjectQA1.keySet().each{key1 ->
					KeywordUtil.logInfo('KEY1 '+key1)
					if(!(jsonObjectQA1.get(key1) instanceof JSONArray)) {
						KeywordUtil.logInfo("ignoreNodesList " +ignoreNodesList)
						if(!ignoreNodesList.contains(key1)) {
							Object qaValue1 = jsonObjectQA1.get(key1)
							Object qamigValue1 = jsonObjectQAMIG1.get(key1)
							KeywordUtil.logInfo('===================================')
							KeywordUtil.logInfo('compare KEY1 '+key1)
							KeywordUtil.logInfo('qaValue '+qaValue1)
							KeywordUtil.logInfo('qamigValue '+qamigValue1)
							KeywordUtil.logInfo('===================================')
							JSONCompareResult jsonObjectResult1 = CustomKeywords.'compare.CompareJsonResponse.compareObjectValues'(key1, qaValue1, qamigValue1)
							CustomKeywords.'compare.GetDifferences.setDifferencesFromResult'(jsonObjectResult1)
						}
						
					}else {
						JSONArray JSONArrayQAP = jsonObjectQA1.get(key1)
						JSONArray JSONArrayQAMIGP = jsonObjectQAMIG1.get(key1)
						ArrayList<String> id1s = CustomKeywords.'common.Utility.getValueFromResponseJsonString'(jsonObjectQA1.toString(), 'parameters.'+keyToSort)
						ArrayList<String> id2s = CustomKeywords.'common.Utility.getValueFromResponseJsonString'(jsonObjectQAMIG1.toString(), 'parameters.'+keyToSort)
										
						KeywordUtil.logInfo("QA ID "+id1s)
						KeywordUtil.logInfo("QAMIG ID "+id2s)
						
						if(id1s.size() != id2s.size()) {
							List<String> missingIds = id2s.size()>id1s.size() ? id2s.stream().filter({list2value -> !(id1s.contains(list2value))}).collect(Collectors.toList()): id1s.stream().filter({list1value -> !(id2s.contains(list1value))}).collect(Collectors.toList());
		
							missing_IDs = missingIds
							
							if(JSONArrayQAP.length()!=JSONArrayQAMIGP.length() && !missingIds.contains(ignoreRecordHavingKey)) {
								KeywordUtil.markFailed("There are "+JSONArrayQAP.length()+" RECORDS in QA and "+JSONArrayQAMIGP.length()+" RECORDS in QAMIG")
							}
							
							KeywordUtil.logInfo("missing "+missingIds)
							
							List<String> presentIds = id2s.stream().filter({list2value -> id1s.contains(list2value)}).collect(Collectors.toList());
		
							//remove extra IDs
							missingIds.each{missingId ->
								for(int index=0; index<JSONArrayQAMIGP.length(); index++) {
									if(JSONArrayQAMIGP.get(index).getAt(keyToSort) == missingId) {
										JSONArrayQAMIGP.remove(index)
									}
								}
								for(int index=0; index<JSONArrayQAP.length(); index++) {
									if(JSONArrayQAP.get(index).getAt(keyToSort) == missingId) {
										JSONArrayQAP.remove(index)
									}
								}
							}
							
							missingIds.remove(ignoreRecordHavingKey);
						}
						
						KeywordUtil.logInfo("qa after missing removed "+JSONArrayQAP.toString())
						
						KeywordUtil.logInfo("qamig after missing removed "+JSONArrayQAMIGP.toString())
						
						Object jsonQA = jsonSlurper.parseText(JSONArrayQAP.toString())
						
						Object jsonQAMIG = jsonSlurper.parseText(JSONArrayQAMIGP.toString())
						
						ignoreNodesList.each { ignoreNode ->
							
							CustomKeywords.'compare.CompareJsonResponse.removeNode'(jsonQA, ignoreNode.replace('parameters.',''))
					
							CustomKeywords.'compare.CompareJsonResponse.removeNode'(jsonQAMIG, ignoreNode.replace('parameters.',''))
						}
						
						JSONArray joQA = new JSONArray(jsonQA);
						
						JSONArray joQAMIG = new JSONArray(jsonQAMIG);
						
						JSONArray qaA = CustomKeywords.'compare.CompareJsonResponse.sortJSONArrayAsc'(joQA, keyToSort);
						JSONArray qamigA = CustomKeywords.'compare.CompareJsonResponse.sortJSONArrayAsc'(joQAMIG, keyToSort);
						
						sortedRecordsQA = joQA.toString()
						sortedRecordsQAMIG = joQAMIG.toString()
						
						JSONCompareResult compareResult = compareJSON(qaA, qamigA, JSONCompareMode.STRICT)
		
						CustomKeywords.'compare.GetDifferences.setDifferencesFromResult'(compareResult)
						
					}					
//				here
			}
		}
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
	
	
	
	
	
	


