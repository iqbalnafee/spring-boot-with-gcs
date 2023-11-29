function showLoader() {
    $(".page-loader").removeClass("page-loader-hidden");
    $(".page-loader").addClass("page-loader-show");
}

function hideLoader() {
    $(".page-loader").removeClass("page-loader-show");
    $(".page-loader").addClass("page-loader-hidden");
}