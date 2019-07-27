var user_info = JSON.parse(localStorage.getItem("UserInfo"))
console.log("user_info",user_info)

//点击搜索
$("#search_btn").click(function () {
    var content = $(".flex_input input[name='content']").val()
})

//新增
$("#primary_btn").click(function () {
    var name = $("#myModal2 input[name='name']").val()
    console.log("name",name)
    $.ajax({
        type: "post",
        url: url+"/Category/AddCategory",
        data:`{"CategoryName":"${name}","ParentCategoryId":null,"UserId":"${user_info.UserId}"}`,
        contentType:"application/json",
        dataType: "json",
        success: function(data){
            console.log(data)
            $("#myModal2 input[name='name']").val("")
            if(data.Result==null){
                if(data.ServiceCode=="SUCCESS"){
                    $("#tips").text("新增类别成功").addClass("alert-danger").show()
                    getNodes()
                }else{
                    $("#tips").text(`${data.Message}`).addClass("alert-danger").show()
                    setTimeout(function () {
                        $("#tips").removeClass("alert-danger").fadeOut()
                    },3000)
                }
            }
        },
        error:function(e){

        }
    });
    $("#myModal2").modal("hide")
})


//节点操作
var setting = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false
    },
    check: {
        enable: true
    },
    callback:{
        //删除回调函数
        beforeRemove:function (treeId,treeNode) {
            console.log("beforeRemove",treeId,treeNode)
            var istrue = confirm("确定删除吗?")
            var success_true = false
            if(istrue){
                $.ajax({
                    type: "post",
                    url: url+"/Category/DeleteCategory",
                    data:`{"CategoryId":"${treeNode.id}"}`,
                    contentType:"application/json",
                    dataType: "json",
                    async:false,
                    success: function(data){
                        if(data.ServiceCode=="SUCCESS"){
                            success_true = true
                        }else{
                            $("#tips").text(`${data.Message}`).addClass("alert-danger").show()
                            setTimeout(function () {
                                $("#tips").removeClass("alert-danger").fadeOut()
                            },3000)
                        }
                    },
                    error:function(e){

                    }
                });
            }else{
                console.log("不删除")
            }
            return success_true
        },
        //编辑回调函数
        beforeRename:function (treeId,treeNode,newName) {
            console.log("beforeRename",treeId,treeNode,newName)
            $.ajax({
                type: "post",
                url: url+"/Category/UpdateCategory",
                data:`{"CategoryId":"${treeNode.id}","CategoryName":"${newName}"}`,
                contentType:"application/json",
                dataType: "json",
                success: function(data){
                },
                error:function(e){

                }
            });
            return true
        }
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    edit: {
        enable: true

    }
};

//查询文章类别
function getNodes() {
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
                $.fn.zTree.init($("#treeDemo"), setting, serverArray(CategoryDTOList));
            }
        },
        error:function(e){

        }
    });
}
getNodes()
//获取zNodes
function serverArray(arr){
    for(var item = 0;item < arr.length;item++){
        if(arr[item].Children){
            arr[item].id = arr[item].CategoryId
            arr[item].name = arr[item].CategoryName
            arr[item].open = true
            arr[item].children = arr[item].Children
            serverArray(arr[item].Children)
        }
    }
    return arr
}


var newCount = 1;
function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='add node' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    //新增节点
    if (btn) btn.bind("click", function(){
        console.log("addHoverDom_btn",treeNode.id,newCount)
        $.ajax({
            type: "post",
            url: url+"/Category/AddCategory",
            data:`{"ParentCategoryId":"${treeNode.id}","CategoryName":"默认","UserId":"${user_info.UserId}"}`,
            contentType:"application/json",
            dataType: "json",
            success: function(data){
                console.log(data)
                if(data.ServiceCode=="SUCCESS"){
                    $("#tips").text("新增类别成功").addClass("alert-danger").show()
                    getNodes()
                }else{
                    $("#tips").text(`${data.Message}`).addClass("alert-danger").show()
                    setTimeout(function () {
                        $("#tips").removeClass("alert-danger").fadeOut()
                    },3000)
                }
            },
            error:function(e){

            }
        });

        return false;
    });
};
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};

