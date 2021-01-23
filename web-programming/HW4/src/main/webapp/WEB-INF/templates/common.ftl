<#macro header>
    <header>
        <a href="/"><img src="/img/logo.png" alt="Codeforces" title="Codeforces"/></a>
        <div class="languages">
            <a href="#"><img src="/img/gb.png" alt="In English" title="In English"/></a>
            <a href="#"><img src="/img/ru.png" alt="In Russian" title="In Russian"/></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user=user nameOnly=true/>
                |
                <a href="#">Logout</a>
            <#else>
                <a href="#">Enter</a>
                |
                <a href="#">Register</a>
            </#if>
        </div>
        <nav>
            <style>
                header nav ul li .${uri} {
                    text-decoration: underline;
                    font-weight: bold;
                }
            </style>
            <ul>
                <li><a class="index" href="/index">Index</a></li>
                <li><a class="mischelp" href="/misc/help">Help</a></li>
                <li><a class="users" href="/users">Users</a></li>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <#list posts as post>
            <section>
                <div class="header">
                    ${post.title}
                </div>
                <div class="body">
                    <#if post.text?length gt 250>
                        <p>${post.text?substring(0, 250)}...</p>
                    <#else>
                        <p>${post.text}</p>
                    </#if>
                </div>
                <div class="footer">
                    <a href="/post?post_id=${post.id}">View all</a>
                </div>
            </section> </#list>
    </aside>

</#macro>

<#macro footer>
    <footer>
        <a href="#">Codeforces</a> &copy; 2010-2019 by Mike Mirzayanov
    </footer>
</#macro>

<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" type="text/css" href="/css/normalize.css">
        <link rel="stylesheet" type="text/css" href="/css/style.css">
        <link rel="icon" href="/favicon.ico" type="image/x-icon"/>
        <link rel="shortcut icon" href="/favicon.ico" type="image/x-icon"/>
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro blog post full=true>
    <article>
        <div class="title">${post.title}</div>
        <div class="information">By ${findBy(users, "id", post.userId).handle}</div>
        <div class="body">
            <p>Hello, Codeforces</p>
            <#if post.text?length gt 250 && full=false>
                <p>${post.text?substring(0, 250)}...</p>
            <#else>
                <p>${post.text}</p>
            </#if>

        </div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+173</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                2 days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">68</a>
            </div>
        </div>
    </article>
</#macro>

<#macro userTable>
    <div class="datatable">
        <div class="caption">Users</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Handle</th>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <style>
                    div table tbody tr:nth-child(${2 *(user_index + 1)}) td:nth-child(2) {
                        color: ${user.color};
                    }
                </style>
                <tr>
                    <td>${user.id}</td>
                    <td>${user.handle}</td>
                    <td><@userlink user=user nameOnly=true/></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#macro>

<#macro userlink user nameOnly=true>
    <#if nameOnly>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a class="userLinkWithColor" href="/user?handle=${user.handle}">${user.name}</a>
        <style>
            main .userLinkWithColor {
                color: ${user.color};
                text-decoration: none;
                font-size: large;
                font-weight: bold;
            }
        </style>
    </#if>

</#macro>

<#macro notFound>
    <h1>404</h1>
    <div>No such page</div>
</#macro>

<#macro userData user>
    <ul>
        <li>id: ${user.id}</li>
        <li>handle: ${user.handle}</li>
        <li>name: ${user.name}</li>
    </ul>

</#macro>

<#macro userPage>
    <#if handle??>
        <#assign viewedUser=findBy(users, "handle", handle)!/>
    <#else>
        <#assign viewedUser=findBy(users, "id", user_id)!/>
    </#if>
    <#if viewedUser??>

        <#if viewedUser.id??>
            <div class="userPage">
                <div class="userPageText">
                    <@c.userlink user=viewedUser nameOnly=false/>
                    <@c.userData user=viewedUser/>
                    <a href="/posts?user_id=${viewedUser.id}">${viewedUser.countPosts}</a>
                </div>
                <img src="img/user.jpg" alt="userPic">
            </div>
        <#else>
            <h1>No such user</h1>
        </#if>
        <div class="arrow">
            <#assign next=nextUser(users, viewedUser.handle)!/>
            <#assign prev=prevUser(users, viewedUser.handle)!/>

            <#if prev.handle??>
                <a href="/user?handle=${prev.handle}">&#8592;</a>
            <#else>
                <span>&#8592;</span>
            </#if>

            <#if next.handle??>
                <a class="rightArrow" href="/user?handle=${next.handle}">&#8594;</a>
            <#else>
                <span>&#8594;</span>
            </#if>
        </div>
    <#else>
        <@notFound/>
        <p>chlen</p>
    </#if>
</#macro>

<#function findBy items key id>
    <#list items as item>
        <#if item[key]==id>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function nextUser items handle>
    <#list items as item>
        <#if item["handle"]==handle>
            <#if item_has_next>
                <#return items[item_index + 1]>
            </#if>
        </#if>
    </#list>
</#function>

<#function prevUser items handle>
    <#list items as item>
        <#if item["handle"]==handle>
            <#if item_index gt 0>
                <#return items[item_index-1]>
            </#if>
        </#if>
    </#list>
</#function>
