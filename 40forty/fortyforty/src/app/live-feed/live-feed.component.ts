import { Component, OnInit } from '@angular/core';
import { WsFeedService } from '../shared/ws-feed.service';
import { WebSockets } from '../live-feed/web-sockets';
import { Post } from '../models/Post';
import { User } from '../models/User';
import { PostService } from '../shared/post.service';
import { LoginService } from '../shared/login.service';
import { UploadService } from '../shared/upload.service';

@Component({
  selector: 'app-live-feed',
  templateUrl: './live-feed.component.html',
  styleUrls: ['./live-feed.component.css']
})
export class LiveFeedComponent implements OnInit {

  //webSockerService : WsFeedService;
  webSockerService : WebSockets;
  author: User;
  text: string;
  dateCreated: any;
  media_url: any;

  private newPostBody = '';
  private selectedFile;
  
  constructor(private postService?: PostService, 
              private loginserv?: LoginService, 
              private uploadServ?: UploadService) { }

  ngOnInit() {

    //this.webSockerService = new WsFeedService(new LiveFeedComponent());
    this.webSockerService = new WebSockets(new LiveFeedComponent());
    this.connect();

    this.author = this.loginserv.getCurrentUser();
    console.log(this.author);
  }

  connect() : void {

    console.log("connecting...");
    this.webSockerService.connectWs();
  }

  disconnect(): void {

    this.webSockerService.diconnectWs();
  }

  /**
   * Create a new live post. Similar to a regular post 
   * in the regular feed component. 
   */
  sendMessage(): void {

    const payload = JSON.parse(`{
      "text": "${this.newPostBody}",
      "mediaURL": "${this.media_url}"
    }`);

    this.webSockerService.sendWs(payload);
  }

  handleMessage(message: any) : void {

    this.text = message;
  }

  onFileSelected(event) {
    this.selectedFile = event.target.files[0] as File;
    console.log(this.selectedFile);
  }

  initPost(p: Post) {

    p.timestamp = new Date(p.dateCreated).toLocaleDateString;
    p.userReaction = 0;
    p.userIsReacting = false;
    p.score = 0;

    return p;
  }
}
