module.exports = (function(){
	var Dog = require("./dog.js");
	
	var petey = new Dog("Petey", 3);
	
	return [petey];
})()