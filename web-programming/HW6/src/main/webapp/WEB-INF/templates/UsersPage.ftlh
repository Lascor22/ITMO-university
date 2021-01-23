<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user_id"></td>
                    <td class="user_login"></td>
                    <#if user?? && user.admin>
                        <td class="isAdmin"><a href=""></a></td>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            var $noData = $(".users .noData");
            $noData.find("td").text("Loading...");
            ajax({
                action: "findAll"
            }, function (response) {
                response["users"].forEach(function (user) {
                    $noData.hide();
                    var $tr = $($(".users template").prop("content")).clone();
                    $tr.find(".user_id").text(user["id"]);
                    $tr.find(".user_login").text(user["login"]);
                    $tr.find("tr").attr("data-userId", user["id"]);
                    $tr.find(".isAdmin a").text(user["admin"] ? "disable" : "enable");
                    $tr.find(".isAdmin a").attr("data-userId", user["id"]);
                    $(".users tbody").append($tr);
                });
                $(".isAdmin a").click(function () {
                    var $tr = $(this);
                    var id = $tr.attr("data-userId");
                    var value = $tr.text() === "enable";
                    $(ajax({
                        action: "changeAdminStatus",
                        id: id,
                        value: value
                    }, function (response) {
                        if (response["answer"]) {
                            $tr.text(value ? "disable" : "enable");
                        } else {
                            $.notify(response["error"]);
                        }
                    }));
                    return false;
                });
                /*$(".users tr").click(function () {
                    ajax({
                        action: "findUser",
                        userId: $(this).attr("data-userId")
                    }, function (response) {
                        notify(response["user"].creationTime);
                    });
                });*/
            });
        })
    </script>
</@c.page>
