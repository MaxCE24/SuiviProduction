package net.atos.suivi_production.dao;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.text.WordUtils;

import com.mysql.jdbc.Statement;

import net.atos.suivi_production.beans.Collaborateur;

public class CollaborateurDAO {

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

			myRs = myStmt.executeQuery("select id_collaborateur from ids");

			while (myRs.next()) {
				idSuivant = myRs.getInt("id_collaborateur");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return idSuivant;

	}

	private static int idSuivant = 1;

	public int creer(Collaborateur collaborateur) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myStmt.executeUpdate("insert into collaborateurs (id, nom, prenom, email) values (null, '"
					+ collaborateur.getNom().toUpperCase() + "', '"
					+ WordUtils.capitalizeFully(collaborateur.getPrenom()) + "', '"
					+ collaborateur.getPrenom().toLowerCase() + "." + collaborateur.getNom().toLowerCase()
					+ "@atos.net')");

			PreparedStatement stmt;
			stmt = myConn.prepareStatement("update ids set id_collaborateur=?");
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

		return 1;

	}

	public Collaborateur trouver(int id) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.PreparedStatement myStmt = null;
		ResultSet myRs = null;

		String nom = null;
		String prenom = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.prepareStatement("select * from collaborateurs where id=?");

			myStmt.setInt(1, id);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				nom = myRs.getString("nom");
				prenom = myRs.getString("prenom");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

		return new Collaborateur(id, nom, prenom);
	}

	@SuppressWarnings("finally")
	public SortedMap<Integer, Collaborateur> lister() throws SQLException {
		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		SortedMap<Integer, Collaborateur> collaborateurs = new TreeMap<Integer, Collaborateur>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(
					"select collaborateurs.id, collaborateurs.nom, collaborateurs.prenom from collaborateurs");

			while (myRs.next()) {
				int id = myRs.getInt("id");
				String nom = myRs.getString("nom");
				String prenom = myRs.getString("prenom");

				Collaborateur collaborateur = new Collaborateur(id, nom, prenom);

				collaborateurs.put(collaborateur.getId(), collaborateur);

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

			return collaborateurs;

		}

	}

	public void supprimer(Collaborateur collaborateur) throws SQLException {

		java.sql.Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = (Statement) myConn.createStatement();

			myStmt.executeUpdate("delete from collaborateurs where id=" + collaborateur.getId());

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	void mettreAJour(Collaborateur collaborateur) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myStmt.executeUpdate("update collaborateurs set prenom='prenom updated' where id=1");

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
