package common
import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData

import java.nio.file.Path
import java.util.stream.Collector
import java.util.stream.Collectors
import java.util.stream.IntStream
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import org.testng.Assert

import com.kms.katalon.core.annotation.Keyword
import com.kms.katalon.core.checkpoint.Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.testcase.TestCase
import com.kms.katalon.core.testdata.TestData
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.testobject.TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows

import groovy.json.JsonSlurper
import groovy.transform.Undefined.CLASS
import groovy.xml.StreamingDOMBuilder
import internal.GlobalVariable

public class Utility {

	/**Method: To Sort the List in Ascending or Descending Order
	 * @param order
	 * @param data
	 * @return List of String
	 */
	@Keyword
	def List<String> listSorting(String order, List<String> data) {

		List<String> sortedData

		if(order.equalsIgnoreCase("ASC")) {
			sortedData = data.stream().sorted().collect(Collectors.toList())
			return sortedData
		}else if(order.equalsIgnoreCase("DESC")) {
			sortedData = data.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList())
			return sortedData
		}else {
			println("Invalid Sorting Type...")
			return data
		}
	}

	/**Method: Compare Two list to check if its identical
	 * @param compareData
	 * @param sortingType
	 * @param list1
	 * @param list2
	 */
	@Keyword
	def compareTwoIdenticalList(List<String> list1, List<String> list2) {

		try {

			for(int i=0; i< list1.size() ; i++) {
				//				println("Comparing: "+list1.get(i)+" with "+list2.get(i))
				if(!list1.get(i).equalsIgnoreCase(list2.get(i))) {
					println("List are not Identical")
					Assert.assertTrue(list1.get(i).equalsIgnoreCase(list2.get(i)))
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
	}

	/**Method: Validate the Permission with the actual vs expected value
	 * @param permission
	 * @param checkString
	 * @return boolean
	 */
	@Keyword
	def boolean validatePermission(String permission, String checkString) {

		boolean flag = false

		if(permission.contains(checkString)) {
			flag = true
		}
		else {
			flag = false
		}

		return flag
	}

	/**Method: Validate the Permission with the actual vs expected value and return permission parameter
	 * status value
	 * @param response
	 * @param value1
	 * @param value2
	 * @param num
	 * @return status
	 */
	@Keyword
	def validateParameterAndGetStatus(ResponseObject res, String value1, String value2, int num) {

		def slurper = new JsonSlurper()
		def response = slurper.parseText(res.getResponseBodyContent())
		def status
		try {

			if(value1.equalsIgnoreCase(value2)) {
				status = response.enabled[num]
			} else {
				Assert.assertEquals(value1, value2)
			}
		} catch (Exception e) {
			e.printStackTrace()
		}
		return status
	}

	/**Method: Method: To generate generic payload and updating the status of the permission
	 * @param id
	 * @param status
	 * @param name
	 * @param permissionName1
	 * @param permissionName2
	 * @param flag
	 * @return
	 */
	@Keyword
	def updatePermissionsStatusAndGetPayload(List<Integer> id, List<String> status, List<String> name, List<String> permissionNames, String flag) {

		for(String permissionName : permissionNames) {
			def index = name.indexOf(permissionName)
			status.set(index, flag)
		}

		String data1 = "["

		for(int i=0; i<id.size(); i++) {

			data1 += '{"id":'+ id.get(i) +',"enabled":'+ status.get(i) +'}'
			if(!(i == id.size()-1)){
				data1 += ","
			}else {
				data1 += "]"
			}
		}
		return data1
	}

	/**Method: To get data from Json Response
	 * @param r
	 * @param jsonPath
	 * @return Key Data
	 */
	@Keyword
	def getValueFromResponseJson(ResponseObject r, String jsonPath) {
		def slurper = new JsonSlurper()
		def responseText = slurper.parseText(r.getResponseBodyContent())
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

	/**Method: To Validate the status of a all the permission in the list
	 * @param getAllNames
	 * @param defaultPermissionsResponse
	 * @param permissionName
	 * @param expectedStatus
	 */
	@Keyword
	def getPermissionStatusAndValidate(List<String> getAllNames, ResponseObject defaultPermissionsResponse, List<String> permissionNames, String expectedStatus) {

		String getStatus

		for(String permissionName : permissionNames) {

			def index = getAllNames.indexOf(permissionName)
			def getName = getValueFromResponseJson(defaultPermissionsResponse, 'name['+ index +']')
			getStatus = validateParameterAndGetStatus(defaultPermissionsResponse, getName, permissionName, index)
		}

		Assert.assertEquals(expectedStatus, getStatus)
	}

	@Keyword
	def validateTotalRecords(ResponseObject acceptanceCriteria, List<String> getAllResponseAttribute) {

		int totalRecord  = getValueFromResponseJson(acceptanceCriteria, 'total')
		WS.verifyResponseStatusCode(acceptanceCriteria, 200)
		int size = getAllResponseAttribute.size()
		Assert.assertEquals(totalRecord, size)
	}

	@Keyword
	def getValueFromResponseJsonString(String r, String jsonPath) {
		def slurper = new JsonSlurper()
		def responseText = slurper.parseText(r)
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
}