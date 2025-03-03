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
//import java.util.*;
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
import static org.skyscreamer.jsonassert.JSONParser.*
import org.json.JSONArray

import com.kms.katalon.core.testobject.ResponseObject
import org.skyscreamer.jsonassert.comparator.DefaultComparator
import org.skyscreamer.jsonassert.comparator.AbstractComparator

public class CompareJsonResponse {

	@Keyword
	public void compareJSON(String response1, String response2, String ignoreNodes, String message) {
		//		ArrayList<Customization> customizationList = new ArrayList();
		//
		//		Arrays.asList(ignoreNodes.split(",")).forEach({ ignoreNode ->
		//			customizationList.add(new Customization(ignoreNode, {obj1, obj2 -> return true}))
		//		})
		//
		//		Customization[] customizationArray = customizationList.toArray(new Customization[customizationList.size()]);
		//
		//		JSONComparator comparator = new CustomComparator(JSONCompareMode.LENIENT, customizationArray)
		//
		//		assertEquals(message, response1, response2, comparator);

		def slurper = new JsonSlurper()

		def json1Object = slurper.parseText(response1)

		def json2Object = slurper.parseText(response2)

		Arrays.asList(ignoreNodes.split(",")).each { ignoreNode ->

			removeNode(json1Object, ignoreNode)

			removeNode(json2Object, ignoreNode)
		}

		def modifiedJson1 = new JsonBuilder(json1Object).toString()//.replaceAll("\\r\\n|\\r|\\n","")
		println("modifiedJson1 "+modifiedJson1)

		def modifiedJson2 = new JsonBuilder(json2Object).toString()//.replaceAll("\\r\\n|\\r|\\n","")
		println("modifiedJson2 "+modifiedJson2)

		KeywordUtil.logInfo("modifiedJson1")

		KeywordUtil.logInfo(modifiedJson1)

		//		def records1 = getValueFromResponseJson1(modifiedJson1, 'total')

		//		KeywordUtil.logInfo(records1)

		KeywordUtil.logInfo("modifiedJson2")

		KeywordUtil.logInfo(modifiedJson2)

		//		KeywordUtil.logInfo(modifiedJson2)

		//		def records2 = getValueFromResponseJson1(modifiedJson2, 'total')

		//		KeywordUtil.logInfo(records2)

		assertEquals(modifiedJson1, modifiedJson2, JSONCompareMode.LENIENT);
		JSONCompareResult compareResult = compareJSON(new JSONObject(modifiedJson1), new JSONObject(modifiedJson2), JSONCompareMode.LENIENT)

		println "compareResult M size"+ compareResult.getFieldMissing().size()
		println "compareResult F size"+ compareResult.getFieldFailures().size()
		println "compareResult U size"+ compareResult.getFieldUnexpected().size()

		compareResult.getFieldMissing().each{field ->
			println 'Field -> '+field.getField()
			KeywordUtil.logInfo('Fields missing')
			KeywordUtil.logInfo('Field -> '+field.getField())
		}
		//
		compareResult.getFieldFailures().each{fieldFailure ->
			println 'Failed Field -> '+fieldFailure.getField()
			KeywordUtil.logInfo('Fields Failed')
			KeywordUtil.logInfo('Failed Field -> '+fieldFailure.getField())
		}

		compareResult.getFieldUnexpected().each{fieldUnexpected ->
			println 'fieldUnexpected -> ' + fieldUnexpected.getField()
			KeywordUtil.logInfo('Fields Unexpected')

			KeywordUtil.logInfo('fieldUnexpected -> ' + fieldUnexpected.getField())
		}
	}

	@Keyword
	def removeNode(def jsonObject, String nodePath) {

		def keys = nodePath.tokenize('.')

		def currentNode = jsonObject

		keys.eachWithIndex { key, index ->
			if (currentNode instanceof List) {
				currentNode.each { item ->
					if (item) {
						// Check if the item is not null
						removeNode(item, nodePath)
					}
				}
			} else {
				if (currentNode && index < keys.size() - 1 && currentNode[key]) {
					currentNode = currentNode[key]
				} else if (currentNode && index == keys.size() - 1) {
					currentNode.remove(key)
				}
			}
		}
	}

	@Keyword
	def getValueFromResponseJson1(String responseText, String jsonPath) {
		//		def slurper = new JsonSlurper()
		//		def responseText = slurper.parseText(r.getResponseBodyContent())
		def keyValue;
		String keys = jsonPath
		String[] key = keys.split("[.]")
		int length = key.length
		int i=0
		while(length>=i) {
			if(key[i].contains("[")) {
				String numValue = key[i].substring(key[i].indexOf('[')+1,key[i].indexOf(']'))
				String strValue = key[i].substring(0,key[i].indexOf('['))
				int index = Integer.parseInt(numValue)
				if(keyValue == null) {
					keyValue = responseText.getAt(strValue).getAt(index)
					i++
				}else {
					keyValue = keyValue.getAt(strValue).getAt(index)
					i++
				}
			}
			if(keyValue == null) {
				keyValue = responseText.getAt(key[i])
				i++
				if(key.length>1) {
					length++
				}
			}else if(length>i || key.length>1){
				if(key[i].contains("[")) {
					String numValue = key[i].substring(key[i].indexOf('[')+1,key[i].indexOf(']'))
					String strValue = key[i].substring(0,key[i].indexOf('['))
					int index = Integer.parseInt(numValue)
					keyValue = keyValue.getAt(strValue).getAt(index)
					i++
				}else {
					keyValue = keyValue.getAt(key[i])
					i++
				}
			}
			length--
		}
		return keyValue
	}

