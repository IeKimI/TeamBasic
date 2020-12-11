function approveOrDisapprove(whichToFlip, altID) {

	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('choice=')[1];
	choiceID = choiceID.split('?')[0];

	var teamMemberID = choiceURL.split('teamMemberID=')[1];


	var data = {};
	data["whichToFlip"] = whichToFlip;
	console.log(whichToFlip)
	data["alternativeID"] = altID;
	console.log(altID)

	var js = JSON.stringify(data);

	var xhr = new XMLHttpRequest();
	xhr.open("POST", approve_url + "/" + choiceID + "/" + teamMemberID, true);
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.send(js);

	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				if (JSON.parse(xhr.responseText)["httpCode"] != 200) {
					alert("Rejected! " + JSON.parse(xhr.responseText)["response"]);
					return;
				}
				getApprovals();
				processGetFeedbackResponse();
			} else {
				processApprovals("N/A");
			}
		}


	}
	/**
	https://teambasic.s3.us-east-2.amazonaws.com/html/choice.html?choice=1395110188?teamMemberID=72 */

}

function changeImage(alternativeID) {

	approveOrDisapprove(true, alternativeID);

}

function changeImageDisapproval(alternativeID) {


	approveOrDisapprove(false, alternativeID);


}
