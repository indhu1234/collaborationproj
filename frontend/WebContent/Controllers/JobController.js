myApp.controller("JobController", function($scope, $http, $location, $rootScope, $cookieStore) {

	$scope.jobList = [];
	$scope.userJobList = [];
	$scope.jobDetail;
	$scope.editJobInfo;

	$scope.job = {
		'jobDesignation' : '',
		'jobProfile' : '',
		'username' : '',
		'jobStatus' : '',
		'qualificationRequired' : '',
		'jobDescription' : ''
	};

	$scope.addJob = function() {
		$scope.job.username = $rootScope.currentUser.username;
		$scope.job.jobStatus = 'open';
		$http.post('http://localhost:' + location.port + '/middleware/addJob', $scope.job).then(function(response) {
			
			$("#addJobModal").modal("hide");
			$("#addJobSuccessfulModal").on('hidden.bs.modal', function(){
			    location.reload();
			  });
			$("#addJobSuccessfulModal").modal("show");
			console.log('Job added');
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

	$scope.showEditJob = function() {
		var urlText = window.location.href;
		var editJobId = urlText.substring(urlText.indexOf("=") + 1);
		$http.get('http://localhost:' + location.port + '/middleware/getJob/' + editJobId).then(function(response) {
			$scope.editJobInfo = response.data;
			delete $scope.editJobInfo.createdDate;
		});
	}

	$scope.updateJob = function() {
		$scope.editJobInfo.status = 'P';
		$http.post('http://localhost:' + location.port + '/middleware/updateJob', $scope.editJobInfo).then(function(response) {
			$("#updateJobSuccessfulModal").on('hidden.bs.modal', function(){
			    $location.path("/job");
			    $scope.$apply();
			  });
			$("#updateJobSuccessfulModal").modal("show");
			console.log("Job updated.");
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

	$scope.getJobs = function() {
		// get all approved jobs
		$http.get('http://localhost:' + location.port + '/middleware/getJobList').then(function(response) {

			$scope.jobList = response.data;
			$scope.jobList.sort(function(a, b) {
				return new Date(a.postedDate) - new Date(b.postedDate);
			});

			console.log("Job list retrieved.");
		});
		// get all jobs created by current user
		$http.get('http://localhost:' + location.port + '/middleware/getUserJobList/' + $rootScope.currentUser.username).then(function(response) {
			$scope.userJobList = response.data;
			$scope.userJobList.sort(function(a, b) {
				return new Date(a.postedDate) - new Date(b.postedDate);
			});
			console.log($rootScope.currentUser.username + "'s job list retrieved.");
		});
	};

	$scope.openJob = function(jobId) {
		$http.get('http://localhost:' + location.port + '/middleware/openJob/' + jobId).then(function(response) {
			console.log("Job opened");
			location.reload();
		});
	}

	$scope.closeJob = function(jobId) {
		$http.get('http://localhost:' + location.port + '/middleware/closeJob/' + jobId).then(function(response) {
			console.log("Job closed");
			location.reload();
		});
	}

	$scope.deleteJob = function(jobId) {
		$http.get('http://localhost:' + location.port + '/middleware/deleteJob/' + jobId).then(function(response) {
			console.log("Job deleted");
			var jobRow = document.getElementById("jobRow" + jobId);
			jobRow.parentNode.removeChild(jobRow);
		});
	}

	$scope.showJob = function(jobId) {
		if($location.path()!='/showJob'){
			$cookieStore.put("showJobId",jobId);
			$location.path("/showJob");
		}
		else{
			jobId=$cookieStore.get("showJobId");
			$http.get('http://localhost:' + location.port + '/middleware/getJob/' + jobId).then(function(response) {
				$scope.jobDetail = response.data;
			});
		}
	}
	
});

function jobMenuSwitch(tabId) {
	var tabPaneAllJobs = document.getElementById("tabPane-AllJobs");
	var tabPaneYourJobs = document.getElementById("tabPane-YourJobs");
	switch (tabId) {
	case "tab-AllJobs":
		tabPaneYourJobs.style.display = "none";
		tabPaneAllJobs.style.display = "";
		break;
	case "tab-YourJobs":
		tabPaneAllJobs.style.display = "none";
		tabPaneYourJobs.style.display = "";
		break;
	}
}