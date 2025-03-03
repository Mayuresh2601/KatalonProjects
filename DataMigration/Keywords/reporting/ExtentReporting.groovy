package reporting

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

import internal.GlobalVariable

import com.aventstack.extentreports.markuputils.CodeLanguage
import com.aventstack.extentreports.markuputils.ExtentColor
import com.aventstack.extentreports.markuputils.MarkupHelper
import com.aventstack.extentreports.*
import com.aventstack.extentreports.reporter.ExtentSparkReporter
import com.aventstack.extentreports.reporter.configuration.Theme
import com.kms.katalon.core.*

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.context.TestCaseContext

import java.text.SimpleDateFormat as SimpleDateFormat
import java.text.DateFormat as DateFormat
import java.text.Format as Format
import java.io.File

import org.testng.TestListenerAdapter

//base64image imports
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ExtentReporting extends TestListenerAdapter{

	static ExtentReports extent
	static ExtentTest test
	static int stepCounter = 1

	@Keyword
	def static void startTest(String reportPath,String testSuiteName ,String testCaseName ,String testCaseDescription ,String author ,String category, String reportFile) {
		if(extent == null) {
			extent = new ExtentReports()
			def spark = new ExtentSparkReporter(reportPath+ ".html")
			spark.config().setDocumentTitle(testSuiteName)
			spark.config().setTheme(Theme.STANDARD)
			spark.config().setReportName(testSuiteName)
			extent.attachReporter(spark)
		}
		test = extent.createTest(testCaseName, testCaseDescription).assignAuthor(author).assignCategory(category)
	}

	@Keyword
	static void extentlogNScreenshot(String status1, String logMessage, String flag) {
		def status
		if (status1 instanceof Boolean){
			status = Boolean.toString(status1)
		}
		else {
			status = status1
		}

		String screenshotPath = captureScreenshot(logMessage)
		String base64Image = encodeImageToBase64(screenshotPath)
		//---Step Description
		String stepDescription = "Step "+stepCounter+" : "+logMessage
		String SFlag = flag
		switch (status.toUpperCase()) {
			case 'PASS':
				if(!SFlag.equalsIgnoreCase("Y")) {
					test.log(Status.PASS, stepDescription)
				}
				else {
					screenshotPath = captureScreenshot(logMessage)
					test.log(Status.PASS, stepDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
				}
				break
			case 'TRUE':
				if(!SFlag.equalsIgnoreCase("Y")) {
					test.log(Status.PASS, stepDescription)
				}
				else {
					screenshotPath = captureScreenshot(logMessage)
					test.log(Status.PASS, stepDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build())
				}
				break
			case 'FAIL':
				if(!SFlag.equalsIgnoreCase("Y")) {
					test.log(Status.FAIL, stepDescription)
				}
				else {
					screenshotPath = captureScreenshot(logMessage)
					test.log(Status.FAIL, stepDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
				}
				break
			case 'FALSE':
				if(!SFlag.equalsIgnoreCase("Y")) {
					test.log(Status.FAIL, stepDescription)
				}
				else {
					screenshotPath = captureScreenshot(logMessage)
					test.log(Status.FAIL, stepDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build())
				}
				break
			case 'SKIP':
				if(!SFlag.equalsIgnoreCase("Y")) {
					test.log(Status.SKIP, stepDescription)
				}
				else
					test.log(Status.SKIP, stepDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
				break
			case 'INFO':
				if(!SFlag.equalsIgnoreCase("Y")) {
					test.log(Status.INFO, stepDescription)
				}
				else
					test.log(Status.INFO, stepDescription, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Image).build());
				break
			default:
				test.log(Status.INFO, stepDescription)
		}
		stepCounter++
	}

	@Keyword
	public static String encodeImageToBase64(String imagePath) {
		String base64Image = ""
		File file = new File(imagePath)
		try {
			FileInputStream imageInFile = new FileInputStream(file)
			byte[] imageData = new byte[(int) file.length()]
			imageInFile.read(imageData)
			base64Image = Base64.getEncoder().encodeToString(imageData);
		} catch (IOException e) {
			e.printStackTrace()
		}

		return base64Image;
	}

	@Keyword
	def static void testStatus(String status, String message) {
		switch (status.toUpperCase()) {
			case 'PASS':
				test.log(Status.PASS, message)
				break
			case 'FAIL':
				test.log(Status.FAIL, message)
				break
			case 'SKIP':
				test.log(Status.SKIP, message)
				break
			default:
				test.log(Status.INFO, message)
		}
	}

	@Keyword
	static String captureScreenshot(String logMessage) {
		String msg=logMessage
		String exeSrc = RunConfiguration.getProjectDir()
		println(exeSrc)
		Format fDate = new SimpleDateFormat("dd_MM_yyyy");
		Date latestdate = new Date()
		String date = fDate.format(latestdate)
		println(date)
		Format fDateTime = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		String dateTime = fDateTime.format(latestdate)
		println(dateTime)
		String testcase = RunConfiguration.getExecutionSource().toString().substring(RunConfiguration.getExecutionSource().toString().lastIndexOf("\\")+1,RunConfiguration.getExecutionSource().toString().length()-3)
		println(testcase)
		String screenshotPath = exeSrc + File.separator + 'Screenshots' + File.separator + testcase +'_'+ dateTime + File.separator + msg + '_' + dateTime + '.png'
		WebUI.takeScreenshot(screenshotPath)
		return screenshotPath
	}

	@Keyword
	static void verifyTextAndLogStatus(TestObject element, String expectedText) {
		String actualText = WebUI.getText(element)

		if (actualText.equals(expectedText)) {
			test.log(Status.PASS, "Text verification passed: Actual text matches expected text.")
		} else {
			test.log(Status.FAIL, "Text verification failed: Actual text does not match expected text.")
		}
	}

	//Log response body and status
	@Keyword
	static void logResponseAndStatus(String responseBody, int statusCode) {
		// Log the response body in the report
		test.info(MarkupHelper.createLabel("Response Body:", ExtentColor.BLUE))
		test.info(MarkupHelper.createCodeBlock(responseBody, CodeLanguage.JSON))

		// Log the status code in the report
		if (statusCode == 200) {
			test.log(Status.PASS, "Status Code: " + statusCode)
		} else {
			test.log(Status.FAIL, "Status Code: " + statusCode)
		}
	}

	@Keyword
	static void logJSONData(String label, String JSON_Data) {
		test.info(MarkupHelper.createLabel(label, ExtentColor.TEAL))
		//		test.info(MarkupHelper.createJsonCodeBlock(JSON_Data))
		test.info(MarkupHelper.createCodeBlock(JSON_Data))
	}

	@Keyword
	static void logStringData(String label, String data) {
		test.info(MarkupHelper.createLabel(label, ExtentColor.INDIGO))
		test.info(MarkupHelper.createCodeBlock(data))
	}

	@Keyword
	static void logFailedData(String label, String data) {
		test.info(MarkupHelper.createLabel(label, ExtentColor.RED))
		test.info(MarkupHelper.createCodeBlock(data))
	}

	@Keyword
	static void logMissingData(String label, String data) {
		test.info(MarkupHelper.createLabel(label, ExtentColor.GREY))
		test.info(MarkupHelper.createCodeBlock(data))
	}

	@Keyword
	static void logListData(String label, Object data) {
		test.info(MarkupHelper.createLabel(label, ExtentColor.GREY))
		test.info(MarkupHelper.createUnorderedList(data))
	}

	@Keyword
	def static void endTest() {
		extent.flush()
	}
}
