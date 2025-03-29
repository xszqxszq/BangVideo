<script lang="ts">
import {defineComponent} from 'vue'
import {VideoPlayer} from '@videojs-player/vue'
import 'video.js/dist/video-js.css'
import hlsQualitySelector from 'videojs-hls-quality-selector/src/plugin'

export default defineComponent({
  name: "watch",
  computed: {
    hlsQualitySelector() {
      return hlsQualitySelector
    }
  },
  components: {VideoPlayer},
  methods: {
    formatTime(date: string) {
      let time = new Date(date)
      let now = new Date()
      let diff = now.getTime() - time.getTime()

      let seconds = Math.floor(diff / 1000)
      let minutes = Math.floor(seconds / 60)
      let hours = Math.floor(minutes / 60)
      let days = Math.floor(hours / 24)

      if (seconds < 60)
        return `${seconds} 秒前`
      else if (minutes < 60)
        return `${minutes} 分钟前`
      else if (hours < 24)
        return `${hours} 小时前`
      else if (days < 7)
        return `${days} 天前`
      else if (now.getFullYear() === time.getFullYear())
        return time.toLocaleTimeString('zh-CN', {
          month: '2-digit',
          day: '2-digit'
        })
      return date
    }
  },
  data() {
    return {
      video: {
        id: 1,
        title: "视频标题",
        description: "简介",
        views: 514,
        likes: 114,
        cover: "/cover/6f101fcbba6868645554949ef07d07685b1ff830.png",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/1/1 11:45:14"
      },
      comments: [{
        user: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        content: "测试评论\n这是第二行\n这是第三行",
        created: "2025/3/29 10:34:14",
        likes: 20
      },{
        user: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        content: "测试评论\n这是第二行\n这是第三行",
        created: "2024/1/20 11:45:14",
        likes: 20
      }],
      recommends: [{
        id: 1,
        title: "视频标题",
        description: "简介",
        views: 514,
        likes: 114,
        cover: "/cover/6f101fcbba6868645554949ef07d07685b1ff830.png",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/1/1 11:45:14"
      },{
        id: 1,
        title: "视频标题",
        description: "简介",
        views: 514,
        likes: 114,
        cover: "/cover/6f101fcbba6868645554949ef07d07685b1ff830.png",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/1/1 11:45:14"
      }],
      options: {
        playbackRates: [0.5, 1.0, 1.5, 2.0],
        language: "zh-CN",
        aspectRatio: "16:9",
        displayCurrentQuality: true
      }
    }
  }
})
</script>

