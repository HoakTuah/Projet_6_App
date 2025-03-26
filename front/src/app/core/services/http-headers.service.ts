import { Injectable } from '@angular/core';
import { HttpHeaders } from '@angular/common/http';


//=============================================================
//  Service responsible for creating and managing HTTP headers
//=============================================================

@Injectable({
  providedIn: 'root'
})

export class HttpHeadersService {

  getAuthHeaders(): HttpHeaders {

    // Retrieve token from local storage
    const token = localStorage.getItem('token');

    // Create and return headers with token in Bearer format
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }
}