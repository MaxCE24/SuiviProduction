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

import net.atos.suivi_production.beans.Facture;

@WebServlet("/ExportationExcelFactures")
public class ExportationExcelFactures extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String ATT_FACTURES = "factures";

	public SortedMap<Integer, Facture> factures;

	public ExportationExcelFactures() {
		super();

	}

	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		if ((factures = (SortedMap<Integer, Facture>) session.getAttribute(ATT_FACTURES)) != null) {

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=Factures.xlsx");

			XSSFWorkbook workbook = new XSSFWorkbook();

			XSSFSheet spreadsheet = workbook.createSheet(" Factures ");

			XSSFRow row;

			XSSFCellStyle style = workbook.createCellStyle();

			style.setWrapText(true);

			spreadsheet.setColumnWidth(0, 5000);
			spreadsheet.setColumnWidth(1, 5000);
			spreadsheet.setColumnWidth(2, 5000);
			spreadsheet.setColumnWidth(3, 5000);
			spreadsheet.setColumnWidth(4, 5000);
			spreadsheet.setColumnWidth(5, 5000);
			spreadsheet.setColumnWidth(6, 5000);
			spreadsheet.setColumnWidth(7, 5000);
			spreadsheet.setColumnWidth(8, 5000);
			spreadsheet.setColumnWidth(9, 5000);
			spreadsheet.setColumnWidth(10, 20000);

			Map<String, Object[]> facturesInfos = new TreeMap<String, Object[]>();
			facturesInfos.put("1",
					new Object[] { "N° Facture", "Date réception", "Client", "Nom SST", "Montant TTC",
							"Date remise aux OP", "Date retour OP", "Date remise au TMU", "Date envoi DAF",
							"Période facturée", "Notes" });

			int i = 2;

			for (Entry<Integer, Facture> facture : factures.entrySet()) {
				facturesInfos.put(String.valueOf(i),
						new Object[] { String.valueOf(facture.getValue().getNumero()),
								String.valueOf(facture.getValue().getDateReception()),
								facture.getValue().getSousTraitant().getSociete().getRaisonSociale(),
								facture.getValue().getSousTraitant().getPrenom() + " "
										+ facture.getValue().getSousTraitant().getNom(),
						String.valueOf(facture.getValue().getMontantTTC()),
						String.valueOf(facture.getValue().getDateRemiseAuxOP()),
						String.valueOf(facture.getValue().getDateRetourOP()),
						String.valueOf(facture.getValue().getDateRemiseAuTMU()),
						String.valueOf(facture.getValue().getDateEnvoiDAF()), facture.getValue().getPeriodeFacturee(),
						facture.getValue().getNotes() });
				i++;
			}

			Set<String> keyid = facturesInfos.keySet();
			int rowid = 0;
			for (String key : keyid) {
				row = spreadsheet.createRow(rowid++);
				Object[] objectArr = facturesInfos.get(key);
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
