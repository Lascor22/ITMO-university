<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>
<@c.page>

    <div class="form-box">
        <div class="header">Send Message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="sendMessage"/>

                <div class="select">
                    <span>Message to</span>
                    <select id="targetLogin" name="targetLogin">
                        <#list users as user>
                            <option value="${user.login}">${user.login}</option>
                        </#list>
                    </select>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="text">Write text</label>
                    </div>
                    <div class="textArea">
                        <textarea id="text" name="text" value="${text!}"> </textarea>
                    </div>
                </div>

                <#if error??>
                    <div class="error">
                        ${error!}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" value="Send">
                </div>
            </form>
        </div>
    </div>

<#--

    <form class="TalksForm" method="post" action="">
        <input type="hidden" name="action" value="sendMessage"/>
        <div class="field">
            <div class="select">
                <span>to</span>
                <select id="targetUserId" name="targetUserId">
                    <#list users as user>
                        <option value="${user.id}">${user.login}</option>
                    </#list>
                </select>
            </div>

            <div class="TalkText">
                <textarea id="text" name="text">
                </textarea>
            </div>
        </div>
        <#if error??>
            <div class="error">
                ${error!}
            </div>
        </#if>
        <div class="button-send">
            <input type="submit" value="Send">
        </div>
    </form>
-->


    <div class="datatable">
        <div class="caption">Talks</div>
        <table>
            <thead>
            <tr>
                <th>From</th>
                <th>To</th>
                <th>Text</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
                <#assign from=c.findBy(users, "id", talk.sourceUserId)!/>
                <#assign to=c.findBy(users, "id", talk.targetUserId)!/>
                <tr>
                    <td>${from.login}</td>
                    <td>${to.login}</td>
                    <td>${talk.text}</td>
                    <td>${talk.creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>