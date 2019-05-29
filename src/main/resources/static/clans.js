$(function(){
    $("#searchBt").click(function(){
        if(checkInput()) {
            var id = $("#searchInput").val();
            var ajax = $.ajax({
                url: "/test/json?name=test",
                type: "GET",
                //data : {id: id},
                timeout: 5000, //超时时间
                beforeSend: function () {
                    $("#searchBt").removeClass("btn-primary").addClass("btn-secondary").html("<i class='fa fa-spinner fa-pulse' style='cursor:no-drop'></i>"); //在后台返回success之前显示loading图标
                },
                success: function (result) {
                    if (result.data === "false") {
                        alert("request failed");
                    } else {
                        reset();
                        var res = JSON.parse(result);
                        $("#name").text(res.name);
                        $("#clanLevel").text(" LV." + res.clanLevel);
                        $("#tag").text(res.tag.replace('%', '#'));
                        $("#clanImg").html("<img style='height:70px;width:70px;' src='" + res.badgeUrls.small + "'/>");
                        $("#members").text(res.members + "/50");

                        $("#clanPoints").text("🏆" + res.clanPoints);
                        $("#clanVersusPoints").text("🏆" + res.clanVersusPoints);
                        $("#type").text(res.type);
                        $("#requiredTrophies").text("🏆" + res.requiredTrophies);
                        $("#description").text(res.description);

                        $("#warFrequency").text(res.warFrequency);
                        $("#warWinStreak").text(res.warWinStreak);
                        $("#isWarLogPublic").text(res.isWarLogPublic);
                        $("#warWins").text(res.warWins);
                        $("#warTies").text(res.warTies);
                        $("#warLosses").text(res.warLosses);
                        fullpage_api.moveSectionDown();
                    }
                    $("#searchBt").removeClass("btn-secondary").addClass("btn-primary").text("搜索");
                },
                fail: function () {
                    alert("请求失败");
                    $("#searchBt").removeClass("btn-secondary").addClass("btn-primary").text("搜索");
                },
                complete: function (XMLHttpRequest, status) { //请求完成后最终执行参数
                    if (status === 'timeout') {//超时,status还有success,error等值的情况
                        ajax.abort();
                        $("#searchBt").removeClass("btn-secondary").addClass("btn-primary").text("搜索");
                    }
                }
            });
        }
    });

    $("#arrowDownBt").click(function () {
        fullpage_api.moveSectionDown();
    });
});

function reset() {
    $("#name").text("");
    $("#clanLevel").text("");
    $("#tag").text("");
    $("#clanImg").html("");
    $("#members").text("");
    $("#clanPoints").text("");
    $("#clanVersusPoints").text("");
    $("#type").text("");
    $("#requiredTrophies").text("");
    $("#description").text("");
    $("#warFrequency").text("");
    $("#warWinStreak").text("");
    $("#warWins").text("");
    $("#isWarLogPublic").text("");
    $("#warTies").text("");
    $("#warLosses").text("");
}

function checkInput() {
    var searchInput = document.getElementById("searchInput");
    var feedback = document.getElementById("inputFeedback");
    //不能为空
    if(searchInput.value === ""){
        feedback.innerHTML = "ID不能为空！";
        searchInput.classList.remove("is-valid");//清除合法状态
        searchInput.classList.add("is-invalid");//添加非法状态
        return false;
    }

    //长度检查
    if(searchInput.value.length < 8 || searchInput.value.length > 9){
        feedback.innerHTML = "ID长度只能为8~9位！";
        searchInput.classList.remove("is-valid");//清除合法状态
        searchInput.classList.add("is-invalid");//添加非法状态
        return false;
    }

    //只能包含数字和字母
    var patrn = /^[0-9a-zA-Z]+$/;//正则表达式
    if(!patrn.exec(searchInput.value)){
        feedback.innerHTML = "ID只能由字母和数字组成！";
        searchInput.classList.remove("is-valid");
        searchInput.classList.add("is-invalid");
        return false;
    }

    //清除错误提示，改成成功提示
    searchInput.classList.remove("is-invalid");
    searchInput.classList.add("is-valid");
    feedback.innerHTML="";
    return true;
}
