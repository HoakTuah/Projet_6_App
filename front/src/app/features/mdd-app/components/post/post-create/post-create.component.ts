import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import { PostService } from '../../../services/post.service';
import { TopicService } from '../../../services/topic.service';
import { Topic } from '../../../interfaces/Topic.interface';

@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.scss']
})
export class PostCreateComponent implements OnInit {
  postForm: FormGroup;
  themes: Topic[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private router: Router,
    private postService: PostService,
    private topicService: TopicService
  ) {
    this.postForm = this.fb.group({
      topicId: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadTopics();
  }

  loadTopics(): void {
    this.isLoading = true;
    this.topicService.getSubscribedTopics().subscribe({
      next: (topics) => {
        this.themes = topics;
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du chargement des thèmes';
        this.isLoading = false;
        console.error('Error loading topics:', error);
      }
    });
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit(): void {
    if (this.postForm.valid) {
      this.isLoading = true;
      this.errorMessage = '';

      const postData = {
        title: this.postForm.value.title,
        content: this.postForm.value.content,
        topicId: Number(this.postForm.value.topicId)
      };

      this.postService.createPost(postData).subscribe({
        next: () => {
          this.isLoading = false;
          this.router.navigate(['/MDD/articles']);
        },
        error: (error) => {
          this.isLoading = false;
          this.errorMessage = 'Erreur lors de la création de l\'article';
          console.error('Error creating post:', error);
        }
      });
    }
  }
}