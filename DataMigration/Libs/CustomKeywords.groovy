
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import java.lang.String

import java.io.File

import org.json.JSONArray

import java.lang.Object

import org.json.JSONObject

import java.util.List

import com.kms.katalon.core.testobject.ResponseObject

import io.restassured.specification.RequestSpecification

import com.kms.katalon.core.testobject.TestObject

import org.skyscreamer.jsonassert.JSONCompareResult

import org.skyscreamer.jsonassert.comparator.JSONComparator



def static "common.FileUtility.convertZipToFile"(
    	byte[] file	
     , 	String filePath	
     , 	String extension	) {
    (new common.FileUtility()).convertZipToFile(
        	file
         , 	filePath
         , 	extension)
}


def static "common.FileUtility.removeFile"(
    	String downloadPath	
     , 	String fileName	) {
    (new common.FileUtility()).removeFile(
        	downloadPath
         , 	fileName)
}


def static "common.FileUtility.getFileName"(
    	File folder	
     , 	String extension	) {
    (new common.FileUtility()).getFileName(
        	folder
         , 	extension)
}


def static "common.FileUtility.getFileContentColumnHeader"(
    	String filePath	
     , 	String fileName	
     , 	String extension	) {
    (new common.FileUtility()).getFileContentColumnHeader(
        	filePath
         , 	fileName
         , 	extension)
}


def static "common.FileUtility.getFileContentByColumn"(
    	String filePath	
     , 	String fileName	
     , 	String columnName	
     , 	String extension	) {
    (new common.FileUtility()).getFileContentByColumn(
        	filePath
         , 	fileName
         , 	columnName
         , 	extension)
}


def static "common.FileUtility.getColumnNameInFile"(
    	String value	) {
    (new common.FileUtility()).getColumnNameInFile(
        	value)
}


def static "common.FileUtility.readFileContent"(
    	String filePath	
     , 	String fileName	) {
    (new common.FileUtility()).readFileContent(
        	filePath
         , 	fileName)
}


def static "common.FileUtility.getFileContentWithSpaceByColumn"(
    	String filePath	
     , 	String fileName	
     , 	String columnName	
     , 	String extension	) {
    (new common.FileUtility()).getFileContentWithSpaceByColumn(
        	filePath
         , 	fileName
         , 	columnName
         , 	extension)
}


def static "compare.CompareJsonResponse.compareJSON"(
    	String response1	
     , 	String response2	
     , 	String ignoreNodes	
     , 	String message	) {
    (new compare.CompareJsonResponse()).compareJSON(
        	response1
         , 	response2
         , 	ignoreNodes
         , 	message)
}


def static "compare.CompareJsonResponse.removeNode"(
    	Object jsonObject	
     , 	String nodePath	) {
    (new compare.CompareJsonResponse()).removeNode(
        	jsonObject
         , 	nodePath)
}


def static "compare.CompareJsonResponse.getValueFromResponseJson1"(
    	String responseText	
     , 	String jsonPath	) {
    (new compare.CompareJsonResponse()).getValueFromResponseJson1(
        	responseText
         , 	jsonPath)
}


def static "compare.CompareJsonResponse.sortJSONArrayAsc"(
    	JSONArray jsonArray	
     , 	String KEY_NAME	) {
    (new compare.CompareJsonResponse()).sortJSONArrayAsc(
        	jsonArray
         , 	KEY_NAME)
}


def static "compare.CompareJsonResponse.getValueFromResponseJson"(
    	String jsonString	
     , 	String jsonPath	) {
    (new compare.CompareJsonResponse()).getValueFromResponseJson(
        	jsonString
         , 	jsonPath)
}


def static "compare.CompareJsonResponse.getComparator"(
    	String ignoreNodes	) {
    (new compare.CompareJsonResponse()).getComparator(
        	ignoreNodes)
}


def static "compare.CompareJsonResponse.compareObjectValues"(
    	String path	
     , 	Object objQA	
     , 	Object objQAMIG	) {
    (new compare.CompareJsonResponse()).compareObjectValues(
        	path
         , 	objQA
         , 	objQAMIG)
}


def static "compare.CompareJsonResponse.compareObjectValuesInArray"(
    	String key	
     , 	JSONArray objQA	
     , 	JSONArray objQAMIG	) {
    (new compare.CompareJsonResponse()).compareObjectValuesInArray(
        	key
         , 	objQA
         , 	objQAMIG)
}


