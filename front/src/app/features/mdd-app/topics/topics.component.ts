import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})

export class TopicsComponent implements OnInit {
  topics = [
    {
      title: "Titre de l'article",
      date: new Date(),
      author: 'Auteur',
      content: 'Contains lorem ipsum dummy text of the printing and typesetting industry. Lorem ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled...'
    }
  ];

  user: any;

  constructor(private router: Router) {
    const userStr = localStorage.getItem('user');
    if (!userStr) {
      this.router.navigate(['/login']);
      return;
    }
    this.user = JSON.parse(userStr);
  }

  ngOnInit(): void {
    if (!this.user) {
      this.router.navigate(['/login']);
    }
  }

  onCreateTopic() {
    this.router.navigate(['/create-topic']);
  }

  onLogout() {
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }

  sortTopics(criteria: string) {
    // Implémenter la logique de tri
    console.log('Tri par:', criteria);
  }
}