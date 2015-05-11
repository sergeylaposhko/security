angular.module('rsaApp', []).controller('RsaController', ['$scope', '$http', function ($scope, $http) {

    $scope.login = '';

    $scope.messages = [];

    $scope.privateKey = '';
    $scope.modPow = '';
    $scope.opponentKey = '';

    $scope.generatedKey = '';

    $scope.g = '';
    $scope.p = '';

    $http.get('api/gp').success(function (data) {
        $scope.g = data.g;
        $scope.p = data.p;

    });

    $scope.generateKeyData = function () {
        $scope.privateKey = Math.floor(Math.random() * 1000);
        $scope.modPow = new bigInt($scope.g).modPow($scope.privateKey, $scope.p).toJSNumber();
    };

    $scope.sendGeneratedData = function () {
        $http.post('api/key?login=' + $scope.login + "&key=" + $scope.modPow).success(function (data) {
            console.log('Response after putting keys.');
            console.log(data);
        });
    };

    $scope.getKeyData = function () {
        $http.get('api/key?login='+$scope.login).success(function(data){
            $scope.opponentKey = data[0];
            $scope.generatedKey = new bigInt($scope.opponentKey).modPow($scope.privateKey, $scope.p).toJSNumber();
        });
    };

    $scope.updateMessages = function () {
        $scope.messages = $http.get('api/message');
    };

    $scope.sendData = function sendData() {
        var sendData = $scope.input;
        var key = $scope.key;

        $http.get('api/des?text=' + sendData + '&password=' + key).success(function (data) {
            console.log(data);
            $scope.encr = data.encrypted;
            $scope.decr = data.decrypted;
            $scope.keys = data.encryptKeys;
            $scope.keysStrings = data.encryptKeysStrings;
            $scope.keyHash = data.keyHash;
        });
    };


    var encrypted = CryptoJS.DES.encrypt("Message", "Secret Passphrase");
    var decrypted = CryptoJS.DES.decrypt(encrypted, "Secret Passphrase");

    var util = {
        primes: [2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997],
        counter: 0,

        getRandomPrime: function () {
            var generatedIndex = Math.random() * this.primes.length;
            generatedIndex = Math.floor(generatedIndex);
            console.log('Generated index is: ' + generatedIndex)
            return this.primes[generatedIndex];
        }
    }

}]);