def static "compare.CompareJsonResponse.getValueFromJsonString"(
    	String jsonString	
     , 	String keys	) {
    (new compare.CompareJsonResponse()).getValueFromJsonString(
        	jsonString
         , 	keys)
}


def static "compare.CompareJsonResponse.convertJsonValueToString"(
    	JSONObject response	
     , 	String jsonKeys	) {
    (new compare.CompareJsonResponse()).convertJsonValueToString(
        	response
         , 	jsonKeys)
}

 /**Method: To Sort the List in Ascending or Descending Order
	 * @param order
	 * @param data
	 * @return List of String
	 */ 
def static "common.Utility.listSorting"(
    	String order	
     , 	java.util.List<String> data	) {
    (new common.Utility()).listSorting(
        	order
         , 	data)
}

 /**Method: Compare Two list to check if its identical
	 * @param compareData
	 * @param sortingType
	 * @param list1
	 * @param list2
	 */ 
def static "common.Utility.compareTwoIdenticalList"(
    	java.util.List<String> list1	
     , 	java.util.List<String> list2	) {
    (new common.Utility()).compareTwoIdenticalList(
        	list1
         , 	list2)
}

 /**Method: Validate the Permission with the actual vs expected value
	 * @param permission
	 * @param checkString
	 * @return boolean
	 */ 
def static "common.Utility.validatePermission"(
    	String permission	
     , 	String checkString	) {
    (new common.Utility()).validatePermission(
        	permission
         , 	checkString)
}

 /**Method: Validate the Permission with the actual vs expected value and return permission parameter
	 * status value
	 * @param response
	 * @param value1
	 * @param value2
	 * @param num
	 * @return status
	 */ 
