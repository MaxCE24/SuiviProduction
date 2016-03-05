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

import net.atos.suivi_production.beans.DemandeDAchat;
import net.atos.suivi_production.beans.Validation;

@WebServlet("/ExportationExcelDemandesDAchat")
public class ExportationExcelDemandesDAchat extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ATT_DEMANDES_D_ACHAT = "demandesDAchat";

	public SortedMap<Integer, DemandeDAchat> demandesDAchat;

	public ExportationExcelDemandesDAchat() {
		super();

	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if ((demandesDAchat = (SortedMap<Integer, DemandeDAchat>) session.getAttribute(ATT_DEMANDES_D_ACHAT)) != null) {

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=Demandes d'achat.xlsx");

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet spreadsheet = workbook.createSheet(" Demandes d'achat ");

			XSSFRow row;

			XSSFCellStyle style = workbook.createCellStyle();

			style.setWrapText(true);

			spreadsheet.setColumnWidth(0, 5000);
			spreadsheet.setColumnWidth(1, 5000);
			spreadsheet.setColumnWidth(2, 5000);
			spreadsheet.setColumnWidth(3, 5000);
			spreadsheet.setColumnWidth(4, 10000);
			spreadsheet.setColumnWidth(5, 5000);
			spreadsheet.setColumnWidth(6, 15000);

			Map<String, Object[]> demandesDAchatInfos = new TreeMap<String, Object[]>();

			demandesDAchatInfos.put("1", new Object[] { "N° DA", "Date de demande", "Demandeur", "Description",
					"Statut", "N° BC", "Validée par" });

			int i = 2;

			for (Entry<Integer, DemandeDAchat> demandeDAchat : demandesDAchat.entrySet()) {
				String infosValidations = "";
				for (Entry<Integer, Validation> validation : demandeDAchat.getValue().getValidations().entrySet()) {
					infosValidations += validation.getValue().getValideur().getPrenom();
					infosValidations += " ";
					infosValidations += validation.getValue().getValideur().getNom();
					infosValidations += ", ";
				}
				if (infosValidations.length() >= 2)
					infosValidations = infosValidations.substring(0, infosValidations.length() - 2);
				demandesDAchatInfos.put(String.valueOf(i),
						new Object[] { String.valueOf(demandeDAchat.getValue().getNumero()),
								String.valueOf(demandeDAchat.getValue().getDate()),
								demandeDAchat.getValue().getCollaborateur().getPrenom() + " "
										+ demandeDAchat.getValue().getCollaborateur().getNom(),
						demandeDAchat.getValue().getDescription(), demandeDAchat.getValue().getStatut(),
						String.valueOf(demandeDAchat.getValue().getNumeroDeBonDeCommande()), infosValidations });
				i++;
			}

			Set<String> keyid = demandesDAchatInfos.keySet();
			int rowid = 0;
			for (String key : keyid) {
				row = spreadsheet.createRow(rowid++);
				Object[] objectArr = demandesDAchatInfos.get(key);
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
