<template>
    <section>
        <div class="header">
            By {{users[post.userId].login}}
        </div>
        <div class="body">
            {{post.text}}
        </div>
        <div class="footer">
            <a :href="'#page=Post/' + post.id" @click="changePage('Post', post)">View all</a>
        </div>
    </section>

</template>

<script>
    export default {
        props: ['users', 'post'],
        name: "SidebarPost",
        methods: {
            changePage: function (page) {
                this.$root.$emit("onChangePage", page, {post: this.post});
            }
        }
    }
</script>

<style scoped>

</style>
