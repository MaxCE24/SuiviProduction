package net.atos.suivi_production.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.atos.suivi_production.beans.Equipe;
import net.atos.suivi_production.beans.Profil;
import net.atos.suivi_production.beans.Societe;
import net.atos.suivi_production.beans.SousTraitant;
import net.atos.suivi_production.dao.EquipeDAO;
import net.atos.suivi_production.dao.ProfilDAO;
import net.atos.suivi_production.dao.SocieteDAO;
import net.atos.suivi_production.forms.CreationSousTraitantForm;

public class CreationSousTraitant extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public static final String ATT_SOUS_TRAITANT = "sousTraitant";
	public static final String ATT_PROFILS = "profils";
	public static final String ATT_EQUIPES = "equipes";
	public static final String ATT_SOCIETES = "societes";

	public static final String ATT_FORM = "form";
	public static final String VUE_SUCCES = "/WEB-INF/chargerCV.jsp";
	public static final String VUE_FORM = "/WEB-INF/creerSousTraitant.jsp";

	public SortedMap<Integer, SousTraitant> sousTraitants;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			SortedMap<Integer, Profil> profils = new ProfilDAO().lister();
			SortedMap<Integer, Equipe> equipes = new EquipeDAO().lister();
			SortedMap<Integer, Societe> societes = new SocieteDAO().lister();
			HttpSession session = request.getSession();
			session.setAttribute(ATT_PROFILS, profils);
			session.setAttribute(ATT_EQUIPES, equipes);
			session.setAttribute(ATT_SOCIETES, societes);
		} catch (SQLException e1) {

			e1.printStackTrace();
		}

		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		CreationSousTraitantForm form = new CreationSousTraitantForm();

		SousTraitant sousTraitant = form.creerSousTraitant(request);

		HttpSession session = request.getSession();
		session.setAttribute(ATT_SOUS_TRAITANT, sousTraitant);
		session.setAttribute(ATT_FORM, form);

		if (form.getErreurs().isEmpty()) {

			this.getServletContext().getRequestDispatcher(VUE_SUCCES).forward(request, response);
		} else {

			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}

	}

}