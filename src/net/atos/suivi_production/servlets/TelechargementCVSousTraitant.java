package net.atos.suivi_production.servlets;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.beans.SousTraitant;

 
@WebServlet("/TelechargementCVSousTraitant")
public class TelechargementCVSousTraitant extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String PARAM_ID_SOUS_TRAITANT = "idSousTraitant";
	public static final String ATT_SOUS_TRAITANTS = "sousTraitants";
	public static final String VUE = "/listeSousTraitants";

	public static final String ATT_UTLISATEUR = "utilisateur";

	public SortedMap<Integer, SousTraitant> sousTraitants;

	 
	public TelechargementCVSousTraitant() {
		super();
		 
	}

	 
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		int idSousTraitant = Integer.parseInt(getValeurParametre(request, PARAM_ID_SOUS_TRAITANT));

		 

		HttpSession session = request.getSession();
		sousTraitants = (SortedMap<Integer, SousTraitant>) session.getAttribute(ATT_SOUS_TRAITANTS);

		if (idSousTraitant != 0 && sousTraitants != null) {

			SousTraitant sousTraitant = sousTraitants.get(idSousTraitant);

			File cV = sousTraitant.getCV();

			ServletOutputStream stream = null;
			BufferedInputStream buf = null;
			try {
				stream = response.getOutputStream();
				response.setContentType("application/pdf");
				response.setHeader("Content-Disposition",
						"inline; filename='" + sousTraitant.getPrenom() + sousTraitant.getNom() + ".pdf'");
				FileInputStream input = new FileInputStream(cV);
				response.setContentLength((int) cV.length());
				buf = new BufferedInputStream(input);
				int readBytes = 0;
				while ((readBytes = buf.read()) != -1)
					stream.write(readBytes);
			} catch (IOException ioe) {
				throw new ServletException(ioe.getMessage());
			} finally {
				if (stream != null)
					stream.close();
				if (buf != null)
					buf.close();
			}

		} else {
			 
			 
			this.getServletContext().getRequestDispatcher(VUE).forward(request, response);
		}
	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		doGet(request, response);
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
