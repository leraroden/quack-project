import { Component, OnInit } from '@angular/core';
import { NewsService } from './news.service';
import { News } from '../news';

@Component({
  selector: 'wt2-angular',
  templateUrl: './angular.component.html',
  styleUrls: ['./angular.component.sass'],
  providers: [NewsService]
})
export class AngularComponent implements OnInit {

  public latest: News | null = null;
  public news: News[] = [];

  constructor(protected newsService: NewsService) {
  }

  ngOnInit() {
    this.load();
  }

  load(): void {
    this.newsService.getNewest().subscribe({
      next: news => this.latest = news,
      error: console.error
    });
    this.newsService.getAll().subscribe({
      next: news => this.news = news,
      error: console.error
    });
  }
}
