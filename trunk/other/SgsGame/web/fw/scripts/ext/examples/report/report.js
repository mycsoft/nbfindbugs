function saveAsExcel(methodStr) {
	_saveAsExcel(methodStr.toString());
}

function _saveAsExcel(methodStr) {
	var temp = methodStr.substring(33);
	temp = temp.substring(0, temp.length - 1);
	temp = temp.replace(/%3D/g, "=");
	temp = temp.replace(/%26/g, "&");
	temp = temp.replace(/%25/g, "%");
	temp = temp.replace("action=4", "action=3");
	temp = temp.replace("_blank", "report1_saveAs_frame");
	var rpStart = temp.indexOf("&url=");
	var rpEnd = temp.indexOf("&file=");
	var tempBefor = temp.substring(1, rpStart);
	var tempAfter = temp.substring(rpEnd);
	temp = tempBefor + tempAfter;
	eval(temp);
}