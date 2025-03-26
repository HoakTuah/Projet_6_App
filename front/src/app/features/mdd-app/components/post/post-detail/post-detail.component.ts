import { Component, OnInit } from '@angular/core';
import { PostService } from '../../../services/post.service';
import { CommentService } from '../../../services/comment.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from '../../../interfaces/Post.Interface';
import { Comment } from '../../../interfaces/Comment.Interface';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})
export class PostDetailComponent implements OnInit {
  post: Post | null = null;
  isLoading = true;
  errorMessage: string | null = null;
  comments: Comment[] = [];
  commentForm: FormGroup;
  isSubmittingComment = false;

  constructor(
    private postService: PostService,
    private commentService: CommentService,
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private location: Location
  ) {
    this.commentForm = this.fb.group({
      content: ['', [Validators.required, Validators.maxLength(500)]]
    });
  }

  ngOnInit(): void {
    this.loadPost();
  }

  loadPost(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) {
      this.errorMessage = "ID de l'article invalide";
      this.isLoading = false;
      return;
    }

    this.postService.getPostById(id).subscribe({
      next: (post) => {
        this.post = post;
        this.isLoading = false;
        this.loadComments();
      },
      error: (error) => {
        console.error('Erreur lors du chargement de l\'article', error);
        this.errorMessage = "Impossible de charger l'article";
        this.isLoading = false;
      }
    });
  }

  loadComments(): void {
    if (this.post?.id) {
      this.commentService.getCommentsByPostId(this.post.id).subscribe({
        next: (comments: Comment[]) => {
          this.comments = comments;
        },
        error: (error: any) => {
          console.error('Erreur lors du chargement des commentaires', error);
        }
      });
    }
  }

  submitComment(): void {
    if (this.commentForm.invalid || !this.post?.id) {
      return;
    }
    
    this.isSubmittingComment = true;
    const newComment = {
      postId: this.post.id,
      content: this.commentForm.get('content')?.value
    };
    
    this.commentService.createComment(newComment).subscribe({
      next: (comment: Comment) => {
        this.comments.push(comment);
        this.commentForm.reset();
        this.isSubmittingComment = false;
      },
      error: (error: any) => {
        console.error('Erreur lors de l\'envoi du commentaire', error);
        this.isSubmittingComment = false;
      }
    });
  }

  goBack(): void {
    this.location.back();
  }
}