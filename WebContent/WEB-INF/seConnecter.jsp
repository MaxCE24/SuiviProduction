<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<title>Connexion</title>

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
value="/inc/form.css"/>" />
</head>
<body>

	<div id="art-main">
		<div class="art-sheet clearfix">
			<header class="art-header">

				<nav class="art-nav">
					<ul class="art-hmenu">
						<li><a href="#"></a>
							<ul>
								<li><a href="#"></a></li>
								<li><a href="#"></a></li>
								<li><a href="#"></a></li>
								<li><a href="#"></a></li>
							</ul></li>
						<li><a href="#"></a></li>
					</ul>
				</nav>

			</header>
			<div class="art-layout-wrapper">
				<div class="art-content-layout">
					<div class="art-content-layout-row">

						<c:if test="${!empty sessionScope.sessionUtilisateur}">
							<jsp:forward page="/WEB-INF/accueil.jsp" />
							
						</c:if>

						<br /> <br /> <br /> <br /> <br />

						<div align="center">

							<form method="post" action="<c:url value="/Connexion" />">
								<table align="center">
									<tr></tr>
									<td>
										<fieldset>
											<legend>Connexion</legend>
											<br />
											<p>Bonjour, vous pouvez vous connecter via ce formulaire.</p>
											<br /> <br />

											<p>
												<label for="eMail">Adresse email <span
													class="requis">*</span></label> <input type="email" id="eMail"
													name="eMail" value="" size="20" maxlength="60" /> <br />
												<span class="erreur">${form.erreurs['eMail']}</span> <br />
											</p>
											<br />
											<p>
												<label for="motDePasse">Mot de passe <span
													class="requis">*</span></label> <input type="password"
													id="motDePasse" name="motDePasse" value="" size="20"
													maxlength="20" /> <br /> <span class="erreur">${form.erreurs['motDePasse']}</span>
												<br />
											</p>
											<br /> <br />
											<div align="right">
												<input type="submit" value="Connexion" class="sansLabel"
													style="height: 30px; width: 120px; background-color: lightblue; font: bold 14px normal verdana, helvetica, sans-serif" />
											</div>
											<br />
											<p class="${empty form.erreurs ? 'succes' :
'erreur'}">${form.resultat}</p>
											<%-- Vérification de la présence d'un objet
utilisateur en session --%>


										</fieldset>
									</td>
								</table>
							</form>

						</div>

						<br /> <br /> <br /> <br /> <br />

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


</body>
</html>