// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://2zylw79630.execute-api.us-east-2.amazonaws.com/teamBasic/";
var getChoice_url = base_url + "choice";
var add_url    = base_url + "choice";   // POST
var list_url   = base_url + "admin";    // GET
var create_url = base_url + "choice";    // POST
var a_choice_url=base_url + "choice";    // GET
var login_url = base_url + "login"; // POST
var alternative_url = base_url + "alternativeChoice" // GET
var approvals_url = base_url + "approval";
var approve_url = base_url + "approval";

/*var delete_url = base_url + "constants";    // POST with {name} so we avoid CORS issues
*/ 
 