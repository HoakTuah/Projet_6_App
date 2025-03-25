import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Location } from '@angular/common';
import { Router } from '@angular/router';

interface Theme {
  id: number;
  name: string;
}

@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrls: ['./post-create.component.scss']
})
export class PostCreateComponent implements OnInit {
  postForm: FormGroup;
  themes: Theme[] = [
    { id: 1, name: 'Thème 1' },
    { id: 2, name: 'Thème 2' },
    { id: 3, name: 'Thème 3' }
  ];

  constructor(
    private fb: FormBuilder,
    private location: Location,
    private router: Router
  ) {
    this.postForm = this.fb.group({
      theme: ['', Validators.required],
      title: ['', Validators.required],
      content: ['', Validators.required]
    });
  }

  ngOnInit(): void {
  }

  goBack(): void {
    this.location.back();
  }

  onSubmit(): void {
    if (this.postForm.valid) {
      console.log(this.postForm.value);
      this.router.navigate(['/MDD/articles']);
    }
  }
}