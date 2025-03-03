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

String stringValue  = 'action.airline.iataCode';

def jsonSlurper = new JsonSlurper()

def response  = '{"action":{"id":"73785455501432506","ticketingAuthority":true,"bsp":{"id":7378,"isoCountryCode":"IN","name":"INDIA"},"airline":{"id":7378545550,"iataCode":"672","name":"ROYXX XXXXXX XXXXXXXX XXX. XXX. XXX XXXXX XXXXXX XXXXXXXX"},"agent":{"id":73781432506,"iataCode":"1432506","name":"VOLXX XXXXX XXX XXXXXXX XXXXXX"}},"author":{"contact":{"name":"MonXXXX XX XXXXX","telephone":"9999999","email":"satxxx.xxxxxxx@accelya.com"},"type":"Airline","code":"672"},"date":"2023-08-28T10:07:10"}';

Object responseObject = jsonSlurper.parseText(response)

List<String> str = stringValue.tokenize('.')

String value;

str.eachWithIndex { s,i ->
	println "stringvalue " + s+ " and index "+i
	if (responseObject && i < str.size() - 1 && responseObject[s]) {
		responseObject = responseObject[s]
	} else if (responseObject && i == str.size() - 1) {
		value = responseObject.getAt(s)
	}
}

println 'value is '+value













