<script lang="ts">
import {defineComponent} from 'vue'
import {VideoPlayer} from '@videojs-player/vue'
import 'video.js/dist/video-js.css'
import hlsQualitySelector from 'videojs-hls-quality-selector/src/plugin'
import video from "@/api/video"
import comment from "@/api/comment";
import {useRouter} from "vue-router";

export default defineComponent({
  name: "watch",
  computed: {
    hlsQualitySelector() {
      return hlsQualitySelector
    }
  },
  components: {VideoPlayer},
  beforeRouteEnter(to, from, next) {
    let id = to.params.id
    video.info(id).then(video => {
      if (video == null) {
        next("/")
      } else {
        next(vm => {
          vm.video = video
        })
      }
    })
  },
  mounted() {
    comment.get(this.id).then(comments => {
      this.comments = comments
      console.log(comments)
    })
  },
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
    },
    like() {
      console.log(video.like(this.id))
    }
  },
  data() {
    return {
      id: this.$route.params.id,
      video: video.empty,
      comments: [] as Array<Comment>,
      recommends: [{
        id: 1,
        title: "测试视频3",
        description: "简介",
        views: 128,
        likes: 114,
        cover: "/cover/1.jpg",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/4/11 10:03:40"
      },{
        id: 1,
        title: "测试视频8",
        description: "简介",
        views: 10,
        likes: 114,
        cover: "/cover/2.jpg",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/4/12 11:30:07"
      },{
        id: 1,
        title: "测试视频7",
        description: "简介",
        views: 634,
        likes: 114,
        cover: "/cover/3.jpg",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/4/12 10:12:10"
      },{
        id: 1,
        title: "测试视频2",
        description: "简介",
        views: 339,
        likes: 114,
        cover: "/cover/4.png",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/4/11 10:02:33"
      },{
        id: 1,
        title: "测试视频9",
        description: "简介",
        views: 97,
        likes: 114,
        cover: "/cover/5.jpg",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/4/12 11:45:14"
      },{
        id: 1,
        title: "测试视频1",
        description: "简介",
        views: 114,
        likes: 114,
        cover: "/cover/6.jpg",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/4/11 10:01:20"
      },{
        id: 1,
        title: "测试视频4",
        description: "简介",
        views: 306,
        likes: 114,
        cover: "/cover/7.jpg",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/4/11 10:04:05"
      },{
        id: 1,
        title: "测试视频6",
        description: "简介",
        views: 1679,
        likes: 114,
        cover: "/cover/8.jpg",
        owner: {
          id: 1,
          nickname: "UP主",
          avatar: "https://avatars0.githubusercontent.com/u/9064066?v=4&s=460",
          watching: 114
        },
        created: "2025/4/12 11:06:42"
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
        created: "2025/4/11 11:45:14"
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
              :src="video.playlist"
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
                    <v-col v-if="video.owner">
                      <router-link class="link text-black" :to="'/user/'+video.owner.id">
                        <v-card-title class="pa-0 text-body-1">{{ video.owner.nickname }}</v-card-title>
                      </router-link>
<!--                      <v-card-subtitle class="pa-0 text-caption">{{ video.owner.watching }} 粉丝</v-card-subtitle>-->
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
                      <v-btn variant="text" color="red" prepend-icon="mdi-thumb-up" @click="like">
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
