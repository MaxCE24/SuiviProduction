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

import net.atos.suivi_production.beans.Collaborateur;
import net.atos.suivi_production.beans.DemandeDAchat;

public class DemandeDAchatDAO {

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

			
			myRs = myStmt.executeQuery("select id_demande_d_achat from ids");

			
			while (myRs.next()) {
				idSuivant = myRs.getInt("id_demande_d_achat");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return idSuivant;

	}

	private static int idSuivant = 1;

	public void creer(DemandeDAchat demandeDAchat) throws SQLException, IOException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			 
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			 
			myStmt = myConn.createStatement();

			

			java.sql.Date sqlDate = new java.sql.Date(demandeDAchat.getDate().getTime());

			myStmt.executeUpdate(
					"insert into demandes_d_achat (id, numero, date, description, statut, numero_de_bon_de_commande, collaborateur) values ("
							+ "NULL, " + demandeDAchat.getNumero() + ", '" + sqlDate + "', '"
							+ demandeDAchat.getDescription() + "', '" + demandeDAchat.getStatut() + "', "
							+ demandeDAchat.getNumeroDeBonDeCommande() + ", " + demandeDAchat.getCollaborateur().getId()
							+ ")");

			
			PreparedStatement stmt;
			stmt = myConn.prepareStatement("update ids set id_demande_d_achat=?");
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
	public SortedMap<Integer, DemandeDAchat> lister() throws SQLException {
		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		SortedMap<Integer, DemandeDAchat> demandesDAchat = new TreeMap<Integer, DemandeDAchat>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			 
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			 
			myStmt = myConn.createStatement();

			
			myRs = myStmt.executeQuery(
					"select demandes_d_achat.id, demandes_d_achat.numero, demandes_d_achat.date, demandes_d_achat.description, demandes_d_achat.statut, demandes_d_achat.numero_de_bon_de_commande, collaborateurs.id, collaborateurs.nom, collaborateurs.prenom from demandes_d_achat as demandes_d_achat inner join collaborateurs on demandes_d_achat.collaborateur = collaborateurs.id");
			
			while (myRs.next()) {

				Integer idDemandeDAchat = myRs.getInt("id");
				Integer numero = myRs.getInt("numero");
				Date date = myRs.getDate("date");
				String description = myRs.getString("description");
				String statut = myRs.getString("statut");
				Integer numeroDeBonDeCommande = myRs.getInt("numero_De_Bon_De_Commande");

				Integer id = myRs.getInt("id");
				String nom = myRs.getString("nom");
				String prenom = myRs.getString("prenom");

				Collaborateur collaborateur = new Collaborateur(id, nom, prenom);

				DemandeDAchat demandeDAchat = new DemandeDAchat(idDemandeDAchat, numero, date, description, statut,
						numeroDeBonDeCommande, collaborateur, ValidationDAO.trouverParDemandeDAchat(idDemandeDAchat));

				demandesDAchat.put(demandeDAchat.getId(), demandeDAchat);

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

			return demandesDAchat;

		}

	}

	public void supprimer(DemandeDAchat demandeDAchat) throws SQLException {

		java.sql.Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			
			myStmt = (Statement) myConn.createStatement();

			myStmt.executeUpdate("delete from demandes_d_achat where id=" + demandeDAchat.getId());

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	void mettreAJour(DemandeDAchat demandeDAchat) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {
			
			myConn = DriverManager.getConnection(dbUrl, user, pass);

			
			myStmt = myConn.createStatement();

			myStmt.executeUpdate("update demandes_d_achat set statut='rejeté' where collaborateur=3 and numero=3");

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
