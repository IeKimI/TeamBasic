
function handleFeedbackClick(alternativeID) {

	if (processGetCompleteChoiceResponse()) {
		alert("This choice is completed.")
		return;

	} else {
		var url = window.location.href;
		var choiceID = url.split('?choice=')[1];
		var teamMemberID = choiceID.split('?teamMemberID=')[1];
		choiceID = choiceID.split('?teamMemberID=')[0];

		var textArea = document.getElementById('feedback' + alternativeID);
		var text = textArea.value;

		var data = {};

		data["text"] = text;
		data["alternativeChoiceID"] = alternativeID;
		data["teamMemberID"] = teamMemberID;

		var js = JSON.stringify(data);
		console.log("JS:" + js);
		var xhr = new XMLHttpRequest();
		xhr.open("POST", createFeedback_url, true);

		// send the collected data as JSON
		xhr.send(js);

		// This will process results and update HTML as appropriate. 
		xhr.onloadend = function() {
			console.log(xhr);
			console.log(xhr.request);
			if (xhr.readyState == XMLHttpRequest.DONE) {
				if (xhr.status == 200) {
					console.log("XHR:" + xhr.responseText);
					processGetFeedbackResponse();
				} else {
					console.log("actual:" + xhr.responseText)
					var js = JSON.parse(xhr.responseText);
					var err = js["response"];
					alert(err);
				}
			} else {
				alert("Can't post the feedbaack");
			}
		};

	}


}