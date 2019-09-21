
(function jtNavBar() {
    //点击伸缩导航条
    $(document).on("click",".jt-nav-bar .navbar-toggle",function () {
        var $navbar_collapse = $(this).parents(".jt-nav-bar").find(`#${$(this).attr("data-id")}`)
        if ($navbar_collapse.attr("data-in") == "false") {
            $navbar_collapse.stop().animate({
                height: $navbar_collapse.find(".navbar-text").length * 50 + "px"
            }, 400, function () { $navbar_collapse.css("height", "") }).attr("data-in", "true")

            $navbar_collapse.siblings(".navbar-collapse").stop().animate({
                height: '0'
            }, 400, function () { $navbar_collapse.siblings(".navbar-collapse").css("height", "") }).attr("data-in", "false")
        } else {
            $navbar_collapse.stop().animate({
                height: '0'
            }, 400, function () { $navbar_collapse.css("height", "") }).attr("data-in", "false")
        }
    })
}());