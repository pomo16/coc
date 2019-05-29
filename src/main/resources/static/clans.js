$(function(){
    $("#searchBt").click(function(){
        if(checkInput()) {
            var id = $("#searchInput").val();
            var ajax = $.ajax({
                url: "/search/clan",
                type: "GET",
                data : {id: id},
                beforeSend: function () {
                    $("#searchBt").removeClass("btn-primary").addClass("btn-secondary").html("<i class='fa fa-spinner fa-pulse' style='cursor:no-drop'></i>"); //在后台返回success之前显示loading图标
                },
                success: function (result) {
                    if (result.data === "false") {
                        $("#searchInput").removeClass("is-valid").addClass("is-invalid");
                        $("#inputFeedback").text("未找到该部落");
                    } else {
                        reset();
                        var res = JSON.parse(result.data);
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
    $("#searchInput").removeClass("is-invalid").addClass("is-valid");
    $("#inputFeedback").text("");
}

function checkInput() {
    //不能为空
    if($("#searchInput").val() === ""){
        $("#searchInput").removeClass("is-valid").addClass("is-invalid");
        $("#inputFeedback").text("ID不能为空！");
        return false;
    }

    //长度检查
    if($("#searchInput").val().length < 8 || $("#searchInput").val().length > 9){
        $("#searchInput").removeClass("is-valid").addClass("is-invalid");
        $("#inputFeedback").text("ID长度只能为8~9位！");
        return false;
    }

    //只能包含数字和字母
    var patrn = /^[0-9a-zA-Z]+$/;//正则表达式
    if(!patrn.exec($("#searchInput").val())){
        $("#searchInput").removeClass("is-valid").addClass("is-invalid");
        $("#inputFeedback").text("ID只能由字母和数字组成！");
        return false;
    }

    //清除错误提示，改成成功提示
    $("#searchInput").removeClass("is-invalid").addClass("is-valid");
    $("#inputFeedback").text("");
    return true;
}
