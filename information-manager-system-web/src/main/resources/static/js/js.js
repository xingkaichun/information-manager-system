/*通用方法*/
var tools = (function () {
    var flag = "getElementsByClassName" in document;
    return {
        //listToArray:将类数组转化为数组（兼容所有浏览器）
        listToArray: function (likeAry) {
            var ary = [];
            if (flag) {
                ary = Array.prototype.slice.call(likeAry, 0);
            } else {
                for (var i = 0; i < likeAry.length; i++) {
                    ary[ary.length] = likeAry[i];
                }
            }
            return ary;
        }
    };
})();


/*文章内容左侧伸缩菜单*/
function flexMenu(event) {
    // var a = event.target;
    var a = event.srcElement ? event.srcElement : event.target;
    var b = a.parentNode;
    var c = b.children;
    if (c.index == true) {
        for (var i = 1; i < c.length; i++) {
            c[i].style.display = 'block';
        }
        c.index = false;
    } else {
        for (var i = 1; i < c.length; i++) {
            c[i].style.display = 'none';
        }
        c.index = true;
    }
}

/*滚动到顶部*/
function scrollTotop() {
    var backTop = document.getElementById("scrolltotop");
    var flag = /Android|webOS|iPhone|iPod|BlackBerry/i.test(navigator.userAgent) ? true : false;
    window.onscroll = function () {
        var scrollTop = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop;
        if (scrollTop > 1 && flag == false) {  //按钮出现的条件
            backTop.style.display = "block";
        } else {
            backTop.style.display = "none";
        }
    }
    var iid;
    backTop.onclick = function () {  //点击事件
        iid = setInterval(function () {
            var current = document.documentElement.scrollTop ? document.documentElement.scrollTop : document.body.scrollTop;
            if (current === 0) {
                clearInterval(iid);
            }
            document.documentElement.scrollTop = current - 50;
            document.body.scrollTop = current - 50;
        }, 16);
    }
    window.onmousewheel = function () {
        clearInterval(iid);
    }
}
scrollTotop();

/*点击出现评论框*/
function conmentInput() {
    var a = event.srcElement ? event.srcElement : event.target;
    var b = a.parentNode;
    var oDiv = b.parentNode;
    var comment = document.createElement("div");
    comment.className="input_reply";
    comment.innerHTML="<input type=\"text\" placeholder=\"请输入评论\"><button>回复</button>";
    if (a.index == true) {
        oDiv.removeChild(oDiv.children[2]);
        a.index = false;
    } else {
        oDiv.appendChild(comment);
        a.index = true;
    }
}

/*移动端左侧菜单按钮事件*/
function toggleMenu(id1,id2) {
    var a = document.getElementById(id1);
    var b = document.getElementById(id2);
        if (a.index==false){
            a.style.display = 'none';
            b.style.display = 'block';
            a.index = true;
        }else {
            a.style.display = 'block';
            b.style.display = 'none';
            a.index = false;
        }
}
 /*课程菜单导航*/
function courseNav(event) {
    var a = event.srcElement ? event.srcElement : event.target;
    var b = a.parentNode;
    var c = b.children;
    for (var i=0; i<c.length; i++){
        c[i].className = "";
    }
    a.className = "on";
    var cont = document.getElementsByClassName("course_name");
    var str1 = a.innerHTML;
    for (var j=0; j<cont.length; j++){
        if (str1 !== cont[j].innerHTML){
            cont[j].parentNode.style.display = 'none';
        }else {
            cont[j].parentNode.style.display = 'block';
        }
    }
}

/*弹出框*/
function popBox(e) {
    var popbox = document.getElementById("pop_box");
    if (e == true){
        popbox.style.display = 'block';
    }else {
        popbox.style.display = 'none';
    }
}

/*教程发布 文章排列升序*/
function sortUp(event) {
    window.event? window.event.cancelBubble = true : e.stopPropagation();
    var o = event.srcElement ? event.srcElement : event.target;
    var curLi = o.parentNode.parentNode;
    var oLis = o.parentNode.parentNode.parentNode.children;
    var arr =tools.listToArray(oLis);
    var curLi_index = arr.indexOf(curLi);
    if (curLi_index == 0){
        alert('已经到达最前！');
    }else {
        curLi.parentNode.insertBefore(curLi, oLis[curLi_index - 1]);
    }
}












