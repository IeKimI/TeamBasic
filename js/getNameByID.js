function getName(){
	
	var choiceURL = window.location.href;
	var choiceID = choiceURL.split('choice=')[1];
	choiceID = choiceID.split('?')[0];

	var teamMemberID = choiceURL.split('teamMemberID=')[1];

	
	var xhr = new XMLHttpRequest();
	xhr.open("GET", teamMember_url +"/" + choiceID + "/" + teamMemberID, true);
	if(teamMemberID!=0){
		xhr.setRequestHeader('Content-Type', 'application/json');
		xhr.send();
		console.log("sent");
	}
	

  // This will process results and update HTML as appropriate. 
xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      getTeamMemberNameByID(xhr.responseText);
    } else {
      getTeamMemberNameByID("N/A");
    }
}
/**
 * Refresh a choice from server
 *
 *    GET a_choice_url
 *    RESPONSE  list of [name, value, system] constants 
 */
function getTeamMemberByID(result) {
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
	var username=js["teamMemberName"];
	
		
		
	
	document.getElementById("message").innerText = "Welcome!" + username;
/*	document.getElementById("login").innerHTML ="<label>Username</label><br> <input name=\"username\" value=\"\"readonly><br>\n\t\t\t<label>Password</label><br> <input type=\"password\" name=\"password\" value=\"\"readonly><br>\n\t\t\t<input type=\"button\" id=\"login\" value=\"Login\"\n\t\t\t\tonClick=\"JavaScript:handleLoginClick(this)\">";
*/	
	/*window.history.replaceState({}, 'teamMemberID=0', currentURL+'teamMemberID=' + teamMemberID)*/
	
	document.getElementById("login").innerHTML ="<label>Username</label><br> <input name=\"username\" value=\"\"readonly><br>\n\t\t\t<label>Password</label><br> <input type=\"password\" name=\"password\" value=\"\"readonly><br>\n\t\t\t>";
	getAlternatives();
	getApprovals();

}

}

