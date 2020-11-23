/**
 * Refresh constant list from server
 *
 *    GET list_url
 *    RESPONSE  list of [name, value, system] constants 
 */
function refreshChoiceList() {
   var xhr = new XMLHttpRequest();
   xhr.open("GET", list_url, true);
   xhr.send();
   
   console.log("sent");

  // This will process results and update HTML as appropriate. 
  xhr.onloadend = function () {
    if (xhr.readyState == XMLHttpRequest.DONE) {
      console.log ("XHR:" + xhr.responseText);
      processListResponse(xhr.responseText);
    } else {
      processListResponse("N/A");
    }
  };
}

/**
 * Respond to server JSON object.
 *
 * Replace the contents of 'constantList' with a <br>-separated list of name,value pairs.
 */
function processListResponse(result) {
  console.log("res:" + result);
  // Can grab any DIV or SPAN HTML element and can then manipulate its contents dynamically via javascript
  var js = JSON.parse(result);
  var choiceList = document.getElementById('choiceList');
  
  var output = "";
  for (var i = 0; i < js.list.length; i++) {
    var choiceJson = js.list[i];
    console.log(choiceJson);
    
/**
data["description"] = form.description.value;
	
	var alternatives = [];

	var alternative1 = {};
	var alternative2 = {};
	var alternative3 = {};
	var alternative4 = {};
	var alternative5 = {};

	alternative1["description"] = form.alternative1.value;
	alternative2["description"] = form.alternative2.value;
	alternative3["description"] = form.alternative3.value;
	alternative4["description"] = form.alternative4.value;
	alternative5["description"] = form.alternative5.value;

	alternatives = [alternative1, alternative2, alternative3, alternative4, alternative5];
	data["maxNumOfTeamMembers"] = form.maxNumOfMembers.value;
	data["alternativeChoices"] = alternatives; */
    var cdescription = choiceJson["description"];
    var calternatives = choiceJson["alternativeChoices"];
  
    	output = output + "<div id=\"choice" + cdescription + "\"><b>" + cdescription + ":</b> = " + calternatives + "(<a href='javaScript:requestDelete(\"" + cdescription + "\")'><img src='deleteIcon.png'></img></a>) <br></div>";
    
  }

  // Update computation result
  choiceList.innerHTML = output;
}

