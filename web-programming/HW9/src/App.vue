<template>
    <!--suppress HtmlUnknownTag -->
    <body id="app">
    <Header :userId="userId" :users="users"/>
    <Middle :users="users" :posts="posts" :comments="comments"/>
    <Footer/>
    </body>
</template>

<script>
    import Header from './components/Header'
    import Middle from './components/Middle'
    import Footer from './components/Footer'

    export default {
        name: 'app',
        data: function () {
            return this.$root.$data;
        },
        components: {
            Header,
            Middle,
            Footer
        }, beforeCreate() {
            this.$root.$on("onLogout", () => {
                this.userId = null;
            });
            this.$root.$on("onEnter", (login) => {
                let users = Object.values(this.users).filter(u => u.login === login);
                if (users.length) {
                    this.userId = users[0].id;
                    this.$root.$emit("onEnterSuccess");
                } else {
                    this.$root.$emit("onEnterValidationError", "Invalid login/password.");
                }
            });
            this.$root.$on("onAddPost", (title, text) => {
                if (this.userId) {
                    if (!title || title.length > 5) {
                        this.$root.$emit("onAddPostValidationError", "Title is invalid");
                    } else if (!text || text.length > 10) {
                        this.$root.$emit("onAddPostValidationError", "Text is invalid");
                    } else {
                        const id = Math.max(...Object.keys(this.posts)) + 1;
                        this.$set(this.posts, id, {
                            id,
                            userId: this.userId,
                            title,
                            text
                        })
                    }
                } else {
                    this.$root.$emit("onAddPostValidationError", "No access");
                }
            });
            this.$root.$on("onRegisterUser", (login, name, password) => {
                if (!validateLogin(this.users, login, (message) => {
                    this.$root.$emit("onRegisterUserValidationError", message);
                })) {
                    //no operation
                } else if (!password || password.length > 10) {
                    this.$root.$emit("onRegisterUserValidationError", "Password is invalid");
                } else if (!name || name.length > 32) {
                    this.$root.$emit("onRegisterUserValidationError", "Name is invalid");
                } else {
                    const id = Math.max(...Object.keys(this.users)) + 1;
                    this.$set(this.users, id, {
                        id: id,
                        login: login,
                        name: name,
                        admin: false,
                    });
                    this.$root.$emit("onChangePage", "Enter");
                }
            });
            this.$root.$on("onEditPost", (id, text) => {
                if (this.userId) {
                    if (!id) {
                        this.$root.$emit("onEditPostValidationError", "ID is invalid");
                    } else if (!text || text.length > 10) {
                        this.$root.$emit("onEditPostValidationError", "Text is invalid");
                    } else {
                        let posts = Object.values(this.posts).filter(p => p.id === parseInt(id));
                        if (posts.length) {
                            posts.forEach((item) => {
                                item.text = text;
                            });
                        } else {
                            this.$root.$emit("onEditPostValidationError", "No such post");
                        }
                    }
                } else {
                    this.$root.$emit("onEditPostValidationError", "No access");
                }
            });
            function validateLogin(users, login, throwError) {
                if (!login) {
                    throwError("Login is empty");
                    return false;
                }
                if (login.length > 16 || login.length < 3) {
                    throwError("Login should be from 3 to 16 symbols");
                    return false;
                }
                if (!login.match("[a-z]+") || login.match("[a-z]+")[0] !== login) {
                    throwError("Login should be contains small latin symbols");
                    return false;
                }

                let listId = Object.keys(users);
                for (let i = 0; i < listId.length; i++) {
                    if (users[listId[i]].login === login) {
                        throwError("Login is used");
                        return false;
                    }
                }
                return true;
            }
        }
    }
</script>

<style>
</style>
