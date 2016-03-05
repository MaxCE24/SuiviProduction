package net.atos.suivi_production.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperRunManager;

@WebServlet("/ImpressionFacture")
public class ImpressionFacture extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String PARAM_ID_FACTURE = "idFacture";

	public ImpressionFacture() {
		super();

	}

	public void init() {

		getServletContext().getInitParameter("invoice-template");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection;
		PreparedStatement statement;
		ResultSet resultSet;

		int idFacture = Integer.parseInt(getValeurParametre(request, PARAM_ID_FACTURE));

		if (idFacture != 0) {

			response.setContentType("application/pdf");

			response.setHeader("Content-Disposition", "inline; filename='Facture-" + idFacture + ".pdf'");

			ServletOutputStream servletOutputStream = response.getOutputStream();
			InputStream reportStream = getServletConfig().getServletContext()
					.getResourceAsStream("/reports/Facture.jasper");

			try {
				String query = "select factures.id, factures.numero, factures.date_reception, factures.montant_ttc, factures.date_Remise_Aux_OP, factures.date_Retour_OP, factures.date_Remise_Au_TMU, factures.date_Envoi_DAF, factures.periode_facturee, factures.notes, sous_traitants.id as id_sous_traitant, sous_traitants.nom, sous_traitants.prenom, sous_traitants.sexe, sous_traitants.CV, sous_traitants.numero_De_Telephone, sous_traitants.date_recrutement,profils.id as id_profil, profils.libelle, equipes.id as id_equipe, equipes.nom as nom_equipe, societes.id as id_societe, societes.raison_sociale from factures as factures inner join sous_traitants on factures.sous_traitant = sous_traitants.id inner join profils on sous_traitants.profil = profils.id inner join equipes on sous_traitants.equipe = equipes.id inner join societes on sous_traitants.societe = societes.id where factures.id=?";

				Class.forName("com.mysql.jdbc.Driver");

				connection = DriverManager
						.getConnection("jdbc:mysql://localhost:3306/suivi_production?user=root&password=admin");
				statement = connection.prepareStatement(query);
				statement.setInt(1, idFacture);
				resultSet = statement.executeQuery();

				JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);

				JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, new HashMap(),
						resultSetDataSource);

				resultSet.close();
				statement.close();
				connection.close();

				servletOutputStream.flush();
				servletOutputStream.close();
			} catch (Exception e) {

				StringWriter stringWriter = new StringWriter();
				PrintWriter printWriter = new PrintWriter(stringWriter);
				e.printStackTrace(printWriter);
				response.setContentType("text/plain");
				response.getOutputStream().print(stringWriter.toString());
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean remplirFacture() {
		try {
			JasperFillManager.fillReportToFile(getServletContext().getRealPath("reports/Facture.jasper"), new HashMap(),
					new JREmptyDataSource());
		} catch (JRException e) {

			e.printStackTrace();
			return false;
		}
		return true;
	}

	private static String getValeurParametre(HttpServletRequest request, String nomChamp) {
		String valeur = request.getParameter(nomChamp);
		if (valeur == null || valeur.trim().length() == 0) {
			return null;
		} else {
			return valeur;
		}
	}

}
