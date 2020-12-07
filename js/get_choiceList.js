/**
 * Refresh constant list from server
 *
 *    GET list_url
 *    RESPONSE  list of [name, value, system] constants 
 */
function handleGenerateClick(e) {
	var xhr = new XMLHttpRequest();
	xhr.open("GET", list_url, true);
	xhr.send();

	console.log("sent");

	// This will process results and update HTML as appropriate. 
	xhr.onloadend = function() {
		if (xhr.readyState == XMLHttpRequest.DONE) {
				console.log("XHR:" + xhr.responseText);
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
	var choiceList = document.getElementById('choice_List');

	var output = "<table>\n  <tr>\n    <th>Choice ID</th>\n   <th>Description</th>\n   <th>Date Created</th>\n    <th>Is Completed</th>\n  </tr>";
	
	for (var i = 0; i < js.list.length; i++) {
		var choiceJson = js.list[i];
		console.log(choiceJson);

		var choiceID = choiceJson["uniqueID"];
		var dateOfCreation = choiceJson["dateOfCreation"];
		var description = choiceJson["description"];
		dateOfCreation = new Date(dateOfCreation).toLocaleString();
		var isCompleted = choiceJson["completed"];
		
		
		output = output + "<tr>\n    <td>"+ choiceID + "</td>\n    <td>" +description + "</td>\n    <td>" +dateOfCreation + "</td>\n    <td>" +isCompleted + "</td>\n  </tr>";

		/*			output = output + "<div id=\"choiceReport" + choiceID + "\"><b>" + dateOfCreation + ":</b> = " + isCompleted + "(<a href='javaScript:requestDelete(\"" + cname + "\")'><img src='deleteIcon.png'></img></a>) <br></div>";
		*/
	}
	output = output + "</table>"

	// Update computation result
	choiceList.innerHTML = output;
}

