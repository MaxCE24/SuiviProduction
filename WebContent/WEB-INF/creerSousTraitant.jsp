<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Création d'un sous traitant</title>
<meta name="viewport"
	content="initial-scale = 1.0, maximum-scale = 1.0, user-scalable = no, width = device-width">


<link rel="stylesheet" href="style.css" media="screen">

<link rel="stylesheet" href="style.responsive.css" media="all">

<style>
.art-content .art-postcontent-0 .layout-item-0 {
	margin-bottom: 10px;
}

.art-content .art-postcontent-0 .layout-item-1 {
	padding-right: 10px;
	padding-left: 10px;
}

.ie7 .art-post .art-layout-cell {
	border: none !important;
	padding: 0 !important;
}

.ie6 .art-post .art-layout-cell {
	border: none !important;
	padding: 0 !important;
}
</style>

<script src="jquery.js"></script>

<link type="text/css" rel="stylesheet"
	href="<c:url
value="/inc/style.css"/>" />
</head>
<body>

	<div id="art-main">
		<div class="art-sheet clearfix">
			<header class="art-header">



				<div class="art-textblock art-textblock-325757307">
					<div class="art-textblock-325757307-text-container">
						<div class="art-textblock-325757307-text">
							<a href="/SuiviProduction/Deconnexion" title="Se déconnecter"
								class="art-linkedin-tag-icon"></a>
						</div>
					</div>

				</div>



				<nav class="art-nav">
					<ul class="art-hmenu">
						<li><a href="/SuiviProduction/listeAstreintes">Astreintes</a></li>
						<li><a href="/SuiviProduction/listeDemandesDAchat">Demandes
								d'Achat</a></li>
						<li><a href="/SuiviProduction/listeFactures">Factures</a></li>
						<li><a href="/SuiviProduction/listeSousTraitants"
							class="active">Gestion SST</a></li>
					</ul>
				</nav>

			</header>
			<div class="art-layout-wrapper">
				<div class="art-content-layout">
					<div class="art-content-layout-row">
						<div class="art-layout-cell art-sidebar1">
							<div class="art-vmenublock clearfix">
								<div class="art-vmenublockheader">
									<h3 class="t">Menu</h3>
								</div>
								<div class="art-vmenublockcontent">
									<ul class="art-vmenu">
										<li><a href="#" class="active">Suivi production</a>
											<ul class="active">
												<li><a href="/SuiviProduction/listeAstreintes">Astreintes</a></li>
												<li><a href="/SuiviProduction/listeDemandesDAchat">Demandes
														d'Achat</a></li>
												<li><a href="/SuiviProduction/listeFactures">Factures</a></li>
												<li><a href="/SuiviProduction/listeSousTraitants"
													class="active">Gestion SST</a></li>
											</ul></li>

									</ul>
								</div>
							</div>
						</div>

						<c:if test="${empty sessionScope.sessionUtilisateur}">
							<jsp:forward page="/WEB-INF/seConnecter.jsp" />
						</c:if>


						<div>
							<form method="post"
								action="<c:url
