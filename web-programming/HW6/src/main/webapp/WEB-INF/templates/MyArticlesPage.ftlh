<#-- @ftlvariable name="articles" type="ru.itmo.wp.model.domain.Article[]" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="articles datatable">
        <div class="caption">Articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hidden</th>
            </tr>
            </thead>
            <tbody>
            <#list articles as article>
                <tr>
                    <td class="articleId">${article.id}</td>
                    <td class="articleTitle">${article.title}</td>
                    <td class="articleHidden">
                        <#if article.hidden>
                            <input id="${article.id}" type="submit" value="Show">
                        <#else>
                            <input id="${article.id}" type="submit" value="Hide">
                        </#if>
                    </td>
                </tr>
            </#list>
            </tbody>
            <script>
                $(function () {
                    $(".articleHidden input").click(function () {
                        var $button = $(this);
                        var id = $button.attr('id');
                        var currentHide = ($button.attr('value') === "Show");

                        ajax({
                            action: "changeHide",
                            id: id,
                            value: !currentHide
                        }, function () {
                            $button.attr('value', currentHide ? "Hide" : "Show");
                        });
                        return false;
                    });
                })
            </script>
        </table>
    </div>
</@c.page>
