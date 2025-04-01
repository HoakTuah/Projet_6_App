export interface LoginRequest {
    username: string; 
    password: string;
}

export interface LoginResponse {
  userId: number;
  username: string;
  email: string;
  token: string;
  message: string;
  success: boolean;
  subscribedTopics: string[];
}