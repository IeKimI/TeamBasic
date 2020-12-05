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
	choiceID = choiceID.split("?")[0];
	
	
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
	var realCount = 0;
	for (var i in js) {

		var eachApproval = js[i];
		var num = eachApproval["numOfApprovals"];
		var numDis = eachApproval["numOfDisapprovals"];
		var nameList = eachApproval["listOfTeamMembers"];
		var nameListDis = eachApproval["listOfTeamMebersDisapproval"];
		var altDesc = eachApproval["altDescription"];
		count = count + 1;
		var approval = document.getElementById('approvals' + count);
		

		if (altDesc != "") {
			console.log(num);
			output = "";
			output = output + "<label id = \"num"+ count +"\">NumOfApprovals: " + num + "</label><br>\n\t<label id = \"nameList"+ count +"\">List of members: " + nameList + "</label><br><br><label id = \"numDis"+ count +"\">NumOfDisapprovals: " + numDis + "</label><br>\n\t<label id = \"nameDisList"+ count +"\">List of members: " + nameListDis + "</label><br>";
			approval.innerHTML = output;

		}
		
		if(nameList.includes(document.getElementById("message").innerText.split("! ")[1])) {
			setImageToColoredApproval(realCount);
		} else {
			setImageToBWApproval(realCount);
		}
		realCount = realCount + 1;
		if (nameListDis.includes(document.getElementById("message").innerText.split("! ")[1])) {
			setImageColoredDisapproval(realCount);
		}
		else {
			setImageBWDisapproval(realCount);
		}
		realCount = realCount + 1;
	}
}

function setImageToColoredApproval(realCount) {
	var image = document.images[realCount];
		image.src = "check-mark.png";
	
}

function setImageToBWApproval(realCount) {
	var image = document.images[realCount];
		image.src = "check-mark_b&w.png";
	
}

function setImageColoredDisapproval(realCount) {
	var image = document.images[realCount];
		image.src = "cancel_color.png";

}
function setImageBWDisapproval(realCount) {
	var image = document.images[realCount];
		image.src = "cancel.png";

}
