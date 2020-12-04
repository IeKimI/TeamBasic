function approveOrDisapprove(whichToFlip, altID) {
	
	/**
	https://teambasic.s3.us-east-2.amazonaws.com/html/choice.html?choice=1395110188?teamMemberID=72 */
	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('choice=')[1];
	choiceID = choiceID.split('?')[0];

	var teamMemberID = choiceURL.split('teamMemberID=')[1];


	var data = {};
	data["whichToFlip"] = whichToFlip;
	data["alternativeID"] = altID;
	
	var js = JSON.stringify(data);

	var xhr = new XMLHttpRequest();
	xhr.open("POST", approve_url + "//" + teamMemberID, true);
	xhr.send(js);

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

function changeImage(realCount, alternativeID) {
	var image = document.images[realCount];
	if (image.src.match("check-mark_b&w.png")) {
		image.src = "check-mark.png";
		approveOrDisapprove(true, alternativeID)
	}
	else {
		image.src = "check-mark_b&w.png";
		approveOrDisapprove(true, alternativeID)

	}
}
