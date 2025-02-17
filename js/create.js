function loadingChoicePage(response, request) {
	// Can grab any DIV or SPAN HTML element and can then manipulate its
	// contents dynamically via javascript
	console.log("result:" + response);

	/*refreshChoiceList();*/
	var choiceID = JSON.parse(response)["response"];
	console.log(choiceID);
	window.location.href = "https://teambasic.s3.us-east-2.amazonaws.com/html/choice.html" + "?choice=" + choiceID + "?teamMemberID=0";

	console.log("result:" + request);

	/*refreshChoiceList();*/
	var description = JSON.parse(request)["description"];
	console.log(description);
	document.getElementById("choiceDesc").innerHTML = choiceID + description;



}

function processCreateChoiceResponse(result, request) {
	console.log("result:" + result);
	loadingChoicePage(result, request)
}



function handleCreateClick(e) {
	var form = document.createForm;

	var data = {};
	data["description"] = form.description.value;

	var alternatives = [];

	var alternative1 = {};
	var alternative2 = {};
	var alternative3 = {};
	var alternative4 = {};
	var alternative5 = {};

	if (form.alternative1.value === "" && form.alternative2.value === "" && form.alternative3.value === "" && form.alternative4.value === "" && form.alternative5.value === "") {
		alert("No alternative found.");
		return;
	}
	else if (form.alternative1.value != "" && form.alternative2.value === "" && form.alternative3.value === "" && form.alternative4.value === "" && form.alternative5.value === "") {
		alert("Need more than one alternative!");
		return;
	}

	alternative1["description"] = form.alternative1.value;
	alternative2["description"] = form.alternative2.value;
	alternative3["description"] = form.alternative3.value;
	alternative4["description"] = form.alternative4.value;
	alternative5["description"] = form.alternative5.value;

	alternatives = [alternative1, alternative2, alternative3, alternative4, alternative5];
	data["maxNum"] = form.maxNumOfMembers.value;
	data["alternativeChoices"] = alternatives;


	var js = JSON.stringify(data);
	console.log("JS:" + js);
	var xhr = new XMLHttpRequest();
	xhr.open("POST", create_url, true);

	// send the collected data as JSON
	xhr.send(js);

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function() {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				var js = JSON.stringify(data);
				processCreateChoiceResponse(xhr.responseText, js);
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert(err);
			}
		} else {
			processCreateResponse("N/A");
		}
	};

}