/**
ApprovalInfo:
    type: "object"
    required:
    - "alternativeID"
    - "altDescription"
    - "participantName"
    - "numOfApprovals"
    properties:
      alternativeID:
        type: "string"
      participantName:
        type: "string"
      numOfApprovals:
        type: "integer"
      altDescription:
        type: "string" */

function getApprovals() {

	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('=')[1];
	if (choiceID.includes("#")) {
		choiceID.split("#")[0];
	}
	var data = {};
	data["choiceID"] = choiceID;


	var xhr = new XMLHttpRequest();
	xhr.open("GET", approvals_url + "/" + choiceID, true);
	xhr.send();

	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				processApprovals(xhr.responseText);
			} else {
				processApprovals("N/A");
			}
		}
	}
}

function processApprovals(result) {
	console.log("res:" + result);

	var js = JSON.parse(result);

	var approval = document.getElementById('approvals');

	console.log(js);
	console.log(approval);


	js = js["list"];
	var output = "";
	for (var i in js) {
		var eachApproval = js[i];
		var num = eachApproval["numOfApprovals"];
		var nameList = eachApproval["listOfTeamMembers"];
		console.log(num);
		output = output + "\t<div>\n\t<label id = \"num\">NumOfApprovals: " + num + "</label><br>\n\t<label id = \"nameList\">List of members: " + nameList + "</label><br>";
	}
	approval.innerHTML = output;
}
