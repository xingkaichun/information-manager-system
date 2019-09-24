$(function () {
    $(document).on("click", ".link_content .rank-one-text", function () {
        var $rank_one = $(this).parents(".rank-one")
        if ($rank_one.find(".rank-two").is(":hidden")) {
            $rank_one.find(".rank-two").show()
        } else {
            $rank_one.find(".rank-two").hide()
        }
    })
    $(".link_content .rank-two-text").click(function () {
        var $rank_two = $(this).parents(".rank-two")
        if ($rank_two.find(".rank-three").is(":hidden")) {
            $rank_two.find(".rank-three").show()
        } else {
            $rank_two.find(".rank-three").hide()
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