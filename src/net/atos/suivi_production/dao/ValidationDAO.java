package net.atos.suivi_production.dao;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import com.mysql.jdbc.Statement;

import net.atos.suivi_production.beans.Astreinte;
import net.atos.suivi_production.beans.Collaborateur;
import net.atos.suivi_production.beans.DemandeDAchat;
import net.atos.suivi_production.beans.Validation;

public class ValidationDAO {

	static String dbUrl = "jdbc:mysql://localhost:3306/suivi_production";
	static String user = "root";
	static String pass = "admin";

	public static int getID() throws IOException, SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery("select id_validation from ids");

			while (myRs.next()) {
				idSuivant = myRs.getInt("id_validation");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return idSuivant;

	}

	private static int idSuivant = 1;

	public void creer(Validation validation) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			java.sql.Date sqlDate = new java.sql.Date(validation.getDate().getTime());

			myStmt.executeUpdate("insert into validations (id, valideur, date, demande_d_achat) values (null, "
					+ validation.getValideur().getId() + ", '" + sqlDate + "'," + validation.getDemandeDAchat().getId()
					+ ")");

			PreparedStatement stmt;
			stmt = myConn.prepareStatement("update ids set id_validation=?");
			stmt.setInt(1, idSuivant + 1);

			stmt.executeUpdate();
			stmt.close();

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close();
			}
		}

	}

	@SuppressWarnings("finally")
	public SortedMap<Integer, Validation> lister() throws SQLException {
		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		SortedMap<Integer, Validation> validations = new TreeMap<Integer, Validation>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(
					"select v.id, v.date, c1.id, c1.nom, c1.prenom, d.id, d.numero, d.date, d.description, d.statut, d.numero_de_bon_de_commande, d.collaborateur, c2.id, c2.nom, c2.prenom from validations v inner join collaborateurs c1 on v.valideur = c1.id inner join demandes_d_achat d on v.demande_d_achat = d.id inner join collaborateurs c2 on d.collaborateur = c2.id");

			while (myRs.next()) {
				int id = myRs.getInt("id");
				Date date = myRs.getDate("date");
				Collaborateur valideur = new Collaborateur();
				valideur.setId(myRs.getInt("id"));
				valideur.setNom(myRs.getString("nom"));
				valideur.setPrenom(myRs.getString("prenom"));
				DemandeDAchat demandeDAchat = new DemandeDAchat();
				demandeDAchat.setId(myRs.getInt("id"));
				demandeDAchat.setNumero(myRs.getInt("numero"));
				demandeDAchat.setDate(myRs.getDate("date"));
				demandeDAchat.setDescription(myRs.getString("description"));
				demandeDAchat.setStatut("statut");
				demandeDAchat.setNumeroDeBonDeCommande(myRs.getInt("numero_de_bon_de_commande"));
				Collaborateur demandeur = new Collaborateur();
				demandeur.setId(myRs.getInt("id"));
				demandeur.setNom(myRs.getString("nom"));
				demandeur.setPrenom(myRs.getString("prenom"));
				demandeDAchat.setCollaborateur(demandeur);
				Validation validation = new Validation(id, date, valideur, demandeDAchat);

				validations.put(validation.getId(), validation);

			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {

			if (myRs != null) {
				myRs.close();
			}

			if (myStmt != null) {
				myStmt.close();
			}

			if (myConn != null) {
				myConn.close();
			}

			return validations;

		}

	}

	public void supprimer(Validation validation) throws SQLException {

		java.sql.Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = (Statement) myConn.createStatement();

			myStmt.executeUpdate("delete from validations where id=" + validation.getId());

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	void mettreAJour(Astreinte astreinte) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myStmt.executeUpdate(
					"update astreintes set type='telephonique' where collaborateur=1 and reference='r111'");

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	public static SortedMap<Integer, Validation> trouverParDemandeDAchat(int idDemandeDAchat) throws SQLException {

		java.sql.Connection myConn = null;
		ResultSet myRs = null;

		SortedMap<Integer, Validation> validations = new TreeMap<Integer, Validation>();

		myConn = DriverManager.getConnection(dbUrl, user, pass);

		PreparedStatement stmt = myConn.prepareStatement(
				"select validations.id, validations.valideur, validations.date, collaborateurs.nom, collaborateurs.prenom from validations inner join collaborateurs on validations.valideur=collaborateurs.id where demande_d_achat = ?");

		stmt.setInt(1, idDemandeDAchat);

		myRs = stmt.executeQuery();

		while (myRs.next()) {

			int idValidation = myRs.getInt("id");
			int idValideur = myRs.getInt("valideur");
			Date dateValidation = myRs.getDate("date");
			String nomValideur = myRs.getString("nom");
			String prenomValideur = myRs.getString("prenom");

			Validation validation = new Validation();
			validation.setId(idValidation);
			validation.setDate(dateValidation);
			Collaborateur valideur = new Collaborateur(idValideur, nomValideur, prenomValideur);
			validation.setValideur(valideur);
			validation.setDemandeDAchat(new DemandeDAchat());

			validations.put(validation.getId(), validation);

		}
		return validations;
	}

	private static void close(java.sql.Connection myConn, java.sql.Statement myStmt, ResultSet myRs)
			throws SQLException {
		if (myRs != null) {
			myRs.close();
		}

		if (myStmt != null) {
			myStmt.close();
		}

		if (myConn != null) {
			myConn.close();
		}
	}

}
