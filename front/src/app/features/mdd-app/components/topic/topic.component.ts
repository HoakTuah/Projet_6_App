import { Component, OnInit } from '@angular/core';
import { TopicService } from '../../services/topic.service';
import { Topic } from '../../interfaces/Topic.interface';

@Component({
  selector: 'app-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.scss']
})
export class TopicComponent implements OnInit {
  topics: Topic[] = [];
  error: string = '';
  loading: boolean = false;

  constructor(private topicService: TopicService) {}

  ngOnInit(): void {
    this.topicService.getAllTopics().subscribe({
      next: (response) => {
        console.log('Topics received:', response); // Ajoutez ce log
        this.topics = response;
      },
      error: (error) => console.error('Error:', error)
    });
  }


  loadTopics(): void {
    this.loading = true;
    this.topicService.getAllTopics()
      .subscribe({
        next: (data: Topic[]) => {
          this.topics = data;
          this.loading = false;
        },
        error: (error) => {
          this.error = 'Failed to load topics';
          this.loading = false;
          console.error('Error loading topics:', error);
        }
      });
  }

  toggleSubscription(topic: Topic): void {
    if (topic.isSubscribed) {
      this.topicService.unsubscribeFromTopic(topic.id)
        .subscribe({
          next: () => {
            topic.isSubscribed = false;
          },
          error: (error) => {
            console.error('Error unsubscribing:', error);
            // Revert the UI state if the request fails
            topic.isSubscribed = true;
          }
        });
    } else {
      this.topicService.subscribeToTopic(topic.id)
        .subscribe({
          next: () => {
            topic.isSubscribed = true;
          },
          error: (error) => {
            console.error('Error subscribing:', error);
            // Revert the UI state if the request fails
            topic.isSubscribed = false;
          }
        });
    }
  }
}