def static "common.Utility.validateParameterAndGetStatus"(
    	ResponseObject res	
     , 	String value1	
     , 	String value2	
     , 	int num	) {
    (new common.Utility()).validateParameterAndGetStatus(
        	res
         , 	value1
         , 	value2
         , 	num)
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
def static "common.Utility.updatePermissionsStatusAndGetPayload"(
    	java.util.List<java.lang.Integer> id	
     , 	java.util.List<String> status	
     , 	java.util.List<String> name	
     , 	java.util.List<String> permissionNames	
     , 	String flag	) {
    (new common.Utility()).updatePermissionsStatusAndGetPayload(
        	id
         , 	status
         , 	name
         , 	permissionNames
         , 	flag)
}

 /**Method: To get data from Json Response
	 * @param r
	 * @param jsonPath
	 * @return Key Data
	 */ 
def static "common.Utility.getValueFromResponseJson"(
    	ResponseObject r	
     , 	String jsonPath	) {
    (new common.Utility()).getValueFromResponseJson(
        	r
         , 	jsonPath)
}

 /**Method: To Validate the status of a all the permission in the list
	 * @param getAllNames
	 * @param defaultPermissionsResponse
	 * @param permissionName
	 * @param expectedStatus
	 */ 
def static "common.Utility.getPermissionStatusAndValidate"(
    	java.util.List<String> getAllNames	
     , 	ResponseObject defaultPermissionsResponse	
     , 	java.util.List<String> permissionNames	
     , 	String expectedStatus	) {
    (new common.Utility()).getPermissionStatusAndValidate(
        	getAllNames
         , 	defaultPermissionsResponse
         , 	permissionNames
         , 	expectedStatus)
}


def static "common.Utility.validateTotalRecords"(
    	ResponseObject acceptanceCriteria	
     , 	java.util.List<String> getAllResponseAttribute	) {
    (new common.Utility()).validateTotalRecords(
        	acceptanceCriteria
         , 	getAllResponseAttribute)
}


def static "common.Utility.getValueFromResponseJsonString"(
    	String r	
     , 	String jsonPath	) {
    (new common.Utility()).getValueFromResponseJsonString(
        	r
         , 	jsonPath)
}


def static "restAssuredMethods.RestClient.givenConnection"(
    	String baseURL	
     , 	String basePath	
     , 	String token	) {
    (new restAssuredMethods.RestClient()).givenConnection(
        	baseURL
         , 	basePath
         , 	token)
}


def static "restAssuredMethods.RestClient.doGet"(
    	String endPoint	
     , 	RequestSpecification requestSpecification	) {
    (new restAssuredMethods.RestClient()).doGet(
        	endPoint
         , 	requestSpecification)
}


def static "restAssuredMethods.RestClient.doPost"(
    	String path	
     , 	Object object	
     , 	RequestSpecification givenConnection	) {
    (new restAssuredMethods.RestClient()).doPost(
        	path
         , 	object
         , 	givenConnection)
}


def static "compare.GetterAndSetter.setQAResponseValue"(
    	String value	) {
    (new compare.GetterAndSetter()).setQAResponseValue(
        	value)
}


def static "compare.GetterAndSetter.getQAResponseValue"() {
    (new compare.GetterAndSetter()).getQAResponseValue()
}


def static "reporting.ExtentReporting.startTest"(
    	String reportPath	
     , 	String testSuiteName	
     , 	String testCaseName	
     , 	String testCaseDescription	
     , 	String author	
     , 	String category	
     , 	String reportFile	) {
    (new reporting.ExtentReporting()).startTest(
        	reportPath
         , 	testSuiteName
         , 	testCaseName
         , 	testCaseDescription
         , 	author
         , 	category
         , 	reportFile)
}


def static "reporting.ExtentReporting.extentlogNScreenshot"(
    	String status1	
     , 	String logMessage	
     , 	String flag	) {
    (new reporting.ExtentReporting()).extentlogNScreenshot(
        	status1
         , 	logMessage
         , 	flag)
}


def static "reporting.ExtentReporting.encodeImageToBase64"(
    	String imagePath	) {
    (new reporting.ExtentReporting()).encodeImageToBase64(
        	imagePath)
}


def static "reporting.ExtentReporting.testStatus"(
    	String status	
     , 	String message	) {
    (new reporting.ExtentReporting()).testStatus(
        	status
         , 	message)
}


def static "reporting.ExtentReporting.captureScreenshot"(
    	String logMessage	) {
    (new reporting.ExtentReporting()).captureScreenshot(
        	logMessage)
}


def static "reporting.ExtentReporting.verifyTextAndLogStatus"(
    	TestObject element	
     , 	String expectedText	) {
    (new reporting.ExtentReporting()).verifyTextAndLogStatus(
        	element
         , 	expectedText)
}


def static "reporting.ExtentReporting.logResponseAndStatus"(
    	String responseBody	
     , 	int statusCode	) {
    (new reporting.ExtentReporting()).logResponseAndStatus(
        	responseBody
         , 	statusCode)
}


def static "reporting.ExtentReporting.logJSONData"(
    	String label	
     , 	String JSON_Data	) {
    (new reporting.ExtentReporting()).logJSONData(
        	label
         , 	JSON_Data)
}


def static "reporting.ExtentReporting.logStringData"(
    	String label	
     , 	String data	) {
    (new reporting.ExtentReporting()).logStringData(
        	label
         , 	data)
}


def static "reporting.ExtentReporting.logFailedData"(
    	String label	
     , 	String data	) {
    (new reporting.ExtentReporting()).logFailedData(
        	label
         , 	data)
}


def static "reporting.ExtentReporting.logMissingData"(
    	String label	
     , 	String data	) {
    (new reporting.ExtentReporting()).logMissingData(
        	label
         , 	data)
}


def static "reporting.ExtentReporting.logListData"(
    	String label	
     , 	Object data	) {
    (new reporting.ExtentReporting()).logListData(
        	label
         , 	data)
}


def static "reporting.ExtentReporting.endTest"() {
    (new reporting.ExtentReporting()).endTest()
}


def static "compare.GetDifferences.setDifferencesFromResult"(
    	JSONCompareResult result	) {
    (new compare.GetDifferences()).setDifferencesFromResult(
        	result)
}


def static "compare.GetDifferences.getFailedFieldsList"() {
    (new compare.GetDifferences()).getFailedFieldsList()
}


def static "compare.GetDifferences.getMissingFieldsList"() {
    (new compare.GetDifferences()).getMissingFieldsList()
}


def static "compare.GetDifferences.getUnexpectedFieldsList"() {
    (new compare.GetDifferences()).getUnexpectedFieldsList()
}


def static "compare.GetDifferences.clearSetValues"() {
    (new compare.GetDifferences()).clearSetValues()
}


def static "compare.GetDifferences.compareUsingRecursion"(
    	JSONObject jsonObjectQA	
     , 	JSONObject jsonObjectQAMIG	
     , 	String KEY	
     , 	JSONComparator comparator	
     , 	java.util.List<String> ignoreNodesList	) {
    (new compare.GetDifferences()).compareUsingRecursion(
        	jsonObjectQA
         , 	jsonObjectQAMIG
         , 	KEY
         , 	comparator
         , 	ignoreNodesList)
}
