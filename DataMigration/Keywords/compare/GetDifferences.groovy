package compare

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import org.skyscreamer.jsonassert.*
import org.skyscreamer.jsonassert.comparator.*
import static org.skyscreamer.jsonassert.JSONAssert.*
import static org.skyscreamer.jsonassert.JSONCompare.*

import org.json.JSONObject
import org.skyscreamer.jsonassert.JSONCompareResult
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder

import internal.GlobalVariable
import com.kms.katalon.core.util.KeywordUtil as KeywordUtil

public class GetDifferences {

	private static Set<FieldComparisonFailure> failedFieldsList = new LinkedHashSet();

	private static Set<FieldComparisonFailure> missingFieldsList = new LinkedHashSet();

	private static Set<FieldComparisonFailure> unexpectedFieldsList = new LinkedHashSet();

	@Keyword
	public static void setDifferencesFromResult(JSONCompareResult result) {
		failedFieldsList.addAll(result.getFieldFailures())
		result.getFieldFailures().each{fieldFailed ->
			KeywordUtil.markWarning('Failed Field -> '+fieldFailed.getField())
			KeywordUtil.logInfo('Actual Field -> '+fieldFailed.getActual())
			KeywordUtil.logInfo('Expected Field -> '+fieldFailed.getExpected())
		}
		KeywordUtil.logInfo('failedFieldsList -> '+failedFieldsList)
		missingFieldsList.addAll(result.getFieldMissing())
		//		result.getFieldMissing().each{fieldMissing ->
		//			KeywordUtil.logInfo('Missing Field -> '+fieldMissing.getField())
		//			KeywordUtil.logInfo('Actual Field -> '+fieldMissing.getActual())
		//			KeywordUtil.logInfo('Expected Field -> '+fieldMissing.getExpected())
		//		}
		unexpectedFieldsList.addAll(result.getFieldUnexpected())

		//		KeywordUtil.logInfo('failedFieldsList size -> '+failedFieldsList.size())
		//		KeywordUtil.logInfo('missingFieldsList size -> '+missingFieldsList.size())
		//		KeywordUtil.logInfo('unexpectedFieldsList size -> '+unexpectedFieldsList.size())
	}

	@Keyword
	public Set<FieldComparisonFailure> getFailedFieldsList() {
		KeywordUtil.logInfo('failedFieldsList size -> '+failedFieldsList.size())
		return failedFieldsList;
	}

	@Keyword
	public Set<FieldComparisonFailure> getMissingFieldsList() {
		KeywordUtil.logInfo('missingFieldsList size -> '+missingFieldsList.size())
		return missingFieldsList;
	}

	@Keyword
	public Set<FieldComparisonFailure> getUnexpectedFieldsList() {
		KeywordUtil.logInfo('unexpectedFieldsList size -> '+unexpectedFieldsList.size())
		return unexpectedFieldsList;
	}

	@Keyword
	public void clearSetValues() {
		failedFieldsList.clear()
		missingFieldsList.clear()
		unexpectedFieldsList.clear()
	}

	@Keyword
	public static void compareUsingRecursion(JSONObject jsonObjectQA, JSONObject jsonObjectQAMIG, String KEY, JSONComparator comparator, List<String> ignoreNodesList) {
		KeywordUtil.logInfo("ignoreNodesList ---------> "+ignoreNodesList)
		jsonObjectQA.keySet().each{ key ->
			KeywordUtil.logInfo("KEY >>> "+KEY+"."+key)
			KeywordUtil.logInfo("Value class is "+jsonObjectQA.get(key).getClass())
			if(!ignoreNodesList.contains(KEY+"."+key)) {
				if(jsonObjectQA.get(key) instanceof JSONObject) {
					compareUsingRecursion(jsonObjectQA.get(key), jsonObjectQAMIG.get(key), KEY+"."+key, comparator, ignoreNodesList)
				} else if(jsonObjectQA.get(key) instanceof String  || jsonObjectQA.get(key) instanceof Boolean || jsonObjectQA.get(key) instanceof Long){
					String qaS = "{"+KEY+"."+key+":\""+jsonObjectQA.get(key)+"\"}";
					String qamigS = "{"+KEY+"."+key+":\""+jsonObjectQAMIG.get(key)+"\"}";

					KeywordUtil.logInfo("value for qa string "+qaS)
					KeywordUtil.logInfo("value for qamig string "+qamigS)


					JSONCompareResult compareJSON = compareJSON(qaS, qamigS, comparator)
					setDifferencesFromResult(compareJSON)
				}
				KeywordUtil.logInfo("---------")
			}
		}
	}
}
