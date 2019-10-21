myApp.controller("UserController", function($scope, $http, $location, $rootScope, $cookieStore) {

	
	$scope.errorList=[];
	$scope.editingUserDetails=false;
	
	$scope.user = {
			'username' : '',
			'password' : '',
			'firstName' : '',
			'lastName' : '',
			'email' : '',
			'role' : '',
			'status' : '',
			'isOnline' : ''
		};
	
if($location.path()=="/showUser")
	{
	$scope.user = {
			'username' : $rootScope.currentUser.username,
			'password' : $rootScope.currentUser.password,
			'firstName' : $rootScope.currentUser.firstName,
			'lastName' : $rootScope.currentUser.lastName,
			'email' : $rootScope.currentUser.email,
			'role' : $rootScope.currentUser.role,
			'status' : $rootScope.currentUser.status,
			'isOnline' : $rootScope.currentUser.isOnline,
			'password':$rootScope.currentUser.password,
			'confirmPassword':$rootScope.currentUser.password
		};
	}
	

	$scope.registerUser = {
		'username' : '',
		'password' : '',
		'confirmPassword':'',
		'firstName' : '',
		'lastName' : '',
		'email' : '',
		'role' : '',
		'status' : '',
		'isOnline' : ''
	};

	$scope.register = function() {
		$scope.registerUser.role = 'student';
		$scope.registerUser.status = 'A';
		$scope.registerUser.isOnline = 'Off';

		$http.post('http://localhost:' + location.port + '/middleware/registerUser', $scope.registerUser).then(function(response) {
			var form=document.getElementById("");
			for(i=0;i<form.elements.length;i++)
				{
					form.elements[i].style.border="1px solid #ced4da";
					form.elements[i].removeAttribute("data-original-title");
					form.elements[i].removeAttribute("data-toggle");
					form.elements[i].removeAttribute("title");
				}
			$scope.registerUser = {
					'username' : '',
					'password' : '',
					'confirmPassword':'',
					'firstName' : '',
					'lastName' : '',
					'email' : '',
					'role' : '',
					'status' : '',
					'isOnline' : ''
				};
			$("#registrationSuccessfulModal").modal("show");
		}).catch(function(e){
				console.log(e);
				var form=document.getElementById("registrationForm");
				for(i=0;i<form.elements.length;i++)
					{
					if(form.elements[i].tagName!="BUTTON")
						{
						form.elements[i].style.border="1px solid #ced4da";
						form.elements[i].removeAttribute("data-original-title");
						form.elements[i].removeAttribute("data-toggle");
						form.elements[i].removeAttribute("title");
						}
					
					}
				var errorList;
				if(e.status=="400")
					errorList=e.data;
				else if(e.status="500"){
					errorList=[];
					errorList.push({'fieldName':'username','errorMessage':'Username is already taken.'});
				}
				if(document.getElementById("confirmPassword").value!=document.getElementById("password").value)
					{
					errorList.push({'fieldName':'password','errorMessage':'Passwords do not match.'});
					errorList.push({'fieldName':'confirmPassword','errorMessage':'Passwords do not match.'});
					}
				
				var errorElement;
				errorList.forEach(function(error){
					errorElement=document.getElementById(error.fieldName);
					errorElement.style.border="2px solid red";
					errorElement.setAttribute("data-toggle","tooltip");
					var x=errorElement.getAttribute("data-original-title");
					errorElement.setAttribute("data-original-title",(x?x+" "+error.errorMessage:error.errorMessage));	
				});
				$('[data-toggle="tooltip"]').tooltip(); 
			
		});

	};

	$scope.loginCheck = function() {
		$http.post('http://localhost:' + location.port + '/middleware/checkLogin', $scope.user).then(function() {
			console.log('login successfull');
			$http.get('http://localhost:' + location.port + '/middleware/getUser/' + $scope.user.username).then(function(response) {
				var user=response.data;
				$http.get('http://localhost:' + location.port + '/middleware/getProfilePic/' + user.username).then(function(response) {
					if(response.data){
						console.log("pp found");
						user.profilePicUrl="http://localhost:"+location.port+"/middleware/showProfilePic/"+user.username;
					}else{
						console.log("pp not found");
						user.profilePicUrl='http://localhost:' + location.port + '/frontend/Resources/Images/empty-profile-pic.png';
					}
					console.log(user.profilePicUrl);
					$rootScope.currentUser = user;
					$cookieStore.put('userDetails', $rootScope.currentUser);
					window.location.href = "http://localhost:" + location.port + "/frontend/index2.html#/blog";
					console.log($rootScope.currentUser);
					});
			});
		}).catch(function(e){
			var errorMsg=document.getElementById("loginErrorMsg");
			if(!errorMsg){
				errorMsg=document.createElement("span");
				errorMsg.id="loginErrorMsg";
				errorMsg.innerHTML="Invalid Credentials";
				errorMsg.style.textAlign="center";
				errorMsg.style.color="red";
				document.getElementById("loginContainer").appendChild(errorMsg);
			}
		});
	};

	$scope.logout = function() {
		delete $rootScope.currentUser;
		$cookieStore.remove('userDetails');
		console.log("logout successful.");
		window.location.href = "http://localhost:" + location.port + "/frontend/";
	};
	
	$scope.showEditUser=function()
	{
		$scope.editingUserDetails=true;
		document.getElementById("firstName").removeAttribute("readonly");
		document.getElementById("lastName").removeAttribute("readonly");
		document.getElementById("email").removeAttribute("readonly");
	};
	
	$scope.closeEditUser=function()
	{
		$scope.editingUserDetails=false;
		document.getElementById("firstName").setAttribute("readonly","");
		document.getElementById("lastName").setAttribute("readonly","");
		document.getElementById("email").setAttribute("readonly","");
		var allErrorElements=document.getElementsByClassName("error");
		for(i=0;i<allErrorElements.length;i++)
		  {
			allErrorElements[i].innerHTML="";
		  }
		$scope.user = {
				'username' : $rootScope.currentUser.username,
				'password' : $rootScope.currentUser.password,
				'firstName' : $rootScope.currentUser.firstName,
				'lastName' : $rootScope.currentUser.lastName,
				'email' : $rootScope.currentUser.email,
				'role' : $rootScope.currentUser.role,
				'status' : $rootScope.currentUser.status,
				'isOnline' : $rootScope.currentUser.isOnline,
				'password':$rootScope.currentUser.password,
				'confirmPassword':$rootScope.currentUser.password
			};
	};
	
	$scope.updateUser=function()
	{	
		$http.post('http://localhost:' + location.port + '/middleware/updateUser',$scope.user).then(function(response){
			$rootScope.currentUser.firstName=$scope.user.firstName;
			$rootScope.currentUser.lastName=$scope.user.lastName;
			$rootScope.currentUser.email=$scope.user.email;
			$cookieStore.put('userDetails', $rootScope.currentUser);
			console.log($rootScope.currentUser);
			location.reload();
			console.log("User updated.");
		}).catch(function(e){
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
	};

});

