

function handleLoginClick(e) {
	var form = document.loginForm;

	data = {};
	var url = window.location.href;
	var choiceID = url.split('=')[1];

	/*	var username = "";
		var password = "";*/

	data["name"] = form.username.value;
	data["password"] = form.password.value;
	data["choiceID"] = choiceID;


	var js = JSON.stringify(data);
	console.log("JS:" + js);
	
	var xhr = new XMLHttpRequest();
	xhr.open("POST", login_url, true);

	// send the collected data as JSON
	xhr.send(js);

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function() {
		console.log(xhr);
		console.log(xhr.request);
		if (xhr.readyState == XMLHttpRequest.DONE) {
			if (xhr.status == 200) {
				console.log("XHR:" + xhr.responseText);
				processCreateResponse(xhr.responseText);
			} else {
				console.log("actual:" + xhr.responseText)
				var js = JSON.parse(xhr.responseText);
				var err = js["response"];
				alert(err);
			}
		} else {
			processLogIn("N/A");
		}
	};
}

function processLogIn(result) {
	// Can grab any DIV or SPAN HTML element and can then manipulate its
	// contents dynamically via javascript
	console.log("result:" + result);
	/*var choiceID = JSON.parse(response)["response"];
	console.log(choiceID);
	
	var username = JSON.parse(request)["name"];
	window.location.href = "https://teambasic.s3.us-east-2.amazonaws.com/html/choice.html?" + choiceID;
	
	
	var choiceInfo = document.getElementById["choiceInfo"];


	var output = "";*/

	/*refreshChoiceList();*/
}