$(function(){
    
    //书籍查询
    var BookDTOList = []
    function getBookList(){
        $.ajax({
            type: "post",
            url: url+"/Book/QueryBookList",
            data:`{}`,
            contentType:"application/json",
            dataType: "json",
            async:false,
            success: function(data){
              console.log("data",data)
              if(data.ServiceCode=="SUCCESS"){
                BookDTOList = data.Result.BookDTOList?data.Result.BookDTOList:[]
                var html = ''
                for(var i =0,len =BookDTOList.length;i<len;i++){
                    html+=`
                                <tr>
                                          <td class="open_book"><a href="./book_node.html?BookId=${BookDTOList[i].BookId}">${BookDTOList[i].BookId}</a></td>
                                          <td>${BookDTOList[i].BookName}</td>
                                          <td>
                                              <button class="btn btn-primary btn-xs edit_show" data-toggle="modal" data-target="#editbook"><i class="icon-pencil"></i></button>
                                              <button class="btn btn-danger btn-xs remove_btn" data-BookId="${BookDTOList[i].BookId}"><i class="icon-trash"></i></button>
                                          </td>
                                      </tr>`
                }
                $("#tbody_list tbody").html(html)
              }
            },
            error:function(e){}
        });
    }
    getBookList()


     //弹出编辑界面
     var edit_data = {}
     $(document).on("click",".edit_show",function(){
        edit_data =BookDTOList[$(this).parents("tr").index()]
        $("#editbook input[name='BookName']").val(edit_data.BookName)
        $("#editbook input[name='BookDescription']").val(edit_data.BookDescription)
        $("#editbook input[name='SeoUrl']").val(edit_data.SeoUrl)
        $("#editbook input[name='SeoTitle']").val(edit_data.SeoTitle)
        $("#editbook input[name='SeoKeywords']").val(edit_data.SeoKeywords)
        $("#editbook input[name='SeoDescription']").val(edit_data.SeoDescription)
        $("#editbook input[name='SeoDescription']").val(edit_data.SeoDescription)
        $("#editbook input[name='IsSoftDelete'][value='"+edit_data.IsSoftDelete+"']").click()
     })
    //编辑书籍
    $(document).on("click","#editbook .sumbit_btn",function(){
        var params ={
            BookName:$("#editbook input[name='BookName']").val(),
            BookDescription:$("#editbook input[name='BookDescription']").val(),
            SeoUrl:$("#editbook input[name='SeoUrl']").val(),
            SeoTitle:$("#editbook input[name='SeoTitle']").val(),
            SeoKeywords:$("#editbook input[name='SeoKeywords']").val(),
            SeoDescription:$("#editbook input[name='SeoDescription']").val(),
            IsSoftDelete:$('#editbook input[name="IsSoftDelete"]:checked').val()
        }
        $.ajax({
            type: "post",
            url: url+"/Book/UpdateBook",
            data:`{
                "BookId": "${edit_data.BookId}",
                "BookName": "${params.BookName}",
                "BookDescription": "${params.BookDescription}",
                "SeoUrl": "${params.SeoUrl}",
                "SeoTitle": "${params.SeoTitle}",
                "SeoKeywords": "${params.SeoKeywords}",
                "SeoDescription": "${params.SeoDescription}",
                "IsSoftDelete": "${params.IsSoftDelete}"
            }`,
            contentType:"application/json",
            dataType: "json",
            async:false,
            success: function(data){
              console.log("data",data)
              if(data.ServiceCode=="SUCCESS"){
                getBookList()
                $("#editbook").modal("hide")
              }
              alert(`${data.Message}`);
            }
        })
    })
      //新增书籍
      $(document).on("click","#addbook .sumbit_btn",function(){
        var params ={
            BookName:$("#addbook input[name='BookName']").val(),
            BookDescription:$("#addbook input[name='BookDescription']").val(),
            SeoUrl:$("#addbook input[name='SeoUrl']").val(),
            SeoTitle:$("#addbook input[name='SeoTitle']").val(),
            SeoKeywords:$("#addbook input[name='SeoKeywords']").val(),
            SeoDescription:$("#addbook input[name='SeoDescription']").val()
        }
        if(params.SeoUrl == ''){
            alert("SeoUrl不能为空");
            return;
        }
        $.ajax({
            type: "post",
            url: url+"/Book/Addbook",
            data:`{
                "BookName":"${params.BookName}",
                "BookDescription":"${params.BookDescription}",
                "SeoUrl":"${params.SeoUrl}",
                "SeoTitle":"${params.BookName}",
                "SeoKeywords":"${params.SeoTitle}",
                "SeoDescription":"${params.SeoDescription}"
            }`,
            contentType:"application/json",
            dataType: "json",
            async:false,
            success: function(data){
              console.log("data",data)
              if(data.ServiceCode=="SUCCESS"){
                $("#addbook input[name='BookName']").val("")
                $("#addbook input[name='BookDescription']").val("")
                $("#addbook input[name='SeoUrl']").val("")
                $("#addbook input[name='SeoTitle']").val("")
                $("#addbook input[name='SeoKeywords']").val("")
                $("#addbook input[name='SeoDescription']").val("")

                getBookList()
                $("#addbook").modal("hide")
              }
              alert(`${data.Message}`);
            }
        })
    })
    //删除书籍
    $(document).on("click",".remove_btn",function(){
        var BookId = $(this).attr("data-BookId")
        if(confirm("确定从物理上删除书籍吗?删除不可恢复!")){
            $.ajax({
                type: "post",
                url: url+"/Book/PhysicsDeleteBookByBookId",
                data:`{"BookId":"${BookId}"}`,
                contentType:"application/json",
                dataType: "json",
                success: function(data){
                    if(data.ServiceCode=="SUCCESS"){
                        getBookList()
                    }
                    alert(`${data.Message}`);
                },
                error:function(e){}
            });
        }

    })
})