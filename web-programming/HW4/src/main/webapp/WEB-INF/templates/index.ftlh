<#import "common.ftl" as c/>

<@c.page>
    <#assign reversePosts=posts?reverse>
    <#list reversePosts as post>
        <@c.blog post false/>
    </#list>
</@c.page>
