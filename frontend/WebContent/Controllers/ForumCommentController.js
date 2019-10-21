myApp.controller("ForumCommentController", function($scope, $http, $location, $rootScope, $cookieStore, $compile) {

	$scope.forumId = $cookieStore.get("showForumId");
	$scope.forumCommentList = [];

	$scope.forumComment = {
		'forumId' : $scope.forumId,
		'commentMessage' : '',
		'username' : ''
	};
	$scope.forumCommentEdit = {
		'forumId' : $scope.forumId,
		'commentMessage' : '',
		'username' : $rootScope.currentUser.username
	};

	$scope.addForumComment = function() {
		$scope.forumComment.username = $rootScope.currentUser.username;
		$http.post('http://localhost:' + location.port + '/middleware/addForumComment', $scope.forumComment).then(function(comment) {
			console.log('Forum comment added.');
			location.reload();
		});
	}

	$scope.getForumCommentList = function(forumId) {
		$http.get('http://localhost:' + location.port + '/middleware/getForumCommentList/' + forumId).then(function(response) {
			$scope.forumCommentList = response.data;
			$scope.forumCommentList.sort(function(a, b) {
				return new Date(a.commentDate) - new Date(b.commentDate);
			});
			$scope.forumCommentList.forEach(function(forumComment) {
				$http.get('http://localhost:' + location.port + '/middleware/getUser/' + forumComment.username).then(function(response) {
					forumComment.firstName = response.data.firstName;
					forumComment.lastName = response.data.lastName;
				});
			});
			$scope.forumCommentList.forEach(function(forumComment) {
				$http.get('http://localhost:' + location.port + '/middleware/getProfilePic/' + forumComment.username).then(function(response) {
					if (response.data) {
						forumComment.profilePicUrl = 'http://localhost:' + location.port + '/middleware/showProfilePic/' + forumComment.username;
					} else {
						forumComment.profilePicUrl = 'http://localhost:' + location.port + '/frontend/Resources/Images/empty-profile-pic.png';
					}
				});
			});

			console.log("Forum comments retrieved.")
			return response.data;
		});
	}

	$scope.deleteForumComment = function(forumCommentId) {
		$http.get('http://localhost:' + location.port + '/middleware/deleteForumComment/' + forumCommentId).then(function(response) {
			console.log("Forum comment deleted.");
			var commentRow = document.getElementById("forumCommentRow" + forumCommentId);
			commentRow.parentNode.removeChild(commentRow);
		});
	}

	$scope.editForumComment = function(forumCommentId) {
		var commentCell = document.getElementById("commentCell" + forumCommentId);
		var existingComment = document.getElementById("forumComment" + forumCommentId);
		var btn_editSaveComment = document.getElementById("editCommentBtn" + forumCommentId);
		var btn_closeEdit = document.getElementById("btn_closeEditComment" + forumCommentId);
		var div_commentOperations = document.getElementById("commentOperationsBox" + forumCommentId);
		if (btn_editSaveComment.getAttribute("title") == "Save") {
			$scope.forumCommentEdit.commentId = forumCommentId;
			$http.post('http://localhost:' + location.port + '/middleware/editForumComment', $scope.forumCommentEdit).then(function(response) {
				console.log(response.data);
				var span_comment = document.createElement("span");
				span_comment.innerHTML = (response.data).commentMessage;
				commentCell.replaceChild(span_comment, existingComment);
				span_comment.setAttribute("id", "forumComment" + forumCommentId);
				btn_editSaveComment.setAttribute("title", "Edit");
				btn_editSaveComment.style.color = "#343a40";
				div_commentOperations.removeChild(btn_closeEdit);
				btn_editSaveComment.classList.remove("fa-check");
				btn_editSaveComment.classList.add("fa-edit");
			});
		} else {
			var editCommentBoxElements = document.querySelectorAll("input[ng-model^='forumCommentEdit.commentMessage']");
			var closeEditBoxButtons = document.querySelectorAll("i[id^='btn_closeEditComment']");
			closeEditBoxButtons.forEach(function(item) {
				item.click();
			});
			var txtBox_editComment = document.createElement("input");
			txtBox_editComment.setAttribute("type", "text");
			txtBox_editComment.classList.add("form-control", "form-control-sm");
			txtBox_editComment.setAttribute("name", "editCommentMessage");
			txtBox_editComment.setAttribute("ng-model", "forumCommentEdit.commentMessage");

			btn_editSaveComment.classList.remove("fa-edit");
			btn_editSaveComment.classList.add("fa-check");
			btn_editSaveComment.style.color = "#1bbd74";

			commentCell.replaceChild(txtBox_editComment, existingComment);
			txtBox_editComment.setAttribute("id", "forumComment" + forumCommentId);

			btn_closeEdit = document.createElement("i");
			btn_closeEdit.classList.add("fas", "fa-times", "mr-2");
			btn_closeEdit.style.color = "red";
			btn_closeEdit.style.cursor = "pointer";
			btn_closeEdit.setAttribute("id", "btn_closeEditComment" + forumCommentId);
			btn_closeEdit.setAttribute("title", "Undo");
			btn_closeEdit.addEventListener("click", function() {
				var span_comment = document.createElement("span");
				span_comment.innerHTML = existingComment.innerHTML;
				commentCell.replaceChild(span_comment, txtBox_editComment);
				span_comment.setAttribute("id", "forumComment" + forumCommentId);
				btn_editSaveComment.setAttribute("title", "Edit");
				btn_editSaveComment.classList.remove("fa-check");
				btn_editSaveComment.classList.add("fa-edit");
				btn_editSaveComment.style.color = "#343a40";
				div_commentOperations.removeChild(btn_closeEdit);
			});
			div_commentOperations.insertBefore(btn_closeEdit, div_commentOperations.childNodes[2]);
			btn_editSaveComment.setAttribute("title", "Save");
			$compile(txtBox_editComment)($scope);
			$scope.forumCommentEdit.commentMessage = existingComment.innerHTML.trim();
		}

	};

});
