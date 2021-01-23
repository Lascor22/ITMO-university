<#-- @ftlvariable name="enterForm" type="ru.itmo.wp.form.UserCredentials" -->
<#-- @ftlvariable name="user" type="ru.itmo.wp.domain.User" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <#if user??>
        <div class="userFields">
            <ul>
                <li>id : ${user.id}</li>
                <li>login : ${user.login}</li>
                <li>creationTime : ${user.creationTime}</li>
            </ul>
        </div>
    <#else>
        <span><b>No such user</b></span>
    </#if>
</@c.page>
