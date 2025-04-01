import {defineStore} from 'pinia'
import type {User} from '@/types/user.d'
import user from "@/api/user";

export const useAuthStore = defineStore('auth', {
  state: () => ({
    user: null as User | null,
    isChecked: false
  }),

  actions: {
    async checkAuth() {
      try {
        this.user = await user.profile()
      } catch (error) {
        this.user = null
      } finally {
        this.isChecked = true
      }
    },

    async logout() {
      this.user = null
      await user.logout()
    }
  },

  getters: {
    isLoggedIn: (state) => state.user !== null,
    avatarUrl: (state) => state.user?.avatar || '/default-avatar.png'
  }
})
