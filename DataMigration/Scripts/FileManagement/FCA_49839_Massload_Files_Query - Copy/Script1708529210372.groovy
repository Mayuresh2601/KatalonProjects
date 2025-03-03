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
import net.javacrumbs.jsonunit.assertj.JsonAssertions

//if (flag == 'Y') {

    def path = endPoint
	
	URL_QA = GlobalVariable.baseURL_QA+"/"+GlobalVariable.basePathAPI+"/"+path

    ResponseObject queryResponseQA = WS.sendRequest(findTestObject('CommonRequest/GET_Method_QA', [('token') : tokenQA, ('endPoint') : path]))

    WS.verifyResponseStatusCode(queryResponseQA, 200)

    responseQA = queryResponseQA.getResponseBodyContent()
	
	URL_QAMIG = GlobalVariable.baseURL_QA_MIG+"/"+GlobalVariable.basePathAPI+"/"+path

    ResponseObject queryResponseQAMIG = WS.sendRequest(findTestObject('CommonRequest/GET_Method_QA_MIG', [('token') : tokenQAMIG
                , ('endPoint') : path]))

    WS.verifyResponseStatusCode(queryResponseQAMIG, 200)

	responseQAMIG = queryResponseQAMIG.getResponseBodyContent()
	
	// till now > 2 responses in string format responseQA and responseQAMIG
	
	List<String> ignoreNodesList = Arrays.asList(ignoreNodes.split(","))
	
	ArrayList<String> actualPathList = new ArrayList<>();
	ArrayList<String> actualValueList = new ArrayList<>();
	ArrayList<String> expPathList = new ArrayList<>();
	ArrayList<String> expValueList = new ArrayList<>();
	
//	JsonAssertions.assertThatJson(responseQA).isEqualTo(responseQAMIG);
	
	JsonAssertions.assertThatJson(responseQA).withDifferenceListener({difference, context->
		String actualPath = difference.getActualPath();
		actualPathList.add(actualPath);
		String actual = ""+difference.getActual();
		actualValueList.add(actual);
		String expPath = difference.getExpectedPath();
		expPathList.add(expPath);
		String exp = "" + difference.getExpected();
		expValueList.add(exp);
	}).isEqualTo(responseQAMIG);

	for (int i = 0; i < actualPathList.size(); i++) {
		println actualPathList.get(i)+" | "+actualValueList.get(i)+" | "+expPathList.get(i)+" | "+expValueList.get(i)+ System.lineSeparator();
	}
//}
//else {
//   KeywordUtil.markWarning("This Test Case is Skipped")
//}
	
	
	
	
	
	


