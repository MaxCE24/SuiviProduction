<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<p>
	<label for="raisonSocialeSociete">Raison sociale<span
		class="requis">*</span></label> <input type="text" id="raisonSocialeSociete"
		name="raisonSocialeSociete" value="" size="30" maxlength="30" /> <span
		class="erreur">${form.erreurs['raisonSocialeSociete']}</span> <br />
</p>
<br />
<br />