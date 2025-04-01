<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "AuthForm"
})
</script>

<template>
  <v-main class="bg-grey-lighten-3">
    <v-container class="align-center">
      <v-sheet
        min-height="70vh"
        rounded="lg"
      >
        <v-container>
          <v-row>
            <v-col cols="12" md="8" class="pa-0 rounded-lg hidden-sm-and-down">
              <v-img
                src="/background.jpg"
                height="70vh"
                cover
                class="rounded-l-lg"
              ></v-img>
            </v-col>

            <v-col cols="12" md="4" class="pa-8 align-content-center">
              <slot></slot>
            </v-col>
          </v-row>
        </v-container>
      </v-sheet>
    </v-container>
  </v-main>
</template>

<style scoped lang="sass">

</style>
