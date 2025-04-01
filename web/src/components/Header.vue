<script lang="ts">
import {defineComponent} from 'vue'
import {useAuthStore} from "@/stores/auth"
import type {User} from "@/types/user"
import user from "@/api/user";

export default defineComponent({
  name: "Header",
  setup() {
    return {
      authStore: useAuthStore()
    }
  },
  computed: {
    isLoggedIn(): boolean {
      return this.authStore.isLoggedIn
    },
    user(): User | null {
      return this.authStore.user
    },
    avatar(): string {
      return user.avatar(this.user ?.avatar)
    }
  },
  data() {
    return {
      nav: [{
        name: '登出',
        link: '/logout'
      },{
        name: '消息',
        link: '/message'
      },{
        name: '收藏',
        link: '/favorite'
      },{
        name: '投稿',
        link: '/manage/post'
      }]
    }
  }
})
</script>

<template>
  <v-app-bar flat>
    <v-container class="mx-auto d-flex align-center justify-center">
      <v-btn
        key="BangVideo"
        text="BangVideo"
        variant="text"
        to="/"
        :active="false"
      ></v-btn>

      <v-spacer></v-spacer>

      <v-responsive max-width="480">
        <v-text-field
          density="compact"
          label="搜索"
          rounded="lg"
          variant="solo-filled"
          flat
          hide-details
          single-line
        ></v-text-field>
      </v-responsive>

      <v-spacer></v-spacer>


      <v-avatar
        class="me-4"
        color="grey-darken-1"
        size="32"
        v-if="isLoggedIn"
      >
        <v-img :src="avatar"></v-img>
      </v-avatar>
      <v-btn
        v-for="button in nav"
        :key="button.link"
        :to="button.link"
        :text="button.name"
        variant="text"
        v-if="isLoggedIn"
      ></v-btn>
      <v-btn
        text="登录"
        variant="text"
        to="/login"
        v-if="!isLoggedIn"
      ></v-btn>
      <v-btn
        text="注册"
        variant="text"
        to="/register"
        v-if="!isLoggedIn"
      ></v-btn>
    </v-container>
  </v-app-bar>
</template>

<style scoped lang="sass">

</style>
