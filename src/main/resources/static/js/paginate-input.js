/*
       clickedBtn = 1 for first paginate button
       clickedBtn = 2 for back paginate button
*/
const pagingClosure = (function () {
    const pagingButtonSize = 5;
    const pagingButtonSizeWithoutFistButton = pagingButtonSize - 1;
    const lastPagingButtonPos = pagingButtonSize + 2;
    const firstPagingButtonPos = 3;
    const nextButtonPos = lastPagingButtonPos + 1;
    const doubleNextButtonPos = lastPagingButtonPos + 2;
    const defaultPagingOptionValues = [10, 25, 50, 100];
    let pagingOptionValues = [10, 25, 50, 100];
    let clickedBtn = firstPagingButtonPos;
    let pageSize = pagingOptionValues[0];
    let currentPage = 1;
    let currentSelectBtn = firstPagingButtonPos;
    let pageCount = 0;

    const pageValue = [];
    for (let i = 0; i < pagingButtonSize; i++) {
        pageValue[i] = i + 1;
    }
    let pageSizeChanged = false;

    function setDisabled(className, val) {
        let selectors = document.getElementsByClassName(className);
        for (let i = 0; i < selectors.length; i++) {
            selectors[i].disabled = val;
        }
    }

    function show(className) {
        let selectors = document.getElementsByClassName(className);
        for (let i = 0; i < selectors.length; i++) {
            selectors[i].style.display = 'block';
        }
    }

    function hide(className) {
        let selectors = document.getElementsByClassName(className);
        for (let i = 0; i < selectors.length; i++) {
            selectors[i].style.display = 'none';
        }
    }

    function addClass(className) {
        let selectors = document.getElementsByClassName(className);
        for (let i = 0; i < selectors.length; i++) {
            selectors[i].classList.add('search-active-btn');
        }
    }

    function removeClass(className) {
        let selectors = document.getElementsByClassName(className);
        for (let i = 0; i < selectors.length; i++) {
            selectors[i].classList.remove('search-active-btn');
        }
    }

    function calculatePageNo(page) {
        switch (page) {
            case 1:
                return 1;
            case 2:
                return currentPage - 1;
            case 8:
                return currentPage + 1;
            case 9:
                return pageCount;
            default:
                return pageValue[page - firstPagingButtonPos];
        }
    }

    return {
        getMinimumPageSize: function () {
            return pagingOptionValues[0];
        },
        getMaximumPageSize: function () {
            return pagingOptionValues[pagingOptionValues.length - 1];
        },
        setPagingOptionValues: function (arr) {
            pagingOptionValues = arr.filter(e => e && !isNaN(e) && e > 0)
                .map(e => parseInt(e))
                .filter((e, i, a) => a.indexOf(e) === i) //removing duplicate
                .sort((a, b) => parseInt(a) - parseInt(b));
            if (pagingOptionValues.length === 0) {
                pagingOptionValues = defaultPagingOptionValues;
            }
            pageSize = pagingOptionValues[0];
        },
        getPagingOptionValues: function () {
            return pagingOptionValues;
        },
        getClickedBtn: function () {
            return clickedBtn;
        },
        getPageSize: function () {
            return pageSize;
        },
        setPageSize: function (ps = pagingOptionValues[0]) {
            if (!ps || isNaN(ps)) {
                ps = pagingOptionValues[0];
            }
            pageSize = ps;
        },
        getCurrentPage: function () {
            return currentPage;
        },
        getCurrentSelectBtn: function () {
            return currentSelectBtn;
        },
        setClickedBtn: function (clkBtn) {
            clickedBtn = clkBtn;
        },
        pageClick: function (page) {
            console.log('page : ' + page);
            clickedBtn = page;
            reloadData(calculatePageNo(page), pageSize);
        },

        changePageSize: function (value) {
            pageSize = value;
            console.log('pageSize : ' + pageSize);
            [...document.getElementsByClassName('page-size')].forEach(e => e.value = value);
            pageSizeChanged = true;
            reloadData(1, pageSize);
        },

        successCallBack: function (totalCount, startPage = 1, pageNo = 1, pageRefresh = false) {
            if (totalCount <= pagingOptionValues[0]) {
                hide('pagination-div')
                return;
            }
            pageCount = Math.ceil(totalCount / pageSize);
            if (pageCount <= pagingButtonSize) {
                for (let idx = firstPagingButtonPos; idx <= pageCount + firstPagingButtonPos - 1; idx++) {
                    show('li_' + idx);
                }
                for (let idx = pageCount + firstPagingButtonPos; idx <= lastPagingButtonPos; idx++) {
                    hide('li_' + idx);
                }
            } else {
                for (let idx = firstPagingButtonPos; idx <= pagingButtonSize; idx++) {
                    show('li_' + idx);
                }
            }
            show('pagination-div');
            if (pageSizeChanged) {
                pageSizeChanged = false;
                clickedBtn = 1;
            }
            if (pageRefresh) {
                pageNo = parseInt(pageNo);
                if (pageNo <= 0 || pageNo > pageCount) {
                    hide('pagination-div')
                    return;
                }
                pageValue.length = 0;
                if (pageCount <= pagingButtonSize) {
                    for (let i = 1, j = 3; i <= pageCount; i++, j++) {
                        pageValue.push(i);
                        if (i === pageNo) {
                            addClass('li_' + j);
                            currentSelectBtn = j;
                        } else {
                            removeClass('li_' + j);
                        }
                    }
                } else {
                    if (startPage && !isNaN(startPage)) {
                        startPage = parseInt(startPage);
                        if (pageNo < startPage) {
                            startPage = pageNo;
                        }
                    } else {
                        startPage = pageNo;
                    }
                    if (startPage + pagingButtonSizeWithoutFistButton > pageCount) {
                        startPage = pageCount - pagingButtonSizeWithoutFistButton;
                    }
                    if (pageNo > startPage + pagingButtonSizeWithoutFistButton) {
                        if (pageNo + pagingButtonSizeWithoutFistButton <= pageCount) {
                            startPage = pageNo;
                        } else {
                            startPage = pageCount - pagingButtonSizeWithoutFistButton;
                        }
                    }
                    for (let i = 0, j = 3; i < pagingButtonSize; i++, j++) {
                        let textValue = startPage + i;
                        pageValue.push(textValue);
                        let elms = document.getElementsByClassName('li_' + j);
                        for (let k = 0; k < elms.length; k++) {
                            elms[k].textContent = commonProp.isLanguageEng ? textValue : numberUtil.convertToBanglaNumber(textValue) + '';
                            if (textValue === pageNo) {
                                elms[k].classList.add('search-active-btn');
                                currentSelectBtn = j;
                            } else {
                                elms[k].classList.remove('search-active-btn')
                            }
                        }
                    }
                }
            } else {
                switch (clickedBtn) {
                    case 1: {
                        let minPageCount = pageCount >= pagingButtonSize ? pagingButtonSize : pageCount;
                        for (let i = 1; i <= minPageCount; i++) {
                            let x = i + 2;
                            show('li_' + x);
                            pageValue[i - 1] = i;
                            $('.li_' + x).text(commonProp.isLanguageEng ? i : numberUtil.convertToBanglaNumber(i));
                            removeClass('li_' + x);
                        }
                        if (minPageCount < pagingButtonSize) {
                            for (let i = minPageCount + 1; i <= pagingButtonSize; i++) {
                                let x = i + 2;
                                pageValue[i - 1] = i;
                                hide('li_' + x);
                            }
                        }
                        currentSelectBtn = firstPagingButtonPos;
                        addClass('li_' + firstPagingButtonPos);
                    }
                        break;
                    case 2: {
                        let firstPageValue = pageValue[0];
                        if (firstPageValue === currentPage) {
                            if (firstPageValue > 1) {
                                if (firstPageValue > pagingButtonSize) {
                                    let x = firstPageValue - pagingButtonSize;
                                    for (let i = firstPagingButtonPos; i <= lastPagingButtonPos; i++) {
                                        removeClass('li_' + i);
                                        pageValue[i - firstPagingButtonPos] = x;
                                        $('.li_' + i).text(commonProp.isLanguageEng ? x : numberUtil.convertToBanglaNumber(x));
                                        x = x + 1;
                                    }
                                    addClass('li_' + lastPagingButtonPos);
                                    currentSelectBtn = lastPagingButtonPos;
                                } else {
                                    for (let i = firstPagingButtonPos, x = 1; i <= lastPagingButtonPos; i++, x++) {
                                        pageValue[i - firstPagingButtonPos] = x;
                                        $('.li_' + i).text(commonProp.isLanguageEng ? x : numberUtil.convertToBanglaNumber(x));
                                        if (x === firstPageValue - 1) {
                                            addClass('li_' + i);
                                            currentSelectBtn = i;
                                        } else {
                                            removeClass('li_' + i);
                                        }
                                    }
                                }
                            } else {

                            }
                        } else {
                            removeClass('li_' + currentSelectBtn);
                            currentSelectBtn = currentSelectBtn - 1;
                            addClass('li_' + currentSelectBtn);
                        }
                    }
                        break;
                    case nextButtonPos: {
                        let lastPageValue;
                        if (pageCount >= pagingButtonSize) {
                            lastPageValue = pageValue[lastPagingButtonPos - firstPagingButtonPos];
                        } else {
                            lastPageValue = pageValue[pageCount];
                        }
                        if (lastPageValue === currentPage) {
                            let div = pageCount - currentPage;
                            let startValue;
                            if (div >= pagingButtonSize) {
                                startValue = lastPageValue + 1;
                            } else {
                                startValue = pageCount - (pagingButtonSize - 1);
                            }
                            for (let i = firstPagingButtonPos; i <= lastPagingButtonPos; i++, startValue++) {
                                pageValue[i - firstPagingButtonPos] = startValue;
                                $('.li_' + i).text(commonProp.isLanguageEng?startValue:numberUtil.convertToBanglaNumber(startValue));
                                if (startValue === lastPageValue + 1) {
                                    addClass('li_' + i);
                                    currentSelectBtn = i;
                                } else {
                                    removeClass('li_' + i);
                                }
                            }
                        } else {
                            removeClass('li_' + currentSelectBtn);
                            currentSelectBtn = currentSelectBtn + 1;
                            addClass('li_' + currentSelectBtn);
                        }
                    }
                        break;
                    case doubleNextButtonPos: {
                        let sval, endButtonPos;
                        if (pageCount >= pagingButtonSize) {
                            sval = pageCount - pagingButtonSize;
                            endButtonPos = lastPagingButtonPos;
                        } else {
                            sval = 0;
                            endButtonPos = pageCount + 2;
                        }
                        for (let i = 1; i <= endButtonPos - 2; i++) {
                            let x = i + 2;
                            pageValue[i - 1] = sval + i;
                            $('.li_' + x).text(commonProp.isLanguageEng?sval + i:numberUtil.convertToBanglaNumber(sval + i));
                            removeClass('li_' + x);
                        }
                        addClass('li_' + endButtonPos);
                        currentSelectBtn = endButtonPos;
                    }
                        break;
                    default:
                        for (let i = firstPagingButtonPos; i <= lastPagingButtonPos; i++) {
                            removeClass('li_' + i);
                        }
                        addClass('li_' + clickedBtn);
                        currentSelectBtn = clickedBtn;
                }
            }
            currentPage = pageValue[currentSelectBtn - firstPagingButtonPos];
            clickedBtn = currentSelectBtn;
            if (currentPage === 1) {
                setDisabled('li_1', true);
                setDisabled('li_2', true);
            } else {
                setDisabled('li_1', false);
                setDisabled('li_2', false);
            }
            if (currentPage === pageCount) {
                setDisabled('li_' + nextButtonPos, true);
                setDisabled('li_' + doubleNextButtonPos, true);
            } else {
                setDisabled('li_' + nextButtonPos, false);
                setDisabled('li_' + doubleNextButtonPos, false);
            }
        }
    }
})();
