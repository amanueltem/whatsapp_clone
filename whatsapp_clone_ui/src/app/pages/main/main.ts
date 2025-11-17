import { Component, OnInit, inject } from '@angular/core';
import { ChatList } from "../../components/chat-list/chat-list";
import { ChatResponse } from '../../services/models';
import { getChat } from '../../services/functions';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-main',
  imports: [ChatList],
  templateUrl: './main.html',
  styleUrls: ['./main.scss'], // note the correct property name is styleUrls
})
export class Main implements OnInit {
  chats: ChatResponse[] = [];
  private http = inject(HttpClient); // Angular 14+ inject
  private rootUrl = 'http://localhost:8088'; // your backend URL

  ngOnInit(): void {
    getChat(this.http, this.rootUrl).subscribe({
      next: (response) => {
        this.chats = response.body || [];
      },
      error: (err) => {
        console.error('Failed to fetch chats', err);
      }
    });
  }
}
