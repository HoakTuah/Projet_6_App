import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  updateProfile(userId: number, updateData: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/api/auth/users/${userId}`, updateData);
  }
}