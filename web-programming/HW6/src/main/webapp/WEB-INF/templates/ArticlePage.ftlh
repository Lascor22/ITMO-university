<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Write new article</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="create"/>

                <div class="field">
                    <div class="name">
                        <label for="hidden">Hidden</label>
                    </div>
                    <div class="value">
                        <input class="checkboxHidden" type="checkbox" id="hidden" name="hidden">
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="title">Title</label>
                    </div>
                    <div class="value">
                        <input id="title" name="title"/>
                    </div>
                </div>

                <div class="field">
                    <div class="name">
                        <label for="text">Write text</label>
                    </div>
                    <div class="textArea">
                        <textarea id="text" name="text"></textarea>
                    </div>
                </div>
                <div class="error">
                    ${error!}
                </div>
                <div class="button-field">
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>
    <script>
        $(function () {
            $(".form-box form").submit(function () {
                var title = $(this).find("input[name='title']").val();
                var text = $(this).find("textarea[name='text']").val();
                var $error = $(this).find(".error");
                var hidden = $(".checkboxHidden").is(":checked");
                ajax({
                    action: "create",
                    title: title,
                    text: text,
                    hidden: hidden
                }, function (response) {
                    if (response["error"]) {
                        $error.text(resAponse["error"]);
                    }
                });
                return false;

            })
        })
    </script>
</@c.page>


