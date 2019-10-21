myApp.controller("ForumController", function($scope, $http, $location, $rootScope, $cookieStore) {

	$scope.forumList = [];
	$scope.userForumList = [];
	$scope.forumDetail;
	$scope.editForumInfo;
	$scope.existingForumLike = undefined;
	$scope.existingForumDislike = undefined;

	$scope.forum = {
		'forumTitle' : '',
		'forumContent' : '',
		'username' : '',
		'status' : '',
		'likes' : 0,
		'dislikes' : 0
	};

	if ($cookieStore.get("showForumId")) {
		// get existing Like
		$http.get('http://localhost:' + location.port + '/middleware/getForumLike/' + $cookieStore.get("showForumId") + '/' + $rootScope.currentUser.username).then(function(response) {
			if (response.data) {
				$scope.existingForumLike = response.data;
				console.log("Existing like retrieved.");
			}
		});

		// get existing Dislike
		$http.get('http://localhost:' + location.port + '/middleware/getForumDislike/' + $cookieStore.get("showForumId") + '/' + $rootScope.currentUser.username).then(function(response) {
			if (response.data) {
				$scope.existingForumDislike = response.data;
				console.log("Existing dislike retrieved.");
			}
		});
	}

	$scope.addForum = function() {
		$scope.forum.username = $rootScope.currentUser.username;
		$scope.forum.status = 'P';
		$http.post('http://localhost:' + location.port + '/middleware/addForum', $scope.forum).then(function(response) {
			
			$("#addForumModal").modal("hide");
			$("#addForumSuccessfulModal").on('hidden.bs.modal', function(){
			    location.reload();
			  });
			$("#addForumSuccessfulModal").modal("show");
			console.log('Forum added');
		}).catch(function(e){
			console.log(e);
			var errorList=e.data;
			var allErrorElements=document.getElementsByClassName("error");
			for(i=0;i<allErrorElements.length;i++)
			  {
				allErrorElements[i].innerHTML="";
			  }
			errorList.forEach(function(error){
				var element=document.getElementById(error.fieldName+"Error");
				if(!element.innerHTML)
					element.innerHTML=error.errorMessage;
				else
					element.innerHTML+="<br>"+error.errorMessage;
			});
		});
	}

	$scope.showEditForum = function() {
		var urlText = window.location.href;
		var editForumId = urlText.substring(urlText.indexOf("=") + 1);
		$http.get('http://localhost:' + location.port + '/middleware/getForum/' + editForumId).then(function(response) {
			$scope.editForumInfo = response.data;
			delete $scope.editForumInfo.createdDate;
		});
	}

	$scope.updateForum = function() {
		$scope.editForumInfo.status = 'P';
		$http.post('http://localhost:' + location.port + '/middleware/updateForum', $scope.editForumInfo).then(function(response) {
			$("#updateForumSuccessfulModal").on('hidden.bs.modal', function(){
			    $location.path("/forum");
			    $scope.$apply();
			  });
			$("#updateForumSuccessfulModal").modal("show");
			console.log("Forum updated.");
		}).catch(function(e){
			console.log(e);
			var errorList=e.data;
			var allErrorElements=document.getElementsByClassName("error");
			for(i=0;i<allErrorElements.length;i++)
			  {
				allErrorElements[i].innerHTML="";
			  }
			errorList.forEach(function(error){
				var element=document.getElementById(error.fieldName+"Error");
				if(!element.innerHTML)
					element.innerHTML=error.errorMessage;
				else
					element.innerHTML+="<br>"+error.errorMessage;
			});
		});
	}

	$scope.getForums = function() {
		// get all approved forums
		$http.get('http://localhost:' + location.port + '/middleware/getForumList').then(function(response) {

			$scope.forumList = response.data;
			$scope.forumList.sort(function(a, b) {
				return new Date(a.createdDate) - new Date(b.createdDate);
			});

			console.log("Forum list retrieved.");
		});
		// get all forums created by current user
		$http.get('http://localhost:' + location.port + '/middleware/getUserForumList/' + $rootScope.currentUser.username).then(function(response) {
			$scope.userForumList = response.data;
			$scope.userForumList.sort(function(a, b) {
				return new Date(a.createdDate) - new Date(b.createdDate);
			});
			console.log($rootScope.currentUser.username + "'s forum list retrieved.");
		});
	};

	$scope.approveForum = function(forumId) {
		$http.get('http://localhost:' + location.port + '/middleware/approveForum/' + forumId).then(function(response) {
			console.log("Forum approved");
			location.reload();
		});
	}

	$scope.rejectForum = function(forumId) {
		$http.get('http://localhost:' + location.port + '/middleware/rejectForum/' + forumId).then(function(response) {
			console.log("Forum rejected");
			location.reload();
		});
	}

	$scope.deleteForum = function(forumId) {
		$http.get('http://localhost:' + location.port + '/middleware/deleteForum/' + forumId).then(function(response) {
			console.log("Forum deleted");
			var forumRow = document.getElementById("forumRow" + forumId);
			forumRow.parentNode.removeChild(forumRow);
		});
	}

	$scope.showForum = function(forumId) {
		if($location.path()!='/showForum'){
			$cookieStore.put("showForumId",forumId);
			$location.path("/showForum");
		}
		else{
			forumId=$cookieStore.get("showForumId");
			$http.get('http://localhost:' + location.port + '/middleware/getForum/' + forumId).then(function(response) {
				$scope.forumDetail = response.data;
			});
		}
	}

	$scope.likeClick = function() {
		// check if there is an existing like by user
		$http.get('http://localhost:' + location.port + '/middleware/getForumLike/' + $cookieStore.get("showForumId") + '/' + $rootScope.currentUser.username).then(
				function(response) {
					var existingLike = response.data;
					if (!existingLike) {
						// add new like
						var forumLike = {
							'forumId' : $cookieStore.get("showForumId"),
							'username' : $rootScope.currentUser.username
						};
						$http.post('http://localhost:' + location.port + '/middleware/addForumLike', forumLike).then(function() {
							document.getElementById("forum-like-count").innerHTML = Number(document.getElementById("forum-like-count").innerHTML) + 1;
							console.log("Forum Liked.");
						});
						// increment forum like value
						$http.get('http://localhost:' + location.port + '/middleware/incrementLike/' + $cookieStore.get("showForumId"));
						// check if there is an existing
						// dislike by user
						$http.get('http://localhost:' + location.port + '/middleware/getForumDislike/' + $cookieStore.get("showForumId") + '/' + $rootScope.currentUser.username).then(
								function(response) {
									var existingDislike = response.data;
									if (existingDislike) {
										// delete
										// forum
										// dislike
										$http.get('http://localhost:' + location.port + '/middleware/deleteForumDislike/' + existingDislike.forumDislikeId).then(function() {
											document.getElementById("forum-dislike-count").innerHTML = Number(document.getElementById("forum-dislike-count").innerHTML) - 1;
											console.log("Dislike removed.");
										});
										// decrement
										// forum
										// dislikes
										// value
										$http.get('http://localhost:' + location.port + '/middleware/decrementDislike/' + $cookieStore.get("showForumId"));
									}
								});
					} else {
						// delete existing like
						$http.get('http://localhost:' + location.port + '/middleware/deleteForumLike/' + existingLike.forumLikeId).then(function() {
							document.getElementById("forum-like-count").innerHTML = Number(document.getElementById("forum-like-count").innerHTML) - 1;
							console.log("Existing like removed.");
						});
						// decrement forum like value
						$http.get('http://localhost:' + location.port + '/middleware/decrementLike/' + $cookieStore.get("showForumId"));
					}
				});
	};

	$scope.dislikeClick = function() {
		// check if there is an existing dislike by user
		$http.get('http://localhost:' + location.port + '/middleware/getForumDislike/' + $cookieStore.get("showForumId") + '/' + $rootScope.currentUser.username).then(function(response) {
			var existingDislike = response.data;
			if (!existingDislike) {
				// add new dislike
				var forumDislike = {
					'forumId' : $cookieStore.get("showForumId"),
					'username' : $rootScope.currentUser.username
				};
				$http.post('http://localhost:' + location.port + '/middleware/addForumDislike', forumDislike).then(function() {
					document.getElementById("forum-dislike-count").innerHTML = Number(document.getElementById("forum-dislike-count").innerHTML) + 1;
					console.log("Forum disliked.");
				});
				// increment forum dislike value
				$http.get('http://localhost:' + location.port + '/middleware/incrementDislike/' + $cookieStore.get("showForumId"));
				// check if there is an existing
				// like by user
				$http.get('http://localhost:' + location.port + '/middleware/getForumLike/' + $cookieStore.get("showForumId") + '/' + $rootScope.currentUser.username).then(function(response) {
					var existingLike = response.data;
					if (existingLike) {
						// delete
						// forum
						// like
						$http.get('http://localhost:' + location.port + '/middleware/deleteForumLike/' + existingLike.forumLikeId).then(function() {
							document.getElementById("forum-like-count").innerHTML = Number(document.getElementById("forum-like-count").innerHTML) - 1;
							console.log("Like removed.");
						});
						// decrement
						// forum
						// likes
						// value
						$http.get('http://localhost:' + location.port + '/middleware/decrementLike/' + $cookieStore.get("showForumId"));
					}
				});
			} else {
				// delete existing dislike
				$http.get('http://localhost:' + location.port + '/middleware/deleteForumDislike/' + existingDislike.forumDislikeId).then(function() {
					document.getElementById("forum-dislike-count").innerHTML = Number(document.getElementById("forum-dislike-count").innerHTML) - 1;
					console.log("Existing dislike removed.");
				});
				// decrement forum dislike value
				$http.get('http://localhost:' + location.port + '/middleware/decrementDislike/' + $cookieStore.get("showForumId"));
			}
		});
	};
});

function forumMenuSwitch(tabId) {
	var tabPaneAllForums = document.getElementById("tabPane-AllForums");
	var tabPaneYourForums = document.getElementById("tabPane-YourForums");
	switch (tabId) {
	case "tab-AllForums":
		tabPaneYourForums.style.display = "none";
		tabPaneAllForums.style.display = "";
		break;
	case "tab-YourForums":
		tabPaneAllForums.style.display = "none";
		tabPaneYourForums.style.display = "";
		break;
	}
}