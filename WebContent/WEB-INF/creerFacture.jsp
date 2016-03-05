<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Création d'une facture</title>
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
						<li><a href="/SuiviProduction/listeDemandesDAchat">Demandes
								d'Achat</a></li>
						<li><a href="/SuiviProduction/listeFactures" class="active">Factures</a></li>
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
												<li><a href="/SuiviProduction/listeDemandesDAchat">Demandes
														d'Achat</a></li>
												<li><a href="/SuiviProduction/listeFactures"
													class="active">Factures</a></li>
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

						<c:choose>
							<c:when test="${ empty sessionScope.sousTraitants }">

								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />

								<div align="center">

									<p>
										<font size="4" class="erreur"> Aucun sous-traitant à
											facturer. </font>
									</p>

								</div>

								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
								<br />
							</c:when>
							<c:otherwise>

								<div>
									<form id="cF" method="post" action="<c:url
value="/creationFacture"/>">
										<c:set var="sousTraitant" value="${ facture.sousTraitant }"
											scope="request" />
										<fieldset>
											<legend>Informations facture</legend>
											<p>
												<label for="numeroFacture">N° Facture <span
													class="requis">*</span></label> <input type="number" step="1"
													min="0" id="numeroFacture" name="numeroFacture"
													value="<c:out value="${facture.numero}"/>" size="20"
													maxlength="20" /> <span class="erreur">${form.erreurs['numeroFacture']}</span>
												<br />
											</p>
											<br>
											<p>
												<label for="dateReceptionFacture">Date réception <span
													class="requis">*</span></label> <input type="date"
													id="dateReceptionFacture" name="dateReceptionFacture"
													size="20" maxlength="20" /> <span class="erreur">${form.erreurs['dateReceptionFacture']}</span>
												<br />
											</p>
											<br>
											<p>
											<div id="ancienSousTraitant">
												<label for="sousTraitantFacture">Nom SST <span
													class="requis">*</span></label><br> <select
													name="sousTraitantFacture" id="sousTraitantFacture">
													<option value="">Choisissez un sous-traitant...</option>
													<c:forEach items="${ sessionScope.sousTraitants }"
														var="sousTraitant">

														<option value="${ sousTraitant.key }">${sousTraitant.value.prenom }
															${ sousTraitant.value.nom }</option>
													</c:forEach>
												</select><span class="erreur">${form.erreurs['sousTraitantFacture']}</span><br />
											</div>



											<br>
											<p>
												<label for="montantTTCFacture">Montant TTC <span
													class="requis">*</span></label> <input type="number" step="0.01"
													min="0" id="montantTTCFacture" name="montantTTCFacture"
													value="<c:out value="${facture.montantTTC}"/>" size="20"
													maxlength="20" /> <span class="erreur">${form.erreurs['montantTTCFacture']}</span>
												<br />
											</p>

											<br>
											<p>
												<label for="dateRemiseAuxOPFacture">Date remise aux
													OP <span class="requis">*</span>
												</label> <input type="date" id="dateRemiseAuxOPFacture"
													name="dateRemiseAuxOPFacture"
													value="<c:out value="${facture.dateRemiseAuxOP}"/>"
													size="20" maxlength="20" /> <span class="erreur">${form.erreurs['dateRemiseAuxOPFacture']}</span>
												<br />
											</p>
											<br>
											<p>
												<label for="dateRetourOPFacture">Date retour OP <span
													class="requis">*</span>
												</label> <input type="date" id="dateRetourOPFacture"
													name="dateRetourOPFacture"
													value="<c:out value="${facture.dateRetourOP}"/>" size="20"
													maxlength="20" /> <span class="erreur">${form.erreurs['dateRetourOPFacture']}</span>
												<br />
											</p>
											<br>
											<p>
												<label for="dateRemiseAuTMUFacture">Date remise au
													TMU <span class="requis">*</span>
												</label> <input type="date" id="dateRemiseAuTMUFacture"
													name="dateRemiseAuTMUFacture"
													value="<c:out value="${facture.dateRemiseAuTMU}"/>"
													size="20" maxlength="20" /> <span class="erreur">${form.erreurs['dateRemiseAuTMUFacture']}</span>
												<br />
											</p>
											<br>
											<p>
												<label for="dateEnvoiDAFFacture">Date envoi DAF <span
													class="requis">*</span>
												</label> <input type="date" id="dateEnvoiDAFFacture"
													name="dateEnvoiDAFFacture"
													value="<c:out value="${facture.dateEnvoiDAF}"/>" size="20"
													maxlength="20" /> <span class="erreur">${form.erreurs['dateEnvoiDAFFacture']}</span>
												<br />
											</p>
											<br>
											<p>
												<label for="periodeFactureeFacture">Période facturée
													<span class="requis">*</span>
												</label> <input type="month" id="periodeFactureeFacture"
													name="periodeFactureeFacture"
													value="<c:out value="${facture.periodeFacturee}"/>"
													size="20" maxlength="20" /> <span class="erreur">${form.erreurs['periodeFactureeFacture']}</span>
												<br />
											</p>
											<br>
											<p>
												<label for="notesFacture">Notes <span class="requis">*</span>
												</label>
												<textarea rows="4" cols="50" id="notesFacture"
													name="notesFacture" style="resize: vertical;"> </textarea>
												<span class="erreur">${form.erreurs['notesFacture']}</span>
												<br />
											</p>
											<br>
										</fieldset>

										<br />
										<p class="info"></p>
										<div align="right" class="art-vmenu">
											<input type="submit" id="Valider" value="Valider"
												style="height: 30px; width: 120px; background-color: lightblue; font: bold 14px Arial" />
											<input type="reset" value="Remettre à zéro"
												style="height: 30px; width: 120px; background-color: lightblue; font: bold 14px normal verdana, helvetica, sans-serif" />
										</div>
										<br>
									</form>
								</div>
							</c:otherwise>
						</c:choose>

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
		$('#cF')
				.submit(
						function() {
							var c = confirm("Etes-vous sûr(e) de vouloir insérer cette facture dans la base ?");
							return c;
						});
	</script>

</body>
</html>