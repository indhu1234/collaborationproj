var myApp = angular.module("myApp", [ "ngRoute", "ngCookies" ]);
myApp.config(function($routeProvider) {
	$routeProvider.when("/blog", {
		templateUrl : "Pages/Blog/Blog.html",
	}).when("/showBlog", {
		templateUrl : "Pages/Blog/BlogDetail.html",
	}).when("/manageBlog", {
		templateUrl : "Pages/Blog/ManageBlog.html",
	}).when("/editBlog", {
		templateUrl : "Pages/Blog/EditBlog.html",
	}).when("/forum", {
		templateUrl : "Pages/Forum/Forum.html",
	}).when("/manageForum", {
		templateUrl : "Pages/Forum/ManageForum.html",
	}).when("/editForum", {
		templateUrl : "Pages/Forum/EditForum.html",
	}).when("/showForum", {
		templateUrl : "Pages/Forum/ForumDetail.html",
	}).when("/showUser", {
		templateUrl : "Pages/User/User.html",
	}).when("/friends", {
		templateUrl : "Pages/Friend/Friend.html",
	}).when("/job", {
		templateUrl : "Pages/Job/Job.html",
	}).when("/showJob", {
		templateUrl : "Pages/Job/JobDetail.html",
	}).when("/manageJob", {
		templateUrl : "Pages/Job/ManageJob.html",
	}).when("/editJob", {
		templateUrl : "Pages/Job/EditJob.html",
	}).when("/index2", {
		templateUrl : "index2.html"
	}).when("/search", {
		templateUrl : "Pages/Search.html"
	});
});

myApp.run(function($rootScope, $cookieStore) {
	$rootScope.portNo=location.port;
	if ($rootScope.currentUser == undefined) {
		$rootScope.currentUser = $cookieStore.get('userDetails');
	}
});