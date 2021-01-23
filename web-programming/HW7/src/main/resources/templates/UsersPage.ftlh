<#-- @ftlvariable name="users" type="ru.itmo.wp.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <#if user??>
                    <th>Disable</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as currentUser>
                    <tr>
                        <td class="id"><a href="/user/${currentUser.id}">${currentUser.id}</a></td>
                        <td class="login">${currentUser.login}</td>
                        <td class="creationTime">${currentUser.creationTime}</td>
                        <#if user??>
                            <td class="disabled">
                                <#if currentUser != user>
                                    <form method="post" action="">
                                        <input type="hidden" name="action" value="">
                                        <input type="hidden" name="userId" value="${currentUser.id}">
                                        <#if currentUser.disable>
                                            <input type="hidden" id="change" name="change" value="false">
                                            <input id="${currentUser.id}" type="submit" value="Enable">
                                        <#else>
                                            <input type="hidden" id="change" name="change" value="true">
                                            <input id="${currentUser.id}" type="submit" value="Disable">
                                        </#if>
                                    </form>
                                </#if>
                            </td>
                        </#if>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
