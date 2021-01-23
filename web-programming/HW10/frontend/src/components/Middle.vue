<template>
    <div class="middle">
        <aside>
            <SidebarPost v-for="post in viewPosts" :post="post" :key="post.id"/>
        </aside>
        <main>
            <Index :posts="getPosts" v-if="page === 'Index'"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users :users="users" v-if="page === 'Users'"/>
            <AddPost v-if="page === 'AddPost'"/>
            <Post :post="post" :user="user" v-if="page === 'Post'"/>
        </main>
    </div>
</template>
<script>
    import Index from './middle/Index';
    import Enter from './middle/Enter';
    import Register from './middle/Register';
    import SidebarPost from './SidebarPost';
    import Users from "./middle/Users";
    import AddPost from "./middle/AddPost";
    import Post from "./middle/Post";

    export default {
        name: "Middle",
        props: ["posts", "users", "user"],
        data: function () {
            return {
                page: "Index",
                post: null,
            }
        },
        computed: {
            viewPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
            },
            getPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id);
            }
        },
        components: {
            AddPost,
            Index,
            Enter,
            Register,
            SidebarPost,
            Users,
            Post,
        }, beforeCreate() {
            this.$root.$on("onChangePage", (page, post) => {
                this.page = page;
                this.post = post;
            });
        }
    }
</script>

<style scoped>

</style>
