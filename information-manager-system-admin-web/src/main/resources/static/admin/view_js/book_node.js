$(function(){
    var BookId = urlArgs().BookId

     //打开书籍
     var BookDTO = {} 
     function setPieceHtml(BookId){
        $.ajax({
            type: "post",
            url: url+"/Book/QueryBookDetailsByBookId",
            data:`{"BookId":"${BookId}"}`,
            contentType:"application/json",
            dataType: "json",
            success: function(data){
                if(data.ServiceCode=="SUCCESS"){
                    BookDTO = data.Result.BookDTO
                    $("#head-title").html(BookDTO.BookName)
                    var html = template("piece_list",BookDTO)
                    $(".piece_list").html(html)
                }
            },
            error:function(e){}
        });
    }
    setPieceHtml(BookId)

    //新增篇
    $(document).on("click","#add-piece-Modal .sumbit_btn",function(){
        var params = {
            BookChapterName:$("#add-piece-Modal input[name='BookChapterName']").val(),
            BookChapterDescription:$("#add-piece-Modal input[name='BookChapterDescription']").val(),
            BookChapterOrder:$("#add-piece-Modal input[name='BookChapterOrder']").val(),
            SeoUrl:$("#add-piece-Modal input[name='SeoUrl']").val(),
            SeoTitle:$("#add-piece-Modal input[name='SeoTitle']").val(),
            SeoKeywords:$("#add-piece-Modal input[name='SeoKeywords']").val(),
            SeoDescription:$("#add-piece-Modal input[name='SeoDescription']").val()
        }
        console.log("params",params)
        $.ajax({
            type: "post",
            url: url+"/Book/AddBookChapter",
            data:`{
                "BookId": "${BookId}",
                "BookChapterName" : "${params.BookChapterName}",
                "BookChapterDescription" : "${params.BookChapterDescription}",
                "BookChapterOrder" :${params.BookChapterOrder},
                "SeoUrl": "${params.SeoUrl}",
                "SeoTitle": "${params.SeoTitle}",
                "SeoKeywords": "${params.SeoKeywords}",
                "SeoDescription": "${params.SeoDescription}"
            }`,
            contentType:"application/json",
            dataType: "json",
            success: function(data){
                if(data.ServiceCode=="SUCCESS"){
                    $("#add-piece-Modal input[name='BookChapterName']").val("")
                    $("#add-piece-Modal input[name='BookChapterDescription']").val("")
                    $("#add-piece-Modal input[name='BookChapterOrder']").val(100)
                    $("#add-piece-Modal input[name='SeoUrl']").val("")
                    $("#add-piece-Modal input[name='SeoTitle']").val("")
                    $("#add-piece-Modal input[name='SeoKeywords']").val("")
                    $("#add-piece-Modal input[name='SeoDescription']").val("")

                    setPieceHtml(BookId)
                    $("#add-piece-Modal").modal("hide")
                }
                alert(`${data.Message}`);
            },
            error:function(e){}
        });
    })
    //修改篇
    var eidt_BookChapterDTO = {}
    $(document).on("click",".edit_piece_btn",function(){
        eidt_BookChapterDTO =BookDTO.BookChapterDTOList[$(this).parents(".piece_li").index()]
        $("#edit-piece-Modal input[name='BookChapterName']").val(eidt_BookChapterDTO.BookChapterName)
        $("#edit-piece-Modal input[name='BookChapterDescription']").val(eidt_BookChapterDTO.BookChapterDescription)
        $("#edit-piece-Modal input[name='BookChapterOrder']").val(eidt_BookChapterDTO.BookChapterOrder)
        $("#edit-piece-Modal input[name='SeoUrl']").val(eidt_BookChapterDTO.SeoUrl)
        $("#edit-piece-Modal input[name='SeoTitle']").val(eidt_BookChapterDTO.SeoTitle)
        $("#edit-piece-Modal input[name='SeoKeywords']").val(eidt_BookChapterDTO.SeoKeywords)
        $("#edit-piece-Modal input[name='SeoDescription']").val(eidt_BookChapterDTO.SeoDescription)
        $("#edit-piece-Modal input[name='IsSoftDelete'][value='"+eidt_BookChapterDTO.IsSoftDelete+"']").click()
        console.log("eidt_BookChapterDTO",eidt_BookChapterDTO)
    })
    $(document).on("click","#edit-piece-Modal .sumbit_btn",function(){
        var params = {
            BookChapterName:$("#edit-piece-Modal input[name='BookChapterName']").val(),
            BookChapterDescription:$("#edit-piece-Modal input[name='BookChapterDescription']").val(),
            BookChapterOrder:$("#edit-piece-Modal input[name='BookChapterOrder']").val(),
            SeoUrl:$("#edit-piece-Modal input[name='SeoUrl']").val(),
            SeoTitle:$("#edit-piece-Modal input[name='SeoTitle']").val(),
            SeoKeywords:$("#edit-piece-Modal input[name='SeoKeywords']").val(),
            SeoDescription:$("#edit-piece-Modal input[name='SeoDescription']").val(),
            IsSoftDelete:$('#edit-piece-Modal input[name="IsSoftDelete"]:checked').val()
        }
        console.log("params",params)
        $.ajax({
            type: "post",
            url: url+"/Book/UpdateBookChapter",
            data:`{
                "BookChapterId":"${eidt_BookChapterDTO.BookChapterId}",
                "BookChapterName": "${params.BookChapterName}",
                "BookChapterDescription": "${params.BookChapterDescription}",
                "BookChapterOrder": ${params.BookChapterOrder},
                "SeoUrl": "${params.SeoUrl}",
                "SeoTitle": "${params.SeoTitle}",
                "SeoKeywords": "${params.SeoKeywords}",
                "SeoDescription": "${params.SeoDescription}",
                "IsSoftDelete": "${params.IsSoftDelete}"
            }`,
            contentType:"application/json",
            dataType: "json",
            success: function(data){
                if(data.ServiceCode=="SUCCESS"){
                    setPieceHtml(BookId)
                    $("#edit-piece-Modal").modal("hide")
                }
                alert(`${data.Message}`);
            },
            error:function(e){}
        });
    })
    //删篇
    $(document).on("click",".piece_li .del_piece_btn",function(){
        var BookChapterId = $(this).parents(".piece_li").attr("data-BookChapterId")
        if(confirm("确定从物理上删除篇吗?删除不可恢复!")){
            $.ajax({
                type: "post",
                url: url+"/Book/PhysicsDeleteBookChapterByBookChapterId",
                data:`{"BookChapterId":"${BookChapterId}"}`,
                contentType:"application/json",
                dataType: "json",
                success: function(data){
                    if(data.ServiceCode=="SUCCESS"){
                        setPieceHtml(BookId)
                    }
                    alert(`${data.Message}`);
                },
                error:function(e){}
            });
        }
    })
    //更改篇的排序
    $(document).on("click",".piece_li .edit_piece_order_btn",function(){
        var BookChapterId = $(this).parents(".piece_li").attr("data-BookChapterId")
        var oldBookChapterOrder = $(this).attr("data-BookChapterOrder")
        var bookChapterOrder = prompt("请输入篇的排序值", oldBookChapterOrder);
        if (bookChapterOrder != null) {
            $.ajax({
                type: "post",
                url: url+"/Book/UpdateBookChapter",
                data:`{
                    "BookChapterId":"${BookChapterId}",
                    "BookChapterOrder": ${bookChapterOrder}
                }`,
                contentType:"application/json",
                dataType: "json",
                success: function(data){
                    if(data.ServiceCode=="SUCCESS"){
                        setPieceHtml(BookId)
                    }
                    alert(`${data.Message}`);
                },
                error:function(e){}
            });
        } else{
            alert("排序值不能为空");
        }
    })

    $('#edit_BookSectionContent').summernote({
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
                        $('#edit_BookSectionContent').summernote('insertImage',data.Result.FileDto.FilePath);
                    })
                }

            }
        }
    });
    $('#add_BookSectionContent').summernote({
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
                        $('#add_BookSectionContent').summernote('insertImage',data.Result.FileDto.FilePath);
                    })
                }

            }
        }
    });

    //新增章
    var add_BookChapterDTO = {}
    $(document).on("click",".add_chapter_btn",function(){
        add_BookChapterDTO =BookDTO.BookChapterDTOList[$(this).parents(".piece_li").index()]
    })
    $(document).on("click","#add-chapter-Modal .sumbit_btn",function(){
        var params = {
            BookSectionName:$("#add-chapter-Modal input[name='BookSectionName']").val(),
            BookSectionDescription:$("#add-chapter-Modal input[name='BookSectionDescription']").val(),
            BookSectionContent:$('#add_BookSectionContent').summernote("code").replace(/"/g, "'"),
            BookSectionOrder:$("#add-chapter-Modal input[name='BookSectionOrder']").val(),
            SeoUrl:$("#add-chapter-Modal input[name='SeoUrl']").val(),
            SeoTitle:$("#add-chapter-Modal input[name='SeoTitle']").val(),
            SeoKeywords:$("#add-chapter-Modal input[name='SeoKeywords']").val(),
            SeoDescription:$("#add-chapter-Modal input[name='SeoDescription']").val()
        }
        $.ajax({
            type: "post",
            url: url+"/Book/AddBookSection",
            data:`{
                "BookChapterId":"${add_BookChapterDTO.BookChapterId}",
                "BookSectionName":"${params.BookSectionName}",
                "BookSectionDescription":"${params.BookSectionDescription}",
                "BookSectionContent":"${params.BookSectionContent}",
                "BookSectionOrder":${params.BookSectionOrder},
                "SeoUrl":"${params.SeoUrl}",
                "SeoTitle":"${params.SeoTitle}",
                "SeoKeywords":"${params.SeoKeywords}",
                "SeoDescription":"${params.SeoDescription}"
            }`,
            contentType:"application/json",
            dataType: "json",
            success: function(data){
                if(data.ServiceCode=="SUCCESS"){
                    $("#add-chapter-Modal input[name='BookSectionName']").val("")
                    $("#add-chapter-Modal input[name='BookSectionDescription']").val("")
                    $('#add_BookSectionContent').summernote("code","")
                    $("#add-chapter-Modal input[name='BookSectionOrder']").val("")
                    $("#add-chapter-Modal input[name='SeoUrl']").val("")
                    $("#add-chapter-Modal input[name='SeoTitle']").val("")
                    $("#add-chapter-Modal input[name='SeoKeywords']").val("")
                    $("#add-chapter-Modal input[name='SeoDescription']").val("")

                    setPieceHtml(BookId)
                    $("#add-chapter-Modal").modal("hide")
                }
                alert(`${data.Message}`);
            },
            error:function(e){}
        });
    })

    //修改章
    var edit_BookSectionDTO = {}
    $(document).on("click",".edit_chapter_btn",function(){
        edit_BookSectionDTO =BookDTO.BookChapterDTOList[$(this).parents(".piece_li").index()].BookSectionDTOList[$(this).parents(".chapter_li").index()]
        $("#edit-chapter-Modal input[name='BookSectionName']").val(edit_BookSectionDTO.BookSectionName)
        $("#edit-chapter-Modal input[name='BookSectionDescription']").val(edit_BookSectionDTO.BookSectionDescription)
        $('#edit_BookSectionContent').summernote("code",edit_BookSectionDTO.BookSectionContent)
        $("#edit-chapter-Modal input[name='BookSectionOrder']").val(edit_BookSectionDTO.BookSectionOrder)
        $("#edit-chapter-Modal input[name='SeoUrl']").val(edit_BookSectionDTO.SeoUrl)
        $("#edit-chapter-Modal input[name='SeoTitle']").val(edit_BookSectionDTO.SeoTitle)
        $("#edit-chapter-Modal input[name='SeoKeywords']").val(edit_BookSectionDTO.SeoKeywords)
        $("#edit-chapter-Modal input[name='SeoDescription']").val(edit_BookSectionDTO.SeoDescription)
        $("#edit-chapter-Modal input[name='IsSoftDelete'][value='"+edit_BookSectionDTO.IsSoftDelete+"']").click()
    })
    $(document).on("click","#edit-chapter-Modal .sumbit_btn",function(){
        var params = {
            BookSectionName:$("#edit-chapter-Modal input[name='BookSectionName']").val(),
            BookSectionDescription:$("#edit-chapter-Modal input[name='BookSectionDescription']").val(),
            BookSectionContent:$('#edit_BookSectionContent').summernote("code").replace(/"/g, "'"),
            BookSectionOrder:$("#edit-chapter-Modal input[name='BookSectionOrder']").val(),
            SeoUrl:$("#edit-chapter-Modal input[name='SeoUrl']").val(),
            SeoTitle:$("#edit-chapter-Modal input[name='SeoTitle']").val(),
            SeoKeywords:$("#edit-chapter-Modal input[name='SeoKeywords']").val(),
            SeoDescription:$("#edit-chapter-Modal input[name='SeoDescription']").val(),
            IsSoftDelete:$('#edit-chapter-Modal input[name="IsSoftDelete"]:checked').val()
        }
        $.ajax({
            type: "post",
            url: url+"/Book/UpdateBookSection",
            data:`{
                "BookSectionId": "${edit_BookSectionDTO.BookSectionId}",
                "BookSectionName": "${params.BookSectionName}",
                "BookSectionDescription": "${params.BookSectionDescription}",
                "BookSectionContent": "${params.BookSectionContent}",
                "BookSectionOrder": ${params.BookSectionOrder},
                "SeoUrl": "${params.SeoUrl}",
                "SeoTitle": "${params.SeoTitle}",
                "SeoKeywords": "${params.SeoKeywords}",
                "SeoDescription": "${params.SeoDescription}",
                "IsSoftDelete": "${params.IsSoftDelete}"
            }`,
            contentType:"application/json",
            dataType: "json",
            success: function(data){
                if(data.ServiceCode=="SUCCESS"){
                    setPieceHtml(BookId)
                    $("#edit-chapter-Modal").modal("hide")
                }
                alert(`${data.Message}`);
            },
            error:function(e){}
        });
    })


      //删章
      $(document).on("click",".chapter_li .del_chapter_btn",function(){
        var BookSectionId = $(this).parents(".chapter_li").attr("data-booksectionid")
        if(confirm("确定从物理上删除章吗?删除不可恢复!")){
            $.ajax({
                type: "post",
                url: url+"/Book/PhysicsDeleteBookSectionByBookSectionId",
                data:`{"BookSectionId":"${BookSectionId}"}`,
                contentType:"application/json",
                dataType: "json",
                success: function(data){
                    if(data.ServiceCode=="SUCCESS"){
                        setPieceHtml(BookId)
                    }
                    alert(`${data.Message}`);
                },
                error:function(e){}
            });
        }
    })

    //更改小节的排序
    $(document).on("click",".chapter_li .edit_chapter_order_btn",function(){
        var bookSectionId = $(this).parents(".chapter_li").attr("data-booksectionid")
        var oldBookSectionOrder = $(this).attr("data-BookSectionOrder")
        var bookSectionOrder = prompt("请输入小节的排序值", oldBookSectionOrder);
        if (bookSectionOrder != null && bookSectionOrder != '') {
            $.ajax({
                type: "post",
                url: url+"/Book/UpdateBookSection",
                data:`{
                    "BookSectionId":"${bookSectionId}",
                    "BookSectionOrder": ${bookSectionOrder}
                }`,
                contentType:"application/json",
                dataType: "json",
                success: function(data){
                    if(data.ServiceCode=="SUCCESS"){
                        setPieceHtml(BookId)
                    }
                    alert(`${data.Message}`);
                },
                error:function(e){}
            });
        } else{
            alert("排序值不能为空");
        }
    })

})