import { Component, OnInit, OnDestroy } from '@angular/core';
import { DatePipe } from '@angular/common';
import { interval, Subscription } from 'rxjs';

@Component({
  selector: 'app-clock',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './dashboard-clock.component.html'
})
export class ClockComponent implements OnInit, OnDestroy {

  currentTime: Date = new Date();
  private sub!: Subscription;

  ngOnInit() {
    this.sub = interval(1000).subscribe(() => {
      this.currentTime = new Date();
    });
  }

  ngOnDestroy() {
    this.sub.unsubscribe();
  }
}