import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { HttpHeadersService } from 'src/app/core/services/http-headers.service';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private httpHeadersService: HttpHeadersService
  ) { }

  updateProfile(userId: number, updateData: any): Observable<any> {
    return this.http.put(
      `${this.apiUrl}/api/auth/users/${userId}`, 
      updateData,
      { headers: this.httpHeadersService.getAuthHeaders() }
    );
  }
  
  getUserProfile(): Observable<any> {
    return this.http.get(
      `${this.apiUrl}/api/auth/users/profile`,
      { headers: this.httpHeadersService.getAuthHeaders() }
    );
  }
}
