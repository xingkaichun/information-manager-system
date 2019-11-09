var userID = null;
var url="";
$(function(){
    $.ajax({
        type: "post",
        url: url+"/User/GetUserInfo",
        contentType:"application/json",
        dataType: "json",
        async:false,
        success: function(data){
            userID = data.Result.UserInfo.UserId;
            var jsElement = document.getElementById("common_auto_login_js");
            var page = jsElement.getAttribute("data");
            if(data.ServiceCode=='SUCCESS'){
                var showHtml =`<a href="/home/home.html"><span>${data.Result.UserInfo.UserName}</span></a>`
                $("#nav").html(showHtml)
            } else {
                if(page == "start_a_new_post_page"){
                    alert("请先登录。确定将跳转到登录页面...");
                    location.href="/login.html"
                }
            }
        },
        error:function(e){
        }
    });
    var article_data = {};
    $.ajax({
        type: "post",
        url: url+"/Bbs/QueryBbsArticleByUserId",
        contentType:"application/json",
        data:`{
            "PageCondition":{
                "PageNum":1,
                "PageSize":10
            }
        }`,
        dataType: "json",
        async:false,
        success: function(data){
            console.log(data)
            if(data.Result!=null){
                article_data.BbsArticleDTOList = data.Result.BbsArticleDTOList.Data;
            }
        },
        error:function(e){
        }
    });
    var article_html = template("article_list_template",article_data);
    $("#home_right").html(article_html);
})