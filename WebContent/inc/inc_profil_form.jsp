<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<p>
	<label for="libelleProfil">Libellé profil<span class="requis">*</span></label>
	<input type="text" id="libelleProfil" name="libelleProfil"
		value="" size="30" maxlength="30" /> <span class="erreur">${form.erreurs['libelleProfil']}</span>
	<br />
</p>
<br />
<br />