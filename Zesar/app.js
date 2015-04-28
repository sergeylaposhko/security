angular.module('zesarApp', []).controller('ZesarController', ['$scope', function ($scope) {
    $scope.input = 'раз два три четыре пять яяяя юююю';
    $scope.key = '2';
    $scope.encoded = '3';
    $scope.decoded = '4';

    var lettersCount = 32;
    var firstLetter = 'а'.charCodeAt(0);

    $scope.encode = function encode(value, key) {
        var res = '';

        for (var i = 0; i < value.length; i++) {
            if (value[i] === ' ') {
                res += ' ';
                continue;
            }
            var currentCharNumber = value.charCodeAt(i) - firstLetter;
            var resCharNumber = (currentCharNumber + parseInt(key)) % lettersCount;
            var resultCharCode = resCharNumber + firstLetter;
            res += String.fromCharCode(resultCharCode);
        }

        return res;
    };

    $scope.decode = function decode(value, key) {
        var res = '';

        for (var i = 0; i < value.length; i++) {
            if (value[i] === ' ') {
                res += ' ';
            } else {
                var currentCharNumber = value.charCodeAt(i) - firstLetter;
                var resLetterNumber = currentCharNumber - key;
                while (resLetterNumber < 0) {
                    resLetterNumber += lettersCount;
                }
                var resultCharCode = resLetterNumber + firstLetter;
                res += String.fromCharCode(resultCharCode);
            }
        }

        return res;
    };


}])