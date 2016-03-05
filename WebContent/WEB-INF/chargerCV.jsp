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
						<li><a href="#" class="active">Suivi production</a>
							<ul class="active">
								<li><a href="/SuiviProduction/listeAstreintes">Astreintes</a></li>
								<li><a href="/SuiviProduction/listeDemandesDAchat">Demandes
										d'Achat</a></li>
								<li><a href="/SuiviProduction/listeFactures">Factures</a></li>
								<li><a href="/SuiviProduction/listeSousTraitants"
									class="active">Gestion SST</a></li>
							</ul></li>
						<li><a href="#">Gestion d'Utilisateurs</a></li>

						<li><a href="/SuiviProduction/Deconnexion">Déconnexion </a></li>
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


						<h3>CV Upload:</h3>
						Merci de joindre le CV du sous traitant afin de terminer
						l'enregistrement: <br />
						<form id="cCV" action="UploadServlet" method="post"
							enctype="multipart/form-data">
							<input type="file" name="cVSousTraitant" size="50" /> <span
								class="erreur">${erreur}</span> <br />
							<input type="submit" value="Valider" />
						</form>

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
		$('#cCV')
				.submit(
						function() {
							var c = confirm("Etes-vous sûr(e) de vouloir insérer ce sous-traitant dans la base ?");
							return c;
						});
	</script>

</body>
</html>