angular.module('rsaApp', ['ngFileUpload']).controller('RsaController', ['$scope', '$http', 'Upload',
    function ($scope, $http, Upload) {
        $scope.digest = 'DIGEST HERE';
        $scope.crc = '';
        $scope.privateKey = '';
        $scope.publicKey = '';
        $scope.n = '';
        $scope.getDigestFile = null;
        $scope.document = {};
        $scope.testData = 'testData';

        $scope.generateKeys = function generateKeys() {
            var p = util.getRandomPrime();
            var q = util.getRandomPrime();
            console.log('p=' + p);
            console.log('q=' + q);

            var n = p * q;
            var r = (p - 1) * (q - 1);
            console.log('r=' + r);

            var e = 3; //TODO

            var d = -1;
            for (var i = 1; i < r; i++) {
                var mul = i * e;
                var part = mul % r;
                if (part === 1) {
                    console.log(part)
                    d = i;
                    break;
                }
            }
            if (d === -1) {
                generateKeys();
                return;
            }
            console.log('d=' + d);

            $scope.privateKey = d;
            $scope.publicKey = e;
            $scope.n = n;
        }

        $scope.sendData = function sendData() {
            console.log($scope.files);
            $scope.upload($scope.files);
        }

        $scope.decrypt = function decrypt() {
            var res = '';
            var numbers = $scope.received;
            var d = $scope.privateKey;
            var n = $scope.n;

            for (var i = 0; i < numbers.length; i++) {
                var currentNum = new bigInt(numbers[i]);
                console.log('Current num is: ' + currentNum);

                var resCharPoint = currentNum.modPow(d, n);
                console.log('Res carPoint is:' + resCharPoint);
                var jsResCharPoint = resCharPoint.toJSNumber();
                console.log('jsResCharPoint is' + jsResCharPoint);
                var resChar = String.fromCharCode(jsResCharPoint);
                res += resChar;
                console.log('Res char is: ' + resChar);
            }

            $scope.decrypted = res;
        }

        $scope.generateCrc = function(){
            $scope.generateKeys();
            var digest = new bigInt($scope.digest);
            var modPow = digest.modPow($scope.privateKey, $scope.n);
            $scope.crc = modPow.toJSNumber();
        }

        $scope.upload = function (files) {
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    console.log(file);
                    Upload.upload({
                        url: 'api/crc?digest=digest1&crc=crc1',
                        fields: {'digest': $scope.digest, 'crc':$scope.crc, 'publicKey':$scope.publicKey, 'n':$scope.n},
                        file: file
                    }).progress(function (evt) {
                        var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                        console.log('progress: ' + progressPercentage + '% ' + evt.config.file.name);
                    }).success(function (data, status, headers, config) {
                        $scope.checkDigestRes = data;
                    }).error(function(err){
                        console.log(err);
                    });
                }
            }
        };

        $scope.getDigest = function () {
            var files = $scope.getDigestFile;
            if (files && files.length) {
                for (var i = 0; i < files.length; i++) {
                    var file = files[i];
                    console.log(file);
                    Upload.upload({
                        url: 'api/getCrc',
                        file: file
                    }).progress(function (evt) {
                    }).success(function (data, status, headers, config) {
                        $scope.serverDigest = data.crc;
                        console.log(data);
                    }).error(function(err){
                        console.log(err);
                    });
                }
            }
        };

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

    }])