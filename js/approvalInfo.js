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

	console.log(js);
	console.log(approval);


	js = js["list"];
	var output = "";
	var count = 0;
	for (var i in js) {

		var eachApproval = js[i];
		var num = eachApproval["numOfApprovals"];
		var nameList = eachApproval["listOfTeamMembers"];
		var altDesc = eachApproval["altDescription"];
		count = count + 1;
		var approval = document.getElementById('approvals' + count);

		if (altDesc != "") {
			console.log(num);
			output = "";
			output = output + "<label id = \"num"+ count +"\">NumOfApprovals: " + num + "</label><br>\n\t<label id = \"nameList"+ count +"\">List of members: " + nameList + "</label><br>";
			approval.innerHTML = output;

		}


	}
}
