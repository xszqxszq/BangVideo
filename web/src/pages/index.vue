<template>
  <v-main class="bg-grey-lighten-3">
    <v-container>
      <v-row>
        <v-col>
          <v-sheet
            min-height="70vh"
            rounded="lg"
          >
            <v-container>
              <v-row>
                <template v-for="n in 4" :key="n">
                  <v-col
                    class="mt-2"
                    cols="12"
                  >
                    <strong>类别{{ n }}</strong>
                  </v-col>

                  <v-slide-group show-arrows>
                    <v-slide-group-item
                      v-for="n in 8"
                      :key="n"
                    >
                      <v-sheet
                        class="ma-3"
                        color="grey-lighten-1"
                        height="144"
                        width="256"
                        style="background-image: url('/cover/6f101fcbba6868645554949ef07d07685b1ff830.png')"
                        rounded
                      >
                        <router-link to="/watch/1" class="link">
                          <v-container
                            class="align-content-end pb-2"
                            height="144"
                            width="256"
                            style="background-image: url('/cover/overlay.png')"
                          >
                            <span class="text-white title">视频标题</span>
                          </v-container>
                        </router-link>
                      </v-sheet>
                    </v-slide-group-item>
                  </v-slide-group>
                </template>
              </v-row>
            </v-container>
          </v-sheet>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<script lang="ts" setup>
  //
</script>

<style scoped>
</style>
