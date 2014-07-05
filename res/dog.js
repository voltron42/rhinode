module.exports = (function(){
	var Dog = function(name,age) {
		this.getName = function() {
			return name;
		}
		this.getAge = function() {
			return age;
		}
		this.getAgeInDogYears = function() {
			return age * 7;
		}
		this.bark = function() {
			return "Woof woof";
		}
		this.toString = function() {
			return ["My name is ",name,". I am ",this.getAgeInDogYears()," years old. ",this.bark(),"!"].join("");
		}
	};
	return Dog;
})();