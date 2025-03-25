export interface Post {
    id: number;
    userId: number;
    username: string;
    topicId: number;
    title: string;
    content: string;
    publishedAt: string;
  }
export interface Comment {
    username: string;
    text: string;
    date: Date;
}