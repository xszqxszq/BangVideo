<script lang="ts">
import {defineComponent} from 'vue'
import user from "@/api/user";
import router from "@/router";
import {useAuthStore} from "@/stores/auth";

export default defineComponent({
  name: "login",
  setup() {
    return {
      authStore: useAuthStore()
    }
  },
  data() {
    return {
      form: {
        username: '',
        password: ''
      },
      loading: false
    }
  },
  methods: {
    required(v: string) {
      return !!v || '必填项'
    },
    passwordRule(v: string) {
      return v.length >= 6 || '密码至少6位'
    },
    async login() {
      this.loading = true
      try {
        if (await user.login(this.form.username, this.form.password) !== null) {
          await this.authStore.checkAuth()
          await router.push('/')
        }
      } finally {
        this.loading = false
      }
    }
  }
})
</script>

<template>

  <AuthForm>
    <v-form @submit.prevent="login" class="mx-auto" style="max-width: 400px">
      <div class="text-h5 mb-8 font-weight-bold">登录</div>

      <v-text-field
        v-model="form.username"
        label="用户名"
        variant="outlined"
        :rules="[required]"
      >
        <template v-slot:prepend-inner>
          <v-icon
            :icon="form.username ? 'mdi-account-check' : 'mdi-account'"
            :color="form.username ? 'primary' : 'grey'"
          />
        </template>
      </v-text-field>

      <v-text-field
        v-model="form.password"
        label="密码"
        type="password"
        variant="outlined"
        :rules="[required, passwordRule]"
      >
        <template v-slot:prepend-inner>
          <v-icon
            :icon="form.password ? 'mdi-lock-open' : 'mdi-lock'"
            :color="form.password ? 'primary' : 'grey'"
          />
        </template>
      </v-text-field>

      <v-row>
        <v-col cols="6">
          <v-btn
            type="submit"
            block
            color="primary"
            size="large"
            class="rounded-lg mt-4"
            :loading="loading"
          >
            登录
          </v-btn>
        </v-col>
        <v-col cols="6">
          <v-btn
            type="submit"
            block
            color="primary"
            size="large"
            class="rounded-lg mt-4"
            to="/register"
          >
            注册
          </v-btn>
        </v-col>
      </v-row>
    </v-form>
  </AuthForm>
</template>

<style scoped lang="sass">

</style>
