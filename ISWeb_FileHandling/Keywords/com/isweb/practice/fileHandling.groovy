package com.isweb.practice

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.nio.file.Files
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.soap.Node
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

import org.apache.commons.collections4.map.HashedMap
import org.apache.commons.io.FilenameUtils
import org.apache.commons.lang.RandomStringUtils
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.NodeList

import com.google.api.client.util.Key
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

public class fileHandling {

	@Keyword
	public static String renameFileWithCurrentTime(String filePath, String prefix, String fileExtension) {

		DateFormat df = new SimpleDateFormat("yyyyMMddhhmmss"); // add S if you need milliseconds
		String filename = filePath + prefix + df.format(new Date()) + "." + fileExtension;
		println("filename : "+filename)

		Files.move(filePath, filePath.resolveSibling(filename));
		return filename;
	}

	@Keyword
	public static Map<String, String> getFileName(String sourceFolder) {

		Map<String, String> map = new HashedMap<String, String>()
		File directoryPath = new File(sourceFolder);
		//List of all files and directories
		File[] filesList = directoryPath.listFiles();
		System.out.println("List of files and directories in the specified directory:");
		Scanner sc = null;
		String fileName;
		String absolutePath;
		StringBuffer sb = new StringBuffer();
		for(File file : filesList) {
			System.out.println("File name: "+file.getName());
			System.out.println("File Absolute Path: "+file.getAbsolutePath())
			fileName = file.getName();
			absolutePath = file.getAbsolutePath();
			//Instantiating the Scanner class
			sc= new Scanner(file);
			String input;
			while (sc.hasNextLine()) {
				input = sc.nextLine();
				sb.append(input+" ");
			}

			//         System.out.println("Contents of the file: "+sb.toString());
			//         System.out.println(" ");
			//           //Instantiating the FileOutputStream class
			//         FileOutputStream fileOut = new FileOutputStream("C:\\Users\\mayuresh.sonar\\OneDrive - Accelya\\Documents\\output.txt");
			//         //Instantiating the DataOutputStream class
			//         DataOutputStream outputStream = new DataOutputStream(fileOut);
			//         //Writing UTF data to the output stream
			//         outputStream.write(sb.toString().getBytes());
			//         outputStream.flush();
			//         System.out.println("Data entered into the file");
		}
		map.put(fileName, absolutePath)
		return map;
	}

	@Keyword
	public static void readAndUpdateXMLFile(String sourceFileName, String destinationPath) {

		try {
			// Load the XML file
			File xmlFile = new File(sourceFileName);

			// Create a DocumentBuilderFactory and DocumentBuilder
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Parse the XML file into a Document object
			Document document = builder.parse(xmlFile);

			// Get the root element of the XML file
			NodeList invoiceNumber = document.getElementsByTagName("invoiceNumber");
			int number = generateRandomNumber();
			println("number "+number)
			invoiceNumber.item(0).setTextContent(""+number+"");

			NodeList fileN = document.getElementsByTagName("fileName");
			String filename = generateRandomString();
			println("filename "+filename)
			fileN.item(0).setTextContent(filename + ".txt");

			// Loop through the books and find the one with the title "Java Programming"
			//			for (int i = 0; i < bookList.getLength(); i++) {
			//				Node bookNode = bookList.item(i);
			//				if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
			//					Element bookElement = (Element) bookNode;
			//					NodeList titleList = bookElement.getElementsByTagName("id");
			//
			//					// Check if the title matches the book we want to update
			//					if (titleList.getLength() > 0 && titleList.item(0).getTextContent().equals("101")) {
			//						// Find the author element and update the text content
			//						NodeList authorList = bookElement.getElementsByTagName("id");
			//						if (authorList.getLength() > 0) {
			//							authorList.item(0).setTextContent("James Smith");  // Update author
			//						}
			//					}
			//				}
			//			}

			// Now write the updated XML back to the file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(document);
			StreamResult result = new StreamResult(new File(destinationPath));

			// Output the updated XML to the file
			transformer.transform(source, result);

			System.out.println("XML file updated successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Keyword
	public static void zippingFileAndMovingToSFTP(String xmlFilePath, String zipFilePath) {

		String sourceFile = xmlFilePath;
		FileOutputStream fos = new FileOutputStream(zipFilePath);
		ZipOutputStream zipOut = new ZipOutputStream(fos);

		File fileToZip = new File(sourceFile);
		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
		zipOut.putNextEntry(zipEntry);

		byte[] bytes = new byte[1024];
		int length;
		while((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}

		zipOut.close();
		fis.close();
		fos.close();
	}

	@Keyword
	public static int generateRandomNumber() {

		int min=1204868;
		int max=98609438;

		System.out.println("Generated numbers are within "+min+" to "+max);
		return (min + (int)(Math.random() * ((max - min) + 1)));
	}

	@Keyword
	public static String generateRandomString() {

		int length = 15;
		boolean useLetters = true;
		boolean useNumbers = false;
		String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

		System.out.println(generatedString);
		return generatedString;
	}

	@Keyword
	public static void moveFileToSftpFolder(String source, String target) {

		Files.move(source, target);
	}

	@Keyword
	def void checkFileDoesNotExistAtInterval(String filePath) {
		while (true) {
			if (!new File(filePath).exists()) {
				WebUI.comment('File does not exist in the folder')
				break
			}
			WebUI.delay(15)
		}
	}

	@Keyword
	def renameFileWithTimestamp(TestObject fileObject) {
		String currentTimestamp = CustomKeywords.'com.kms.katalon.core.util.KeywordUtil.getTimestamp'('yyyyMMdd_HHmmss')
		String originalFilePath = WebUI.getAttribute(fileObject, 'src')
		String fileExtension = FilenameUtils.getExtension(originalFilePath)
		String newFileName = currentTimestamp + '.' + fileExtension
		String newFilePath = FilenameUtils.getFullPath(originalFilePath) + newFileName

		CustomKeywords.'com.kms.katalon.core.util.KeywordUtil.copyFile'(originalFilePath, newFilePath)
		CustomKeywords.'com.kms.katalon.core.util.KeywordUtil.deleteFile'(originalFilePath)
	}
}
