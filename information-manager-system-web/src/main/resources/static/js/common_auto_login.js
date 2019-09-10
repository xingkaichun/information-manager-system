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
                var log = "<a href='/bbs/article_list.html' class='nav_li'>论坛</a>"
                        + "<a href='javascript:void(0)' class='nav_li'>"+data.Result.UserInfo.UserName+"</a>";
                $("#rigth_nav").html(log)
            }
        },
        error:function(e){

        }
    });
})