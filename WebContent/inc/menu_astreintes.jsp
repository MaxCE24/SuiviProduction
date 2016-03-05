<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="menu">
	<p>
		<a href="<c:url value="/creationAstreinte"/>">Créer une nouvelle
			astreinte</a>
	</p>
	<br />
	<p>
		<a href="<c:url value="/ExportationExcelAstreintes"/>">Exporter la
			liste des astreintes vers Excel</a>
	</p>

</div>