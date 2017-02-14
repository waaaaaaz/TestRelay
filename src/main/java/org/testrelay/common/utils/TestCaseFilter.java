package org.testrelay.common.utils;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestCaseFilter {
	
	public static List<String> getList(String testcaseLocation) throws IOException, DirectoryIteratorException{
		
		List<String> testcaseList = new ArrayList<String>(); 
		Path dir = Paths.get(testcaseLocation);
		DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "{testcase}*.{xml}");
		for (Path file: stream) {
			testcaseList.add(getTestCasePath(file));
			}
		return testcaseList;
		}
	
	private static String getTestCasePath(Path file){
		return file.getParent() + "/" + file.getFileName();
	}

}