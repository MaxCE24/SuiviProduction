package net.atos.suivi_production.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.SortedMap;
import java.util.TreeMap;

import com.mysql.jdbc.Statement;

import net.atos.suivi_production.beans.Equipe;
import net.atos.suivi_production.beans.Facture;
import net.atos.suivi_production.beans.Profil;
import net.atos.suivi_production.beans.Societe;
import net.atos.suivi_production.beans.SousTraitant;

public class FactureDAO {

	static String dbUrl = "jdbc:mysql://localhost:3306/suivi_production";
	static String user = "root";
	static String pass = "admin";

	private static final int BUFFER_SIZE = 4096;

	public static int getID() throws IOException, SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery("select id_facture from ids");

			while (myRs.next()) {
				idSuivant = myRs.getInt("id_facture");
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return idSuivant;

	}

	private static int idSuivant = 1;

	public void creer(Facture facture) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			java.sql.Date sqlDateReception = new java.sql.Date((facture.getDateReception()).getTime());
			java.sql.Date sqlDateRemiseAuxOP = new java.sql.Date((facture.getDateRemiseAuxOP()).getTime());
			java.sql.Date sqlDateRetourOP = new java.sql.Date((facture.getDateRetourOP()).getTime());
			java.sql.Date sqlDateRemiseAuTMU = new java.sql.Date((facture.getDateRemiseAuTMU()).getTime());
			java.sql.Date sqlDateEnvoiDAF = new java.sql.Date((facture.getDateEnvoiDAF()).getTime());

			myStmt.executeUpdate(
					"insert into factures (id, numero, date_reception, montant_ttc, date_Remise_Aux_OP, date_Retour_OP, date_Remise_Au_TMU,date_Envoi_DAF,periode_facturee,notes,sous_traitant) values (null, "
							+ facture.getNumero() + ", '" + sqlDateReception + "', " + facture.getMontantTTC() + ", '"
							+ sqlDateRemiseAuxOP + "', '" + sqlDateRetourOP + "', '" + sqlDateRemiseAuTMU + "', '"
							+ sqlDateEnvoiDAF + "', '" + facture.getPeriodeFacturee() + "', '" + facture.getNotes()
							+ "', " + facture.getSousTraitant().getId() + ")");

			PreparedStatement stmt;
			stmt = myConn.prepareStatement("update ids set id_facture=?");
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

	public SortedMap<Integer, Facture> lister() throws SQLException {
		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		SortedMap<Integer, Facture> factures = new TreeMap<Integer, Facture>();

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myRs = myStmt.executeQuery(
					"select factures.id, factures.numero, factures.date_reception, factures.montant_ttc, factures.date_Remise_Aux_OP, factures.date_Retour_OP, factures.date_Remise_Au_TMU, factures.date_Envoi_DAF, factures.periode_facturee, factures.notes, sous_traitants.id as id_sous_traitant, sous_traitants.nom, sous_traitants.prenom, sous_traitants.sexe, sous_traitants.CV, sous_traitants.numero_De_Telephone, sous_traitants.date_recrutement,profils.id as id_profil, profils.libelle, equipes.id as id_equipe, equipes.nom as nom_equipe, societes.id as id_societe, societes.raison_sociale from factures as factures inner join sous_traitants on factures.sous_traitant = sous_traitants.id inner join profils on sous_traitants.profil = profils.id inner join equipes on sous_traitants.equipe = equipes.id inner join societes on sous_traitants.societe = societes.id");

			while (myRs.next()) {

				String filePath = "";

				int id = myRs.getInt("id");
				int numero = myRs.getInt("numero");
				java.util.Date dateReception = myRs.getDate("date_Reception");
				double montantTTC = myRs.getInt("montant_TTC");
				java.util.Date dateRemiseAuxOP = myRs.getDate("date_Remise_Aux_OP");
				java.util.Date dateRetourOP = myRs.getDate("date_Retour_OP");
				java.util.Date dateRemiseAuTMU = myRs.getDate("date_Remise_Au_TMU");
				java.util.Date dateEnvoiDAF = myRs.getDate("date_Envoi_DAF");
				String periodeFacturee = myRs.getString("periode_Facturee");
				String notes = myRs.getString("notes");

				int idSousTraitant = myRs.getInt("id_sous_traitant");
				String nomSousTraitant = myRs.getString("nom");
				String prenomSousTraitant = myRs.getString("prenom");
				String sexe = myRs.getString("sexe");
				Blob cV = myRs.getBlob("CV");
				InputStream inputStream = cV.getBinaryStream();
				filePath += (prenomSousTraitant + nomSousTraitant + ".pdf");
				@SuppressWarnings("resource")
				OutputStream outputStream = new FileOutputStream(filePath);

				int bytesRead = -1;
				byte[] buffer = new byte[BUFFER_SIZE];
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				String numeroDeTelephone = myRs.getString("numero_De_Telephone");
				java.util.Date dateRecrutement = myRs.getDate("date_Recrutement");

				int idProfil = myRs.getInt("id_profil");
				String libelle = myRs.getString("libelle");
				Profil profil = new Profil(idProfil, libelle);

				int idEquipe = myRs.getInt("id_equipe");
				String nom = myRs.getString("nom_equipe");
				Equipe equipe = new Equipe(idEquipe, nom);

				int idSociete = myRs.getInt("id_societe");
				String raisonSociale = myRs.getString("raison_sociale");
				Societe societe = new Societe(idSociete, raisonSociale);

				SousTraitant sousTraitant = new SousTraitant(idSousTraitant, nomSousTraitant, prenomSousTraitant, sexe,
						new File(filePath), numeroDeTelephone, dateRecrutement, profil, equipe, societe);

				Facture facture = new Facture(id, numero, dateReception, montantTTC, dateRemiseAuxOP, dateRetourOP,
						dateRemiseAuTMU, dateEnvoiDAF, periodeFacturee, notes, sousTraitant);

				factures.put(facture.getId(), facture);

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
		return factures;

	}

	public void supprimer(Facture facture) throws SQLException {

		java.sql.Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = (Statement) myConn.createStatement();

			myStmt.executeUpdate("delete from factures where id=" + facture.getId());

		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}

	}

	void mettreAJour(Facture facture) throws SQLException {

		java.sql.Connection myConn = null;
		java.sql.Statement myStmt = null;
		ResultSet myRs = null;

		try {

			myConn = DriverManager.getConnection(dbUrl, user, pass);

			myStmt = myConn.createStatement();

			myStmt.executeUpdate("update factures set notes='bla bla bla' where sous_traitant=1");

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
