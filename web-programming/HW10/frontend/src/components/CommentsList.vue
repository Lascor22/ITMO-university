<template>
    <div class="comments">
        <div class="comment" v-for="comment in comments" v-bind:key="comment.id">
            <span>{{comment.user.login}}</span>
            <div class="content">
                {{comment.text}}
            </div>
        </div>
    </div>

</template>

<script>
    export default {
        props: ["comments"],
        name: "CommentsList",
    }
</script>

<style scoped>

    .comments {
        display: flex;
        flex-direction: column;
        float: initial;
        width: 40rem;
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

    .post {
        display: flex;
        flex-direction: column;
    }
</style>