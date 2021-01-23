<!--suppress HtmlUnknownTag -->
<template>
    <div class="middle">
        <Sidebar :posts="posts" :users="users"/>
        <main>
            <Index :users="users" :posts="getPosts" :comments="comments" v-if="page === 'Index'"/>
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <AddPost v-if="page === 'AddPost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <Users :users="getUsers" v-if="page === 'Users'"/>
            <Post :post="post" :users="users" :comments="getComments" v-if="page === 'Post'"/>
        </main>
    </div>
</template>
<script>
    import Index from './middle/Index';
    import Enter from './middle/Enter';
    import Register from './middle/Register';
    import AddPost from './middle/AddPost';
    import EditPost from "./middle/EditPost";
    import Sidebar from "./Sidebar";
    import Users from "./middle/Users";
    import Post from "./middle/Post";

    export default {
        name: "Middle",
        props: ['users', 'posts', 'comments'],
        data: function () {
            return {
                page: "Index",
                post: undefined,
            }
        },
        computed: {
            getUsers: function () {
                return Object.values(this.users);
            },
            getPosts: function () {
                return Object.values(this.posts).sort((a, b) => b.id - a.id);
            },
            getComments: function () {
                return Object.values(this.comments).filter((comment) => comment.postId === this.post.id);
            }
        },
        components: {
            EditPost,
            Index,
            Enter,
            Register,
            Sidebar,
            AddPost,
            Users,
            Post,
        }, beforeCreate() {
            this.$root.$on("onChangePage", (page, data) => {
                this.page = page;
                this.post = data.post;
            });
        }
    }
</script>

<style scoped>

</style>
