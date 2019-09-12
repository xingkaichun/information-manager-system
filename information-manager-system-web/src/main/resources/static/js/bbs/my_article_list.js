$(function(){
    var url="http://localhost:80"
    var article_data = {}

    $.ajax({
        type: "post",
        url: url+"/Bbs/QueryBbsArticleByUserId",
        contentType:"application/json",
        data:`{}`,
        dataType: "json",
        async:false,
        success: function(data){
            console.log(data)
            if(data.Result!=null){
                article_data.BbsArticleDTOList = data.Result.BbsArticleDTOList
            }
        },
        error:function(e){

        }
    });
    console.log("article_data",article_data)
    var article_html = template("article_list_template",article_data)
    $("#article_list").html(article_html)
     
})