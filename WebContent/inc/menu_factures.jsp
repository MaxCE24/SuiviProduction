<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="menu">
	<p>
		<a href="<c:url value="/creationFacture"/>">Créer une nouvelle
			facture</a>
	</p>
	<br />
	<p>
		<a href="<c:url value="/ExportationExcelFactures"/>">Exporter la
			liste des factures vers Excel</a>
	</p>

</div>