package net.atos.suivi_production.servlets;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import net.atos.suivi_production.beans.SousTraitant;
import net.atos.suivi_production.dao.SousTraitantDAO;
import net.atos.suivi_production.forms.CreationSousTraitantForm;

public class UploadServlet extends HttpServlet {

	public static final String ATT_SOUS_TRAITANT = "sousTraitant";
	public static final String ATT_FORM = "form";

	public static final String VUE_SUCCES = "/listeSousTraitants";
	public static final String VUE_FORM = "/WEB-INF/chargerCV.jsp";

	private static final long serialVersionUID = 1L;
	private boolean isMultipart;
	private String filePath;
	private int maxFileSize = 50 * 1024 * 1000 * 10;
	private int maxMemSize = 4 * 1024 * 1000 * 10;
	private File file;
	String erreur = "";

	public void init() {

		filePath = getServletContext().getInitParameter("file-upload");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		HttpSession session = request.getSession();
		SousTraitant sousTraitant = (SousTraitant) session.getAttribute(ATT_SOUS_TRAITANT);
		CreationSousTraitantForm form = (CreationSousTraitantForm) session.getAttribute(ATT_FORM);

		isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {

			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();

		factory.setSizeThreshold(maxMemSize);

		factory.setRepository(new File("C:\\draft"));

		ServletFileUpload upload = new ServletFileUpload(factory);

		upload.setSizeMax(maxFileSize);

		try {

			@SuppressWarnings("rawtypes")
			List fileItems = upload.parseRequest(request);

			@SuppressWarnings("rawtypes")
			Iterator i = fileItems.iterator();

			while (i.hasNext()) {
				FileItem fi = (FileItem) i.next();
				if (!fi.isFormField()) {

					String fileName = fi.getName();

					if (fileName.lastIndexOf("\\") >= 0) {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\")));
					} else {
						file = new File(filePath + fileName.substring(fileName.lastIndexOf("\\") + 1));
					}
					fi.write(file);

					sousTraitant.setCV(file);

					request.setAttribute(ATT_SOUS_TRAITANT, sousTraitant);
					request.setAttribute(ATT_FORM, form);
					new SousTraitantDAO().creer(sousTraitant);
					response.sendRedirect(request.getContextPath() + VUE_SUCCES);
				}
			}

		} catch (Exception ex) {
			erreur = ex.getMessage();
			this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
		}
	}
}