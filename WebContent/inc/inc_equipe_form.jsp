<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<p>
	<label for="nomEquipe">Nom équipe <span class="requis">*</span></label> <input
		type="text" id="nomEquipe" name="nomEquipe" value="" size="30"
		maxlength="30" /> <span class="erreur">${form.erreurs['nomEquipe']}</span>
	<br />
</p>
<br />
<br />