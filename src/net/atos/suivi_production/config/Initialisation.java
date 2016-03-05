package net.atos.suivi_production.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Initialisation implements ServletContextListener {
	private static final String ATT_UTILISATEUR = "utilisateur";
	private static final String CONFIG_PATH = "C:\\Config\\config.txt";

	InputStream inputStream;

	private Utilisateur utilisateur;

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext = event.getServletContext();

		Properties prop = new Properties();
		try {
			inputStream = new FileInputStream(CONFIG_PATH);
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		if (inputStream != null) {
			try {
				prop.load(inputStream);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		} else {
			try {
				throw new FileNotFoundException("property file '" + CONFIG_PATH + "' not found in the classpath");
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			}
		}
		String email = prop.getProperty("email");
		String password = prop.getProperty("password");

		try {
			inputStream.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		utilisateur = new Utilisateur(email, password);

		servletContext.setAttribute(ATT_UTILISATEUR, this.utilisateur);

	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}

}
