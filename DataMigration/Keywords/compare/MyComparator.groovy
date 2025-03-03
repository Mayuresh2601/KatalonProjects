package compare

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import org.skyscreamer.jsonassert.comparator.CustomComparator

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

import org.json.JSONObject
import org.skyscreamer.jsonassert.comparator.*
import org.skyscreamer.jsonassert.JSONCompareResult
import org.skyscreamer.jsonassert.*
import org.json.JSONException

import internal.GlobalVariable

public class MyComparator extends CustomComparator{
	private final Set attributesToIgnore;
	
		public MyComparator(JSONCompareMode mode, Set attributesToIgnore, Customization... customizations) {
			super(mode, customizations);
			this.attributesToIgnore = attributesToIgnore;
		}
	
		@Override
		protected void checkJsonObjectKeysExpectedInActual(String prefix, JSONObject expected, JSONObject actual, JSONCompareResult result) throws JSONException {
			//Remove ignored keys from json object
			attributesToIgnore.forEach({attribute -> expected.remove(attribute)});
			super.checkJsonObjectKeysExpectedInActual(prefix, expected, actual, result);
		}
}
