
/**
 * This class is generated automatically by Katalon Studio and should not be modified or deleted.
 */

import java.lang.String

import com.kms.katalon.core.testobject.TestObject



def static "com.isweb.practice.fileHandling.renameFileWithCurrentTime"(
    	String filePath	
     , 	String prefix	
     , 	String fileExtension	) {
    (new com.isweb.practice.fileHandling()).renameFileWithCurrentTime(
        	filePath
         , 	prefix
         , 	fileExtension)
}


def static "com.isweb.practice.fileHandling.getFileName"(
    	String sourceFolder	) {
    (new com.isweb.practice.fileHandling()).getFileName(
        	sourceFolder)
}


def static "com.isweb.practice.fileHandling.readAndUpdateXMLFile"(
    	String sourceFileName	
     , 	String destinationPath	) {
    (new com.isweb.practice.fileHandling()).readAndUpdateXMLFile(
        	sourceFileName
         , 	destinationPath)
}


def static "com.isweb.practice.fileHandling.zippingFileAndMovingToSFTP"(
    	String xmlFilePath	
     , 	String zipFilePath	) {
    (new com.isweb.practice.fileHandling()).zippingFileAndMovingToSFTP(
        	xmlFilePath
         , 	zipFilePath)
}


def static "com.isweb.practice.fileHandling.generateRandomNumber"() {
    (new com.isweb.practice.fileHandling()).generateRandomNumber()
}


def static "com.isweb.practice.fileHandling.generateRandomString"() {
    (new com.isweb.practice.fileHandling()).generateRandomString()
}


def static "com.isweb.practice.fileHandling.moveFileToSftpFolder"(
    	String source	
     , 	String target	) {
    (new com.isweb.practice.fileHandling()).moveFileToSftpFolder(
        	source
         , 	target)
}


def static "com.isweb.practice.fileHandling.checkFileDoesNotExistAtInterval"(
    	String filePath	) {
    (new com.isweb.practice.fileHandling()).checkFileDoesNotExistAtInterval(
        	filePath)
}


def static "com.isweb.practice.fileHandling.renameFileWithTimestamp"(
    	TestObject fileObject	) {
    (new com.isweb.practice.fileHandling()).renameFileWithTimestamp(
        	fileObject)
}
