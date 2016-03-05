package net.atos.suivi_production.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.config.Utilisateur;
import net.atos.suivi_production.forms.ConnexionForm;

 
@WebServlet("/Connexion")
public class Connexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String CONF_UTLISATEUR = "utilisateur";
	public static final String ATT_UTLISATEUR = "utilisateur";

	public static final String ATT_SESSION_USER = "sessionUtilisateur";

	public static final String ATT_FORM = "form";

	public static final String VUE_CONNEXION = "/WEB-INF/seConnecter.jsp";
	public static final String VUE_ACCUEIL = "/WEB-INF/accueil.jsp";

	public static final String FORMAT_DATE = "dd/MM/yyyy HH:mm:ss";

	private Utilisateur utilisateur;

	 
	public Connexion() {
		super();
		 
	}

	public void init() throws ServletException {
		 
		this.utilisateur = ((Utilisateur) getServletContext().getAttribute(CONF_UTLISATEUR));

	}

	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Utilisateur utilisateurSession = (Utilisateur) session.getAttribute(ATT_UTLISATEUR);

		if (utilisateurSession != null && utilisateurSession.geteMail().equals(utilisateur.geteMail())) {
			this.getServletContext().getRequestDispatcher(VUE_ACCUEIL).forward(request, response);
		} else {

			 
			this.getServletContext().getRequestDispatcher(VUE_CONNEXION).forward(request, response);
		}
	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ConnexionForm form = new ConnexionForm();

		request.setAttribute(CONF_UTLISATEUR, utilisateur);

		Utilisateur utilisateur = form.connecterUtilisateur(request);

		request.setAttribute(ATT_UTLISATEUR, utilisateur);
		request.setAttribute(ATT_FORM, form);
		if (form.getErreurs().isEmpty()) {

			 

			request.getSession().setAttribute(ATT_SESSION_USER, this.utilisateur);

			this.getServletContext().getRequestDispatcher(VUE_ACCUEIL).forward(request, response);

		}

		else {

			this.getServletContext().getRequestDispatcher(VUE_CONNEXION).forward(request, response);
		}
	}

}
