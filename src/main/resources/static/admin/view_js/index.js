$(function(){
    $('#add_content').summernote({
        height: 200,
        tabsize: 2,
        lang: 'zh-CN',
        callbacks:{
            onImageUpload: function (files) {
                //上传图片到服务器

                for (var i = 0;i<files.length;i++){
                    var formData = new FormData();
                    formData.append('file',files[i]);
                    $.ajax({
                        type: 'post',
                        url: url+"/File/FileUpload", //上传文件的请求路径必须是绝对路劲
                        data: formData,
                        cache: false,
                        processData: false,
                        contentType: false,
                    }).success(function (data) {
                        $('#add_content').summernote('insertImage',data.Result.FileDto.FilePath);
                    })
                }

            }
        }
    });
    $('#edit_content').summernote({
        height: 200,
        tabsize: 2,
        lang: 'zh-CN',
        callbacks:{
            onImageUpload: function (files) {
                //上传图片到服务器
                for (var i = 0;i<files.length;i++){
                    var formData = new FormData();
                    formData.append('file',files[i]);
                    $.ajax({
                        type: 'post',
                        url: url+"/File/FileUpload", //上传文件的请求路径必须是绝对路劲
                        data: formData,
                        cache: false,
                        processData: false,
                        contentType: false,
                    }).success(function (data) {
                        $('#edit_content').summernote('insertImage',data.Result.FileDto.FilePath);
                    })
                }

            }
        }
    });
    var user_info = JSON.parse(localStorage.getItem("UserInfo"))
    console.log("user_info",user_info)

    //获取zNodes
    var server_array =[]
    function serverArray(arr,idx){
        var idxs = idx?idx+1:1
        for(var item = 0;item < arr.length;item++){
            if(arr[item].Children){
                server_array.push({id:arr[item].CategoryId,name: arr[item].CategoryName,idxs:idxs})
                serverArray(arr[item].Children,idxs)
            }
        }
        return arr
    }
    //查询文章类别列表
    (function(){
        $.ajax({
            type: "post",
            url: url+"/Category/QueryHierarchicalCategory",
            data:`{"CategoryId":null}`,
            contentType:"application/json",
            dataType: "json",
            success: function(data){
                console.log(data)
                if(data.Result!=null){
                    var  CategoryDTOList = data.Result.CategoryDTOList
                    server_array = []
                    serverArray(CategoryDTOList)
                    var server_html = ""
                    for(var i=0,len =server_array.length;i<len;i++){
                         server_html += `<li data-id="${server_array[i].id}" class="t${server_array[i].idxs}"><a href="#">${server_array[i].name}</a></li>`
                    }
                    $("#sel_CategoryId .dropdown-menu").html(server_html)
                    $("#add_CategoryId .dropdown-menu").html(server_html)
                    $("#edit_CategoryId .dropdown-menu").html(server_html)
                    console.log("serverarray",server_array)
                }
            },
            error:function(e){

            }
        });
    })()

    //下拉菜单
    $(document).on("click","#sel_CategoryId .dropdown-menu li",function(){
        $("#sel_CategoryId .daan").text(`${$(this).text()}`).attr("data-id",`${$(this).attr("data-id")}`)
    })
    $(document).on("click","#add_CategoryId .dropdown-menu li",function(){
        $("#add_CategoryId .daan").text(`${$(this).text()}`).attr("data-id",`${$(this).attr("data-id")}`)
    })
    $(document).on("click","#edit_CategoryId .dropdown-menu li",function(){
        $("#edit_CategoryId .daan").text(`${$(this).text()}`).attr("data-id",`${$(this).attr("data-id")}`)
    })
    var page_num = sessionStorage.getItem("page_num")?parseInt(sessionStorage.getItem("page_num")):1,pages =1
    //分页
    $(document).on("click",".page_num",function () {
        var num = $(this).attr("data-page")
        getList(num)
    })
    $(document).on("click",".p_page",function () {
        var num = page_num-1
        if(num<=0){
            return false
        }
        getList(num)
    })
    $(document).on("click",".n_page",function () {
        var num = page_num+1
        if(num>pages){
            return false
        }
        getList(num)
    })

    //新增 文件上传
    $(document).on("change","input[name='add_file']",function(files){
        console.log("files",this.files)
        for (var i = 0;i<this.files.length;i++){
            var formData = new FormData();
            formData.append("file",this.files[i])
            $.ajax({
                type: 'post',
                url: url+"/File/FileUpload", //上传文件的请求路径必须是绝对路劲
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
            }).success(function (data) {
                console.log("data",data);
                $(".add_file_list").append(` <div class="file_li" data-id ="${data.Result.FileDto.FileId}">
                                                <div class="file_text">${data.Result.FileDto.FileName}</div>
                                                <div class="file_close"><button class="btn btn-danger btn-xs close_btn"><i class="icon-trash"></i></button></div>
                                            </div>`)
            })
        }
    })
    //编辑 文件上传
    $(document).on("change","input[name='edit_file']",function(files){
        console.log("files",this.files)
        for (var i = 0;i<this.files.length;i++){
            var formData = new FormData();
            formData.append("file",this.files[i])
            $.ajax({
                type: 'post',
                url: url+"/File/FileUpload", //上传文件的请求路径必须是绝对路劲
                data: formData,
                cache: false,
                processData: false,
                contentType: false,
            }).success(function (data) {
                console.log("data",data);
                $(".edit_file_list").append(` <div class="file_li" data-id ="${data.Result.FileDto.FileId}">
                                                <div class="file_text">${data.Result.FileDto.FileName}</div>
                                                <div class="file_close"><button class="btn btn-danger btn-xs close_btn"><i class="icon-trash"></i></button></div>
                                            </div>`)
            })
        }
    })
    //删除上传文件
    $(document).on("click",".file_list .file_li .file_close",function () {
        if(confirm("确定删除文件吗?")){
            $(this).parents(".file_li").remove()
        }

    })


    //新增
    $(document).on("click","#add_btn",function(){
        var add_json = {
            title:$("input[name='add_title']").val(),
            content:$('#add_content').summernote("code"),
            CategoryId:$("#add_CategoryId .daan").attr("data-id")?$("#add_CategoryId .daan").attr("data-id"):""
        }
        var filestr = []
        $(".add_file_list .file_li").each(function () {
            filestr.push($(this).attr("data-id"))
        })
        $.ajax({
            type: "post",
            url: url+"/Article/AddArticle",
            data:`{"AttachedFiles":"${filestr.join(",")}","CategoryId":"${add_json.CategoryId}","Content":"${add_json.content.replace(/"/g, "'")}","Title":"${add_json.title}","UserId":"${user_info.UserId}"}`,
            contentType:"application/json",
            dataType: "json",
            success: function(data){
                if(data.ServiceCode=="SUCCESS"){
                    getList(page_num)
                    $("input[name='add_title']").val("")
                    $('#add_content').summernote("code","")
                    $("#add_CategoryId .daan").attr("data-id","").text("请选择")
                    $(".add_file_list .file_li").remove()

                    $("#myModal2").modal("hide")
                }
                alert(`${data.Message}`);
            },
            error:function(e){}
        });

        console.log("add_json",add_json)
    })
    //弹出编辑界面
    var edit_ArticleId = ""
    $(document).on("click",".edit_show",function(){
        var data_json = lists[$(this).parents("tr").index()]
        console.log("edit_show",data_json)
        edit_ArticleId= data_json.ArticleId
        $("input[name='edit_title']").val(data_json.Title)
        $('#edit_content').summernote("code",data_json.Content)
        $("#edit_CategoryId .daan").attr("data-id",data_json.CategoryId)
        //$("input[name='edit_IdSoftDelete'][value='"+data_json.IsSoftDelete+"']").attr("checked",data_json.IsSoftDelete);
        $("input[name='edit_IsSoftDelete'][value='"+data_json.IsSoftDelete+"']").click();
        $("#edit_CategoryId li").each(function(){
            if($(this).attr("data-id")==data_json.CategoryId){
                $("#edit_CategoryId .daan").text($(this).text())
                return false
            }
        })
        if(data_json.AttachedFilesDetails){
            $(".edit_file_list").html("")
            for(var i=0,len =data_json.AttachedFilesDetails.length;i<len;i++){
                $(".edit_file_list").append(` <div class="file_li" data-id ="${data_json.AttachedFilesDetails[i].FileId}">
                                                <div class="file_text">${data_json.AttachedFilesDetails[i].FileName}</div>
                                                <div class="file_close"><button class="btn btn-danger btn-xs close_btn"><i class="icon-trash"></i></button></div>
                                            </div>`)
            }
        }


    })
    //修改
    $(document).on("click","#edit_btn",function(){
        var edit_json = {
            title:$("input[name='edit_title']").val(),
            content:$('#edit_content').summernote("code"),
            CategoryId:$("#edit_CategoryId .daan").attr("data-id")?$("#edit_CategoryId .daan").attr("data-id"):"",
            IsSoftDelete:$('input[name="edit_IsSoftDelete"]:checked').val()
        }
        var filestr = []
        $(".edit_file_list .file_li").each(function () {
            filestr.push($(this).attr("data-id"))
        })
        $.ajax({
            type: "post",
            url: url+"/Article/UpdateArticle",
            data:`{"ArticleId":"${edit_ArticleId}","AttachedFiles":"${filestr.join(",")}","CategoryId":"${edit_json.CategoryId}","IsSoftDelete":"${edit_json.IsSoftDelete}","Content":"${edit_json.content.replace(/"/g, "'")}","Title":"${edit_json.title}","UserId":"${user_info.UserId}"}`,
            contentType:"application/json",
            dataType: "json",
            success: function(data){
                if(data.ServiceCode=="SUCCESS"){
                    getList(page_num)
                    $("#myModal").modal("hide")
                }
                alert(`${data.Message}`);
            },
            error:function(e){}
        });
    })
    //删除
    $(document).on("click",".remove_btn",function(){
        var ArticleId = $(this).attr("data-ArticleId")
        if(confirm("确定从物理上删除文章吗?删除不可恢复!")){
            $.ajax({
                type: "post",
                url: url+"/Article/PhysicsDeleteArticle",
                data:`{"ArticleId":"${ArticleId}"}`,
                contentType:"application/json",
                dataType: "json",
                success: function(data){
                    if(data.ServiceCode=="SUCCESS"){
                        getList(page_num)
                    }
                    alert(`${data.Message}`);
                },
                error:function(e){}
            });
        }

    })
    //查询
    var search_json = {
        title:"",
        content:"",
        start_time:"",
        end_time:'',
        CategoryId:''
    }
    $(document).on("click","#search_btn",function(){
        search_json = {
            title:$("input[name='sel_title']").val(),
            content:$("input[name='sel_content']").val(),
            start_time:$("input[name='sel_start_time']").val()?$("input[name='sel_start_time']").val()+" 00:00:00":"",
            end_time:$("input[name='sel_end_time']").val()?$("input[name='sel_end_time']").val()+" 00:00:00":"",
            CategoryId:$("#sel_CategoryId .daan").attr("data-id")?$("#sel_CategoryId .daan").attr("data-id"):""
        }
        getList(page_num)
        console.log("search_json",search_json)
    })
    //获取文章列表
    var lists = []
    function getList(page) {
        var data_json = search_json.CategoryId?`{"PageCondition":{"PageNum":${page},"PageSize":10},"Title":"${search_json.title}","Content":"${search_json.content}","CreateTimeStart":"${search_json.start_time}","LastEditTimeEnd":"${search_json.end_time}"`+`,"CategoryId":"${search_json.CategoryId}"`+`}`:
        `{"PageCondition":{"PageNum":${page},"PageSize":10},"Title":"${search_json.title}","Content":"${search_json.content}","CreateTimeStart":"${search_json.start_time}","LastEditTimeEnd":"${search_json.end_time}"}`
        $.ajax({
            type: "post",
            url: url+"/Article/QueryArticle",
            data:data_json,
            contentType:"application/json",
            dataType: "json",
            async:false,
            success: function(data){
                console.log("data",data)
                if(!data.Result.ArticleDTOPageInformation.data.length&&page>=2){
                    getList(page-1)
                    return false
                }
                page_num = page
                sessionStorage.setItem("page_num",page_num)
                pages = data.Result.ArticleDTOPageInformation["pages"]
                //分页
                var pageStr = `<li class="p_page"><a href="javascript:;">«</a></li>`
                for(var i=1;i<=pages;i++){
                    pageStr+=i==page?`<li class="active page_num" data-page="${i}"><a href="javascript:;">${i}</a></li>`:`<li class="page_num" data-page="${i}"><a href="javascript:;">${i}</a></li>`
                }
                pageStr+=`<li class="n_page"><a href="javascript:;">»</a></li>`
                $(".page_ul").html(pageStr)
                var html = ``
                var list = data.Result.ArticleDTOPageInformation.data
                lists = list
                for(var i=0,len =list.length;i<len;i++){
                    html+=`<tr>
                                      <td>${list[i].ArticleId}</td>
                                      <td class="hidden-phone">${list[i].Title}</td>
                                      <td>${list[i].CreateTime}</td>
                                      <td>${list[i].LastEditTime}</td>
                                      <td>${list[i].IsSoftDelete}</td>
                                      <td>
                                          <button class="btn btn-primary btn-xs edit_show" data-toggle="modal" data-target="#myModal"><i class="icon-pencil"></i></button>
                                          <button class="btn btn-danger btn-xs remove_btn" data-ArticleId='${list[i].ArticleId}' ><i class="icon-trash"></i></button>
                                      </td>
                                  </tr>`
                }
                $("#tbody_list tbody").html(html)
            },
            error:function(e){

            }
        });
    }
    getList(page_num)
});
