angular.module('rsaApp', []).controller('RsaController', ['$scope', '$http', function($scope, $http) {
    $scope.input = 'one two and other words for encryption';
    $scope.key = 'key';
    $scope.encr = '';
    $scope.decr = '';
    $scope.n = '';

    $scope.sendData = function sendData () {
        var sendData = $scope.input;
        var key = $scope.key;
        //api/des?text=someText&password=somePass
        $http.get('api/des?text='+sendData+'&password='+key).success(function(data){
            console.log(data);
            $scope.encr = data.encrypted;
            $scope.decr = data.decrypted;
            $scope.keys = data.encryptKeys;
            $scope.keysStrings = data.encryptKeysStrings;
            $scope.keyHash = data.keyHash;
        });
    }

}]);