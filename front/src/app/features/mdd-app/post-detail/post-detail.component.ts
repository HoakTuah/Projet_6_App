import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { Post, Comment } from '../interfaces/PostRequest.Interface';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrls: ['./post-detail.component.scss']
})

export class PostDetailComponent implements OnInit {
  post: Post = {
    id: 1,
    title: "Titre de l'article",
    date: new Date("2024-03-08"),
    author: "Auteur",
    content: "Content: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled...",
    isSubscribed: false
  };

  currentComment: Comment | null = null;
  newComment: string = '';
  username: string = 'User123'; // This should come from your auth service

  constructor(
    private route: ActivatedRoute,
    private location: Location
  ) {}

  ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
  }

  goBack(): void {
    this.location.back();
  }

  submitComment(): void {
    if (this.newComment && !this.currentComment) {
      this.currentComment = {
        username: this.username,
        text: this.newComment,
        date: new Date()
      };
      this.newComment = '';
    }
  }
}