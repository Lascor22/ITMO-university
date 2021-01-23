<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.String" -->
<#import "common.ftl" as c/>
<@c.page>
    <#if handle?? || user_id??>
        <@c.userPage/>
    <#else>
        <@c.notFound/>
    </#if>
</@c.page>