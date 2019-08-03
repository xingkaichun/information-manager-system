$(function () {
    //点击登录
    $("#submit_btn").click(function () {
        var email = $("input[name='email']").val(),
            password = $("input[name='password']").val()

        $.ajax({
            url: url+"/User/Login",
            data:`{"Email":"${email}","Password":"${password}"}`,
            contentType:"application/json",
            type: "post",
            dataType: "json",
            success: function(data){
                console.log(data)
                istrue = true
                alert(`${data.Message}`);
                if(data.ServiceCode=="SUCCESS"){
                    localStorage.setItem("UserInfo",JSON.stringify(data.Result.UserInfo))
                    location.href="./index.html"
                }
            },
            error:function(e){
            }
        });
    })
})
