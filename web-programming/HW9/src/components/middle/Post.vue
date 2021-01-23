<template>
    <div class="post">
        <PostTemplate :post="post" :login="users[post.userId].login" :commentCount="getCommentCount(post)"/>
        <div class="comments">
            <div class="comment" v-for="comment in comments" v-bind:key="comment.id">
                <span>{{users[comment.userId].login}}</span>
                <div class="content">
                    {{comment.text}}
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import PostTemplate from "../PostTemplate";

    export default {
        props: ["post", "users", "comments"],
        name: "Post",
        components: {
            PostTemplate,
        },
        methods: {
            getCommentCount(post) {
                let list = Object.values(this.comments);
                let count = 0;
                for (let i = 0; i < list.length; i++) {
                    if (list[i].postId === post.id) {
                        count++;
                    }
                }
                return count;
            }
        }

    }
</script>

<style scoped>

    .comments {
        display: flex;
        flex-direction: column;
        float: left;
        width: 20rem;
    }

    .comments .comment {
        border-radius: var(--border-radius);
        border: 1px solid var(--border-color);
        margin-bottom: 1rem;
        display: flex;
        flex-direction: column;
        text-align: center;
    }

    .comments .comment div {
        text-align: left;
        margin-bottom: 1rem;
        margin-left: 1rem;
    }

    .comments .comment span {
        margin-bottom: 1rem;
    }

    .comment-box .form-comment {
        margin-bottom: 1rem;
        padding-bottom: 1rem;
    }

    .comment-box {
        display: flex;
        flex-direction: column;
    }
</style>