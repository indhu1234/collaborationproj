var file;
var fileChanged = function(event) {
	var input = event.target;
	var reader = new FileReader();
	reader.onload = function() {
		file = new Int8Array(reader.result);
	};
	reader.readAsArrayBuffer(input.files[0]);

};

myApp.controller("ProfilePicController", function($scope, $http, $location, $rootScope, $cookieStore) {

	// checking if profile pic exists
	$http.get('http://localhost:' + location.port + '/middleware/getProfilePic/' + $rootScope.currentUser.username).then(function(response) {
		$scope.profilePic = response.data;
		if ($scope.profilePic) {
			console.log("pp found");
			$scope.removeProfilePicUrl = "http://localhost:" + location.port + "/middleware/deleteProfilePic/" + $rootScope.currentUser.username;
			$rootScope.currentUser.profilePicUrl = 'http://localhost:' + location.port + '/middleware/showProfilePic/' + $rootScope.currentUser.username;
			$cookieStore.put('userDetails', $rootScope.currentUser);
		} else {
			console.log("pp not found");
			$rootScope.currentUser.profilePicUrl = 'http://localhost:' + location.port + '/frontend/Resources/Images/empty-profile-pic.png';
		}
	});

	$scope.newProfilePic = {
		'username' : $rootScope.currentUser.username,
		'image' : ''
	};

	$scope.addProfilePic = function() {
		$location.path("/showUser");
	}
	
	$scope.deleteProfilePic = function() {
		$http.get('http://localhost:' + location.port + '/middleware/deleteProfilePic/' + $rootScope.currentUser.username).then(function(response){
			console.log("pp deleted");
		});
	}
});

$(".custom-file-input").on("change", function() {
	var fileName = $(this).val().split("\\").pop();
	$(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});