import type {User} from "@/types/user";

export interface Video {
  id: number
  cid: number
  title: string
  description: string
  cover: string
  owner: User
  duration: number
  category: number
  tags: Array<string>
  views: number
  likes: number
  favorites: number
  created: string
  updated: string
  playlist: string
  auditPassed: boolean | null
  auditMessage: string | null
}