value="/creationSousTraitant"/>">

								<c:set var="profil" value="${ sousTraitant.profil }"
									scope="request" />
								<c:set var="equipe" value="${ sousTraitant.equipe }"
									scope="request" />
								<c:set var="societe" value="${ sousTraitant.societe }"
									scope="request" />


								<fieldset>
									<legend>Informations sous traitant</legend>

									<p>
										<label for="nomSousTraitant">Nom <span class="requis">*</span></label>
										<input type="text" id="nomSousTraitant" name="nomSousTraitant"
											value="<c:out value="${sousTraitant.nom}"/>" size="20"
											maxlength="20" /> <span class="erreur">${form.erreurs['nomSousTraitant']}</span>
										<br />
									</p>
									<br>
									<p>
										<label for="prenomSousTraitant">Prénom <span
											class="requis">*</span></label> <input type="text"
											id="prenomSousTraitant" name="prenomSousTraitant"
											value="<c:out value="${sousTraitant.prenom}"/>" size="20"
											maxlength="20" /> <span class="erreur">${form.erreurs['prenomSousTraitant']}</span>
										<br />
									</p>
									<br> <br>

									<p>
										<label for="sexeSousTraitant">Sexe <span
											class="requis">*</span>
										</label> <select name="sexeSousTraitant" id="sexeSousTraitant">
											<option value="${sousTraitant.sexe}">Choisissez le
												sexe...</option>
											<%-- Boucle sur les sexes --%>

											<option value="Homme">Homme</option>
											<option value="Femme">Femme</option>

										</select><br> <br> <span class="erreur">${form.erreurs['sexeSousTraitant']}</span>
										<br />
									</p>

									<p>
										<label for="numeroDeTelephoneSousTraitant">Tél <span
											class="requis">*</span>
										</label> <input type="tel" id="numeroDeTelephoneSousTraitant"
											name="numeroDeTelephoneSousTraitant"
											value="<c:out value="${sousTraitant.numeroDeTelephone}"/>"
											size="20" maxlength="20" /> <span class="erreur">${form.erreurs['numeroDeTelephoneSousTraitant']}</span>
										<br />
									</p>
									<br>
									<p>
										<label for="dateRecrutementSousTraitant">Date
											recrutement <span class="requis">*</span>
										</label> <input type="date" id="dateRecrutementSousTraitant"
											name="dateRecrutementSousTraitant" value="" size="20"
											maxlength="20" /> <span class="erreur">${form.erreurs['dateRecrutementSousTraitant']}</span>
										<br />
									</p>
									<br>
									<p>
									<fieldset>
										<legend>Informations profil</legend>

										<c:if test="${ !empty sessionScope.profils }">
											<label for="choixNouveauProfil">Nouveau profil ? <span
												class="requis">*</span>
											</label>
											<input type="radio" id="choixNouveauProfil"
												name="choixNouveauProfil" value="nouveauProfil" checked /> Oui
<input type="radio" id="choixNouveauProfil" name="choixNouveauProfil"
												value="ancienProfil" /> Non
<br />
											<br />
										</c:if>

										<div id="nouveauProfil">
											<c:import url="/inc/inc_profil_form.jsp" />
										</div>

										<%-- Si et seulement si la Map des profils en requete n'est
pas vide, alors on crée la liste déroulante --%>
										<c:if test="${ !empty sessionScope.profils }">
											<div id="ancienProfil">
												<select name="listeProfils" id="listeProfils">
													<option value="">Choisissez un profil...</option>
													<%-- Boucle sur la map des clients --%>
													<c:forEach items="${ sessionScope.profils }" var="profil">
														<%-- L'expression EL ${profil.value} permet de
cibler l'objet Profil stocké en tant que valeur dans la Map,
et on cible ensuite simplement ses propriétés
nom et prenom comme on le ferait avec n'importe quel bean. --%>
														<option value="${ profil.key }">${ profil.value.libelle }</option>
													</c:forEach>
												</select>
											</div>
										</c:if>
									</fieldset>
									<br />
									</p>
									<br>
									<p>
									<fieldset>
										<legend>Informations équipe</legend>

										<c:if test="${ !empty sessionScope.equipes }">
											<label for="choixNouveauEquipe">Nouvelle équipe ? <span
												class="requis">*</span>
											</label>
											<input type="radio" id="choixNouveauEquipe"
												name="choixNouveauEquipe" value="nouveauEquipe" checked /> Oui
<input type="radio" id="choixNouveauEquipe" name="choixNouveauEquipe"
												value="ancienEquipe" /> Non
<br />
											<br />
										</c:if>

										<div id="nouveauEquipe">
											<c:import url="/inc/inc_equipe_form.jsp" />
										</div>

										<%-- Si et seulement si la Map des profils en requete n'est
pas vide, alors on crée la liste déroulante --%>
										<c:if test="${ !empty sessionScope.equipes }">
											<div id="ancienEquipe">
												<select name="listeEquipes" id="listeEquipes">
													<option value="">Choisissez une équipe...</option>
													<%-- Boucle sur la map des profils --%>
													<c:forEach items="${ sessionScope.equipes }" var="equipe">
														<%-- L'expression EL ${profil.value} permet de
