import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { HttpHeadersService } from 'src/app/core/services/http-headers.service';
import { Comment } from '../interfaces/Comment.Interface';

@Injectable({
  providedIn: 'root'
})
export class CommentService {
  private apiUrl = `${environment.apiUrl}/api/comments`;

  constructor(
    private http: HttpClient,
    private httpHeadersService: HttpHeadersService,
  ) {}

  getCommentsByPostId(postId: number): Observable<Comment[]> {
    return this.http.get<Comment[]>(`${this.apiUrl}/post/${postId}`, {
      headers: this.httpHeadersService.getAuthHeaders()
    });
  }

  createComment(commentData: { postId: number; content: string }): Observable<Comment> {
    return this.http.post<Comment>(
      this.apiUrl, 
      commentData,
      { headers: this.httpHeadersService.getAuthHeaders() }
    );
  }

}