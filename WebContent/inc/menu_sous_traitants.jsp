<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="menu">
	<p>
		<a href="<c:url value="/creationSousTraitant"/>">Créer un nouveau
			sous traitant</a>
	</p>
	<br />
	<p>
		<a href="<c:url value="/ExportationExcelSousTraitants"/>">Exporter
			la liste des sous-traitants vers Excel</a>
	</p>

</div>