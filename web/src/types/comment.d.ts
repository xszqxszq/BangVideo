import type {User} from "@/types/user";

export interface Comment {
  id: string,
  video: number,
  user: User,
  content: string,
  parent: string | null
  created: string,
  updated: string,
  likes: number,
  replies: Array<Comment>
}