cibler l'objet Profil stocké en tant que valeur dans la Map,
et on cible ensuite simplement sa propriété
libellé comme on le ferait avec n'importe quel bean. --%>
														<option value="${ equipe.key }">${
equipe.value.nom }</option>
													</c:forEach>
												</select>
											</div>
										</c:if>
									</fieldset>
									<br />
									</p>
									<br>
									<p>
									<fieldset>
										<legend>Informations cabinet</legend>

										<c:if test="${ !empty sessionScope.societes }">
											<label for="choixNouveauSociete">Nouveau cabinet ? <span
												class="requis">*</span>
											</label>
											<input type="radio" id="choixNouveauSociete"
												name="choixNouveauSociete" value="nouveauSociete" checked /> Oui
<input type="radio" id="choixNouveauSociete" name="choixNouveauSociete"
												value="ancienSociete" /> Non
<br />
											<br />
										</c:if>

										<div id="nouveauSociete">
											<c:import url="/inc/inc_societe_form.jsp" />
										</div>

										<%-- Si et seulement si la Map des profils en requete n'est
pas vide, alors on crée la liste déroulante --%>
										<c:if test="${ !empty sessionScope.societes }">
											<div id="ancienSociete">
												<select name="listeSocietes" id="listeSocietes">
													<option value="">Choisissez un cabinet...</option>
													<%-- Boucle sur la map des profils --%>
													<c:forEach items="${ sessionScope.societes }" var="societe">
														<%-- L'expression EL ${profil.value} permet de
cibler l'objet Profil stocké en tant que valeur dans la Map,
et on cible ensuite simplement sa propriété
libellé comme on le ferait avec n'importe quel bean. --%>
														<option value="${ societe.key }">${
societe.value.raisonSociale }</option>
													</c:forEach>
												</select>
											</div>
										</c:if>
									</fieldset>
									<br />
									</p>
									<br>
								</fieldset>

								<br />
								<p class="info"></p>
								<br />
								<div align="right" class="art-vmenu">
									<input type="submit" id="Valider" value="Valider"
										style="height: 30px; width: 120px; background-color: lightblue; font: bold 14px Arial" />
									<input type="reset" value="Remettre à zéro"
										style="height: 30px; width: 120px; background-color: lightblue; font: bold 14px normal verdana, helvetica, sans-serif" />
								</div>
								<br> <br>
							</form>
						</div>

						<%-- Petite fonction jQuery permettant le remplacement de la première
partie du formulaire par la liste déroulante, au clic sur le bouton radio. --%>
						<script>
							jQuery(document)
									.ready(
											function() {

												$("div#ancienProfil").hide();

												jQuery(
														'input[name=choixNouveauProfil]:radio')
														.click(
																function() {
																	$(
																			"div#nouveauProfil")
																			.hide();
																	$(
																			"div#ancienProfil")
																			.hide();
																	var divId = jQuery(
																			this)
																			.val();
																	$(
																			"div#"
																					+ divId)
																			.show();
																});
											});

							jQuery(document)
									.ready(
											function() {

												$("div#ancienEquipe").hide();

												jQuery(
														'input[name=choixNouveauEquipe]:radio')
														.click(
																function() {
																	$(
																			"div#nouveauEquipe")
																			.hide();
																	$(
																			"div#ancienEquipe")
																			.hide();
																	var divId = jQuery(
																			this)
																			.val();
																	$(
																			"div#"
																					+ divId)
																			.show();
																});

											});

							jQuery(document)
									.ready(
											function() {

												$("div#ancienSociete").hide();

												jQuery(
														'input[name=choixNouveauSociete]:radio')
														.click(
																function() {
																	$(
																			"div#nouveauSociete")
																			.hide();
																	$(
																			"div#ancienSociete")
																			.hide();
																	var divId = jQuery(
																			this)
																			.val();
																	$(
																			"div#"
																					+ divId)
																			.show();
																});

											});
						</script>

					</div>
				</div>
			</div>

			<footer class="art-footer">
				<p>
					© <a href="http://fr.atos.net/fr-fr/accueil.html" target="_blank"><em
						style="text-decoration: underline;">Atos SE</em></a> 2016 Tous droits
					réservés
				</p>
			</footer>

		</div>

	</div>



	<script src="script.js"></script>
	<script src="script.responsive.js"></script>

</body>
</html>