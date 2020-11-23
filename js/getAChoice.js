/**
 * Refresh a choice from server
 *
 *    GET a_choice_url
 *    RESPONSE  list of [name, value, system] constants 
 */
function refreshAChoice() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", a_choice_url, true);
   xhr.send();
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processChoiceResponse(xhr.responseText);
    } else {
      processChoiceResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processChoiceResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var choiceTable = document.getElementById('uniqueID');

//I have a question, it says getElementByID,
  
  var output = "";

    console.log(constantJson);
/* 
 var maxNumOfMembers = constantJson["maxNum"];
  var description = constantJson["description"];
  var alternatives = [];
  if (sysvar) {
    	output = output + "<div id=\"const" + choiceID + "\"><b>" + choiceID + ":</b> = " + cval + "<br></div>";
    } else {
    	output = output + "<div id=\"const" + cname + "\"><b>" + cname + ":</b> = " + cval + "(<a href='javaScript:requestDelete(\"" + cname + "\")'><img src='deleteIcon.png'></img></a>) <br></div>";
    }
*/
	var data = {};
	data["description"] = constantJson["description"];
	
	var alternatives = [];

	var alternative1 = {};
	var alternative2 = {};
	var alternative3 = {};
	var alternative4 = {};
	var alternative5 = {};

	alternative1["description"] = constantJson["alternative1"];
	alternative2["description"] = constantJson["alternative2"];
	alternative3["description"] = constantJson["alternative3"];
	alternative4["description"] = constantJson["alternative4"];
	alternative5["description"] = constantJson["alternative5"];

	alternatives = [alternative1, alternative2, alternative3, alternative4, alternative5];
	data["maxNum"] = constantJson["maxNum"];
	data["alternativeChoices"] = alternatives;
  

  // Update computation result
  choiceTable.innerHTML = output;
}

