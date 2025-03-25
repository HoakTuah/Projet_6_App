import { Component, OnInit } from '@angular/core';
import { Router,ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../auth/services/auth.service';
import { Post } from '../../../interfaces/PostRequest.Interface';
import { PostService } from '../../../services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})

  export class PostComponent implements OnInit {
  isMenuOpen = false;
  posts: Post[] = [
    {
      id: 1,
      title: "Titre de l'article 1 ",
      date: new Date("2024-03-08"), 
      author: "Auteur",
      content: "Content: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled...",
      isSubscribed: false
    },
    {
      id: 2,
      title: "Titre de l'article 2 ",
      date: new Date("2024-03-08"),
      author: "Auteur",
      content: "Content: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled...",
      isSubscribed: false
    },
    {
      id: 3,
      title: "Titre de l'article 3 ",
      date: new Date("2024-03-08"),
      author: "Auteur",
      content: "Content: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled...",
      isSubscribed: false
    },
    {
      id: 4,
      title: "Titre de l'article 4 ",
      date: new Date("2024-03-08"),
      author: "Auteur",
      content: "Content: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled...",
      isSubscribed: false
    }
  ];


  user: any;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit() {
    // Chargez vos topics ici
  }

  onCreatePost() {
    // Navigation vers la page de création
    this.router.navigate(['create'], { relativeTo: this.route });
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

  onPostClick(postId: number): void {
    this.router.navigate(['../articles', postId], { relativeTo: this.route });
  }


}