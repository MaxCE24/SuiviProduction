package net.atos.suivi_production.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.SortedMap;
import java.util.TreeMap;

import com.mysql.jdbc.Statement;

import net.atos.suivi_production.beans.Equipe;
import net.atos.suivi_production.beans.Profil;
import net.atos.suivi_production.beans.Societe;
import net.atos.suivi_production.beans.SousTraitant;

public class SousTraitantDAO {

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

			myRs = myStmt.executeQuery("select id_sous_traitant from ids");

			while (myRs.next()) {
				idSuivant = myRs.getInt("id_sous_traitant");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return idSuivant;

	}

	private static int idSuivant = 1;

	private static final int BUFFER_SIZE = 4096;

	public void creer(SousTraitant sousTraitant) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		FileInputStream input = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			input = new FileInputStream(sousTraitant.getCV());

			java.sql.Date sqlDate = new java.sql.Date(sousTraitant.getDateRecrutement().getTime());

			String sql = "INSERT INTO sous_traitants (id, nom, prenom, sexe, CV, numero_De_Telephone,date_recrutement,profil,equipe,societe) values (?, ?, ?, ?, ?, ?,?,?,?,?)";
			java.sql.PreparedStatement statement = myConn.prepareStatement(sql);
			statement.setNull(1, java.sql.Types.INTEGER);
			statement.setString(2, sousTraitant.getNom());
			statement.setString(3, sousTraitant.getPrenom());
			statement.setString(4, sousTraitant.getSexe());
			statement.setBlob(5, input);
			statement.setString(6, sousTraitant.getNumeroDeTelephone());
			statement.setDate(7, sqlDate);
			statement.setInt(8, sousTraitant.getProfil().getId());
			statement.setInt(9, sousTraitant.getEquipe().getId());
			statement.setInt(10, sousTraitant.getSociete().getId());

			statement.executeUpdate();
			statement.close();

			java.sql.PreparedStatement stmt;
			stmt = myConn.prepareStatement("update ids set id_sous_traitant=?");
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

	public SortedMap<Integer, SousTraitant> lister() throws SQLException {
		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		SortedMap<Integer, SousTraitant> sousTraitants = new TreeMap<Integer, SousTraitant>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			String sql = "select sous_traitants.id, sous_traitants.nom, sous_traitants.prenom, sous_traitants.sexe, sous_traitants.CV, sous_traitants.numero_De_Telephone, sous_traitants.date_recrutement, profils.id, profils.libelle, equipes.id, equipes.nom as nom_equipe, societes.id, societes.raison_sociale from sous_traitants as sous_traitants inner join profils on sous_traitants.profil = profils.id inner join equipes on sous_traitants.equipe = equipes.id inner join societes on sous_traitants.societe = societes.id";
			java.sql.PreparedStatement statement = myConn.prepareStatement(sql);
			myRs = statement.executeQuery();

			while (myRs.next()) {

				String filePath = "C:\\apache-tomcat-8.0.22\\webapps\\CVs\\";

				int id = myRs.getInt("id");
				String nom = myRs.getString("nom");
				String prenom = myRs.getString("prenom");
				String sexe = myRs.getString("sexe");
				Blob cV = myRs.getBlob("CV");
				InputStream inputStream = cV.getBinaryStream();
				filePath += (prenom + nom + ".pdf");
				@SuppressWarnings("resource")
				OutputStream outputStream = new FileOutputStream(filePath);

				int bytesRead = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}

				String numeroDeTelephone = myRs.getString("numero_De_Telephone");
				Date dateRecrutement = myRs.getDate("date_Recrutement");

				int idProfil = myRs.getInt("id");
				String libelle = myRs.getString("libelle");
				Profil profil = new Profil(idProfil, libelle);

				int idEquipe = myRs.getInt("id");
				String nomEquipe = myRs.getString("nom_equipe");
				Equipe equipe = new Equipe(idEquipe, nomEquipe);

				int idSociete = myRs.getInt("id");
				String raisonSociale = myRs.getString("raison_Sociale");
				Societe societe = new Societe(idSociete, raisonSociale);

				SousTraitant sousTraitant = new SousTraitant(id, nom, prenom, sexe, new File(filePath),
						numeroDeTelephone, dateRecrutement, profil, equipe, societe);

				sousTraitants.put(sousTraitant.getId(), sousTraitant);

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
		}
		return sousTraitants;

	}

	public void supprimer(SousTraitant sousTraitant) throws SQLException {

		java.sql.Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = (Statement) myConn.createStatement();

			myStmt.executeUpdate("delete from sous_traitants where id=" + sousTraitant.getId());
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	void mettreAJour(SousTraitant sousTraitant) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myStmt.executeUpdate("update sous_traitants set prenom='prenom updated' where profil=1 and societe=1");

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
