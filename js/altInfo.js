function getAlternatives() {

	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('=')[1];
	if (choiceID.includes("#")) {
		choiceID.split("#")[0];
	}

	var data = {};
	data["choiceID"] = choiceID;

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

function processAlternatives(result) {
	console.log("res:" + result);

	var js = JSON.parse(result);

	var alternatives = document.getElementById('alternatives');
	console.log(js);

	js = js["alternatives"];
	console.log(js);
	var output = "";
	var count = 0; 
	var realCount = 0;
	var approvalButtons = "";
	var disapprovalButtons = "";

	for (var i in js) {
		var alternative = js[i];
		var alternativeID = alternative["alternativeID"];
		count = count + 1;

		approvalButtons = "<br><br><a onclick=\"changeImage(" + alternativeID + ")\"\n>\t<img value=" + realCount.toString() + " src=\"check-mark_b&w.png\" width=\"28\" height=\"28\" border=\"0\" alt=\"javascript button\">\n</a>";
		realCount = realCount + 1;
		disapprovalButtons = "<a onclick=\"changeImageDisapproval(" + alternativeID + ")\"\n>\t<img value=" + realCount.toString() + " src=\"cancel.png\" width=\"28\" height=\"28\" border=\"0\" alt=\"javascript button\">\n</a>";
		realCount = realCount + 1;
		let alternativeDesc = alternative["description"];
		console.log(alternativeDesc)
		if (alternativeDesc != "") {
			var isLoggedIn = window.location.href;
			isLoggedIn = isLoggedIn.split("teamMemberID=")[1];
			if (isLoggedIn != "0") {
				output = output + "<br><div id = alternative" + count + " class = \"myDiv\"><input id=complete(" + alternativeID + ") type=\"button\" button class = \"buttonSmall buttonBlue\" value=\"Complete Choice\" onclick=\"JavaScript:handleCompleteChoiceClick(" + alternativeID + ")\"> <h4 style=\"font-family:verdana\"> Alternative " + count + ": </h4><label style=\"font-family:verdana; padding: 5px; font-size:20px\">" + "	" + alternativeDesc + "</label> " + "<div id=\"approvals" + count + "\">" + approvalButtons + "<label style=\"font-family:verdana; padding: 5px\" id = \"nameList" + count + "\">Loading...</label><br><label style=\"font-family:verdana; padding: 8px\" id = \"num" + count + "\"> " + "</label><br><br>" + "</div>" + "<div id=\"disapprovals" + count + "\">" + disapprovalButtons + "<label style=\"font-family:verdana; padding: 5px\" id = \"nameDisList" + count + "\">Loading... " + "</label><br><label style=\"font-family:verdana; padding: 8px\" id = \"numDis" + count + "\"> " + "</label><br><br>" + "</div>" + "<div id=\"feedback" + count + "\"></div></div><br>";
			}
			else {
				output = output + "<h4 style=\"font-family:verdana\"> Alternative " + count + ": </h4><label style=\"font-family:verdana; padding: 5px; font-size:20px\">" + "	" + alternativeDesc + "</label> <br>" + "<div id=\"approvals" + count + "\"></div>";
			}
			console.log(output);
		}


	}
	alternatives.innerHTML = output;
}
