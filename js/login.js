

function handleLoginClick(e) {
	var form = document.loginForm;

	data = {};
	var url = window.location.href;
	var wholeURL = url.split('=')[1];
	var choiceID = wholeURL.split('?')[0];


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
				processLogIn(xhr.responseText);
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

	var js = JSON.parse(result);
	js = js["response"];
	var username = js.split(':')[1];

	var teamMemberID = js.split(":")[2];
	var currentURL = window.location.href;
	currentURL = currentURL.split("html/")[1];
	currentURL = currentURL.split("teamMemberID")[0];
	var realID = window.location.href.split("teamMemberID=")[1];

	console.log(username);
	if (username === undefined) {
		/*		document.getElementById("message").innerText = "Registration rejected! \n Check if \n 1) You entered the correct username and password. \n 2) The choice has exceeded the maximum number of participants.";
		*/
		function myFunction() {
			alert("Registration rejected! \n Check if \n1) You entered the correct username and password. \n2) The choice has exceeded the maximum number of participants.");
		}
		myFunction();
	}

	else {
		document.getElementById("message").innerText = "Welcome!" + username;
		document.getElementById("login").innerHTML = "<label style=\"font-family:verdana\">Username</label><br> <input type=\"textNotAllowed\" style=\"font-family:verdana\" name=\"username\" value=\"\" readonly><br>\n\t\t\t<label style=\"font-family:verdana\">Password</label><br> <input type=\"textNotAllowed\" style=\"font-family:verdana\" name=\"password\" value=\"\" readonly><br>";

		window.history.replaceState({}, 'teamMemberID=0', currentURL + 'teamMemberID=' + teamMemberID)

		getAlternatives();
		getApprovals();
	}



}