	@Keyword
	def sortJSONArrayAsc(JSONArray jsonArray, String KEY_NAME) {
		List<JSONObject> jsonValues = new ArrayList<JSONObject>();
		for (int i = 0; i < jsonArray.length(); i++) {
			jsonValues.add(jsonArray.getJSONObject(i));
		}
		jsonValues.sort(new Comparator<JSONObject>() {
					@Override
					public int compare(JSONObject a, JSONObject b) {
						//						println 'a class '+a.get(KEY_NAME).getClass()
						//						println 'b class '+b.get(KEY_NAME).getClass()

						String valA = (String)getValueFromResponseJson(a.toString(), KEY_NAME)
						String valB = (String)getValueFromResponseJson(b.toString(), KEY_NAME)
						//						KeywordUtil.logInfo('sorting '+valA+ " with "+valB)

						return valA.compareTo(valB);
					}
				})
		JSONArray jsonArrayR = parseJSON(jsonValues.toArray().toString())
		return jsonArrayR
	}

	@Keyword
	def getValueFromResponseJson(String jsonString, String jsonPath) {
		def slurper = new JsonSlurper()
		def responseText = slurper.parseText(jsonString)
		def keyValue;
		String keys = jsonPath
		String[] key = keys.split("[.]")
		int length = key.length
		int i=0
		while(length>=i) {
			if(key[i].contains("[")) {
				String numValue = key[i].substring(key[i].indexOf('[')+1,key[i].indexOf(']'))
				String strValue = key[i].substring(0,key[i].indexOf('['))
				int index = Integer.parseInt(numValue)
				if(keyValue == null) {
					keyValue = responseText.getAt(strValue).getAt(index)
					i++
				}else {
					keyValue = keyValue.getAt(strValue).getAt(index)
					i++
				}
			}
			if(keyValue == null) {
				keyValue = responseText.getAt(key[i])
				i++
				if(key.length>1) {
					length++
				}
			}else if(length>i || key.length>1){
				if(key[i].contains("[")) {
					String numValue = key[i].substring(key[i].indexOf('[')+1,key[i].indexOf(']'))
					String strValue = key[i].substring(0,key[i].indexOf('['))
					int index = Integer.parseInt(numValue)
					keyValue = keyValue.getAt(strValue).getAt(index)
					i++
				}else {
					keyValue = keyValue.getAt(key[i])
					i++
				}
			}
			length--
		}
		return keyValue
	}


	@Keyword
	def getComparator(String ignoreNodes) {

		ArrayList<Customization> customizationList = new ArrayList();

		Arrays.asList(ignoreNodes.split(",")).forEach({ ignoreNode ->
			customizationList.add(new Customization(ignoreNode, { obj1, obj2 ->
				return true
			}))
		})

		Customization[] customizationArray = customizationList.toArray(new Customization[customizationList.size()]);

		JSONComparator comparator = new CustomComparator(JSONCompareMode.NON_EXTENSIBLE, customizationArray)

		return comparator
	}

	@Keyword
	def compareObjectValues(String path, Object objQA, Object objQAMIG) {

		JSONCompareResult result = new JSONCompareResult()

		DefaultComparator dc = new DefaultComparator(JSONCompareMode.LENIENT);

		dc.compareValues(path, objQA, objQAMIG, result)

		return result
	}

	@Keyword
	def compareObjectValuesInArray(String key, JSONArray objQA, JSONArray objQAMIG) {

		JSONCompareResult result = new JSONCompareResult()

		AbstractComparator ac = new DefaultComparator(JSONCompareMode.NON_EXTENSIBLE)

		ac.recursivelyCompareJSONArray(key, objQA, objQAMIG, result)

		return result
	}

	@Keyword
	def getValueFromJsonString(String jsonString, String keys) {

		def jsonSlurper = new JsonSlurper()

		Object responseObject = jsonSlurper.parseText(jsonString)

		List<String> keyList = keys.tokenize('.')

		String value;

		keyList.eachWithIndex {key,i ->
			if (responseObject && i < keyList.size() - 1 && responseObject[key]) {
				responseObject = responseObject[key]
			} else if (responseObject && i == keyList.size() - 1) {
				value = responseObject.getAt(key)
			}
		}

		return value
	}
	
	@Keyword
	def JSONObject convertJsonValueToString(JSONObject response, String jsonKeys) {

		List<String> listOfKeys = Arrays.asList(jsonKeys.split(","));
		
		response.keySet().each { key ->

			if(jsonKeys.contains(key)) {
				String qamigValue = (String) response.get(key)
				response.put(key, qamigValue)
			}
		}
		return response;
	}
}
