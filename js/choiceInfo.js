function refreshChoiceInfo() {
	var xhr = new XMLHttpRequest();

	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('=')[1];

	xhr.open("GET", getChoice_url + "/" + choiceID, true);
	xhr.send();

	xhr.onloadend = function() {
		if (xhr.readyState == XMHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				processChoiceResponse(xhr.responseText);
			} else {
				processChoiceResponse("N/A");
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


	var description = js["description"];
	var maxnum = js["maxNum"];

	var alternative1 = js["alternatives"][0];
	var alternative2 = js["alternatives"][1];
	var alternative3 = js["alternatives"][2];
	var alternative4 = js["alternatives"][3];
	var alternative5 = js["alternatives"][4];


	var output = "";

	output = output + "\t<div>\n\t<label id = \"desc\">Description: " + description + "</label>\n\t<label id = \"maxNum\">Maximum Number of Participants: " + maxnum + "</label>\n\t<label id = \"alternative1\">Alternative 1: </label>\n\t\n\t</div>"; 

	choiceInfo.innerHTML = output;
}


