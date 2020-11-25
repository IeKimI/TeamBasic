function refreshChoiceInfo() {
	

	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('=')[1];

	var data = {};
	data["choiceID"] = choiceID;
	console.log(choiceID);
	
	var js = JSON.stringify(data);
	console.log(js);
	
	var xhr = new XMLHttpRequest();
	
	xhr.open("GET", getChoice_url + "/" + choiceID, true);
	xhr.send(js);

	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				processChoiceResponse(xhr.responseText);
			} else {
				processChoiceResponse("N/A");
			}
		}

	}
	getAlternatives(choiceID);
	
}
	
function getAlternatives(choiceID){
	
		var data = {};
	data["choiceID"] = choiceID;
	
	var js = JSON.stringify(data);
	
	var xhr = new XMLHttpRequest();
	xhr.open("GET", alternative_url + "/" + choiceID, true);
	xhr.send(js);

	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				processAlternatives(xhr.responseText);
			} else {
				processAlternatives("N/A");
			}
		}
	}
}

function processChoiceResponse(result) {
	console.log("res:" + result);
	// Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
	var js = JSON.parse(result);
	var choiceInfo = document.getElementById('choiceInfo');
	console.log(js);

	js = js["choice"];
	var description = js["description"];
	var maxnum = js["maxNumOfTeamMembers"];


	var output = "";

	output = output + "\t<div>\n\t<label id = \"desc\">Description: " + description + "</label>\n\t<label id = \"maxNum\">Maximum Number of Participants: " + maxnum + "</label>\n\t<label id = \"alternative1\">Alternative 1: </label>\n\t\n\t</div>"; 

	choiceInfo.innerHTML = output;
}

function processAlternatives(result) {
	console.log("res:" + result);
	
	var js = JSON.parse(result);
	
	var alternatives = document.getElementById('alternatives');
	console.log(js);
	
	js = js["listOfAlternatives"];
	
	var i, output = ""
	var count = 0;
	
	for (i in js) {
		var alternative = js[i]
		count  = count + 1
		
		let alternativeDesc = alternative["alternativeChoices"];
		
		ouput = output + "<h4> Alternative " + count + ": " + alternativeDesc +"</h4>"
	}
	alternatives.innerHTML = output
}

