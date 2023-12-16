let timeoutVar;
function requiredMissing(errorMsg = 'Required param is missing') {
    throw new Error(errorMsg);
}

const searchAction = (reloadUrl = requiredMissing('reload url parameter is required'), searchNav = 'searchNav', loadInitially = true, defaultParams = {}, validateInputFunction, helperParams = {},) => {
    let paramQueries = {pageNo: 1, pageSize: pagingClosure.getMinimumPageSize()};

    function encodeQueryData(data) {
        const ret = [];
        for (let d in data) {
            ret.push(`${encodeURIComponent(d)}=${encodeURIComponent(data[d])}`);
        }
        return ret.join('&');
    }

    async function fetchAPI(url, method) {
        const response = await fetch(url, {method: method, async: false});
        if (response.status === 200) {
            return response.json();
        }
        throw new Error('Error is occurred');
    }

    function executeURL(url, success, failed, method = 'GET') {
        showLoader();
        fetchAPI(url, method).then(data => {
            hideLoader();
            console.log('success : ', data);
            success(data);
        }).catch(err => {
            hideLoader();
            console.log('Error : ', err);
            if (typeof (failed) === 'function') {
                failed(err);
            }
        });
    }

    function buildSearchParams(pageNo, pageSize) {
        pageNo = pageNo ? pageNo : 1;
        pageSize = pageSize ? pageSize : pagingClosure.getMinimumPageSize();
        let params = `pageNo=${pageNo}&pageSize=${pageSize}`;
        if (document.getElementsByClassName('li_3').length > 0) {
            const startPage = document.getElementsByClassName('li_3')[0].textContent.trim();
            params += `&startPage=${startPage}`;
        }
        if (searchNav) {
            const searchParams = $('#' + searchNav).serializeArray()
                .filter(e => e.value)
                .map(e => {
                    if (e.name.endsWith("Hidden")) {
                        e.name = e.name.substring(0, e.name.indexOf('Hidden'));
                    }
                    return e;
                })
                .map(e => helperParams.doNotConvertToEnglish ?
                    `${encodeURIComponent(e.name)}=${encodeURIComponent(e.value)}` :
                    `${encodeURIComponent(e.name)}=${encodeURIComponent(e.value)}`)
                .join("&");
            if (searchParams) {
                params += `&${searchParams}`;
            }
        }
        paramQueries = params;
        console.log(params);
        return params;
    }

    return Object.freeze({
        reloadData: (pageNo = 1, pageSize = pagingClosure.getMinimumPageSize(), success, startPage = 1, pageRefresh = false) => {
            console.debug('reloadData is called..');
            if (validateInputFunction && typeof validateInputFunction === 'function') {
                if (!validateInputFunction()) {
                    return;
                }
            }
            let url = `${commonProp.defaultSuccessUrl}${reloadUrl}?${buildSearchParams(pageNo, pageSize)}`;
            console.debug('helperParams: ' +JSON.stringify(helperParams));
            console.debug('defaultParams: ' +JSON.stringify(defaultParams));
            if (defaultParams) {
                for (let e in defaultParams) {
                    url += `&${e}=${defaultParams[e]}`;
                }
            }
            console.log('url: ', url);
            executeURL(url, data => {
                const startingSerial = pageSize * (pageNo - 1);
                setTableContent(data, startingSerial);
                pagingClosure.successCallBack(data.totalCount, startPage, pageNo, pageRefresh);
                buildSearchParams(pageNo, pageSize);
                if (success && typeof success === 'function') {
                    success();
                }
            });
        },
        loadDataWithParams: (queryParamsObj = requiredMissing('url parameter object is required'), successCallback, errorCallBack) => {
            let QueryParamsString = encodeQueryData(queryParamsObj);
            let url = `${commonProp.defaultSuccessUrl}${reloadUrl}?${QueryParamsString}`;

            executeURL(url, data => {
                successCallback(data);
            }, err => {
                errorCallBack(err);
            });
        },
        deleteItem: (self, id, url, deletePopupMsg) => {
            deletePopupMsg = `Do you want to delete ?`;
            deleteDialog(deletePopupMsg, null, () => {
                executeURL(`${commonProp.defaultSuccessUrl}${url}/${id}`, data => {
                    let msg;
                    if (data.success) {
                        CustomToast.successToast('Deleted Successfully!');
                        self.reloadData(pagingClosure.getCurrentPage(), pagingClosure.getPageSize());
                    } else {

                        CustomToast.errorToast('Can not delete!');
                    }
                }, () => {
                    CustomToast.errorToast('Can not delete');
                }, 'DELETE');
            });
        },


        execute: (url, success, failed, met = 'GET') => {
            executeURL(url, success, failed, met);
        },
        getSearchParam: () => paramQueries,
        getSearchNavName: () => searchNav,
        isLoadInitially: () => loadInitially
    })
};

function deleteDialog(deleteMsg, message, successFunction, cancelFunction) {
    if (!message) {
        message =  "You will not be able to revert.";
    }
    if (!deleteMsg) {
        deleteMsg =  'Do you want to delete?';
    }
    const deleteButtonText = 'Delete';
    const cancelButtonText = 'Cancel';
    messageDialog(deleteMsg, message, 'error', true, deleteButtonText, cancelButtonText, successFunction, cancelFunction);
}



class CustomToast {
    static DEFAULT_FADE_TIME_MS = 10000;
    static toastClass = "custom-toast";
    static showClass = "custom-toast-show";
    static messageClass = "custom-toast-message";
    static closeBtnClass = "custom-toast-close";
    static $successToastDiv = $('#success-toast-div');
    static $errorToastDiv = $('#error-toast-div');

    static showToast($toastDiv, showClassName) {
        $toastDiv.addClass(showClassName);
    }

    static hideToast($toastDiv) {
        $toastDiv.removeClass(CustomToast.showClass);
    }

    static show($toastDiv, message, timeOutInMs, callback) {
        clearTimeout(timeoutVar);
        CustomToast.hideSuccessToast();
        CustomToast.hideErrorToast();
        $toastDiv.find(`.${CustomToast.messageClass}`).text(message);

        CustomToast.showToast($toastDiv, CustomToast.showClass);
        if (timeOutInMs) {
            timeoutVar=setTimeout(() => {
                CustomToast.hideToast($toastDiv);
                if(callback && typeof callback === 'function'){
                    callback();
                }
            }, timeOutInMs);
        }
    }

    static success(message, timeOutInMs, callback) {
        CustomToast.show(CustomToast.$successToastDiv, message, timeOutInMs, callback);
    }

    static successToast(message, callback) {
        CustomToast.show(CustomToast.$successToastDiv, message, CustomToast.DEFAULT_FADE_TIME_MS, callback);
    }

    static error(message, timeOutInMs, callback) {
        CustomToast.show(CustomToast.$errorToastDiv, message, timeOutInMs, callback);
    }

    static errorToast(message, callback) {
        CustomToast.show(CustomToast.$errorToastDiv, message, CustomToast.DEFAULT_FADE_TIME_MS, callback);
    }

    static hideErrorToast() {
        console.debug('hide error toast is called');
        CustomToast.hideToast(CustomToast.$errorToastDiv);
    }

    static hideSuccessToast() {
        CustomToast.hideToast(CustomToast.$successToastDiv);
    }
}