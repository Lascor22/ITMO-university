<#import "commons.ftlh" as c>

<@c.page>
    <div class="loading"></div>
    <div class="articles">
        <template>
            <article>
                <div class="title"></div>
                <div class="information"></div>
                <div class="body">

                </div>
                <div class="footer">
                    <div class="left">
                        <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                        <span class="positive-score">+173</span>
                        <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
                    </div>
                    <div class="right">
                        <span class="creationTime"></span>
                    </div>
                </div>
            </article>
        </template>
    </div>
    <script>
        $(function () {
            var $noData = $(".loading");
            $noData.text("Loading...");
            ajax({
                action: "findAll"
            }, function (response) {
                $noData.hide();
                response["articles"].forEach(function (article) {
                    var user = findBy(response["users"], "id", article["userId"]);
                    var $article = $($(".articles template").prop("content")).clone();
                    $article.find(".title").text(article["title"]);
                    $article.find(".body").text(article["text"]);
                    $article.find(".information").text("By " + user.login);
                    $article.find(".creationTime").text(article["creationTime"]);
                    $(".articles").append($article);
                });
            });
        })
    </script>
</@c.page>
