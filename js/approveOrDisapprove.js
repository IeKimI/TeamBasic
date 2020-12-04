function getApprovals() {
	/**
	https://teambasic.s3.us-east-2.amazonaws.com/html/choice.html?choice=1395110188?teamMemberID=72 */
	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('choice=')[1];
	choiceID = choiceID.split('?')[0];

	var teamMemberID = choiceURL.split('teamMemberID=')[1];


	var data = {};
	data["choiceID"] = choiceID;


	var js = JSON.stringify(data);

	var xhr = new XMLHttpRequest();
	xhr.open("POST", approve_url + "//" + teamMemberID + true);
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

function processApprovals(result) {
	console.log("res:" + result);

	var js = JSON.parse(result);

	var alternatives = document.getElementById('alternatives');
	console.log(js);

	js = js["alternatives"];
	console.log(js);
	var output = "";
	var count = 0;
	var realCount = 0;
	var buttons = "";

	for (var i in js) {
		var alternative = js[i];
		count = count + 1;
		realCount = count - 1;
		buttons = "<a onClick=\"changeImage(" + realCount.toString() + ")\"\n>\t<img value=" + realCount.toString() + " src=\"check-mark_b&w.png\" width=\"28\" height=\"28\" border=\"0\" alt=\"javascript button\">\n</a>"
		let alternativeDesc = alternative["description"];
		console.log(alternativeDesc)
		if (alternativeDesc != "") {
			var isLoggedIn = window.location.href;
			isLoggedIn = isLoggedIn.split("teamMemberID=")[1];
			if (isLoggedIn != "0") {
				output = output + "<h4> Alternative " + count + ": </h4><label>" + alternativeDesc + "</label> " + buttons + "<br>" + "<div id=\"approvals" + count + "\"></div>";
			}
			else {
				output = output + "<h4> Alternative " + count + ": </h4><label>" + alternativeDesc + "</label> <br>" + "<div id=\"approvals" + count + "\"></div>";
			}
			console.log(output);
		}


	}
	alternatives.innerHTML = output;
}

function changeImage(realCount) {
	var image = document.images[realCount];
	if (image.src.match("check-mark_b&w.png")) {
		image.src = "check-mark.png";
	}
	else {
		image.src = "check-mark_b&w.png";
	}
}
