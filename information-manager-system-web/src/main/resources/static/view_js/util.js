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


//获取参数
function urlArgs() {
    var args = {};
    var query = location.search.substring(1);
    var pairs = query.split("&");
    for (var i = 0; i < pairs.length; i++) {
        var pos = pairs[i].indexOf("=");
        if (pos == -1) continue;
        var name = pairs[i].substring(0, pos);
        var value = pairs[i].substring(pos + 1);
        value = decodeURIComponent(value);
        args[name] = value;
    }
    return args;
}
