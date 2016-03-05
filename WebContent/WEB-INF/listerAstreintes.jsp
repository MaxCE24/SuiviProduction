<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Liste des astreintes existantes</title>

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
		$("#tableAstreintes").tablesorter();
	});

	$(document).ready(
			function() {
				$("#table_search").keyup(
						function() {
							if ($(this).val() != "") {
								// Show only matching TR, hide rest of them
								$("#tableAstreintes tbody>tr").hide();
								$(
										"#tableAstreintes td:contains-ci('"
												+ $(this).val() + "')").parent(
										"tr").show();
							} else {
								$("#tableAstreintes tbody>tr").show();
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
						<c:import url="/inc/menu_astreintes.jsp" />
						<div id="corps">
							<c:choose>
								<%-- Si aucune astreinte n'existe en requete, affichage d'un
message par défaut. --%>
								<c:when test="${ empty sessionScope.astreintes }">
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
											<font size="4" class="erreur"> Aucune astreinte
												enregistrée. </font>
										</p>

									</div>

								</c:when>
								<%-- Sinon, affichage du tableau. --%>
								<c:otherwise>

									<input type="text" id="table_search"
										placeholder="Tapez une référence, une date, un nombre, etc..."
										value="" />
									<table id="tableAstreintes" class="tablesorter" border="1"
										cellpadding="5" cellspacing="5" width="100%">
										<thead>
											<tr>
												<th>Réf. Astreintes</th>
												<th>Date Astreinte</th>
												<th>Nbre Heures</th>
												<th>Type Astreinte</th>
												<th>Concerné</th>
												<th class="action">Action</th>
											</tr>
										</thead>
										<%-- Parcours de la Map des astreintes en requete, et
utilisation de l'objet varStatus. --%>
										<c:forEach items="${ sessionScope.astreintes }"
											var="astreinte" varStatus="boucle">
											<%-- Simple test de parité sur l'index de parcours,
pour alterner la couleur de fond de chaque ligne du tableau. --%>
											<tr class="${boucle.index % 2 == 0 ? 'pair' :
'impair'}">
												<%-- Affichage des propriétés du bean Astreinte,
qui est stocké en tant que valeur de l'entrée courante de la map --%>
												<td><c:out value="${ astreinte.value.reference
}" /></td>
												<td><c:out value="${ astreinte.value.date
}" /></td>
												<td><c:out value="${ astreinte.value.nombreHeures
}" /></td>
												<td><c:out value="${ astreinte.value.type
}" /></td>
												<td><c:out
														value="${ astreinte.value.collaborateur.prenom
} ${ astreinte.value.collaborateur.nom
}" /></td>
												<%-- Lien vers la servlet de suppression, avec
passage de laréférence de l'astreinte - c'est-à-dire la clé de la Map - en
paramètre grâce à la balise <c:param/>. --%>
												<td class="action"><a
													href="<c:url
value="/suppressionAstreinte"><c:param name="idAstreinte" value="${
astreinte.key }" />
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