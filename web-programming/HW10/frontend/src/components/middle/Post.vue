<template>
    <div class="post">
        <PostTemplate :post="post" :commentsCount="comments.length"/>
        <CommentsList v-if="flag" :comments="viewComments"/>
        <template v-if="user">
            <div class="form-comment">
                <div class="header">Write Comment</div>
                <div class="body">
                    <form @submit.prevent="onAdd(post)">
                        <div class="field">
                            <div class="name">
                                <label for="text">Text</label>
                            </div>
                            <div class="value">
                                <textarea id="text" v-model="text"/>
                            </div>
                            <div class="error">{{error}}</div>
                        </div>
                        <div class="button-field">
                            <input type="submit" value="Write">
                        </div>
                    </form>
                </div>
            </div>
        </template>
    </div>
</template>

<script>
    import PostTemplate from "../PostTemplate";
    import CommentsList from "../CommentsList";

    export default {
        props: ["post", "user"],
        name: "Post",

        components: {
            CommentsList,
            PostTemplate,
        },
        data: function () {
            return {
                text: "",
                error: "",
                flag: true,
                comments: this.post.comments,
            }
        },
        computed: {
            viewComments: function () {
                return this.comments;
            }
        },
        beforeCreate() {
            this.$root.$on("onAddCommentValidationError", (error) => {
                this.error = error;
            });
            this.$root.$on("ClearFieldComment", () => {
                this.text = "";
            });
            this.$root.$on("UpdateComments", post => {
                this.post = post;
                this.comments = post.comments;
                this.flag = true;
            })
        },
        methods: {
            onAdd: function () {
                this.$root.$emit("onAddComment", this.text, this.post);
                this.flag = false;
            }
        }
    }
</script>

<style scoped>
    /* form-comment */
    .form-comment {
        border: 1px solid var(--border-color);
        border-top-right-radius: var(--border-radius);
        border-top-left-radius: var(--border-radius);
        width: 25rem;
    }

    .form-comment .header {
        margin-left: 1rem;
        margin-top: 1rem;
        color: var(--caption-color);
        margin-bottom: 1rem;
    }

    .form-comment .field {
        margin-bottom: 1rem;
    }

    .form-comment .field .name {
        margin-left: 1rem;
    }

    .form-comment .field .value {
        width: 35rem;
    }

    .form-comment .field input, .form-comment .field textarea {
        width: 60%;
        margin-top: 0.5rem;
        margin-left: 1rem;
    }

    .form-comment .button-field {
        margin-left: 1rem;
    }

    .form-comment .button-field input {
        padding: 0.1rem 1.5rem;
    }

    .form-comment .error {
        color: var(--error-color);
        font-size: 0.75rem;
    }
</style>