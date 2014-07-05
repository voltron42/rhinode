(function(){
	var kennel = require("./kennel.js");
	Object.keys = function(obj) {
		var out = [];
		for (var key in obj) {
			if (obj.hasOwnProperty(key)) {
				out.push(key)
			}
		}
		return out;
	}
	console.log("Hello World");
	console.log("PI = " + Math.PI);
	var obj = {
		x:11,
		y:37
	};
	console.log(Object.keys(obj).join());
	
	kennel.forEach(function(dog) {
		console.log(dog.toString());
	})
	return [1,2,3];
})()