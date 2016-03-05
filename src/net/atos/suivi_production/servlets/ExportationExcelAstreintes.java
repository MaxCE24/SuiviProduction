package net.atos.suivi_production.servlets;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import net.atos.suivi_production.beans.Astreinte;

@WebServlet("/ExportationExcelAstreintes")
public class ExportationExcelAstreintes extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ATT_ASTREINTES = "astreintes";

	public SortedMap<Integer, Astreinte> astreintes;

	public ExportationExcelAstreintes() {
		super();

	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		if ((astreintes = (SortedMap<Integer, Astreinte>) session.getAttribute(ATT_ASTREINTES)) != null) {

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=Astreintes.xlsx");

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet spreadsheet = workbook.createSheet(" Astreintes ");

			XSSFRow row;

			XSSFCellStyle style = workbook.createCellStyle();

			style.setWrapText(true);

			spreadsheet.setColumnWidth(0, 5000);
			spreadsheet.setColumnWidth(1, 5000);
			spreadsheet.setColumnWidth(2, 5000);
			spreadsheet.setColumnWidth(3, 5000);
			spreadsheet.setColumnWidth(4, 5000);

			Map<String, Object[]> astreintesInfos = new TreeMap<String, Object[]>();
			astreintesInfos.put("1",
					new Object[] { "Réf. Astreintes", "Date Astreinte", "Nbre Heures", "Type Astreinte", "Concerné" });

			int i = 2;

			for (Entry<Integer, Astreinte> astreinte : astreintes.entrySet()) {
				astreintesInfos.put(String.valueOf(i),
						new Object[] { astreinte.getValue().getReference(),
								String.valueOf(astreinte.getValue().getDate()),
								String.valueOf(astreinte.getValue().getNombreHeures()), astreinte.getValue().getType(),
								astreinte.getValue().getCollaborateur().getPrenom() + " "
										+ astreinte.getValue().getCollaborateur().getNom() });
				i++;
			}

			Set<String> keyid = astreintesInfos.keySet();
			int rowid = 0;
			for (String key : keyid) {
				row = spreadsheet.createRow(rowid++);
				Object[] objectArr = astreintesInfos.get(key);
				int cellid = 0;
				for (Object obj : objectArr) {

					Cell cell = row.createCell(cellid++);
					cell.setCellStyle(style);
					cell.setCellValue((String) obj);

				}

			}

			makeRowBold(workbook, spreadsheet.getRow(0));

			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
		} else {
			response.sendRedirect(request.getContextPath());
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	public static void makeRowBold(Workbook wb, Row row) {
		CellStyle style = wb.createCellStyle();
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(font);

		for (int i = 0; i < row.getLastCellNum(); i++) {

			row.getCell(i).setCellStyle(style);
		}
	}

}
