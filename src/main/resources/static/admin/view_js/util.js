var url=""

var Common = {
    confirm:function(params){
        var model = $("#common_confirm_model");
        model.find(".title").html(params.title)
        model.find(".message").html(params.message)

        $("#common_confirm_btn").click()
        //每次都将监听先关闭，防止多次监听发生，确保只有一次监听
        model.find(".cancel").die("click")
        model.find(".ok").die("click")

        model.find(".ok").live("click",function(){
            params.operate(true)
        })

        model.find(".cancel").live("click",function(){
            params.operate(false)
        })
    }
}

//获取用户信息
if(location.href.indexOf("login.html")<0){
    $.ajax({
        type: "post",
        url: url+"/User/GetUserInfo",
        data:"",
        contentType:"application/json",
        dataType: "json",
        success: function(data){
            console.log(data)
            if(data.Result==null){
                location.href="./login.html"
            }
        },
        error:function(e){

        }
    });
}
