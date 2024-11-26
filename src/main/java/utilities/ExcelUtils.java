package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	private static FileInputStream fis;
	private static XSSFWorkbook book;
	private static XSSFSheet sheet;

	public static String[][] GetExcelData(String Path, String SheetName) throws IOException {
		fis = new FileInputStream(Path);
		book = new XSSFWorkbook(fis);
		sheet = book.getSheet(SheetName);
		int row = 1;
		int col = 1;
		int Ci, Cj;
		int TotalRows = sheet.getLastRowNum();
		int TotalColumns = sheet.getRow(0).getLastCellNum();
		TotalColumns = TotalColumns - 1;
		String[][] Table = new String[TotalRows][TotalColumns];
		Ci = 0;

		for (int i = row; i <= TotalRows; i++, Ci++) {
			Cj = 0;
			for (int j = col; j <= TotalColumns; j++, Cj++) {
				Table[Ci][Cj] = GetCellData(i, j);
			}
		}
		return Table;
	}

	public static String GetCellData(int RowValue, int ColumnValue) {
		String Data = "";
		XSSFCell cell = sheet.getRow(RowValue).getCell(ColumnValue);
		if (cell != null) {
			if (cell.getCellType() == CellType.STRING) {
				Data = cell.getStringCellValue();
			} else {
				Data = String.valueOf(cell.getNumericCellValue());
			}
		}
		// System.out.println(Data);
		return Data;
	}

}
