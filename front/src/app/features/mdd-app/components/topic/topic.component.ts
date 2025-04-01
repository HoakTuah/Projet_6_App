import { Component, OnInit } from '@angular/core';
import { TopicService } from '../../services/topic.service';
import { Topic } from '../../interfaces/Topic.interface';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss']
})
export class TopicComponent implements OnInit {
  topics: Topic[] = [];
  isLoading = false;
  errorMessage = '';
  showOnlySubscribed = false;

  constructor(private topicService: TopicService) {}

  //=============================================================
  //  Lifecycle Hooks
  //=============================================================
  ngOnInit(): void {
    this.loadAllData();
  }

  //=============================================================
  //  Data Loading Methods
  //=============================================================
  loadAllData(): void {
    this.isLoading = true;
    this.errorMessage = '';

    forkJoin({
      allTopics: this.topicService.getAllTopics(),
      subscribedTopics: this.topicService.getSubscribedTopics()
    }).subscribe({
      next: (result) => {
        const subscribedIds = result.subscribedTopics.map(topic => topic.id);
        this.topics = result.allTopics.map(topic => ({
          ...topic,
          isSubscribed: subscribedIds.includes(topic.id)
        }));
        this.isLoading = false;
      },
      error: (error) => {
        this.errorMessage = 'Erreur lors du chargement des thèmes';
        this.isLoading = false;
        console.error('Error loading topics:', error);
      }
    });
  }

  //=============================================================
  //  Topic Filtering Methods
  //=============================================================
  getFilteredTopics(): Topic[] {
    return this.showOnlySubscribed 
      ? this.topics.filter(topic => topic.isSubscribed)
      : this.topics;
  }

  //=============================================================
  //  Subscription Management Methods
  //=============================================================
  subscribe(topic: Topic): void {
    if (!topic.isSubscribed) {
      this.topicService.subscribeToTopic(topic.id).subscribe({
        next: () => {
          topic.isSubscribed = true;
          if (topic.subscriberCount !== undefined) {
            topic.subscriberCount++;
          }
        },
        error: (error) => {
          console.error('Error subscribing:', error);
        }
      });
    }
  }
}