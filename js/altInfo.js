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
	var disapprovalButtons ="";

	for (var i in js) {
		var alternative = js[i];
		var alternativeID = alternative["alternativeID"];
		count = count + 1;
		
		approvalButtons = "<a onClick=\"changeImage(" + realCount.toString() + "," + alternativeID +")\"\n>\t<img value=" + realCount.toString() + " src=\"check-mark_b&w.png\" width=\"28\" height=\"28\" border=\"0\" alt=\"javascript button\">\n</a>";
		realCount = realCount + 1;
		disapprovalButtons = "<a onClick=\"changeImageDisapproval(" + realCount.toString() + "," + alternativeID +")\"\n>\t<img value=" + realCount.toString() + " src=\"cancel.png\" width=\"28\" height=\"28\" border=\"0\" alt=\"javascript button\">\n</a>";
		realCount = realCount + 1;
		let alternativeDesc = alternative["description"];
		console.log(alternativeDesc)
		if (alternativeDesc != "") {
			var isLoggedIn = window.location.href ;
			isLoggedIn = isLoggedIn.split("teamMemberID=")[1];
			if(isLoggedIn != "0"){
				output = output + "<h4> Alternative " + count + ": </h4><label>" + alternativeDesc + "</label> " + approvalButtons + disapprovalButtons + "<br>" + "<div id=\"approvals" + count + "\"></div>";
			}
			else{
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

function changeImageDisapproval(realCount) {
	var image = document.images[realCount];
	console.log(image.src);
	if (image.src.match("cancel.png")) {
		image.src = "cancel_color.png";
	}
	else {
		image.src = "cancel.png";
	}
}



