myApp.controller("NotificationController", function($scope, $http, $location,
		$rootScope) {

	$scope.notificationList;
	
	if ($rootScope.currentUser) {
		$http.get(
				'http://localhost:' + location.port
						+ '/middleware/getNotifications/'
						+ $rootScope.currentUser.username).then(
				function(response) {
					$scope.notificationList = response.data;
					console.log("Notification list retrieved.");
				});
	}
	
	$scope.deleteNotification = function(notificationId) {
		$http.get(
				'http://localhost:' + location.port
						+ '/middleware/deleteNotification/'
						+ notificationId).then(function(response) {
			console.log("Notification deleted.");
		});
	};

});