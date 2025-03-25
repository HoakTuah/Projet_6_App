import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Topic } from '../interfaces/Topic.interface';
import { HttpHeadersService } from 'src/app/core/services/http-headers.service';


@Injectable({
  providedIn: 'root'
})
export class TopicService {
  private apiUrl = `${environment.apiUrl}/api/topics`;

  constructor(
    private http: HttpClient,
    private httpHeadersService: HttpHeadersService
  ) {}

  getAllTopics(): Observable<Topic[]> {
    return this.http.get<Topic[]>(this.apiUrl, { 
      headers: this.httpHeadersService.getAuthHeaders() 
    });
  }

  subscribeToTopic(topicId: number): Observable<any> {
    return this.http.post(
      `${this.apiUrl}/${topicId}/subscribe`, 
      {}, 
      { headers: this.httpHeadersService.getAuthHeaders() }
    );
  }

  unsubscribeFromTopic(topicId: number): Observable<any> {
    return this.http.delete(
      `${this.apiUrl}/${topicId}/unsubscribe`,
      { headers: this.httpHeadersService.getAuthHeaders() }
    );
  }
}