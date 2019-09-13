$(function(){
    var url=""

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

    var article_data = {}

    $.ajax({
        type: "post",
        url: url+"/User/GetUserInfo",
        contentType:"application/json",
        dataType: "json",
        async:false,
        success: function(data){
            console.log(data)
            if(data.Result!=null){
                article_data.UserInfo = data.Result.UserInfo
            }
        },
        error:function(e){

        }
    });

    function getHtml(){
        $.ajax({
            type: "post",
            url: url+"/Bbs/QueryBbsArticleDetailByBbsArticleId",
            contentType:"application/json",
            dataType: "json",
            data:`{
                "BbsArticleId":"${urlArgs().BbsArticleId}"
            }`,
            async:false,
            success: function(data){
                console.log(data)
                if(data.Result!=null){
                    article_data.BbsArticleDTO = data.Result.BbsArticleDTO
                    function recursionFun(BbsArticleCommentDTOList){
                        if(BbsArticleCommentDTOList==null){
                            return
                        }
                        for(var i =0,len = BbsArticleCommentDTOList.length;i<len;i++){
                            BbsArticleCommentDTOList[i].user = article_data.UserInfo
                            if(BbsArticleCommentDTOList[i].ChildrenBbsArticleCommentDTOList.length){
                                recursionFun(BbsArticleCommentDTOList[i].ChildrenBbsArticleCommentDTOList)
                            }
                        }
                        return BbsArticleCommentDTOList
                    }
                    article_data.BbsArticleDTO.BbsArticleCommentDTOList = recursionFun(article_data.BbsArticleDTO.BbsArticleCommentDTOList)
                    
                }
            },
            error:function(e){
    
            }
        });
        console.log("article_data",article_data)
        var article_html = template("article_details_template",article_data)
        $("#article_details").html(article_html)
    }
    getHtml()



    //评论
    $(document).on("click",".comment_form .sumbit_btn",function(){
        if(!article_data.UserInfo){
            alert("请先登录")
            return false
        }
        var $comment_form = $(this).parents(".comment_form")
        var id = $comment_form.attr("id")
        var content  = $comment_form.find("div[name='comment_val']").html()
        $.ajax({
            type: "post",
            url: url+"/Bbs/AddBbsArticleComment",
            contentType:"application/json",
            dataType: "json",
            data:`{
                "BbsArticleId":"${urlArgs().BbsArticleId}",
                "ParentBbsArticleCommentId":"${id}",
	            "Content":"${content}"
            }`,
            async:false,
            success: function(data){
                console.log(data)
                alert(data.Message)
                getHtml()
            },
            error:function(e){
    
            }
        });
    })
    //显示回复框
    $(document).on("click",".comment_list .comment_btn",function(){
        var $this = $(this)
        $(`#${$this.attr("data-commentId")}`).show()
        setTimeout(function(){
            $("#article_details").off("click")
            $("#article_details").click(function (e) {
                if (!$(e.target).closest(`#${$this.attr("data-commentId")}`).length) {
                    $(`.comment_list .comment_form`).hide()
                }
            });
        },0)
    })
    



    $(document).on("click",".Anchor_content .title",function(){
        var $Anchor_content = $(this).parent(".Anchor_content")
        $Anchor_content.attr("data-in")=="false"?$Anchor_content.attr("data-in","true"):$Anchor_content.attr("data-in","false")
    })
})