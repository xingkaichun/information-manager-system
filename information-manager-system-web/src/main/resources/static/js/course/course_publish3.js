var url = "";
var parameters = {};

//通过URL获取小节ID
function getSectionIdByUrl() {
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

//获取小节内容
function getSectionContent() {
    var section_content = {};
    $.ajax({
        type: "post",
        url: url + "/Book/QueryBookSectionListBybookChapterId",
        contentType: "application/json",
        data: `{
                "BookChapterId": "${getSectionIdByUrl().BookChapterId}"
        }`,
        dataType: "json",
        async: false,
        success: function (data) {
            var section_list = data.Result.BookSectionDTOList;
            for (var i=0; i<section_list.length; i++){
                if (section_list[i].BookSectionId == getSectionIdByUrl().BookSectionId){
                    section_content.Section = section_list[i];
                    parameters.section_content = section_list[i].BookSectionContent;
                    parameters.section_url = section_list[i].SeoUrl;
                    parameters.bookid = section_list[i].BookId;
                    console.log(parameters.section_url);
                    console.log(parameters.bookid);
                }
            }
        },
        error: function (e) {
        }
    });
    var section_content_html = template("course_list_template",section_content);
    $("#section_content").html(section_content_html);
}
getSectionContent();

//获取用户输入信息
function userInputInfo() {
    var user = {};
    user.name = $("#section_info input[name=name]").val();
    user.description = $("#section_info input[name=section_description]").val();
    user.content = editor.txt.html();
    user.seo_url = $("#section_info input[name=seo_url]").val();
    user.seo_title = $("#section_info input[name=seo_title]").val();
    user.seo_keywords = $("#section_info input[name=seo_keywords]").val();
    user.seo_description = $("#section_info input[name=seo_description]").val();
    return user;
}

//获取书籍URL
function getBookUrl() {
    var book_url = {};
    $.ajax({
        type: "post",
        url: url + "/Book/QueryBookDetailsByBookId",
        contentType: "application/json",
        data: `{
                "BookId": "${parameters.bookid}"
        }`,
        dataType: "json",
        async: false,
        success: function (data) {
            book_url.a = data.Result.BookDTO.SeoUrl;
        },
        error: function (e) {
        }
    });
    return book_url;
}

//修改小节
function modifySection() {
    userInputInfo();
    $.ajax({
            type: "post",
            url: url+"/Book/UpdateBookSection",
            contentType:"application/json",
            data:`{
                    "BookSectionId": "${getSectionIdByUrl().BookSectionId}",
                    "BookSectionName": "${userInputInfo().name}",
                    "BookSectionDescription": "${userInputInfo().description}",
                    "BookSectionContent": "${userInputInfo().content}",
                    "SeoUrl": "${userInputInfo().seo_url}",
                    "SeoTitle": "${userInputInfo().seo_title}",
                    "SeoKeywords": "${userInputInfo().seo_keywords}",
                    "SeoDescription": "${userInputInfo().seo_description}",
                    "IsSoftDelete": false
            }`,
            dataType: "json",
            async:false,
            success: function(data){
                alert("修改成功！");
                location.href="/jiaocheng/"+getBookUrl().a+"/"+parameters.section_url+".html";
            },
            error:function(e){
            }
        });
}


//编辑器
var E = window.wangEditor;
var editor = new E(document.getElementById('editor'));
editor.create();
editor.txt.html(parameters.section_content);






