<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<p>
	<label for="nomCollaborateur">Nom <span class="requis">*</span></label>
	<input type="text" id="nomCollaborateur" name="nomCollaborateur"
		value="" size="30" maxlength="30" /> <span class="erreur">${form.erreurs['nomCollaborateur']}</span>
	<br />
</p>
<br />
<p>
	<label for="prenomCollaborateur">Prénom <span class="requis">*</span></label>
	<input type="text" id="prenomCollaborateur" name="prenomCollaborateur"
		value="" size="30" maxlength="30" /> <span class="erreur">${form.erreurs['prenomCollaborateur']}</span>
	<br />
</p>
<br />