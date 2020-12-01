function getAlternatives(){
	
		var choiceURL = window.location.href;
	var choiceID = choiceURL.split('=')[1];

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
	var buttons = "<a href=\"#\" onClick=\"changeImage()\" onClick= \"JavaScript:changeImageBack()\"\n>\t<img name=\"jsbutton\" src=\"wink.png\" width=\"28\" height=\"28\" border=\"0\" alt=\"javascript button\">\n</a>";
	
	for (var i in js) {
		var alternative = js[i];
		count  = count + 1;
		
		let alternativeDesc = alternative["description"];
		console.log(alternativeDesc)
		output = output + "<h4> Alternative " + count + ": </h4><label>" + alternativeDesc + "</label> "+ buttons + "<br>";
		console.log(output);
	}
	alternatives.innerHTML = output;
}

function changeImage() 
{
  document.images["jsbutton"].src = "wink (1).png";
  return true;
}

function changeImageBack() 
{
  document.images["jsbutton"].src = "wink.png";
  return true;
}

