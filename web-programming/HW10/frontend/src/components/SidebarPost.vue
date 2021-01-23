<template>
    <section>
        <div class="header">
            By {{post.user.name}}
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
        props: ['post'],
        name: "SidebarPost",
        methods: {
            changePage: function (page, post) {
                this.$root.$emit("onChangePage", page, post);
            },
        }

    }
</script>

<style scoped>

</style>
