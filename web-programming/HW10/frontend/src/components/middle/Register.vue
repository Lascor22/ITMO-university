<template>
    <div class="registration form-box">
        <div class="header">Register</div>
        <div class="body">
            <form @submit.prevent="onRegister">
                <div class="field">
                    <div class="name">
                        <label for="login">Login</label>
                    </div>
                    <div class="value">
                        <input autofocus id="login" name="login" class="login" v-model="login"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="fullName">Name</label>
                    </div>
                    <div class="value">
                        <input id="fullName" name="fullName" class="fullName" v-model="fullName"/>
                    </div>
                </div>
                <div class="field">
                    <div class="name">
                        <label for="password">Password</label>
                    </div>
                    <div class="value">
                        <input class="password" id="password" name="password" type="password" v-model="password"/>
                    </div>
                    <div class="error">{{error}}</div>
                </div>
                <div class="button-field">
                    <input type="submit" value="Register">
                </div>
            </form>
        </div>
    </div>
</template>

<script>
    export default {
        data: function () {
            return {
                login: "",
                fullName: "",
                password: "",
                error: ""
            }
        },
        name: "Register",
        beforeMount() {
            this.login = this.fullName = this.password = this.error = "";
            this.$root.$on("onRegisterUserValidationError", error => this.error = error);
        },
        methods: {
            onRegister: function () {
                this.$root.$emit("onRegisterUser", this.login, this.fullName, this.password);
            }
        }
    }
</script>

<style scoped>

</style>
