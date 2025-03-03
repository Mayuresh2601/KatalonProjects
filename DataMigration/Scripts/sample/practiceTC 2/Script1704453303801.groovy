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
import org.json.JSONObject
import org.skyscreamer.jsonassert.JSONCompareResult
import groovy.json.JsonSlurper
import groovy.json.JsonBuilder
import org.json.JSONArray

String jsonArrayString  = '[{"downloadDate":"2023-01-03T00:19:35","receiverType":"Agent","name":"INaz14370543_20230101_221204_Agent_Billing.zip","descriptor":"az","type":"IBSPs Billing Output","receiverUserId":"1437054"},{"downloadDate":"2023-01-03T00:48:51","receiverType":"Agent","name":"INaz14001326_20230102_230102_Agent_Daily_Transaction_Files.zip","descriptor":"az","type":"IBSPs Billing Output","receiverUserId":"1400132"},{"downloadDate":"2023-01-03T00:48:51","receiverType":"Agent","name":"INaz14001326_20230101_230101_Agent_Daily_Transaction_Files.zip","descriptor":"az","type":"IBSPs Billing Output","receiverUserId":"1400132"},{"downloadDate":"2023-01-03T00:27:36","receiverType":"Agent","name":"INmk14344621_20230101_230101_SCB_BankStatement1.pdf","descriptor":"mk","type":"Unmasked CC# file(s)","receiverUserId":"1434462"},{"downloadDate":"2023-01-03T00:48:52","receiverType":"Agent","name":"INaz14001326_20221231_221231_Agent_Daily_Transaction_Files.zip","descriptor":"az","type":"IBSPs Billing Output","receiverUserId":"1400132"},{"downloadDate":"2023-01-03T00:48:52","receiverType":"Agent","name":"INaz14001326_20221230_221230_Agent_Daily_Transaction_Files.zip","descriptor":"az","type":"IBSPs Billing Output","receiverUserId":"1400132"},{"downloadDate":"2023-01-03T00:48:52","receiverType":"Agent","name":"INaz14001326_20221228_221228_Agent_Daily_Transaction_Files.zip","descriptor":"az","type":"IBSPs Billing Output","receiverUserId":"1400132"},{"downloadDate":"2023-01-03T00:27:25","receiverType":"Agent","name":"INaz14344621_20230101_221204_Agent_Billing.zip","descriptor":"az","type":"IBSPs Billing Output","receiverUserId":"1434462"},{"downloadDate":"2023-01-03T00:48:52","receiverType":"Agent","name":"INaz14001326_20221229_221229_Agent_Daily_Transaction_Files.zip","descriptor":"az","type":"IBSPs Billing Output","receiverUserId":"1400132"}]';

def jsonSlurper = new JsonSlurper()

Object responseQAObject = jsonSlurper.parseText(jsonArrayString)

//JSONArray jsonObjectQA = (JSONArray) responseQAObject;
//
//println 'JSONArray '+jsonObjectQA.toString()

JSONArray joQA = (JSONArray) responseQAObject;

println 'before sorting '+jsonArrayString

JSONArray qaA = CustomKeywords.'compare.CompareJsonResponse.sortJSONArrayAsc'(joQA, 'downloadDate');


List<String> dateL = new ArrayList();

Set<String> dateS = new HashSet();

List<JSONObject> listJSON = new ArrayList()

for(int i = 0; i<qaA.length(); i++) {
	String o  = (String)qaA.get(i).getAt("downloadDate")
	dateL.add(o)
	dateS.add(o)
	println 'object '+o
}

println 'dates List'+dateL.size()

println 'dates Set '+dateS.size()




for(int j = 0; j<dateS.size(); j++) {
	String o  = (String)qaA.get(i).getAt("downloadDate")
}


