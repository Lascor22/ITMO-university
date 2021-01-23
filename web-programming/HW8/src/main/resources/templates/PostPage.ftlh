<#-- @ftlvariable name="post" type="ru.itmo.wp.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#if post??>
        <@c.post post=post/>
        <div class="comment-box">
            <#if user??>
                <div class="form-comment">
                    <div class="header">Write Comment</div>
                    <div class="body">
                        <form method="post" action="">
                            <input type="hidden" name="action" value="writeComment"/>
                            <div class="field">
                                <div class="name">
                                    <label for="text">Text</label>
                                </div>
                                <div class="value">
                                    <textarea id="text" name="text"></textarea>
                                </div>
                                <@c.error "comment.text"/>
                            </div>
                            <div class="button-field">
                                <input type="submit" value="Write">
                            </div>
                        </form>
                    </div>
                </div>
            </#if>
            <@c.comments post=post/>
        </div>
    <#else>
        <span><b>NO SUCH POST</b></span>
    </#if>
</@c.page>
