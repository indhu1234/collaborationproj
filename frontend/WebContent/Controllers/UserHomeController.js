myApp.controller("UserHomeController", function($scope, $http, $location, $rootScope) {

	$scope.blogStartRowNum = 1;
	$scope.blogEndRowNum = 4;
	$scope.forumStartRowNum = 1;
	$scope.forumEndRowNum = 4;
	$scope.jobStartRowNum = 1;
	$scope.jobEndRowNum = 4;
	
	$scope.newsFeedItems = [];
	$scope.newsFeedNextItems = [];
	$scope.blogComments = [];
	$scope.forumComments = [];

	$scope.generateNewsFeed = function() {
		
		// get limited blog list
		$http.get('http://localhost:' + location.port + '/middleware/getLimitedBlogList/' + $rootScope.currentUser.username + '/' + $scope.blogStartRowNum + '/' + $scope.blogEndRowNum).then(
				function(response) {
					response.data.forEach(function(item) {
						$scope.newsFeedItems.push(item);
						alert(response.data.length+" blogs retrieved");
						$scope.blogStartRowNum=response.data.length+1
					});
					// get limited forum list
					$http.get(
							'http://localhost:' + location.port + '/middleware/getLimitedForumList/' + $rootScope.currentUser.username + '/' + $scope.forumStartRowNum + '/'
									+ $scope.forumEndRowNum).then(
							function(response) {
								response.data.forEach(function(item) {
									$scope.newsFeedItems.push(item);
								});
								// get limited job list
								$http.get(
										'http://localhost:' + location.port + '/middleware/getLimitedJobList/' + $rootScope.currentUser.username + '/' + $scope.jobStartRowNum + '/'
												+ $scope.jobEndRowNum).then(
										function(response) {
											response.data.forEach(function(item) {
												$scope.newsFeedItems.push(item);
											});

											$scope.newsFeedItems.forEach(function(item) {
												// add "type" property
												if (item.hasOwnProperty("blogId"))
													item.type = "blog";
												else if (item.hasOwnProperty("forumId"))
													item.type = "forum";
												else
													item.type = "job";

												// add property "date" to items
												// and
												// remove property "createdDate"
												// or
												// "postedDate"
												if (item.type == "blog" || item.type == "forum") {
													item.date = item.createdDate;
													delete item.createdDate;
												} else {
													item.date = item.postedDate;
													delete item.postedDate;
												}
												// calculate elapsed time
												item.elapsedTime = getElapsedTime(Date.now() - new Date(item.date));
											});

											// sort items in desc order of their
											// date
											$scope.newsFeedItems.sort(function(a, b) {
												return new Date(b.date) - new Date(a.date);
											});

											// keep only 10 items, send
											// remaining to
											// $scope.newsFeedNextItems
											if ($scope.newsFeedItems.length > 10) {
												var removedItems = $scope.newsFeedItems.splice(10, ($scope.newsFeedItems.length - 10));
												removedItems.forEach(function(item) {
													$scope.newsFeedNextItems.push(item);
												});
											}
											else if($scope.newsFeedItems.length < 10)
												
											// get first and last name of user
											// of each item
											$scope.newsFeedItems.forEach(function(item) {
												$http.get('http://localhost:' + location.port + '/middleware/getUser/' + item.username).then(
														function(response) {
															item.firstName = response.data.firstName;
															item.lastName = response.data.lastName;
															// get comments of
															// blog and forums
															if (item.type == 'blog' || item.type == 'forum') {
																$http.get(
																		'http://localhost:' + location.port + '/middleware/'
																				+ (item.type == 'blog' ? 'getBlogCommentList' : 'getForumCommentList') + '/'
																				+ (item.type == 'blog' ? item.blogId : item.forumId)).then(function(response) {
																	response.data.forEach(function(comment) {
																		$http.get('http://localhost:' + location.port + '/middleware/getUser/' + comment.username).then(function(response) {
																			comment.firstName = response.data.firstName;
																			comment.lastName = response.data.lastName;
																			if (item.type == 'blog')
																				$scope.blogComments.push(comment);
																			else
																				$scope.forumComments.push(comment);
																		});
																	});

																});
															}
														});
												console.log("News feed items generated");
											});
										});
							});
				});
	};
});