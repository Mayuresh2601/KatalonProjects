package restAssuredMethods

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

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import internal.GlobalVariable

public class RestClient {

	//	public Response postRequestForToken(String user){
	//
	//				String baseURIToken = "baseURIToken";
	//				String basePathToken = "basePathToken";
	//				Response accessTokenResponse = RestAssured.given()
	//						.baseUri(PropertyReader.getInstance().getProperty("application", baseURIToken))
	//						.basePath(PropertyReader.getInstance().getProperty("application", basePathToken))
	//						.formParam(GRANT_TYPE, readJsonFile.getValue(GRANT_TYPE))
	//						.formParam(CLIENT_ID, readJsonFile.getValue(CLIENT_ID))
	//						.formParam(CLIENT_SECRET, readJsonFile.getValue(CLIENT_SECRET))
	//						.formParam(USERNAME, readJsonFile.getValue(user+"."+USERNAME))
	//						.formParam(PASSWORD, readJsonFile.getValue(user+"."+PASSWORD))
	//						.relaxedHTTPSValidation()
	//						.post();
	//				setToken(accessTokenResponse.then().extract().path("access_token"));
	//				return accessTokenResponse;
	//			}

	@Keyword
	public RequestSpecification givenConnection(String baseURL, String basePath, String token) {
		RequestSpecification request = given().baseUri(baseURL).port(443)
				.basePath(basePath+'/')
				.header(new Header("Authorization", "Bearer " + token))
				.relaxedHTTPSValidation()
				.contentType(ContentType.JSON);
				
		return request;
	}

	@Keyword
	public Response doGet(String endPoint, RequestSpecification requestSpecification){
		return requestSpecification
				.when()
				.get(endPoint);
	}

	@Keyword
	public Response doPost(String path, Object object, RequestSpecification givenConnection) {
		return givenConnection
				.body(object)
				.when()
				.post(path);
	}

}
