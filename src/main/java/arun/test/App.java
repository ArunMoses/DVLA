package arun.test;

import java.io.IOException;
import java.util.Arrays;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	private static FileService getFileServiceBean() {
		
		try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml")){
			
			return (FileService) context.getBean("FileService");
		}		
	}
		
	private static void getFilesByMimeType(String mimeType) throws IOException{
				
		getFileServiceBean().filterFilesByMimeType(mimeType).iterator().forEachRemaining(file -> {
			
		System.out.println(Arrays.asList(file).get(4));
		
	    });
	}
		
	public static void main(String[] args) throws IOException {
		
		FileService files = getFileServiceBean();
					
		//Get filename, file mime type, file size, file extension of all files in the configured directory
		files.getFileInfo().iterator().forEachRemaining(file -> {
				
			System.out.println(Arrays.asList(file));
		});
		
		//Filter files by mime type -> Excel
		getFilesByMimeType("application/vndms-excel,application/excel,application/x-excel,application/x-msexcel,application/msword");
				
		////Filter files by mime type -> CSV
		getFilesByMimeType("application/octet-stream");
	}
}
