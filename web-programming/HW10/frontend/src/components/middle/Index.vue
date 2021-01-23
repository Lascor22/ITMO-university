<template>
    <div>
        <PostTemplate v-for="post in posts" :post="post" :commentsCount="post.comments.length" v-bind:key="post.id"/>
    </div>
</template>

<script>
    import PostTemplate from "../PostTemplate";

    export default {
        props: ["posts"],
        name: "Index",
        components: {
            PostTemplate,
        }
    }
</script>

<style scoped>

</style>
