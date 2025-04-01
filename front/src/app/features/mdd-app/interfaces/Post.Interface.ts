import { Topic } from "./Topic.interface";

export interface Post {
    id: number;
    userId: number;
    username: string;
    topicTitle: string;
    title: string;
    content: string;
    publishedAt: string;
  }
export interface Comment {
    username: string;
    text: string;
    date: Date;
}