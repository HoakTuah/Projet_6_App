import { Component, OnInit } from '@angular/core';

interface Topic {
  id: number;
  title: string;
  description: string;
  isSubscribed: boolean;
}

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss']
})
export class TopicComponent implements OnInit {
  topic: Topic[] = [
    {
      id: 1,
      title: 'Titre du thème',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard...',
      isSubscribed: false
    },
    {
      id: 2,
      title: 'Titre du thème',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard...',
      isSubscribed: false
    },
    {
      id: 3,
      title: 'Titre du thème',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard...',
      isSubscribed: false
    },
    {
      id: 4,
      title: 'Titre du thème',
      description: 'Description: lorem ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard...',
      isSubscribed: false
    },

    
  ];

  constructor() {}

  ngOnInit(): void {}

  toggleSubscription(topic: Topic): void {
    topic.isSubscribed = !topic.isSubscribed;
  }
  
}