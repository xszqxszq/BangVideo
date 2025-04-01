<script lang="ts">
import {defineComponent} from 'vue'
import {useAuthStore} from "@/stores/auth";
import router from "@/router";

export default defineComponent({
  name: "logout",
  setup() {
    let store = useAuthStore()
    store.logout()
    router.push("/login")
  }
})
</script>

<template>

</template>

<style scoped lang="sass">

</style>
