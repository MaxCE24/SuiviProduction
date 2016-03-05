<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Création d'une demande d'achat</title>
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
						<li><a href="/SuiviProduction/listeDemandesDAchat"
							class="active">Demandes d'Achat</a></li>
						<li><a href="/SuiviProduction/listeFactures">Factures</a></li>
						<li><a href="/SuiviProduction/listeSousTraitants">Gestion
								SST</a></li>
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
												<li><a href="/SuiviProduction/listeDemandesDAchat"
													class="active">Demandes d'Achat</a></li>
												<li><a href="/SuiviProduction/listeFactures">Factures</a></li>
												<li><a href="/SuiviProduction/listeSousTraitants">Gestion
														SST</a></li>
											</ul></li>

									</ul>
								</div>
							</div>
						</div>

						<c:if test="${empty sessionScope.sessionUtilisateur}">
							<jsp:forward page="/WEB-INF/seConnecter.jsp" />
						</c:if>

						<div>
							<form id="cDDA" method="post"
								action="<c:url
value="/creationDemandeDAchat"/>">

								<%-- Petite astuce ici : placer le collaborateur accessible
via
l'EL ${ demandeDAchat.collaborateur } dans une variable
"collaborateur" de
portée request, pour que le code du fragment
fonctionne
à la fois depuis le formulaire de création d'un
collaborateur
et depuis celui de création d'une demande d'achat. --%>
								<c:set var="collaborateur"
									value="${ demandeDAchat.collaborateur }" scope="request" />

								<fieldset>
									<legend>Informations demande d'achat</legend>
									<p>
										<label for="numeroDemandeDAchat">N° DA <span
											class="requis">*</span></label> <input type="number" step="1" min="0"
											id="numeroDemandeDAchat" name="numeroDemandeDAchat"
											value="<c:out value="${demandeDAchat.numero}"/>" size="20"
											maxlength="20" /> <span class="erreur">${form.erreurs['numeroDemandeDAchat']}</span>
										<br /> <br>
									</p>
									<p>
										<label for="dateDemandeDAchat">Date de demande <span
											class="requis">*</span></label> <input type="date"
											id="dateDemandeDAchat" name="dateDemandeDAchat" value=""
											size="20" maxlength="20" /> <span class="erreur">${form.erreurs['dateDemandeDAchat']}</span><br />
									</p>


									<br />

									<fieldset>
										<legend>Informations demandeur</legend>

										<c:if test="${ !empty sessionScope.collaborateurs }">
											<label for="choixNouveauCollaborateur">Nouveau
												collaborateur ? <span class="requis">*</span>
											</label>
											<input type="radio" id="choixNouveauCollaborateur"
												name="choixNouveauCollaborateur"
												value="nouveauCollaborateur" checked /> Oui
<input type="radio" id="choixNouveauCollaborateur"
												name="choixNouveauCollaborateur" value="ancienCollaborateur" /> Non
<br />
											<br />
										</c:if>

										<div id="nouveauCollaborateur">
											<c:import url="/inc/inc_collaborateur_form.jsp" />
										</div>

										<%-- Si et seulement si la Map des collaborateurs en requete n'est
pas vide, alors on crée la liste déroulante --%>
										<c:if test="${ !empty sessionScope.collaborateurs }">
											<div id="ancienCollaborateur">
												<select name="listeCollaborateurs" id="listeCollaborateurs">
													<option value="">Choisissez un collaborateur...</option>
													<%-- Boucle sur la map des clients --%>
													<c:forEach items="${ sessionScope.collaborateurs }"
														var="collaborateur">
														<%-- L'expression EL ${collaborateur.value} permet de
