myApp.controller("BlogCommentController", function($scope, $http, $location, $rootScope, $cookieStore, $compile) {

	$scope.blogId = $cookieStore.get("showBlogId");
	$scope.blogCommentList = [];

	$scope.blogComment = {
		'blogId' : $scope.blogId,
		'commentMessage' : '',
		'username' : ''
	};
	$scope.blogCommentEdit = {
		'blogId' : $scope.blogId,
		'commentMessage' : '',
		'username' : $rootScope.currentUser.username
	};

	$scope.addBlogComment = function() {
		$scope.blogComment.username = $rootScope.currentUser.username;
		$http.post('http://localhost:' + location.port + '/middleware/addBlogComment', $scope.blogComment).then(function(comment) {
			console.log('Blog comment added.');
			location.reload();
		});
	}

	$scope.getBlogCommentList = function(blogId) {
		$http.get('http://localhost:' + location.port + '/middleware/getBlogCommentList/' + blogId).then(function(response) {
			$scope.blogCommentList = response.data;
			$scope.blogCommentList.sort(function(a, b) {
				return new Date(a.commentDate) - new Date(b.commentDate);
			});
			$scope.blogCommentList.forEach(function(blogComment) {
				$http.get('http://localhost:' + location.port + '/middleware/getUser/' + blogComment.username).then(function(response) {
					blogComment.firstName = response.data.firstName;
					blogComment.lastName = response.data.lastName;
				});
			});
			$scope.blogCommentList.forEach(function(blogComment) {
				$http.get('http://localhost:' + location.port + '/middleware/getProfilePic/' + blogComment.username).then(function(response) {
					if (response.data) {
						blogComment.profilePicUrl = 'http://localhost:' + location.port + '/middleware/showProfilePic/' + blogComment.username;
					} else {
						blogComment.profilePicUrl = 'http://localhost:' + location.port + '/frontend/Resources/Images/empty-profile-pic.png';
					}
				});
			});
			console.log("Blog comments retrieved.")
		});
	}

	$scope.deleteBlogComment = function(blogCommentId) {
		$http.get('http://localhost:' + location.port + '/middleware/deleteBlogComment/' + blogCommentId).then(function(response) {
			console.log("Blog comment deleted.");
			var commentRow = document.getElementById("blogCommentRow" + blogCommentId);
			commentRow.parentNode.removeChild(commentRow);
		});
	}

	$scope.editBlogComment = function(blogCommentId) {
		var commentCell = document.getElementById("commentCell" + blogCommentId);
		var existingComment = document.getElementById("blogComment" + blogCommentId);
		var btn_editSaveComment = document.getElementById("editCommentBtn" + blogCommentId);
		var btn_closeEdit = document.getElementById("btn_closeEditComment" + blogCommentId);
		var div_commentOperations = document.getElementById("commentOperationsBox" + blogCommentId);
		if (btn_editSaveComment.getAttribute("title") == "Save") {
			$scope.blogCommentEdit.commentId = blogCommentId;
			$http.post('http://localhost:' + location.port + '/middleware/editBlogComment', $scope.blogCommentEdit).then(function(response) {
				console.log(response.data);
				var span_comment = document.createElement("span");
				span_comment.innerHTML = (response.data).commentMessage;
				commentCell.replaceChild(span_comment, existingComment);
				span_comment.setAttribute("id", "blogComment" + blogCommentId);
				btn_editSaveComment.setAttribute("title", "Edit");
				btn_editSaveComment.style.color = "#343a40";
				div_commentOperations.removeChild(btn_closeEdit);
				btn_editSaveComment.classList.remove("fa-check");
				btn_editSaveComment.classList.add("fa-edit");
			});
		} else {
			var editCommentBoxElements = document.querySelectorAll("input[ng-model^='blogCommentEdit.commentMessage']");
			var closeEditBoxButtons = document.querySelectorAll("i[id^='btn_closeEditComment']");
			closeEditBoxButtons.forEach(function(item) {
				item.click();
			});
			var txtBox_editComment = document.createElement("input");
			txtBox_editComment.setAttribute("type", "text");
			txtBox_editComment.classList.add("form-control", "form-control-sm");
			txtBox_editComment.setAttribute("name", "editCommentMessage");
			txtBox_editComment.setAttribute("ng-model", "blogCommentEdit.commentMessage");

			btn_editSaveComment.classList.remove("fa-edit");
			btn_editSaveComment.classList.add("fa-check");
			btn_editSaveComment.style.color = "#1bbd74";

			commentCell.replaceChild(txtBox_editComment, existingComment);
			txtBox_editComment.setAttribute("id", "blogComment" + blogCommentId);

			btn_closeEdit = document.createElement("i");
			btn_closeEdit.classList.add("fas", "fa-times", "mr-2");
			btn_closeEdit.style.color = "red";
			btn_closeEdit.style.cursor = "pointer";
			btn_closeEdit.setAttribute("id", "btn_closeEditComment" + blogCommentId);
			btn_closeEdit.setAttribute("title", "Undo");
			btn_closeEdit.addEventListener("click", function() {
				var span_comment = document.createElement("span");
				span_comment.innerHTML = existingComment.innerHTML;
				commentCell.replaceChild(span_comment, txtBox_editComment);
				span_comment.setAttribute("id", "blogComment" + blogCommentId);
				btn_editSaveComment.setAttribute("title", "Edit");
				btn_editSaveComment.classList.remove("fa-check");
				btn_editSaveComment.classList.add("fa-edit");
				btn_editSaveComment.style.color = "#343a40";
				div_commentOperations.removeChild(btn_closeEdit);
			});
			div_commentOperations.insertBefore(btn_closeEdit, div_commentOperations.childNodes[2]);
			btn_editSaveComment.setAttribute("title", "Save");
			$compile(txtBox_editComment)($scope);
			$scope.blogCommentEdit.commentMessage = existingComment.innerHTML.trim();
		}

	};

});
