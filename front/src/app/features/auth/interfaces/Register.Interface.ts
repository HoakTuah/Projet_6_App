export interface User {
    id?: number;
    username: string;
    email: string;
    password?: string;
  }
  
  export interface RegisterRequest {
    username: string;
    email: string;
    password: string;
  }
  
  export interface RegisterResponse {
    userId: number;
    username: string;
    email: string;
    message: string;
    success: boolean;
  }