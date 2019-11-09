var url = "";
var chapter_container = document.getElementById("chapter_container");
var click_chapter_id = {};


//从URL中获取教程ID
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

//获取章列表
function getChapterList() {
    var chapter_list = {};
    $.ajax({
        type: "post",
        url: url + "/Book/QueryBookDetailsByBookId",
        contentType: "application/json",
        data: `{
            "BookId":"${urlArgs().BookId}"
        }`,
        dataType: "json",
        async: false,
        success: function (data) {
            chapter_list.BookChapterDTOList = data.Result.BookDTO.BookChapterDTOList;
        },
        error: function (e) {
        }
    });
    var chapter_list_html = template("chapter_list_template",chapter_list);
    $("#chapter_list").html(chapter_list_html);
    return chapter_list.BookChapterDTOList;
}
getChapterList();

//生成弹出框
function createPopBox(para) {
    var course_title;
    var user = {
        BookChapterName: "",
        BookChapterDescription: "",
        SeoUrl: "",
        SeoTitle: "",
        SeoKeywords: "",
        SeoDescription: ""
    };
    if (para == 0){
        user = getCurrentChapter();
        course_title = "修改章";
    }else if(para == 1) {
        course_title = "新增章";
    }else if (para == 2){
        click_chapter_id.id = getChapterId();
        course_title = "新增小节";
    }
    var oDiv = document.createElement("div");
    oDiv.className = "create_main";
    oDiv.innerHTML =
        "<div class=\"create_cont\">" +
        "<div class=\"t\">"+course_title+"<span onclick=\"closePopBox()\">X</span></div>" +
        "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" id=\"add_course\" class=\"course_add_cont course_add_cont2\">" +
        "<tr>" +
        "<td width=\"100\">章名称</td>" +
        "<td><input type=\"text\" value=\""+user.BookChapterName+"\" name=\"name\" class=\"txt\"></td>" +
        "</tr>" +
        "<tr>" +
        "<td>章描述</td>" +
        "<td><input type=\"text\" value=\""+user.BookChapterDescription+"\" name=\"description\" class=\"txt\"></td>" +
        "</tr>" +
        "<tr>" +
        "<td>SEO网址</td>" +
        "<td><input type=\"text\" value=\""+user.SeoUrl+"\" name=\"seo_url\" class=\"txt\"></td>" +
        "</tr>" +
        "<tr>" +
        "<td>SEO标题</td>" +
        "<td><input type=\"text\" value=\""+user.SeoTitle+"\" name=\"seo_title\" class=\"txt\"></td>" +
        "</tr>" +
        "<tr>" +
        "<td>SEO关键字</td>" +
        "<td><input type=\"text\" value=\""+user.SeoKeywords+"\" name=\"seo_keywords\" class=\"txt\"></td>" +
        "</tr>" +
        "<tr>" +
        "<td>SEO描述</td>" +
        "<td><input type=\"text\" value=\""+user.SeoDescription+"\" name=\"seo_description\" class=\"txt\"></td>" +
        "</tr>" +
        "<tr>" +
        "<td></td>" +
        "<td><input type=\"button\" class=\"c_btn c_btn_imp\" value=\"提交\" onclick=\"submitAddData("+para+")\"></td>" +
        "</tr>" +
        "</table>" +
        "</div>";
    chapter_container.appendChild(oDiv);
}

//获取用户输入信息
function userInputInfo() {
    var user = {};
    user.name = $("#add_course input[name=name]").val();
    user.description = $("#add_course input[name=description]").val();
    user.seo_url = $("#add_course input[name=seo_url]").val();
    user.seo_title = $("#add_course input[name=seo_title]").val();
    user.seo_keywords = $("#add_course input[name=seo_keywords]").val();
    user.seo_description = $("#add_course input[name=seo_description]").val();
    return user;
}

//通过点击元素获取章的ID
function getCourseIdByUrl() {
    var cur_btn = event.srcElement ? event.srcElement : event.target;
    var chapter_id = cur_btn.parentNode.getAttribute("id");
    return chapter_id;
}

