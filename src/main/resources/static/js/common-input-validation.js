let commonInputValidation = (function () {
    function isDelUpDownLeftKey(event) {
        switch (event.key) {
            case "Backspace":
            case "ArrowRight":
            case "ArrowLeft":
                return true;
        }
        return false;
    }

    return {
        inputValidation: function (event, thiz) {
            if (isDelUpDownLeftKey(event)) {
                return true;
            }
            let self = $(thiz);
            if (self.attr('numbertype')) {
                let numType = self.attr('numbertype');
                let maxValue = self.attr('maxValue');
                let isValid;
                if (numType === 'int') {
                    isValid = inputValidationForIntValue(event, self, maxValue);
                } else {
                    let decimalPoint = self.attr('decimalPoint');
                    if (!decimalPoint) {
                        decimalPoint = 2;
                    }
                    isValid = inputValidationForFloatValue(event, self, decimalPoint, maxValue);
                }
                return isValid;
            }

            return true;
        }
    }
})();

function inputValidationForIntValue(event, element, maxValue) {
    if (event) {
        switch (event.key) {
            case "Backspace":
            case "ArrowRight":
            case "ArrowLeft":
                return true;
        }
        let isDigit = inputValidationForDigit(event);
        if (isDigit) {
            let curPosition = element[0].selectionStart;
            let oldValue = element.val();
            if (curPosition === 0) {
                if ((event.key === '0') && oldValue.length === 0) {
                    return true;
                }
            }

            let curValue = buildInputValue(event, element);
            if (parseInt(oldValue) === parseInt(curValue)) {
                return false;
            }
            if (maxValue == null) {
                return true;
            }
            return parseInt(maxValue) >= parseInt(curValue);
        }
    }
    return false;
}

function inputValidationForDigit(e) {
    if (e) {

        switch (e.key) {
            case "0":
            case "1":
            case "2":
            case "3":
            case "4":
            case "5":
            case "6":
            case "7":
            case "8":
            case "9":
                return true;
        }
    }
    return false;
}

function buildInputValue(event, element) {
    let curPosition = element[0].selectionStart;
    let curValue = element.val();
    let left = "", right = "";
    for (let i in curValue) {
        if (i < curPosition) {
            left = left + curValue[i];
        } else {
            right = right + curValue[i];
        }
    }
    return left + event.key + right;
}

function inputValidationForFloatValue(event, element, maxDecimalDigit, maxValue) {
    if (event) {
        switch (event.key) {
            case "Backspace":
            case "ArrowRight":
            case "ArrowLeft":
                return true;
        }
        let isDigit = inputValidationForDigit(event);
        if (isDigit || event.key === '.') {
            let value = element.val();
            let curPosition = element[0].selectionStart;
            let dotIndex = value.search(/[.]{1}/);
            if (event.key === '.') {
                return dotIndex < 0;
            }

            if (dotIndex >= 0) {
                if (curPosition > dotIndex) {
                    if (maxDecimalDigit != null) {
                        let decimalPart = value.substring(dotIndex + 1);
                        if (decimalPart.length < parseInt(maxDecimalDigit)) {
                            return compareValueWithMaxValue(event, element, maxValue);
                        }
                        return false;
                    }
                    return true;
                } else {
                    if ((event.key === '0' || event.key === '০') && curPosition === 0 && dotIndex === 0) {
                        return true;
                    }
                    let newValue = buildInputValue(event, element);
                    if (parseFloat(value) === parseFloat(newValue)) {
                        return false;
                    }
                    return compareValueWithMaxValue(event, element, maxValue);
                }
            } else {
                return inputValidationForIntValue(event, element, maxValue);
            }
        }
    }
    return false;
}

function compareValueWithMaxValue(event, element, maxValue) {
    if (maxValue != null) {
        let buildValue = buildInputValue(event, element);
        return parseFloat(maxValue) >= parseFloat(buildValue);
    }
    return true;
}