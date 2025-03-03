import com.kms.katalon.core.logging.KeywordLogger
import com.kms.katalon.core.exception.StepFailedException
import com.kms.katalon.core.main.TestCaseMain
import com.kms.katalon.core.testdata.TestDataColumn
import com.kms.katalon.core.testcase.TestCaseBinding
import com.kms.katalon.core.driver.internal.DriverCleanerCollector
import com.kms.katalon.core.model.FailureHandling
import com.kms.katalon.core.configuration.RunConfiguration
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import internal.GlobalVariable as GlobalVariable

DriverCleanerCollector.getInstance().addDriverCleaner(new com.kms.katalon.core.webui.contribution.WebUiDriverCleaner())
DriverCleanerCollector.getInstance().addDriverCleaner(new com.kms.katalon.core.mobile.contribution.MobileDriverCleaner())
DriverCleanerCollector.getInstance().addDriverCleaner(new com.kms.katalon.core.cucumber.keyword.internal.CucumberDriverCleaner())
DriverCleanerCollector.getInstance().addDriverCleaner(new com.kms.katalon.core.windows.keyword.contribution.WindowsDriverCleaner())
DriverCleanerCollector.getInstance().addDriverCleaner(new com.kms.katalon.core.testng.keyword.internal.TestNGDriverCleaner())


RunConfiguration.setExecutionSettingFile("C:\\Users\\MAYURE~1.SON\\AppData\\Local\\Temp\\Katalon\\Test Cases\\BSP_Management\\File_Descriptors\\FCA_24441_GET_Descriptors_Refund_Settings\\20240916_112927\\execution.properties")

TestCaseMain.beforeStart()

       TestCaseMain.startTestCaseBinding('Test Cases/BSP_Management/File_Descriptors/FCA_24441_GET_Descriptors_Refund_Settings', new File("C:\\Users\\MAYURE~1.SON\\AppData\\Local\\Temp\\Katalon\\Test Cases\\BSP_Management\\File_Descriptors\\FCA_24441_GET_Descriptors_Refund_Settings\\20240916_112928\\testCaseBinding"))
    
