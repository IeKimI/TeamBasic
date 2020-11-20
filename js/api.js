// all access driven through BASE. Must end with a SLASH
// be sure you change to accommodate your specific API Gateway entry point
var base_url = "https://odcdx0vcw9.execute-api.us-east-1.amazonaws.com/final/";

var add_url    = base_url + "calculator";   // POST
var list_url   = base_url + "constants";    // GET
var create_url = base_url + "constants";    // POST
var delete_url = base_url + "constants";    // POST with {name} so we avoid CORS issues
 
