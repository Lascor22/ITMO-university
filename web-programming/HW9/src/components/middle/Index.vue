<template>
    <div>
        <PostTemplate v-for="post in posts" :post="post" :login="users[post.userId].login" :commentCount="getCommentCount(post)" v-bind:key="post.id"/>
    </div>
</template>

<script>
    import PostTemplate from "../PostTemplate";

    export default {
        props: ["users", "posts", "comments"],
        name: "Index",
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

</style>
