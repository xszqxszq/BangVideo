import type {Comment} from "@/types/comment";
import {$axios} from "@/api/axios";
import user from "@/api/user";

export default {
  empty: {} as Comment,
  get: async function(id: number): Promise<Array<Comment>> {
    try {
      let response = await $axios.get('/community/comment/' + id)
      let comments: Array<Comment> = response.data
      for (let i = 0; i < comments.length; i++) {
        comments[i].user.avatar = user.avatar(comments[i].user.avatar)
      }
      return comments
    } catch (error) {
      return []
    }
  }
}
