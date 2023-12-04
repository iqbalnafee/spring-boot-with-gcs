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
        // /*deleteItem: (self, id, nameEn, nameBn, url, deletePopupMsg, successMsgEn, successMsgBn, failMsgEn, failMsgBn) => {
        //     deletePopupMsg = `Do you want to delete ?`;
        //     deleteDialog(deletePopupMsg, null, commonProp.isLanguageEng, () => {
        //         executeURL(`${commonProp.defaultSuccessUrl}${url}/${id}`, data => {
        //             let msg;
        //             if (data.success) {
        //                 CustomToast.successToast(commonProp.deleteSuccessMessage);
        //                 self.reloadData(pagingClosure.getCurrentPage(), pagingClosure.getPageSize());
        //             } else {
        //
        //                 if (failMsgEn !== null && failMsgEn !== undefined && failMsgEn !== '' &&
        //                     failMsgBn !== null && failMsgBn !== undefined && failMsgBn !== '') {
        //                     msg = commonProp.isLanguageEng ? failMsgEn : failMsgBn;
        //                 }
        //                 else if (data.message !== undefined && data.message.toString().trim().length !== 0 ) {
        //                     CustomToast.errorToast(data.message);
        //                 }
        //                 else
        //                     msg = commonProp.deleteFailMessage;
        //                 CustomToast.errorToast(msg);
        //             }
        //         }, () => {
        //             CustomToast.errorToast(commonProp.deleteFailMessage);
        //         }, 'DELETE');
        //     });
        // },*/


        execute: (url, success, failed, met = 'GET') => {
            executeURL(url, success, failed, met);
        },
        getSearchParam: () => paramQueries,
        getSearchNavName: () => searchNav,
        isLoadInitially: () => loadInitially
    })
};