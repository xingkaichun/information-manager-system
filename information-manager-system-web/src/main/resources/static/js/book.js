$(function () {
    $(document).on("click", ".link_content .rank-one-text", function () {
        var $rank_one = $(this).parents(".rank-one")
        if ($rank_one.attr("is-open")=="false") {
            // $rank_one.find(".rank-two").show()
            $rank_one.removeAttr("is-open")
        } else {
            $rank_one.attr("is-open","false")
            // $rank_one.find(".rank-two").hide()
        }
    })
    $(".rank-one").each(function(){
        if($(this).find(".rank-two").length){
            $(this).find(".rank-one-text .link_li").append(`<label><img src="/images/arrows.png"/></label>`)
        }
    })
    $(".rank-two").each(function(){
        if($(this).find(".rank-three").length){
            $(this).find(".rank-two-text .link_li").append(`<label><img src="/images/arrows.png"/></label>`)
        }
    })
    $(".link_content .rank-two-text").click(function () {
        var $rank_two = $(this).parents(".rank-two")
        if ($rank_two.attr("is-open")=="false") {
            // $rank_two.find(".rank-three").show()
            $rank_two.removeAttr("is-open")
        } else {
            $rank_two.attr("is-open","false")
            // $rank_two.find(".rank-three").hide()
        }
    })
    $(".link_content .rank-three-text").click(function () {
        if ($(".Anchor_link-btn").hasClass("x")) {
            $(".Anchor_link").stop().animate({
                "left": "-100%"
            }, 400)
            $(".Anchor_link-btn").removeClass("x")
        }
    })
    $(document).on("click", ".Anchor_link-btn", function () {
        if (!$(this).hasClass("x")) {
            $(this).addClass("x")
            $(".Anchor_link").stop().animate({
                "left": "0%"
            }, 400)

        } else {
            $(this).removeClass("x")
            $(".Anchor_link").stop().animate({
                "left": "-100%"
            }, 400)
        }
    })
    $(".top-btn").click(function(){
        $('html,body').stop().animate({'scrollTop' : "0"},400)
    })
})