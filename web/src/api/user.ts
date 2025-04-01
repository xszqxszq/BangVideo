import {$axios} from "@/api/axios"
import type {User} from "@/types/user"

export default {
  empty: {
    id: 0
  } as User,
  login: async function (
    username: string,
    password: string
  ): Promise<User | null> {
    try {
      let response = await $axios.post('/user/login', {
        username: username,
        password: password,
      })
      return response.data
    } catch (error) {
      return null
    }
  },
  logout: async function (): Promise<boolean> {
    let response = await $axios.post('/user/logout')
    return response.status === 200
  },
  profile: async function (): Promise<User | null> {
    try {
      let response = await $axios.get('/user/profile/')
      return response.data
    } catch (error) {
      return null
    }
  },
  avatar: function(avatar: string | null | undefined): string {
    if (avatar == null || avatar === "")
      return "/avatar/default.jpg"
    return avatar
  }
}
