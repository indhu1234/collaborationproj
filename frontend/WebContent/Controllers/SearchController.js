myApp.controller("SearchController", function($scope, $http, $location, $rootScope, $cookieStore) {

	$rootScope.blogResults = [];
	$rootScope.forumResults = [];
	$rootScope.jobResults = [];
	$rootScope.userResults = [];
	$rootScope.searchText = '';

	$scope.searchBar = document.getElementById("searchBar");

	$scope.showBlog = function(blogId) {
		if ($location.path() != '/showBlog') {
			$cookieStore.put("showBlogId", blogId);
			$location.path("/showBlog");
		}
	}

	$scope.showForum = function(forumId) {
		if ($location.path() != '/showForum') {
			$cookieStore.put("showForumId", forumId);
			$location.path("/showForum");
		}
	}

	$scope.showJob = function(jobId) {
		if ($location.path() != '/showJob') {
			$cookieStore.put("showJobId", jobId);
			$location.path("/showJob");
		}
	}

	$scope.search = function() {
		$rootScope.blogResults = [];
		$rootScope.forumResults = [];
		$rootScope.jobResults = [];
		$rootScope.userResults = [];
		$rootScope.searchText = '';

		if ((searchBar.value.trim()).length > 0) {
			$rootScope.searchText = $scope.searchBar.value;
			// blog search
			$http.get('http://localhost:' + location.port + '/middleware/blogSearch/' + $scope.searchText).then(
					function(response) {
						response.data.forEach(function(item) {
							$rootScope.blogResults.push(item);
						});
						// forum search
						$http.get('http://localhost:' + location.port + '/middleware/forumSearch/' + $scope.searchText).then(
								function(response) {
									response.data.forEach(function(item) {
										$rootScope.forumResults.push(item);
									});
									// job search
									$http.get('http://localhost:' + location.port + '/middleware/jobSearch/' + $scope.searchText).then(
											function(response) {
												response.data.forEach(function(item) {
													$rootScope.jobResults.push(item);
												});
												// user search
												$http.get('http://localhost:' + location.port + '/middleware/userSearch/' + $scope.searchText).then(
														function(response) {
															$rootScope.userResults = response.data;
															$rootScope.userResults.some(function(u, index) {
																if (u.username == $rootScope.currentUser.username)
																	$rootScope.userResults.splice(index, 1);
															});

															$rootScope.userResults.forEach(function(user) {
																$http.get('http://localhost:' + location.port + '/middleware/getProfilePic/' + user.username).then(
																		function(response) {
																			if (response.data) {
																				user.profilePicUrl = 'http://localhost:' + location.port + '/middleware/showProfilePic/' + user.username;
																			} else {
																				user.profilePicUrl = 'http://localhost:' + location.port + '/frontend/Resources/Images/empty-profile-pic.png';
																			}

																			// setting
																			// friend
																			// status
																			$http.get(
																					'http://localhost:' + location.port + '/middleware/checkIfFriends/' + $rootScope.currentUser.username + '/'
																							+ user.username).then(function(response) {
																				if (response.data) {
																					if (response.data.status == 'A')
																						user.friendStatus = "A";
																					else
																						user.friendStatus = "P";
																				}
																				// search
																				// results
																				// generated

																			});
																		});
															});

														});
											});

								});
					});
		}
	};
});