cibler l'objet Collaborateur stocké en tant que valeur dans la Map,
et on cible ensuite simplement ses propriétés
nom et prenom comme on le ferait avec n'importe quel bean. --%>
														<option value="${ collaborateur.key }">${
collaborateur.value.prenom }
															${ collaborateur.value.nom }</option>
													</c:forEach>
												</select>
											</div>
										</c:if>
									</fieldset>

									<br />


									<p>
										<label for="descriptionDemandeDAchat">Description <span
											class="requis">*</span></label> <input type="text"
											id="descriptionDemandeDAchat" name="descriptionDemandeDAchat"
											value="<c:out value="${demandeDAchat.description}"/>"
											size="20" maxlength="20" /> <span class="erreur">${form.erreurs['descriptionDemandeDAchat']}</span><br />
									</p>

									<br /> <br />

									<p>
										<label for="statutDemandeDAchat">Statut <span
											class="requis">*</span>
										</label> <select name="statutDemandeDAchat" id="statutDemandeDAchat">
											<option value="${demandeDAchat.statut}">Choisissez
												un satut...</option>
											<%-- Boucle sur les statuts --%>

											<option value="Sauvegardée">Sauvegardée</option>
											<option value="En attente de validation">En attente
												de validation</option>
											<option value="Validée">Validée</option>
											<option value="Rejetée">Rejetée</option>

										</select><br> <br> <span class="erreur">${form.erreurs['statutDemandeDAchat']}</span>
										<br />
									</p>


									<p>
										<label for="numeroDeBonDeCommandeDemandeDAchat">N° BC
											<span class="requis">*</span>
										</label> <input type="number" step="1" min="0"
											id="numeroDeBonDeCommandeDemandeDAchat"
											name="numeroDeBonDeCommandeDemandeDAchat"
											value="<c:out value="${demandeDAchat.numeroDeBonDeCommande}"/>"
											size="20" maxlength="20" /><br>
										<span class="erreur">${form.erreurs['numeroDeBonDeCommandeDemandeDAchat']}</span>
										<br />
									</p>
								</fieldset>
								<fieldset>
									<legend>Suivi validations</legend>

									<br>Si votre valideur ne figure pas, veuillez l'ajouter en
									cliquant <a href="<c:url value="/creationCollaborateur"/>">ici</a>.<br>

									<br />
									<c:if test="${fn:length(sessionScope.collaborateurs) gt 0}">
										<div id="validation1" class="info"
											style="text-decoration: underline;">Ajouter validation</div>
									</c:if>

									<div class="validation1"
										style="display: none; position: relative;">
										<fieldset>


											<p>
											<div id="ancienCollaborateur1">
												<label for="listeCollaborateurs1">Valideur 1 </label><br>
												<select name="listeCollaborateurs1"
													id="listeCollaborateurs1">
													<option value="">Choisissez un collaborateur...</option>
													<%-- Boucle sur la map des clients --%>
													<c:forEach items="${ sessionScope.collaborateurs }"
														var="collaborateur">
														<%-- L'expression EL ${collaborateur.value} permet de
cibler l'objet Collaborateur stockÃ© en tant que valeur dans la Map,
et on cible ensuite simplement ses propriÃ©tÃ©s
nom et prenom comme on le ferait avec n'importe quel bean. --%>
														<option value="${ collaborateur.key }">${
collaborateur.value.prenom }
															${ collaborateur.value.nom }</option>
													</c:forEach>
												</select>
											</div>
											</p>

											<br />
											<p>
												<label for="dateValidation1DemandeDAchat">Date
													validation 1 </label> <input type="date"
													id="dateValidation1DemandeDAchat"
													name="dateValidation1DemandeDAchat" value="" size="20"
													maxlength="20" /> <span class="erreur">${form.erreurs['dateValidation1DemandeDAchat']}</span><br />
											</p>

											<br />
										</fieldset>
										<c:if test="${fn:length(sessionScope.collaborateurs) gt 1}">
											<div id="validation2" class="info"
												style="text-decoration: underline;">Ajouter validation</div>
										</c:if>
									</div>



									<br />

									<div class="validation2"
										style="display: none; position: relative;">
										<fieldset>


											<p>
											<div id="ancienCollaborateur2">
												<label for="listeCollaborateurs2">Valideur 2 </label><br>
												<select name="listeCollaborateurs2"
													id="listeCollaborateurs2">
													<option value="">Choisissez un collaborateur...</option>
													<%-- Boucle sur la map des clients --%>
													<c:forEach items="${ sessionScope.collaborateurs }"
														var="collaborateur">
														<%-- L'expression EL ${collaborateur.value} permet de
cibler l'objet Collaborateur stockÃ© en tant que valeur dans la Map,
et on cible ensuite simplement ses propriÃ©tÃ©s
nom et prenom comme on le ferait avec n'importe quel bean. --%>
														<option value="${ collaborateur.key }">${
collaborateur.value.prenom }
															${ collaborateur.value.nom }</option>
													</c:forEach>
												</select>
											</div>
											</p>

											<br />
											<p>
												<label for="dateValidation2DemandeDAchat">Date
													validation 2 </label> <input type="date"
													id="dateValidation2DemandeDAchat"
													name="dateValidation2DemandeDAchat" value="" size="20"
													maxlength="20" /> <span class="erreur">${form.erreurs['dateValidation2DemandeDAchat']}</span><br />
											</p>

											<br />
										</fieldset>
										<c:if test="${fn:length(sessionScope.collaborateurs) gt 2}">
											<div id="validation3" class="info"
												style="text-decoration: underline;">Ajouter validation</div>
										</c:if>
									</div>


									<br />

									<div class="validation3"
										style="display: none; position: relative;">
										<fieldset>


											<p>
											<div id="ancienCollaborateur3">
												<label for="listeCollaborateurs3">Valideur 3 </label><br>
												<select name="listeCollaborateurs3"
													id="listeCollaborateurs3">
													<option value="">Choisissez un collaborateur...</option>
													<%-- Boucle sur la map des clients --%>
													<c:forEach items="${ sessionScope.collaborateurs }"
														var="collaborateur">
														<%-- L'expression EL ${collaborateur.value} permet de
