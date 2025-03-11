export interface LoginRequest {
    username: string;  // Peut être un email ou un nom d'utilisateur
    password: string;
}

export interface LoginResponse {
    userId: number;
    username: string;
    email: string;
    message: string;
    success: boolean;
}