<#import "commons.ftlh" as c>

<@c.page>
    <div class="enter form-box">
        <div class="header">New Notice</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="create"/>
                <div class="field">
                    <div class="name">
                        <label for="noticeArea">Notice</label>
                    </div>
                    <div class="value">
                        <textarea id="noticeArea" name="content"></textarea>
                    </div>
                    <@c.error "noticeArea.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>