cibler l'objet Collaborateur stockÃ© en tant que valeur dans la Map,
et on cible ensuite simplement ses propriÃ©tÃ©s
nom et prenom comme on le ferait avec n'importe quel bean. --%>
														<option value="${ collaborateur.key }">${
collaborateur.value.prenom }
															${ collaborateur.value.nom }</option>
													</c:forEach>
												</select>
											</div>
											</p>

											<br />
											<p>
												<label for="dateValidation3DemandeDAchat">Date
													validation 3 </label> <input type="date"
													id="dateValidation3DemandeDAchat"
													name="dateValidation3DemandeDAchat" value="" size="20"
													maxlength="20" /> <span class="erreur">${form.erreurs['dateValidation3DemandeDAchat']}</span><br />
											</p>

											<br />
										</fieldset>
										<c:if test="${fn:length(sessionScope.collaborateurs) gt 3}">
											<div id="validation4" class="info"
												style="text-decoration: underline;">Ajouter validation</div>
										</c:if>
									</div>


									<br />

									<div class="validation4"
										style="display: none; position: relative;">
										<fieldset>


											<p>
											<div id="ancienCollaborateur4">
												<label for="listeCollaborateurs4">Valideur 4 </label><br>
												<select name="listeCollaborateurs4"
													id="listeCollaborateurs4">
													<option value="">Choisissez un collaborateur...</option>
													<%-- Boucle sur la map des clients --%>
													<c:forEach items="${ sessionScope.collaborateurs }"
														var="collaborateur">
														<%-- L'expression EL ${collaborateur.value} permet de
cibler l'objet Collaborateur stockÃ© en tant que valeur dans la Map,
et on cible ensuite simplement ses propriÃ©tÃ©s
nom et prenom comme on le ferait avec n'importe quel bean. --%>
														<option value="${ collaborateur.key }">${
collaborateur.value.prenom }
															${ collaborateur.value.nom }</option>
													</c:forEach>
												</select>
											</div>
											</p>

											<br />
											<p>
												<label for="dateValidation4DemandeDAchat">Date
													validation 4 </label> <input type="date"
													id="dateValidation4DemandeDAchat"
													name="dateValidation4DemandeDAchat" value="" size="20"
													maxlength="20" /> <span class="erreur">${form.erreurs['dateValidation4DemandeDAchat']}</span><br />
											</p>

											<br />
										</fieldset>
										<c:if test="${fn:length(sessionScope.collaborateurs) gt 4}">
											<div id="validation5" class="info"
												style="text-decoration: underline;">Ajouter validation</div>
										</c:if>
									</div>


									<br />

									<div class="validation5"
										style="display: none; position: relative;">
										<fieldset>


											<p>
											<div id="ancienCollaborateur5">
												<label for="listeCollaborateurs5">Valideur 5 </label><br>
												<select name="listeCollaborateurs5"
													id="listeCollaborateurs5">
													<option value="">Choisissez un collaborateur...</option>
													<%-- Boucle sur la map des clients --%>
													<c:forEach items="${ sessionScope.collaborateurs }"
														var="collaborateur">
														<%-- L'expression EL ${collaborateur.value} permet de
