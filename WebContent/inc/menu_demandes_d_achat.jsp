<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="menu">
	<p>
		<a href="<c:url value="/creationDemandeDAchat"/>">Créer une
			nouvelle demande d'achat</a>
	</p>
	
	<br />
	<p>
		<a href="<c:url value="/ExportationExcelDemandesDAchat"/>">Exporter la
			liste des demandes d'achat vers Excel</a>
	</p>

</div>