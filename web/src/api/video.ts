import type {Video} from "@/types/video";
import {$axios} from "@/api/axios";
import user from "@/api/user";

export default {
  empty: {
    owner: user.empty
  } as Video,
  info: async function(id: number): Promise<Video | null> {
    try {
      let response = await $axios.get('/video/info/' + id)
      let video: Video = response.data
      video.owner.avatar = user.avatar(video.owner.avatar)
      return response.data
    } catch (error) {
      return null
    }
  },
  like: async function(id: number): Promise<boolean> {
    try {
      await $axios.post('/community/like/' + id)
      return true
    } catch (error) {
      return false
    }
  }
}
