import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map} from 'rxjs';
import { environment } from '../../../../environments/environment';
import { HttpHeadersService } from 'src/app/core/services/http-headers.service';
import { Post } from '../interfaces/Post.Interface';
@Injectable({
  providedIn: 'root'
})
export class PostService {
  private apiUrl = `${environment.apiUrl}/api/posts`;

  constructor(
    private http: HttpClient,
    private httpHeadersService: HttpHeadersService,
  ) {}

  getAllPosts(): Observable<Post[]> {
    return this.http.get<Post[]>(this.apiUrl, {
      headers: this.httpHeadersService.getAuthHeaders()
    }).pipe(
      map(posts => posts.map(post => ({
        ...post,
        publishedAt: new Date(post.publishedAt).toLocaleDateString('fr-FR')
      })))
    );
  }

  createPost(postData: { title: string; content: string; topicId: number }): Observable<Post> {
    return this.http.post<Post>(
      this.apiUrl, 
      postData,
      { headers: this.httpHeadersService.getAuthHeaders() }
    );
  }

  getPostById(id: number): Observable<Post> {
    return this.http.get<Post>(`${this.apiUrl}/${id}`, {
      headers: this.httpHeadersService.getAuthHeaders()
    });
  }
}