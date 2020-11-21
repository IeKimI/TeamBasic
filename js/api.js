// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://6lwvc7kqj0.execute-api.us-east-2.amazonaws.com/basic/";

var add_url    = base_url + "choice";   // POST
var list_url   = base_url + "choice";    // GET
var create_url = base_url + "choice";    // POST
/*var delete_url = base_url + "constants";    // POST with {name} so we avoid CORS issues
*/ 
