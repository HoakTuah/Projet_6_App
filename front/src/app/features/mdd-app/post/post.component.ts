import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/services/auth.service';


interface Post {
  title: string;
  date: Date;  // Changé de string à Date
  author: string;
  content: string;
  isSubscribed: boolean;
}
@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})

  export class PostComponent implements OnInit {
  isMenuOpen = false;
  posts: Post[] = [
    {
      title: "Titre de l'article",
      date: new Date("2024-03-08"), 
      author: "Auteur",
      content: "Content: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled...",
      isSubscribed: false
    },
    {
      title: "Titre de l'article",
      date: new Date("2024-03-08"),
      author: "Auteur",
      content: "Content: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled...",
      isSubscribed: false
    },
    {
      title: "Titre de l'article",
      date: new Date("2024-03-08"),
      author: "Auteur",
      content: "Content: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled...",
      isSubscribed: false
    },
    {
      title: "Titre de l'article",
      date: new Date("2024-03-08"),
      author: "Auteur",
      content: "Content: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled...",
      isSubscribed: false
    }
  ];


  user: any;

  constructor(
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {
    // Chargez vos topics ici
  }

  onCreatePost() {
    // Navigation vers la page de création
    this.router.navigate(['/post/create']);
  }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }

    sortPosts(criteria: string) {
    switch (criteria) {
      case 'date':
        this.posts.sort((a, b) => b.date.getTime() - a.date.getTime());
        break;
      case 'title':
        this.posts.sort((a, b) => a.title.localeCompare(b.title));
        break;
      case 'author':
        this.posts.sort((a, b) => a.author.localeCompare(b.author));
        break;
    }
  }
  
  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
    // Empêche le défilement du body quand le menu est ouvert
    if (this.isMenuOpen) {
      document.body.style.overflow = 'hidden';
    } else {
      document.body.style.overflow = 'auto';
    }
  }

  toggleSubscription(post: Post): void {
    post.isSubscribed = !post.isSubscribed;
  }

}