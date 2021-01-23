<#import "common.ftl" as c/>

<@c.page>
    <#assign post=c.findBy(posts "id" post_id)!/>
    <@c.blog post true/>
</@c.page>
