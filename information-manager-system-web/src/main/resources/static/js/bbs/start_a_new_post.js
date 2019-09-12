$(function(){
    var url="http://localhost:80"

    //上传图片
    $("input[name='upload_img']").change(function(){
        console.log(this.files)
       
        for(var i=0,len = this.files.length;i<len;i++){
            var dataFrom = new FormData();
            dataFrom.append("file",  this.files[i])
            $.ajax({
                url:  url+"/File/FileUpload",
                type: "post",
                processData: false,
                contentType: false,
                traditional: true,
                dataType: "json",
                data:dataFrom,
                async: false,
                success: function success(data) {
                    $("div[name='content']").append(`<img style='max-width:100%;display:block;' src='${data.Result.FileDto.FilePath}'/>`)
                }
            })
        }
        
    })

    //提交
    $("#sumbit_btn").click(function(){
            var title = $("input[name='title']").val()
            var content = $("div[name='content']").html().replace(/\"/g,"'");
            if(!title||title.trim()==""){
                alert("标题为空")
                return false
            }
            if(!content||content.trim()==""){
                alert("内容为空")
                return false
            }
            $.ajax({
                type: "post",
                url: url+"/Bbs/AddBbsArticle",
                contentType:"application/json",
                dataType: "json",
                data:`{
                    "Title":"${title}",
                    "Content":"${content}"
                }`,
                async:false,
                success: function(data){
                    console.log(data)
                    $("input[name='title']").val("")
                    $("div[name='content']").html("")

                    if(data.ServiceCode=='SUCCESS'){
                        alert(data.Message+"。"+"即将跳转到我的帖子页面...")
                        location.href="/bbs/my_post_list.html"
                    } else {
                        alert(data.Message)
                    }
                },
                error:function(e){
                }
            });
    })

})