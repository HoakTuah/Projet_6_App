export interface Topic {
  id: number;
  title: string;      
  content: string;     
  createdAt: string;
  subscriberCount: number;
  message: string;
  success: boolean;
  isSubscribed?: boolean;
}