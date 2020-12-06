function refreshChoiceInfo() {
	

	var choiceURL = window.location.href;
	var wholeURL = choiceURL.split('=')[1];
	var choiceID = wholeURL.split('?')[0];
	

	var data = {};
	data["choiceID"] = choiceID;
	console.log(choiceID);
	
	var js = JSON.stringify(data);
	console.log(js);
	
	choiceID_display.innerHTML = "<label style=\"font-family:verdana\" id = \"choiceID\">ChoiceID: " + choiceID + "</label><br>";
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
	var url = window.location.href;
	url = url.split('teamMemberID=')[0];
	url = url + "teamMemberID=0";

	var output = "";

	output = output + "\t<div>\n\t<label style=\"font-family:verdana\" id = \"desc\">Description: " + description + "</label><br>\n\t<label style=\"font-family:verdana\" id = \"maxNum\">Maximum Number of Participants: " + maxnum + "</label><br>" + "<label style=\"font-family:verdana\" id = \"url\"> Share this URL! " + url + "</label><br>" ; 

	choiceInfo.innerHTML = output;
}



