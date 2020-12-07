/**
 * Refresh constant list from server
 *
 *    GET list_url
 *    RESPONSE  list of [name, value, system] constants 
 */
function handleDeleteClick(e) {
	var data = {};

	var form = document.deleteForm;
	var nDaysOld = form.nDays.value;
	console.log(nDaysOld);

	data["nDaysOld"] = nDaysOld;
	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", delete_url, true);
	xhr.send(js);

	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log("XHR:" + xhr.responseText);
			processListResponse(xhr.responseText);

		} else {
			processListResponse("N/A");
		}
	};
}

