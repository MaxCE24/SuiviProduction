package net.atos.suivi_production.dao;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

import com.mysql.jdbc.Statement;

import net.atos.suivi_production.beans.Equipe;

public class EquipeDAO {

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

			myRs = myStmt.executeQuery("select id_equipe from ids");

			while (myRs.next()) {
				idSuivant = myRs.getInt("id_equipe");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return idSuivant;

	}

	private static int idSuivant = 1;

	public int creer(Equipe equipe) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myStmt.executeUpdate("insert into equipes (id, nom) values (null, '" + equipe.getNom() + "')");

			PreparedStatement stmt;
			stmt = myConn.prepareStatement("update ids set id_equipe=?");
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

	public Equipe trouver(int id) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.PreparedStatement myStmt = null;
		ResultSet myRs = null;

		String nom = null;

		try {

			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.prepareStatement("select * from equipes where id=?");

			myStmt.setInt(1, id);

			myRs = myStmt.executeQuery();

			while (myRs.next()) {
				nom = myRs.getString("nom");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

		return new Equipe(id, nom);
	}

	@SuppressWarnings("finally")
	public SortedMap<Integer, Equipe> lister() throws SQLException {
		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		SortedMap<Integer, Equipe> equipes = new TreeMap<Integer, Equipe>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery("select equipes.id, equipes.nom from equipes");

			while (myRs.next()) {
				int id = myRs.getInt("id");
				String nom = myRs.getString("nom");

				Equipe equipe = new Equipe(id, nom);

				equipes.put(equipe.getId(), equipe);

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

			return equipes;

		}

	}

	public void supprimer(Equipe equipe) throws SQLException {

		java.sql.Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = (Statement) myConn.createStatement();

			myStmt.executeUpdate("delete from equipes where id=" + equipe.getId());

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	void mettreAJour(Equipe equipe) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myStmt.executeUpdate("update equipes set nom='nom updated' where id=1");

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
