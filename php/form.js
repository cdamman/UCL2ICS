function valider(frm){
	if(frm.elements['codes'].value == "") {
		alert("Veuillez indiquer au moins un code cours ou un lien ADE");
		frm.elements['codes'].focus();
		return false;
	} else if(frm.elements['semaines'].value == "") {
		alert("Veuillez indiquer au moins une semaine");
		frm.elements['semaines'].focus();
		return false;
	} else if(frm.elements['projet'].value == "") {
		alert("Veuillez indiquer l'ID du projet");
		frm.elements['projet'].focus();
		return false;
	} else {
		return true;
	}
}