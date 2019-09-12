$(function(){
    var url="http://localhost:80"
    $.ajax({
        type: "post",
        url: url+"/User/GetUserInfo",
        contentType:"application/json",
        dataType: "json",
        async:false,
        success: function(data){
            console.log(data)
            if(data.ServiceCode=='SUCCESS'){
                var jsElement = document.getElementById("common_auto_login_js");
                var page = jsElement.getAttribute("data");
                var showHtml = "" ;
                if(page == "jiaocheng_page"){
                    showHtml = "<a href='/bbs/post_list.html' class='nav_li'>论坛</a>"
                             + "<a href='javascript:void(0)' class='nav_li'>"+data.Result.UserInfo.UserName+"</a>";
                }
                if(page == "post_page"){
                    showHtml = "<a href='/bbs/post_list.html' class='nav_li active'>论坛</a>"
                             + "<a href='/bbs/start_a_new_post.html' class='nav_li'>发帖</a>"
                             + "<a href='/bbs/my_article_list.html' class='nav_li'>我的帖子</a>"
                             + "<a href='javascript:void(0)' class='nav_li'>"+data.Result.UserInfo.UserName+"</a>";
                }
                $("#rigth_nav").html(showHtml)
            } else {

            }
        },
        error:function(e){

        }
    });
})