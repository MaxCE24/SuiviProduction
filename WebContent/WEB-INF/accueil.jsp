<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Accueil</title>

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
						<li><a href="/SuiviProduction/listeFactures">Factures</a></li>
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

						<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
						<br />

						<div align="center">

							<p>
								<font size="4"> Vous êtes connecté(e) avec
									l'adresse : ${sessionScope.sessionUtilisateur.eMail}. <br /> <br />Veuillez
									sélectionner un onglet à partir de la barre en haut.
								</font>
							</p>

						</div>

						<br /> <br /> <br /> <br /> <br /> <br /> <br /> <br /> <br />
						<br /> <br /> <br /> <br /> <br /> <br />

					</div>
				</div>
			</div>

			<footer class="art-footer">
				<p>
					© <a href="http://fr.atos.net/fr-fr/accueil.html" target="_blank"><em
						style="text-decoration: underline;">Atos SE</em></a> 2016 Tous droits réservés
				</p>
			</footer>

		</div>

	</div>

	<script src="jquery.js"></script>
	<script src="script.js"></script>
	<script src="script.responsive.js"></script>

</body>
</html>