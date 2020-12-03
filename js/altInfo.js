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
	var buttons = "";

	for (var i in js) {
		var alternative = js[i];
		count = count + 1;
		realCount = count - 1;
		buttons = "<a onClick=\"changeImage(" + realCount.toString() + ")\"\n>\t<img value=" + realCount.toString() + " src=\"check-mark_b&w.png\" width=\"28\" height=\"28\" border=\"0\" alt=\"javascript button\">\n</a>"
		let alternativeDesc = alternative["description"];
		console.log(alternativeDesc)
		if (alternativeDesc != "") {
			/**
			another if statement: check if the user is logged in using the url and if so, include the buttons if not, exclude them
		 */
	
			output = output + "<h4> Alternative " + count + ": </h4><label>" + alternativeDesc + "</label> " + buttons + "<br>" + "<div id=\"approvals" + count + "\"></div>";
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



