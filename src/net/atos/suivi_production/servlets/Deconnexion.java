package net.atos.suivi_production.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

 
@WebServlet("/Deconnexion")
public class Deconnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public static final String VUE = "/Connexion";

	 
	public Deconnexion() {
		super();
		 
	}

	 
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		 
		HttpSession session = request.getSession();
		session.invalidate();
		 
		this.getServletContext().getRequestDispatcher(VUE).forward(request, response);

	}

	 
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 
		doGet(request, response);
	}

}
