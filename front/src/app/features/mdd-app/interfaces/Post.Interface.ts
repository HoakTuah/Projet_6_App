export interface Post {
    id: number;
    title: string;
    date: Date;
    author: string;
    content: string;
    isSubscribed: boolean;
}

export interface Comment {
    username: string;
    text: string;
    date: Date;
}