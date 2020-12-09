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

	var count = 0;
	var realCount = 0;
	for (var i in js) {


		var eachApproval = js[i];
		var alternativeID = eachApproval["alternativeID"];
		var num = eachApproval["numOfApprovals"];
		var numDis = eachApproval["numOfDisapprovals"];
		var nameList = eachApproval["listOfTeamMembers"];
		var nameListDis = eachApproval["listOfTeamMebersDisapproval"];
		var altDesc = eachApproval["altDescription"];
		count = count + 1;
		var approval = document.getElementById('nameList' + count);
		var approvalNum = document.getElementById('num' + count);
		var disapproval = document.getElementById('nameDisList' + count);
		var disapprovalNum = document.getElementById('numDis' + count);

		var feedback = document.getElementById('feedback' + count);

		if (altDesc != "") {

			console.log(num);

			approval.innerHTML = "Approved by: " + nameList;
			approvalNum.innerHTML = num;

			disapproval.innerHTML = "Disapproved by: " + nameListDis;
			disapprovalNum.innerHTML = numDis;

			feedback.innerHTML = "<label style=\"font-family:verdana\" for=\"feedback\">Feedback:</label><br>\n<textarea type=\"styled\" name=\"feedbackText\" id=\"feedback" + alternativeID + "\" rows=\"4\" cols=\"50\">\n  </textarea>\n  <br><br>   <input type=\"button\" button class = \"buttonSmall buttonBlue\" value=\"Create Feedback\" onClick=\"JavaScript:handleFeedbackClick(" + alternativeID + ")\">\n";

			if (nameList.includes(document.getElementById("message").innerText.split("! ")[1])) {
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
