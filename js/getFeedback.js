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

	var count = 0;
	for (var i in js) {


		var eachFeedback = js[i];
		var timeStamp = eachFeedback["timeStamp"];
		var text = eachFeedback["text"];
		var teamMemberName = eachFeedback["teamMemberName"];
		var alternativeID = eachFeedback["alternativeChoiceID"];
		
		var feedback = document.getElementById('feedbackText' + alternativeID);

		var timestmpString = new Date(timeStamp).toLocaleString();
		count = count + 1;
		
		

			feedback.innerHTML = feedback.innerHTML + "<div class=\"feedback\">\n  \"Time:" + timestmpString + " Feedback: " + text + " by" + teamMemberName + "<br>\"\n</div>";
		}


	
}

