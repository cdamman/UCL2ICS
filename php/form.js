function valider(frm){
	if(frm.elements['codes'].value == "") {
		alert("Veuillez indiquer au moins un code cours ou un lien ADE");
		return false;
	} else if(frm.elements['semaines'].value == "") {
		alert("Veuillez indiquer au moins une semaine");
		return false;
	} else if(frm.elements['projet'].value == "") {
		alert("Veuillez indiquer l'ID du projet");
		return false;
	} else {
		return true;
	}
}