function processGetFeedbackResponse() {
	console.log("waiting for the get feedback response");
	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('=')[1];
	choiceID = choiceID.split("?")[0];


	var data = {};
	data["choiceID"] = choiceID;

	var xhr = new XMLHttpRequest();
	xhr.open("GET", getFeedback_url + "/" + choiceID, true);
	xhr.send();

	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			console.log("XHR:" + xhr.responseText);
			processFeedback(xhr.responseText);

		} else {
			processFeedback("N/A");
		}
	};
}

function processFeedback(result) {
	console.log("res:" + result);

	var js = JSON.parse(result);

	console.log(js);


	js = js["feedback"];
	console.log("Feedback: " + js);

	var count = 0;
	var output = "";
	var oldAlternative = 0;
	for (var i in js) {

/**
"feedback": [
    {
      "timeStamp": 1607480419390,
      "text": "  I am creating a feedback",
      "teamMemberID": 135,
      "teamMemberName": "Eri",
      "alternativeChoiceID": 1206,
      "feedbackID": 9
    },
    {
      "timeStamp": 1607488972620,
      "text": "  feedback",
      "teamMemberID": 135,
      "teamMemberName": "Eri",
      "alternativeChoiceID": 1206,
      "feedbackID": 12
    }, */
		var eachFeedback = js[i];
		var timeStamp = eachFeedback["timeStamp"];
		var text = eachFeedback["text"];
		var teamMemberName = eachFeedback["teamMemberName"];
		var alternativeID = eachFeedback["alternativeChoiceID"];
		if (alternativeID !== oldAlternative) {
			output = "";
			oldAlternative = alternativeID;
		}

		var feedback = document.getElementById('feedbackText' + alternativeID);

		var timestmpString = new Date(timeStamp).toLocaleString();
		count = count + 1;

		output = output + "<div class=\"feedback\">\n Feedback: " + text + " by " + teamMemberName + "<br>Time: " + timestmpString + "<br></div>";

		feedback.innerHTML = output;
	}

}