cibler l'objet Collaborateur stockÃ© en tant que valeur dans la Map,
et on cible ensuite simplement ses propriÃ©tÃ©s
nom et prenom comme on le ferait avec n'importe quel bean. --%>
														<option value="${ collaborateur.key }">${
collaborateur.value.prenom }
															${ collaborateur.value.nom }</option>
													</c:forEach>
												</select>
											</div>
											</p>

											<br />
											<p>
												<label for="dateValidation5DemandeDAchat">Date
													validation 5 </label> <input type="date"
													id="dateValidation5DemandeDAchat"
													name="dateValidation5DemandeDAchat" value="" size="20"
													maxlength="20" /> <span class="erreur">${form.erreurs['dateValidation5DemandeDAchat']}</span><br />
											</p>

											<br />
										</fieldset>
										<c:if test="${fn:length(sessionScope.collaborateurs) gt 5}">
											<div id="validation6" class="info"
												style="text-decoration: underline;">Ajouter validation</div>
										</c:if>
									</div>


									<br />

									<div class="validation6"
										style="display: none; position: relative;">
										<fieldset>


											<p>
											<div id="ancienCollaborateur6">
												<label for="listeCollaborateurs6">Valideur 6 </label><br>
												<select name="listeCollaborateurs6"
													id="listeCollaborateurs6">
													<option value="">Choisissez un collaborateur...</option>
													<%-- Boucle sur la map des clients --%>
													<c:forEach items="${ sessionScope.collaborateurs }"
														var="collaborateur">
														<%-- L'expression EL ${collaborateur.value} permet de
cibler l'objet Collaborateur stockÃ© en tant que valeur dans la Map,
et on cible ensuite simplement ses propriÃ©tÃ©s
nom et prenom comme on le ferait avec n'importe quel bean. --%>
														<option value="${ collaborateur.key }">${
collaborateur.value.prenom }
															${ collaborateur.value.nom }</option>
													</c:forEach>
												</select>
											</div>
											</p>

											<br />
											<p>
												<label for="dateValidation6DemandeDAchat">Date
													validation 6 </label> <input type="date"
													id="dateValidation6DemandeDAchat"
													name="dateValidation6DemandeDAchat" value="" size="20"
													maxlength="20" /> <span class="erreur">${form.erreurs['dateValidation6DemandeDAchat']}</span><br />
											</p>

											<br />
										</fieldset>

									</div>

									<br />


								</fieldset>

								<br /> <br />
								<p class="info"></p>
								<br />

								<div align="right" class="art-vmenu">
									<input type="submit" id="Valider" value="Valider"
										style="height: 30px; width: 120px; background-color: lightblue; font: bold 14px Arial" />
									<input type="reset" value="Remettre à zéro"
										style="height: 30px; width: 120px; background-color: lightblue; font: bold 14px normal verdana, helvetica, sans-serif" />
								</div>
								<br />

							</form>
						</div>

					</div>


					<script src="<c:url value="/jquery.js"/>"></script>
					<%-- Petite fonction jQuery permettant le remplacement de la première
partie du formulaire par la liste déroulante, au clic sur le bouton radio. --%>
					<script>
						jQuery(document)
								.ready(
										function() {

											$("div#ancienCollaborateur").hide();

											jQuery(
													'input[name=choixNouveauCollaborateur]:radio')
													.click(
															function() {
																$(
																		"div#nouveauCollaborateur")
																		.hide();
																$(
																		"div#ancienCollaborateur")
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

						$(document).ready(function() {
							$('#validation1').click(function() {
								$('.validation1').slideToggle("fast");
							});
						});
						$(document).ready(function() {
							$('#validation2').click(function() {
								$('.validation2').slideToggle("fast");
							});
						});
						$(document).ready(function() {
							$('#validation3').click(function() {
								$('.validation3').slideToggle("fast");
							});
						});
						$(document).ready(function() {
							$('#validation4').click(function() {
								$('.validation4').slideToggle("fast");
							});
						});
						$(document).ready(function() {
							$('#validation5').click(function() {
								$('.validation5').slideToggle("fast");
							});
						});
						$(document).ready(function() {
							$('#validation6').click(function() {
								$('.validation6').slideToggle("fast");
							});
						});
						$('#validation1').click(function(event) {
							event.preventDefault();
							$(this).hide();
						});
						$('#validation2').click(function(event) {
							event.preventDefault();
							$(this).hide();
						});
						$('#validation3').click(function(event) {
							event.preventDefault();
							$(this).hide();
						});
						$('#validation4').click(function(event) {
							event.preventDefault();
							$(this).hide();
						});
						$('#validation5').click(function(event) {
							event.preventDefault();
							$(this).hide();
						});
						$('#validation6').click(function(event) {
							event.preventDefault();
							$(this).hide();
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

	<script src="jquery.js"></script>
	<script src="script.js"></script>
	<script src="script.responsive.js"></script>

	<script type="text/javascript">
		$('#cDDA')
				.submit(
						function() {
							var c = confirm("Etes-vous sûr(e) de vouloir insérer cette demande d'achat dans la base ?");
							return c;
						});
	</script>

</body>
</html>