function handleCompleteChoiceClick(alternativeID) {
	
	console.log("button is clicked");
	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('choice=')[1];
	choiceID = choiceID.split('?')[0];

	var teamMemberID = choiceURL.split('teamMemberID=')[1];


	var data = {};
	data["chosenAltID"] = alternativeID;
	data["choiceID"]= choiceID;


	var js = JSON.stringify(data);

	var xhr = new XMLHttpRequest();
	xhr.open("POST", completechoice_url, true);
	xhr.send(js);

	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				completeChoice(alternativeID);
			} else {
				completeChoice("N/A");
			}
		}
	}
}

function completeChoice(alternativeID) { 
	var btn = document.getElementById("complete("+ alternativeID +")"); 
	btn.disabled = true;
}

