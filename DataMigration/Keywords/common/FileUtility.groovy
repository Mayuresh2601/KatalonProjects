package common

import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject

import java.nio.file.Path
import java.util.stream.Collector
import java.util.stream.Collectors
import java.util.stream.IntStream
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

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

public class FileUtility {
	@Keyword
	public static void convertZipToFile(byte[] file, String filePath, String extension) {
		try {
			FileOutputStream os = new FileOutputStream(filePath+"file."+extension+".zip");
			os.write(file);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String destination =  filePath+"file."+extension+".zip";
		try {
			ZipFile zipFile = new ZipFile(destination);
			zipFile.extractAll(filePath);
			removeFile(filePath, "file."+extension+".zip");
		}catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	@Keyword
	public static void removeFile(String downloadPath, String fileName) {
		File dir = new File(downloadPath);
		File[] dirFiles = dir.listFiles();
		if(dirFiles.length > 0) {
			for (File dirFile : dirFiles) {
				System.out.println(dirFile.getName()+"============="+fileName);
				if(dirFile.getName().equals(fileName)) {
					dirFile.delete();
					//                    Thread.sleep(5000);
					return;
				}
			}
		}
	}

	@Keyword
	public static String getFileName(File folder, String extension) {
		String txtFileName = "";
		for (final File fileEntry : folder.listFiles()) {
			if(fileEntry.getName().endsWith(extension)){
				txtFileName = fileEntry.getName();
			}
		}
		return txtFileName;
	}

	@Keyword
	public static String[] getFileContentColumnHeader(String filePath, String fileName, String extension)  {
		String delimiter = null;
		if(extension.equals("txt") || extension.equals("")){
			delimiter = "\\|";
		}else if(extension.equals("csv")){
			delimiter = ";";
		}
		Scanner read = null;
		try {
			read = new Scanner(new FileReader(filePath+fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		assert delimiter != null;
		assert read != null;
		read.useDelimiter(delimiter);
		String firstLine = read.nextLine();
		String[] headers = firstLine.replaceAll("\"", "").split(delimiter);
		read.close();
		return headers;
	}

	@Keyword
	public static List<Object> getFileContentByColumn(String filePath, String fileName, String columnName, String extension)  {
		String delimiter = null;
		if(extension.equals("txt") || extension.equals("")){
			delimiter = "\\|";
		}else if(extension.equals("csv")){
			delimiter = ";";
		}
		Scanner read = null;
		try {
			read = new Scanner(new FileReader(filePath+fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<String> recordsInDownloadedFile = new ArrayList<>();
		assert delimiter != null;
		assert read != null;
		read.useDelimiter(delimiter);
		String firstLine = read.nextLine();
		String[] headers = firstLine.replaceAll("\"", "").split(delimiter);
		int columnNumber = findColumnNumber(columnName, headers);
		//		println columnNumber
		while (read.hasNext()) {
			String[] currentRow = read.nextLine().replaceAll("\"", "").split(delimiter);
			//			println currentRow
			//			println currentRow.length
			recordsInDownloadedFile.add(currentRow[columnNumber]);
		}
		read.close();
		return recordsInDownloadedFile;
	}

	private static int findColumnNumber(String field, String [] availableHeaders) {
		int columnNumber = 0;
		for (int i = 0; i < availableHeaders.length; i++) {
			if(availableHeaders[i].replaceAll("\"", "").equals(field)){
				columnNumber =  i;
				break;
			}
		}
		return columnNumber;
	}

	@Keyword
	public static String getColumnNameInFile(String value){
		//		String value = param.split("=")[0];
		switch (value) {
			case "iataCode":
				return "Code";
			case "template":
				return "Template";
			case "designator":
				return "Designator";
			case "globalName":
				return "Name";
			default:
				System.out.println("invalid option");
				return null;
		}
	}

	@Keyword
	public static String readFileContent(String filePath, String fileName){
		try  {
			File file=new File(filePath+fileName);    //creates a new file instance
			FileReader fr=new FileReader(file);   //reads the file
			BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
			StringBuffer sb=new StringBuffer();    //constructs a string buffer with no characters
			String line;
			while((line=br.readLine())!=null) {
				sb.append(line);      //appends line to string buffer
				sb.append("\n");     //line feed
			}
			fr.close();    //closes the stream and release the resources
			//			System.out.println("Contents of File: ");
			//			System.out.println(sb.toString());   //returns a string that textually represents the object
			return sb.toString()
		}
		catch(IOException e)  {
			//			e.printStackTrace();
			return null
		}
	}

	@Keyword
	public static List<String> getFileContentWithSpaceByColumn(String filePath, String fileName, String columnName, String extension)  {
		String delimiter = null;
		if(extension.equals("txt") || extension.equals("")){
			delimiter = "\\|";
		}else if(extension.equals("csv")){
			delimiter = ";";
		}
		Scanner read = null;
		try {
			read = new Scanner(new FileReader(filePath+fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		List<String> recordsInDownloadedFile = new ArrayList<>();
		assert delimiter != null;
		assert read != null;
		read.useDelimiter(delimiter);
		String firstLine = read.nextLine();
		String[] headers = firstLine.replaceAll("\"", "").split(delimiter);
		int columnNumber = findColumnNumber(columnName, headers);

		while (read.hasNext()) {
			String lineContent = read.nextLine()
			//			println lineContent
			String[] currentRow
			if(lineContent.contains("\"\"")) {
				//				println columnName
				//				println 'inside if '
				currentRow = lineContent.replaceAll("\"\"", " ").replaceAll("\"", "").split(delimiter);
			}else {
				//				println columnName
				//				println 'inside else'
				currentRow = lineContent.replaceAll("\"", "").split(delimiter);
			}

			recordsInDownloadedFile.add(currentRow[columnNumber]);
		}
		read.close();
		return recordsInDownloadedFile;
	}
}