//通过点击修改获取章节ID，与章节列表对比获取该条章节信息
function getCurrentChapter() {
    //此处事件源依赖于触发元素，溯源：createPopBox()->modifyCourse()->修改按钮
    var cur_btn = event.srcElement ? event.srcElement : event.target;
    var chapter_id = cur_btn.parentNode.getAttribute("id");
    click_chapter_id.id = chapter_id;
    var course_arr = getChapterList();
    var arr;
    for (var i=0; i<course_arr.length; i++){
        if (course_arr[i].BookChapterId == chapter_id){
            arr = course_arr[i];
        }
    }
    return arr;
}

//点击新增小节获取章的ID
function getChapterId() {
    var cur_btn = event.srcElement ? event.srcElement : event.target;
    var chapter_id = cur_btn.parentNode.previousElementSibling.getAttribute("id");
    return chapter_id;
}

//提交数据
function submitAddData(para) {
    if (para == 0){  //提交修改数据
        $.ajax({
            type: "post",
            url: url+"/Book/UpdateBookChapter",
            contentType:"application/json",
            data:`{
                    "BookChapterId": "${click_chapter_id.id}",
                    "BookChapterName": "${userInputInfo().name}",
                    "BookChapterDescription": "${userInputInfo().description}",
                    "BookChapterOrder": 300,
                    "SeoUrl": "${userInputInfo().seo_url}",
                    "SeoTitle": "${userInputInfo().seo_title}",
                    "SeoKeywords": "${userInputInfo().seo_keywords}",
                    "SeoDescription": "${userInputInfo().seo_description}",
                    "IsSoftDelete": false
        }`,
            dataType: "json",
            async:false,
            success: function(data){
                alert("修改章成功！");
                closePopBox();
                getChapterList();
            },
            error:function(e){
            }
        });
    }else if(para == 1){  //提交新增数据
        $.ajax({
            type: "post",
            url: url+"/Book/AddBookChapter",
            contentType:"application/json",
            data:`{
                    "BookId": "${urlArgs().BookId}",
                    "BookChapterName" : "${userInputInfo().name}",
                    "BookChapterDescription" : "${userInputInfo().description}",
                    "SeoUrl": "${userInputInfo().seo_url}",
                    "SeoTitle": "${userInputInfo().seo_title}",
                    "SeoKeywords": "${userInputInfo().seo_keywords}",
                    "SeoDescription": "${userInputInfo().seo_description}"
        }`,
            dataType: "json",
            async:false,
            success: function(data){
                alert("新增章成功！");
                closePopBox();
                getChapterList();
            },
            error:function(e){
            }
        });
    }else if(para == 2){  //提交新增数据
        console.log(click_chapter_id.id);
        $.ajax({
            type: "post",
            url: url+"/Book/AddBookSection",
            contentType:"application/json",
            data:`{
                    "BookChapterId":"${click_chapter_id.id}",
                    "BookSectionName":"${userInputInfo().name}",
                    "BookSectionDescription":"${userInputInfo().description}",
                    "BookSectionContent":" ",
                    "SeoUrl":"${userInputInfo().seo_url}",
                    "SeoTitle":"${userInputInfo().seo_title}",
                    "SeoKeywords":"${userInputInfo().seo_keywords}",
                    "SeoDescription":"${userInputInfo().seo_description}"
        }`,
            dataType: "json",
            async:false,
            success: function(data){
                alert("新增小节成功！");
                closePopBox();
                getChapterList();
            },
            error:function(e){
            }
        });
    }
}

//销毁弹出框
function closePopBox() {
    chapter_container.removeChild(chapter_container.children[0]);
}

//新增章
function addCourse() {
    createPopBox(1);
}

//修改章
function modifyCourse() {
    createPopBox(0);
}

//新增小节
function addChapter() {
    createPopBox(2);
}

