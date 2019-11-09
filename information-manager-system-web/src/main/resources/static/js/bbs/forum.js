//分页功能 后面再做
function Paging(article_count){
    var article_count =  23;
    var a = Math.ceil(article_count / 10);
    console.log(a);
}
Paging();



//论坛首页 获取默认帖子列表
$(function(){
    var url="";
    var article_data = {};
    var totalArticleCount;
    $.ajax({
        type: "post",
        url: url+"/Bbs/QueryBbsArticle",
        contentType:"application/json",
        data:`{
            "PageCondition":{
                "PageNum":1,
                "PageSize":10
            },
            "OrderByField":"LastEditTime"
        }`,
        dataType: "json",
        async:false,
        success: function(data){
            if(data.Result!=null){
                article_data.BbsArticleDTOPageInformation = data.Result.BbsArticleDTOPageInformation.Data;
                totalArticleCount = data.Result.BbsArticleDTOPageInformation.TotalDataCount;
            }
        },
        error:function(e){
        }
    });
    var article_html = template("article_list_template",article_data);
    $("#article_list").html(article_html);
    console.log(totalArticleCount);
})