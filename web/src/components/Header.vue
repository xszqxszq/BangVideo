<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "Header",
  data() {
    return {
      links: [
        '消息',
        '动态',
        '收藏',
        '投稿',
      ]
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
        class="me-4 "
        color="grey-darken-1"
        size="32"
        v-if="false"
      ></v-avatar>
      <v-btn
        v-for="link in links"
        :key="link"
        :text="link"
        variant="text"
        v-if="false"
      ></v-btn>
      <v-btn
        text="登录"
        variant="text"
        to="/login"
      ></v-btn>
      <v-btn
        text="注册"
        variant="text"
        to="/register"
      ></v-btn>
    </v-container>
  </v-app-bar>
</template>

<style scoped lang="sass">

</style>
