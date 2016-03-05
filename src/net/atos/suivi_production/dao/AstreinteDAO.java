package net.atos.suivi_production.dao;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.text.WordUtils;

import com.mysql.jdbc.Statement;

import net.atos.suivi_production.beans.Astreinte;
import net.atos.suivi_production.beans.Collaborateur;

public class AstreinteDAO {

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

			myRs = myStmt.executeQuery("select id_astreinte from ids");

			while (myRs.next()) {
				idSuivant = myRs.getInt("id_astreinte");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return idSuivant;

	}

	private static int idSuivant = 1;

	public void creer(Astreinte astreinte) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			java.sql.Date sqlDate = new java.sql.Date(astreinte.getDate().getTime());

			myStmt.executeUpdate(
					"insert into astreintes (id, reference, date, nombre_Heures, type, collaborateur) values (null, '"
							+ WordUtils.capitalizeFully(astreinte.getReference()) + "', '" + sqlDate + "',"
							+ astreinte.getNombreHeures() + ", '" + astreinte.getType() + "', "
							+ astreinte.getCollaborateur().getId() + ")");

			PreparedStatement stmt;
			stmt = myConn.prepareStatement("update ids set id_astreinte=?");
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
	public SortedMap<Integer, Astreinte> lister() throws SQLException {
		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		SortedMap<Integer, Astreinte> astreintes = new TreeMap<Integer, Astreinte>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(
					"select astreintes.id, astreintes.reference, astreintes.date, astreintes.nombre_Heures, astreintes.type, collaborateurs.id, collaborateurs.nom, collaborateurs.prenom from astreintes as astreintes inner join collaborateurs on astreintes.collaborateur = collaborateurs.id");
			while (myRs.next()) {
				int id = myRs.getInt("id");
				String reference = myRs.getString("reference");
				Date date = myRs.getDate("date");
				Double nombreHeures = myRs.getDouble("nombre_heures");
				String type = myRs.getString("type");

				Integer idCollaborateur = myRs.getInt("id");
				String nom = myRs.getString("nom");
				String prenom = myRs.getString("prenom");

				Collaborateur collaborateur = new Collaborateur(idCollaborateur, nom, prenom);
				Astreinte astreinte = new Astreinte(id, reference, date, nombreHeures, type, collaborateur);

				astreintes.put(astreinte.getId(), astreinte);

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

			return astreintes;

		}

	}

	public void supprimer(Astreinte astreinte) throws SQLException {

		java.sql.Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = (Statement) myConn.createStatement();

			myStmt.executeUpdate("delete from astreintes where id=" + astreinte.getId());

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	public void modifier(Astreinte astreinte) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myStmt.executeUpdate("update astreintes set reference='" + astreinte.getReference() + "', date='"
					+ astreinte.getDate() + "', nombre_Heures=" + astreinte.getNombreHeures() + ", type='"
					+ astreinte.getType() + "' where id=" + astreinte.getId());

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

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
