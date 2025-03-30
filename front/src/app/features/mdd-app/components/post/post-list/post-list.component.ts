import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../../../auth/services/auth.service';
import { Post } from '../../../interfaces/Post.Interface';
import { PostService } from '../../../services/post.service';

@Component({
  selector: 'app-post',
  templateUrl: './post-list.component.html',
  styleUrls: ['./post-list.component.scss']
})
export class PostComponent implements OnInit {
  isMenuOpen = false;
  posts: Post[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private postService: PostService
  ) {}

  //=============================================================
  //  Lifecycle Hooks
  //=============================================================
  ngOnInit() {
    this.loadPosts();
  }

  //=============================================================
  //  Post Loading Methods
  //=============================================================
  loadPosts() {
    this.isLoading = true;
    this.errorMessage = '';

    this.postService.getAllPosts().subscribe({
      next: (posts) => {
        this.posts = posts;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du chargement des articles';
        this.isLoading = false;
        console.error('Error loading posts:', error);
        
        if (error.status === 401) {
          this.router.navigate(['/auth/login']);
        }
      }
    });
  }

  //=============================================================
  //  Post Sorting Methods
  //=============================================================
  sortPosts(criteria: string) {
    switch (criteria) {
      case 'date-desc':
        this.posts.sort((a, b) => {
     
          const dateA = new Date(a.publishedAt.split('/').reverse().join('-'));
          const dateB = new Date(b.publishedAt.split('/').reverse().join('-'));
          return dateB.getTime() - dateA.getTime();
        });
        break;
      case 'date-asc':
        this.posts.sort((a, b) => {

          const dateA = new Date(a.publishedAt.split('/').reverse().join('-'));
          const dateB = new Date(b.publishedAt.split('/').reverse().join('-'));
          return dateA.getTime() - dateB.getTime();
        });
        break;
      case 'title':
        this.posts.sort((a, b) => a.title.localeCompare(b.title));
        break;
    }
  }

  //=============================================================
  //  Navigation Methods & Logout
  //=============================================================
  onCreatePost() {
    this.router.navigate(['create'], { relativeTo: this.route });
  }
  onPostClick(postId: number): void {
    this.router.navigate(['../articles', postId], { relativeTo: this.route });
  }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/auth/login']);
  }
}