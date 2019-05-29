 $(function(){
     $("#searchBt").click(function(){
         var searchInput = document.getElementById("searchInput");
         var feedback = document.getElementById("inputFeedback");
         if(checkInput()) {
             var id = $("#searchInput").val();
             var ajax = $.ajax({
                 url: "/search/player",
                 type: "GET",
                 data: {id: id},
                 timeout: 5000, //超时时间
                 beforeSend: function () {
                     $("#searchBt").removeClass("btn-primary").addClass("btn-secondary").html("<i class='fa fa-spinner fa-pulse' style='cursor:no-drop'></i>"); //在后台返回success之前显示loading图标
                 },
                 success: function (result) {
                     if (result.data === "false") {
                         $("#searchInput").removeClass("is-valid").addClass("is-invalid");
                         $("#inputFeedback").text("未找到该玩家");
                     } else {
                         reset();
                         var res = $.parseJSON(result.data);
                         $("#name").text(res.name);
                         $("#expLevel").text(" LV." + res.expLevel);
                         $("#tag").text(res.tag.replace('%', '#'));
                         if ('clan' in res) {
                             $("#clanImg").html("<img style='height:30px;width:30px;' src='" + res.clan.badgeUrls.small + "'/>" + res.clan.name + "(LV." + res.clan.clanLevel + ")");
                         }

                         $("#townHallLevel").text(" (" + res.townHallLevel + "本)");

                         if ('builderHallLevel' in res) {
                             $("#builderHallLevel").text(" (" + res.builderHallLevel + "本)");
                         }

                         $("#trophies").text("🏆" + res.trophies);
                         $("#bestTrophies").text("🏆" + res.bestTrophies);
                         $("#versusTrophies").text("🏆" + res.versusTrophies);
                         $("#bestVersusTrophies").text("🏆" + res.bestVersusTrophies);

                         $("#attackWins").text(res.attackWins);
                         $("#versusBattleWins").text(res.versusBattleWins);
                         $("#defenseWins").text(res.defenseWins);

                         $("#warStars").text(res.warStars);

                         if ('role' in res) {
                             $("#role").text(res.role);
                         }

                         $("#donations").text(res.donations);
                         $("#donationsReceived").text(res.donationsReceived);
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
    var searchInput = document.getElementById("searchInput");
    var feedback = document.getElementById("inputFeedback");
    $("#name").text("");
    $("#expLevel").text("");
    $("#tag").text("");
    $("#clanImg").html("");
    $("#townHallLevel").text("");
    $("#builderHallLevel").text("暂无");
    $("#trophies").text("");
    $("#bestTrophies").text("");
    $("#versusTrophies").text("");
    $("#bestVersusTrophies").text("");
    $("#attackWins").text("");
    $("#versusBattleWins").text("");
    $("#defenseWins").text("");
    $("#warStars").text("");
    $("#role").text("暂未加入部落");
    $("#donations").text("");
    $("#donationsReceived").text("");
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
