package arun.test;

import java.io.IOException;
import java.util.Arrays;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
	
	public static void main(String[] args) throws IOException {

		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");
		
		FileService obj = (FileService) context.getBean("FileService");
		
		obj.getFileInfo().iterator().forEachRemaining(file -> {
				
			System.out.println(Arrays.asList(file));
		});
		
		obj.filterFilesByMimeType("application/msword").iterator().forEachRemaining(file -> {
			
		System.out.println(Arrays.asList(file).get(4));
		
	    });
	}
}
