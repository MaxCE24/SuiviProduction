<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Liste des factures existantes</title>

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

<link type="text/css" rel="stylesheet"
	href="<c:url
value="/themes/blue/style.css"/>" />

<link type="text/css" rel="stylesheet"
	href="<c:url
value="/addons/pager/jquery.tablesorter.pager.css"/>" />

<script src="jquery.js"></script>
<script src="jquery.tablesorter.js"></script>
<script src="./addons/pager/jquery.tablesorter.pager.js"></script>


<script>
	$(document).ready(function() {
		$("#tableFactures").tablesorter();
	});

	$(document).ready(
			function() {
				$("#table_search").keyup(
						function() {
							if ($(this).val() != "") {
								// Show only matching TR, hide rest of them
								$("#tableFactures tbody>tr").hide();
								$(
										"#tableFactures td:contains-ci('"
												+ $(this).val() + "')").parent(
										"tr").show();
							} else {
								$("#tableFactures tbody>tr").show();
							}
						});
			});
	$.extend($.expr[":"],
			{
				"contains-ci" : function(elem, i, match, array) {
					return (elem.textContent || elem.innerText
							|| $(elem).text() || "").toLowerCase().indexOf(
							(match[3] || "").toLowerCase()) >= 0;
				}
			});
</script>

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


						<c:if test="${empty sessionScope.sessionUtilisateur}">
							<jsp:forward page="/WEB-INF/seConnecter.jsp" />
						</c:if>

						<c:import url="/inc/menu_factures.jsp" />
						<div id="corps">
							<c:choose>
								<%-- Si aucune facture n'existe en requete, affichage d'un
message par défaut. --%>
								<c:when test="${ empty sessionScope.factures }">

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
											<font size="4" class="erreur"> Aucune facture
												enregistrée. </font>
										</p>

									</div>

								</c:when>
								<%-- Sinon, affichage du tableau. --%>
								<c:otherwise>
									<input type="text" id="table_search"
										placeholder="Tapez un numéro, une date, un client, etc..."
										value="" />
									<table id="tableFactures" class="tablesorter" border="1"
										cellpadding="5" cellspacing="5" width="100%">
										<thead>
											<tr>
												<th>N° Facture</th>
												<th>Date réception</th>
												<th>Client</th>
												<th>Montant TTC</th>
												<th>Nom SST</th>
												<th>Date remise aux OP</th>
												<th>Date retour OP</th>
												<th>Date remise au TMU</th>
												<th>Date envoi DAF</th>
												<th>Période facturée</th>
												<th>Impr</th>
												<th class="action">Sup</th>
											</tr>
										</thead>
										<%-- Parcours de la Map des factures en requete, et
utilisation de l'objet varStatus. --%>
										<c:forEach items="${ sessionScope.factures }" var="facture"
											varStatus="boucle">
											<%-- Simple test de parité sur l'index de parcours,
pour alterner la couleur de fond de chaque ligne du tableau. --%>
											<tr class="${boucle.index % 2 == 0 ? 'pair' :
'impair'}">
												<%-- Affichage des propriétés du bean Facture,
qui est stocké en tant que valeur de l'entrée courante de la map --%>
												<td><c:out value="${ facture.value.numero
}" /></td>
												<td><c:out value="${ facture.value.dateReception
}" /></td>
												<td><c:out
														value="${ facture.value.sousTraitant.societe.raisonSociale
}" /></td>
												<td><c:out value="${ facture.value.montantTTC
}" /></td>
												<td><c:out
														value="${ facture.value.sousTraitant.prenom
} ${ facture.value.sousTraitant.nom}" /></td>
												<td><c:out value="${ facture.value.dateRemiseAuxOP
}" /></td>
												<td><c:out value="${ facture.value.dateRetourOP
}" /></td>
												<td><c:out value="${ facture.value.dateRemiseAuTMU
}" /></td>
												<td><c:out value="${ facture.value.dateEnvoiDAF
}" /></td>
												<td><c:out value="${ facture.value.periodeFacturee
}" /></td>

												<td class="action"><a target="_blank"
													href="<c:url
value="/ImpressionFacture"><c:param name="idFacture" value="${
facture.key }" />
</c:url>">
														<img src="<c:url
value="/inc/imprimer.png"/>"
														alt="Imprimer" />
												</a></td>


												<%-- Lien vers la servlet de suppression, avec
passage de laréférence de la facture - c'est-à-dire la clé de la Map - en
paramètre grâce à la balise <c:param/>. --%>
												<td class="action"><a
													href="<c:url
value="/suppressionFacture"><c:param name="idFacture" value="${
facture.key }" />
</c:url>">
														<img src="<c:url
value="/inc/supprimer.png"/>"
														alt="Supprimer" />
												</a></td>
											</tr>
										</c:forEach>
									</table>
								</c:otherwise>
							</c:choose>
						</div>

						<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
						<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
						<br /> <br />

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