<template>
  <v-main class="bg-grey-lighten-3">
    <v-container>
      <v-row>
        <v-col>
          <v-sheet
            rounded="lg"
            class="overflow-hidden d-flex"
          >
            <video-player
              src="http://localhost:7002/video/6/playlist.m3u8"
              :poster="video.cover"
              :options="options"
              :plugins="{ hlsQualitySelector }"
              controls
            />
          </v-sheet>
          <v-sheet
            class="mt-3 pt-2"
            rounded="lg"
          >
            <v-card-title>{{ video.title }}</v-card-title>
            <v-card-text>
              <v-row align="center">
                <v-col cols="8" md="auto">
                  <v-row align="center" no-gutters>
                    <v-col cols="auto">
                      <router-link :to="'/user/'+video.owner.id">
                        <v-avatar size="48" class="mr-3">
                          <v-img :src="video.owner.avatar" />
                        </v-avatar>
                      </router-link>
                    </v-col>
                    <v-col>
                      <router-link class="link text-black" :to="'/user/'+video.owner.id">
                        <v-card-title class="pa-0 text-body-1">{{ video.owner.nickname }}</v-card-title>
                      </router-link>
                      <v-card-subtitle class="pa-0 text-caption">{{ video.owner.watching }} 粉丝</v-card-subtitle>
                    </v-col>
                    <v-col cols="auto" class="ml-3">
                      <v-btn color="red" variant="flat" size="small">关注</v-btn>
                    </v-col>
                  </v-row>
                </v-col>

                <v-spacer />

                <v-col cols="5" md="auto">
                  <v-row dense>
                    <v-col cols="auto">
                      <v-btn variant="text" color="red" prepend-icon="mdi-thumb-up">
                        <template v-slot:default>
                          <span class="mt-1">{{ video.likes }}</span>
                        </template>
                      </v-btn>
                    </v-col>
                    <v-col cols="auto">
                      <v-btn variant="text" prepend-icon="mdi-star-outline">
                        收藏
                      </v-btn>
                    </v-col>
                    <v-col cols="auto">
                      <v-btn variant="text" prepend-icon="mdi-share">
                        分享
                      </v-btn>
                    </v-col>
                    <v-col cols="auto">
                      <v-btn variant="text" prepend-icon="mdi-alert-octagon">
                        举报
                      </v-btn>
                    </v-col>
                  </v-row>
                </v-col>
              </v-row>
            </v-card-text>
            <v-card-text class="text-body-2">{{ video.description }}</v-card-text>
          </v-sheet>

          <v-sheet
            class="mt-3 pt-2"
            rounded="lg"
          >  <v-card-title>评论（{{ comments.length }}）</v-card-title>
            <v-list>
              <v-list-item
                v-for="(comment, i) in comments"
                :key="i"
                class="align-items-start"
              >
                <template v-slot:prepend>
                  <v-avatar size="40" class="mt-2 justify-start">
                    <v-img :src="comment.user.avatar" />
                  </v-avatar>
                </template>

                <div class="w-100">
                  <div class="d-flex align-center">
                    <router-link class="link text-black" :to="'/user/'+comment.user.id">
                      <span class="text-body-2 font-weight-medium">{{ comment.user.nickname }}</span>
                    </router-link>
                    <span class="text-caption text-medium-emphasis ml-2">{{ formatTime(comment.created) }}</span>
                  </div>

                  <div class="text-body-2 my-1">{{ comment.content }}</div>

                  <div class="d-flex">
                    <v-btn
                      variant="text"
                      size="x-small"
                      class="text-medium-emphasis"
                      prepend-icon="mdi-thumb-up-outline"
                    >
                      <template v-slot:default>
                        <span style="line-height: 1">{{ comment.likes }}</span>
                      </template>
                    </v-btn>
                    <v-btn
                      variant="text"
                      size="x-small"
                      class="text-medium-emphasis"
                    >
                      <template v-slot:default>
                        <span style="line-height: 1">回复</span>
                      </template>

                    </v-btn>
                  </div>
                </div>

                <template v-slot:append>
                  <v-menu>
                    <template v-slot:activator="{ props }">
                      <v-btn
                        variant="text"
                        icon="mdi-dots-vertical"
                        size="x-small"
                        v-bind="props"
                      />
                    </template>
                    <v-list density="compact">
                      <v-list-item value="report">
                        <template v-slot:prepend>
                          <v-icon icon="mdi-flag-outline"></v-icon>
                        </template>
                        <v-list-item-title>举报</v-list-item-title>
                      </v-list-item>
                    </v-list>
                  </v-menu>
                </template>
              </v-list-item>
            </v-list>
          </v-sheet>
        </v-col>

        <v-col cols="3">
          <v-card
            v-for="(recommend, i) in recommends"
            :key="i"
            variant="flat"
            class="mb-4"
          >
            <v-row>
              <v-col cols="5">
                <router-link :to="'/watch/'+recommend.id">
                  <v-img
                    :src="recommend.cover"
                    aspect-ratio="16/9"
                  />
                </router-link>
              </v-col>
              <v-col cols="7" class="ps-0">
                <router-link class="link text-black" :to="'/watch/'+recommend.id">
                  <v-card-title class="text-body-1 ps-0">
                    {{ recommend.title }}
                  </v-card-title>
                </router-link>
                <router-link class="link text-black" :to="'/user/'+recommend.owner.id">
                  <v-card-subtitle class="text-caption ps-0">
                    {{ recommend.owner.nickname }}
                  </v-card-subtitle>
                </router-link>
                <v-card-subtitle class="text-caption ps-0">
                  {{ recommend.views }} 次播放 • {{ formatTime(recommend.created) }}
                </v-card-subtitle>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<style scoped lang="sass">

</style>
