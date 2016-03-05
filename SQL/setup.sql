CREATE DATABASE suivi_production;

USE suivi_production;

CREATE TABLE suivi_production.ids (
id_collaborateur INT( 11 ),
id_ASTREINTE INT( 11 ),
id_demande_d_achat INT( 11 ),
id_VALIDATION INT( 11 ),
id_equipe INT( 11 ),
id_profil INT( 11 ),
id_societe INT( 11 ),
id_sous_traitant INT( 11 ),
id_facture INT( 11 )
) ENGINE = INNODB;

INSERT INTO ids VALUES (1, 1, 1, 1, 1, 1, 1, 1, 1);

CREATE TABLE suivi_production.collaborateurs (
id INT( 11 ) NOT NULL AUTO_INCREMENT ,
nom VARCHAR( 20 ) NOT NULL ,
prenom VARCHAR( 20 ) NOT NULL ,
email VARCHAR( 40 ) NOT NULL UNIQUE,
PRIMARY KEY ( id )
) ENGINE = INNODB;

CREATE TABLE suivi_production.ASTREINTES (
id INT( 11 ) NOT NULL AUTO_INCREMENT ,
reference VARCHAR( 20 ) NOT NULL UNIQUE ,
date DATETIME NOT NULL ,
nombre_Heures FLOAT( 10,1) NOT NULL ,
type VARCHAR( 20 ) NOT NULL ,
collaborateur INT( 11 ) NOT NULL REFERENCES collaborateurs(id),
PRIMARY KEY (id)
)ENGINE=INNODB;

CREATE TABLE suivi_production.demandes_d_achat (
id INT( 11 ) NOT NULL AUTO_INCREMENT ,
numero INT( 11 ) NOT NULL UNIQUE ,
date DATETIME NOT NULL ,
description VARCHAR( 20 ) NOT NULL ,
statut VARCHAR( 100 ) NOT NULL ,
numero_de_bon_de_commande INT( 11 ) NOT NULL ,
collaborateur INT( 11 ) NOT NULL REFERENCES collaborateurs(id),
PRIMARY KEY (id)
)ENGINE=INNODB;

CREATE TABLE suivi_production.VALIDATIONS (
id INT( 11 ) NOT NULL AUTO_INCREMENT ,
valideur INT( 11 ) NOT NULL REFERENCES collaborateurs(id) ,
date DATETIME NOT NULL ,
demande_d_achat INT( 11 ) NOT NULL REFERENCES demandes_d_achat(id),
PRIMARY KEY (id)
)ENGINE=INNODB;

CREATE TABLE suivi_production.equipes (
id INT( 11 ) NOT NULL AUTO_INCREMENT ,
nom VARCHAR( 20 ) NOT NULL UNIQUE ,
PRIMARY KEY (id)
)ENGINE=INNODB;

CREATE TABLE suivi_production.profils (
id INT( 11 ) NOT NULL AUTO_INCREMENT ,
libelle VARCHAR( 20 ) NOT NULL UNIQUE ,
PRIMARY KEY (id)
)ENGINE=INNODB;

CREATE TABLE suivi_production.societes (
id INT( 11 ) NOT NULL AUTO_INCREMENT ,
raison_sociale VARCHAR( 20 ) NOT NULL UNIQUE ,
PRIMARY KEY (id)
)ENGINE=INNODB;

CREATE TABLE suivi_production.sous_traitants (
id INT( 11 ) NOT NULL AUTO_INCREMENT ,
nom VARCHAR( 20 ) NOT NULL ,
prenom VARCHAR( 20 ) NOT NULL ,
sexe VARCHAR( 20 ) NOT NULL ,
CV MEDIUMBLOB NOT NULL ,
numero_De_Telephone VARCHAR( 20 ) NOT NULL UNIQUE ,
date_recrutement DATETIME NOT NULL ,
profil INT( 11 ) NOT NULL REFERENCES profils(id),
equipe INT( 11 ) NOT NULL REFERENCES equipes(id),
societe INT( 11 ) NOT NULL REFERENCES societes(id),
PRIMARY KEY (id)
)ENGINE=INNODB;

CREATE TABLE suivi_production.factures (
id INT( 11 ) NOT NULL AUTO_INCREMENT ,
numero INT( 11 ) NOT NULL UNIQUE ,
date_reception DATETIME NOT NULL ,
montant_ttc DEC( 11 ) NOT NULL ,
date_Remise_Aux_OP DATETIME NOT NULL ,
date_Retour_OP DATETIME NOT NULL ,
date_Remise_Au_TMU DATETIME NOT NULL ,
date_Envoi_DAF DATETIME NOT NULL ,
periode_facturee VARCHAR( 20 ) NOT NULL ,
notes TEXT NOT NULL ,
sous_traitant INT( 11 ) NOT NULL REFERENCES sous_traitants(id),
PRIMARY KEY (id)
)ENGINE=INNODB;