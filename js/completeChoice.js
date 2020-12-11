function handleCompleteChoiceClick(alternativeID) {
	console.log("button is clicked");

	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('choice=')[1];
	choiceID = choiceID.split('?')[0];

	var teamMemberID = choiceURL.split('teamMemberID=')[1];


	var data = {};
	data["chosenAltID"] = alternativeID;
	data["choiceID"] = choiceID;


	var js = JSON.stringify(data);

	var xhr = new XMLHttpRequest();
	xhr.open("POST", completechoice_url, true);
	xhr.send(js);

	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				if (JSON.parse(xhr.responseText)["statusCode"] != 200) {
					alert("Rejected! " + JSON.parse(xhr.responseText)["error"]);
					return;
				}
				processGetCompleteChoiceResponse();
			} else {
				processGetCompleteChoiceResponse("N/A");
			}
		}
	}


}

function processGetCompleteChoiceResponse() {


	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('choice=')[1];
	choiceID = choiceID.split('?')[0];

	var data = {};
	data["choiceID"] = choiceID;



	var xhr = new XMLHttpRequest();
	xhr.open("GET", getComplete_url + "/" + choiceID, true);
	xhr.send();

	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);

				if (JSON.parse(xhr.responseText)["isComplete"] == true) {
					var chosenDesc = JSON.parse(xhr.responseText)["chosenDesc"]

					var choiceInfo = document.getElementById('choiceInfo');
					choiceInfo.innerHTML = choiceInfo.innerHTML + "<p class=\"chosenAlt\" style=\"font-family:verdana; font-size: 18px\" class=\"chosenDesc\">The choice is complete. The chosen alternative is: " + chosenDesc + " </p>";
					var div = document.getElementsByTagName('div');
					var input = document.getElementsByTagName('input');
					var a = document.getElementsByTagName('a');
					var label = document.getElementsByTagName('label');
					for (var i = 0; i < div.length; i++) {
						div[i].setAttribute('disabled', true);
					}
					for (var i = 0; i < input.length; i++) {
						input[i].onclick = function() { alert("The choice is completed."); };
					}
					for (var i = 0; i < a.length; i++) {
						a[i].setAttribute('disabled', true);
						a[i].onclick = function() { alert("The choice is completed."); };
					}
					for (var i = 0; i < label.length; i++) {
						label[i].setAttribute('disabled', true);
					}
					var textarea = document.getElementsByTagName('textarea');
					for (var i = 0; i < textarea.length; i++) {
						textarea[i].setAttribute('disabled', true);
					}
					return true;
					/*var btn = document.querySelectorAll('button');
var input = document.querySelectorAll('input');
btn.disabled = true;
input.disabled = true;*/

					/*					completeChoice(alternativeID);
*/
				}
			} else {
				return false;
			}
		}
	}
}

function completeChoice(alternativeID) {
	var btn = document.querySelector('button');
	var input = document.querySelector('input');
	btn.style.display = "none"
	input.style.display = "none"


	console.log("button is clicked");


}

