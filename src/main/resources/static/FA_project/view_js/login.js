$(function () {
    var istrue = true
    //点击登录
    $("#submit_btn").click(function () {
        if(istrue==false){
            return false
        }
        var email = $("input[name='email']").val(),
            password = $("input[name='password']").val()
            istrue = false

        if(email!=""&&password!=""){
            $.ajax({
                url: url+"/User/Login",
                data:`{"Email":"${email}","Password":"${password}"}`,
                contentType:"application/json",
                type: "post",
                dataType: "json",
                success: function(data){
                    console.log(data)
                    istrue = true
                    if(data.Result!=null){
                        $("#tips").text(`登录成功`).addClass("alert-danger").show()
                        setTimeout(function () {
                            $("#tips").removeClass("alert-danger").fadeOut()
                            localStorage.setItem("UserInfo",JSON.stringify(data.Result.UserInfo))
                            location.href="./index.html"
                        },3000)
                    }else{
                        $("#tips").text(`${data.Message}`).addClass("alert-danger").show()
                        setTimeout(function () {
                            $("#tips").removeClass("alert-danger").fadeOut()
                        },3000)
                    }

                },
                error:function(e){

                }
            });
        }else{
            $("#tips").text("表单不能为空").addClass("alert-danger").show()
            setTimeout(function () {
                $("#tips").removeClass("alert-danger").fadeOut()
            },3000)
        }
    })
})
