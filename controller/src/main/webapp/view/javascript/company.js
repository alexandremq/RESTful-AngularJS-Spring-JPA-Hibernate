var app = angular.module("CompanyManagement", []);
app.controller('CompanyController', function($scope, $http){
	$scope.statusMsg = "Select a tab to start.";
	
	//ajax call to get all companies stored in the application repository. Happens whenever page is loaded.
	$http.get("/cma/services/companies")
		.success(function (data, status, headers, config){
			$scope.companies = data;
		})
		.error(function (data, status, headers, config){
			$scope.statusMsg = "An internal error ocurred preventing loading all companies. Message: "+ JSON.stringify(data);
			$scope.style = "alert-danger";
		});
	
	
	//ajax call to get all owners stored in the application repository. Happens whenever page is loaded.
	$http.get("/cma/services/owners")
		.success(function (data, status, headers, config){
			$scope.owners = data;
		})
		.error(function (data, status, headers, config){
			$scope.statusMsg = "An internal error ocurred preventing loading all owners. Message: "+ JSON.stringify(data);
			$scope.style = "alert-danger";
		});
	
	
	//ajax call to get all details of the company with the given id in the side panel. 
	$scope.companyDetails = function(id){
		$http.get("/cma/services/companies/"+ id)
		.success(function (data, status, headers, config){
			$scope.company = data;
		})
		.error(function (data, status, headers, config){
			$scope.statusMsg = "An internal error ocurred preventing loading company details. Message: "+ JSON.stringify(data);
			$scope.style = "alert-danger";
			return;
		});
		
		$scope.statusMsg = "Company details loaded sucessfully!";
		$scope.style = "alert-success";
	};
	
	
	//show details of the owner with the given id in the side panel. 
	$scope.ownerDetails = function(owner){		
		$scope.owner = owner;		
	};
	
	
	//add the given owner to the current company list of owners.
	$scope.addOwner = function(owner){
		if($scope.company.owners !== undefined){
			if($scope.company.owners.indexOf(owner) >= 0){ //preventing owner to be added again.
				return;
			}			
		} else {
			$scope.company.owners = new Array();
		}
		
		$scope.company.owners.push(owner);
		
		$scope.statusMsg = "Owner added to this company. Use the save button to confirm this operation.";
		$scope.style = "alert-warning";
	};
	
	
	//remove the given owner from the list of owners of the current company.
	$scope.removeOwner = function(owner){
		var index = $scope.company.owners.indexOf(owner);
		$scope.company.owners.splice(index, 1);
		
		$scope.statusMsg = "Owner removed from this company. Use the save button to confirm this operation.";
		$scope.style = "alert-warning";
	}
	
	
	//clean the owner object to allow creation of a new one.
	$scope.newOwner = function(){
		$scope.owner = new Object();
		$scope.statusMsg = "";
		$scope.style = "alert-success";
	}
	
	
	//post request to create a new owner in the repository of this application.
	$scope.saveOwner = function(){
		var index = -1;
		
		//if updating company, this condition ensures getting the index position for later update in the array of companies
		if($scope.owner.ownerId != null){
			for(var i = 0; i < $scope.owners.length; i++){
				if($scope.owners[i].ownerId == $scope.owner.ownerId){
					index = i;
					break;
				}
			}
		}
		
		$http.post("/cma/services/owners/save", $scope.owner)
			.success(function (data, status, headers, config){
				if(index < 0){
					$scope.owners.push(data);
				} else {
					$scope.owners[index] = data;
				}
				$scope.owner = data;
			})
			.error(function (data, status, headers, config){
				$scope.statusMsg = "Owner was not created. An internal error ocurred. Message: "+ JSON.stringify(data);
				$scope.style = "alert-danger";
				return;
			});
		
		$scope.statusMsg = "owner created sucessfully!";
		$scope.style = "alert-success";
	}
	
	
	//clean the company object to allow creation of a new one.
	$scope.newCompany = function(){
		$scope.company = new Object();
		$scope.statusMsg = "";
		$scope.style = "alert-success";
	}
	
	
	//save the given company and all changes applied to that in the repository of this application.
	$scope.saveCompany = function(company){
		var index = -1;
		
		//if updating company, this condition ensures getting the index position for later update in the array of companies
		if(company.companyId != null){
			for(var i = 0; i < $scope.companies.length; i++){
				if($scope.companies[i].companyId == company.companyId){
					index = i;
					break;
				}
			}
		} 
		
		$http.post("/cma/services/companies/save", company)
			.success(function (data, status, headers, config){
				if(index < 0){ // add created company to the list of companies. 
					$scope.companies.push(data);
				} else { //save the updated company in the list of companies.
					$scope.companies[index] = data;			
				}
				$scope.company = data;
			})
			.error(function (data, status, headers, config){
				$scope.statusMsg = "Company was not saved. An internal error ocurred. Message: "+ JSON.stringify(data);
				$scope.style = "alert-danger";
				return;
			});
		
		$scope.statusMsg = "Company saved sucessfully!";
		$scope.style = "alert-success";
	};
	
});