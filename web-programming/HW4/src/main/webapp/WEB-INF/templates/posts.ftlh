<#import "common.ftl" as c/>
<#if user_id??>
    <@c.page>
        <#list posts as post>
            <#if post.userId==user_id>
                <@c.blog post false/>
            </#if>
        </#list>
    </@c.page>
<#else>
    <@c.notFound/>
</#if>
