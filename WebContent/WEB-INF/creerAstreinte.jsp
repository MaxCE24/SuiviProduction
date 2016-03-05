<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Création d'une astreinte</title>

<meta name="viewport"
	content="initial-scale = 1.0, maximum-scale = 1.0, user-scalable = no, width = device-width">

<script src="jquery.js"></script>


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
						<li><a href="/SuiviProduction/listeAstreintes" class="active">Astreintes</a></li>
						<li><a href="/SuiviProduction/listeDemandesDAchat">Demandes
								d'Achat</a></li>
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
												<li><a href="/SuiviProduction/listeAstreintes"
													class="active">Astreintes</a></li>
												<li><a href="/SuiviProduction/listeDemandesDAchat">Demandes
														d'Achat</a></li>
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
							<form id="cA" method="post"
								action="<c:url
value="/creationAstreinte"/>">
								<%-- Petite astuce ici : placer le collaborateur accessible
via
l'EL ${ astreinte.collaborateur } dans une variable
"collaborateur" de
portée request, pour que le code du fragment
fonctionne
à la fois depuis le formulaire de création d'un
collaborateur
et depuis celui de création d'une astreinte. --%>
								<c:set var="collaborateur" value="${ astreinte.collaborateur }"
									scope="request" />
								<fieldset>
									<legend>Informations astreinte</legend>

									<p>
										<label for="referenceAstreinte">Réf. Astreintes <span
											class="requis">*</span></label> <input type="text"
											id="referenceAstreinte" name="referenceAstreinte"
											value="${astreinte.reference}" size="20" maxlength="20" /> <span
											class="erreur">${form.erreurs['referenceAstreinte']}</span> <br />
									</p>
									<br />

									<fieldset>
										<legend>Informations collaborateur</legend>

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
										<label for="dateAstreinte">Date Astreinte <span
											class="requis">*</span>
										</label> <input type="date" id="dateAstreinte" name="dateAstreinte"
											value="" size="20" maxlength="20" /><span class="erreur">${form.erreurs['dateAstreinte']}</span>
										<br />
									</p>
									<br />
									<p>
										<label for="nombreHeuresAstreinte">Nbre Heures <span
											class="requis">*</span>
										</label> <input type="number" step="0.5" min="0.5"
											id="nombreHeuresAstreinte" name="nombreHeuresAstreinte"
											value="" size="20" maxlength="20" /> <span class="erreur">${form.erreurs['nombreHeuresAstreinte']}</span>
										<br />
									</p>
									<br /> <br />
									<p>
										<label for="typeAstreinte">Type Astreinte <span
											class="requis">*</span>
										</label> <select name="typeAstreinte" id="typeAstreinte">
											<option value="${astreinte.type}">Choisissez un
												type...</option>


											<option value="Sur site">Sur site</option>
											<option value="Téléphonique">Téléphonique</option>

										</select><br> <br> <span class="erreur">${form.erreurs['typeAstreinte']}</span>
										<br />
									</p>


									<br />

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
								<br />

							</form>
						</div>

						<%-- Petite fonction jQuery permettant le remplacement de la première
partie du formulaire par la liste déroulante, au clic sur le bouton radio. --%>
						<script>
							jQuery(document)
									.ready(
											function() {

												$("div#ancienCollaborateur")
														.hide();

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

	<script type="text/javascript">
		$('#cA')
				.submit(
						function() {
							var c = confirm("Etes-vous sûr(e) de vouloir insérer cette astreinte dans la base ?");
							return c;
						});
	</script>

</body>
</html>