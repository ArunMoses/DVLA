package arun.test;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class StepHelper {

	private static final String FILE_NAME = "/Users/arun/Documents/Eclipse Workspace/test/TestDirectory/TestXLS.xls";

	public List<String> getVehicleDetails() throws Exception {
		
		ApplicationContext context = new ClassPathXmlApplicationContext("Beans.xml");

		List<String> vehicleReg = new ArrayList<>();

		FileInputStream excelFile = new FileInputStream(new File(FILE_NAME));

		Workbook workbook = new HSSFWorkbook(excelFile);

		Sheet spreadsheet = workbook.getSheetAt(0);

		for (int rowIndex = 1; rowIndex <= spreadsheet.getLastRowNum(); rowIndex++) {

			Row row = spreadsheet.getRow(rowIndex);

			if (row != null) {

				Cell cell = row.getCell(0);

				if (cell != null) {

					vehicleReg.add(cell.getStringCellValue());
				}
			}
		}

		return vehicleReg;
	}
}
