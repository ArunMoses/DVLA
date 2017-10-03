package arun.test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceArray;

public class GetVehiclesData {
	
	private String mimeType;
	
	private FileService getFile;
	
	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public FileService getGetFile() {
		return getFile;
	}

	public void setGetFile(FileService getFile) {
		this.getFile = getFile;
	}

	private static GetVehiclesData getVehicles() {
		
		try(ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml")){
			
			return (GetVehiclesData) context.getBean("GetVehiclesData");
		}		
	}

	public List<List<String>> getVehicleDetails() throws Exception {
		
		List<String> vehicleReg = new ArrayList<>();

		List<String> make = new ArrayList<>();

		List<String> colour = new ArrayList<>();
		
		GetVehiclesData vehicles = getVehicles();
				
		AtomicReferenceArray<String> FILE_NAME = new AtomicReferenceArray<String>(1);
		
		vehicles.getGetFile().filterFilesByMimeType(vehicles.getMimeType()).iterator().forEachRemaining(file -> {
					
			FILE_NAME.set(0, Arrays.asList(file).get(4));
					
			return;
							
		});
			
		System.out.println(FILE_NAME.get(0));
		
		FileInputStream excelFile = new FileInputStream(new File(FILE_NAME.get(0)));
		
		try (HSSFWorkbook workbook = new HSSFWorkbook(excelFile)) {

			Sheet spreadsheet = workbook.getSheetAt(0);

			for (int rowIndex = 1; rowIndex <= spreadsheet.getLastRowNum(); rowIndex++) {

				Row row = spreadsheet.getRow(rowIndex);

				if (row != null) {

					Cell cell = row.getCell(0);

					if (cell != null) {

						vehicleReg.add(cell.getStringCellValue());
					}
				}

				if (row != null) {

					Cell cell = row.getCell(1);

					if (cell != null) {

						make.add(cell.getStringCellValue());
					}
				}

				if (row != null) {

					Cell cell = row.getCell(2);

					if (cell != null) {

						colour.add(cell.getStringCellValue());
					}
				}
			}
			
			List<List<String>> vehicleDetails = new ArrayList<>();
			vehicleDetails.add(vehicleReg);
			vehicleDetails.add(make);
			vehicleDetails.add(colour);

			return vehicleDetails;
		}
	}
}
