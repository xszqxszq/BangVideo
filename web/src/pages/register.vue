<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "register",
  data() {
    return {
      form: {
        username: '',
        email: '',
        nickname: '',
        password: '',
        confirmPassword: ''
      },
      loading: false
    }
  },
  methods: {
    required(v: string) {
      return !!v || '必填项'
    },
    emailRule(v: string) {
      return /.+@.+\..+/.test(v) || '邮箱格式不正确'
    },
    passwordRule(v: string) {
      return v.length >= 6 || '密码至少6位'
    },
    confirmRule(v: string) {
      return v === this.form.password || '密码不一致'
    },
    async handle() {
      this.loading = true
      try {
        await new Promise(resolve => setTimeout(resolve, 1000))
      } finally {
        this.loading = false
      }
    }
  }
})
</script>

<template>
  <AuthForm>
    <v-form @submit.prevent="handle" class="mx-auto" style="max-width: 400px">
      <div class="text-h5 mb-8 font-weight-bold">注册</div>

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
        v-model="form.email"
        label="电子邮箱"
        type="email"
        variant="outlined"
        :rules="[required, emailRule]"
      >
        <template v-slot:prepend-inner>
          <v-icon :color="form.email ? 'primary' : 'grey'">mdi-email</v-icon>
        </template>
      </v-text-field>

      <v-text-field
        v-model="form.nickname"
        label="昵称"
        variant="outlined"
        :rules="[required]"
      >
        <template v-slot:prepend-inner>
          <v-icon
            :icon="form.nickname ? 'mdi-account-check' : 'mdi-account'"
            :color="form.nickname ? 'primary' : 'grey'"
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

      <v-text-field
        v-model="form.confirmPassword"
        label="确认密码"
        type="password"
        variant="outlined"
        :rules="[required, confirmRule]"
      >
        <template v-slot:prepend-inner>
          <v-icon
            :color="form.confirmPassword && form.confirmPassword === form.password ? 'primary' : 'grey'"
          >
            {{ form.confirmPassword === form.password ? 'mdi-lock-open-check' : 'mdi-lock' }}
          </v-icon>
        </template>
      </v-text-field>

      <v-btn
        type="submit"
        block
        color="primary"
        size="large"
        class="rounded-lg mt-4"
        :loading="loading"
      >
        立即注册
      </v-btn>

      <div class="text-center mt-4">
        <v-btn
          variant="text"
          color="primary"
          size="small"
          class="text-decoration-underline"
          to="/login"
        >
          已有账号？立即登录
        </v-btn>
      </div>
    </v-form>
  </AuthForm>
</template>

<style scoped lang="sass">

</style>
