import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { LiveFeedComponent } from '../live-feed/live-feed.component';

export class WebSockets {

  webSocketEndPoint: string = 'http://localhost:8080/40forty/ws';
  topic: string = "/topic/messages";
  stompClient: any;

  constructor(private liveFeed: LiveFeedComponent) { }

  connectWs(): void {

    console.log("initialize websocket connection");

    let ws = new SockJS(this.webSocketEndPoint);
    this.stompClient = Stomp.over(ws);

    const _this = this;
    _this.stompClient.connect({}, function (frame) {
      
      _this.stompClient.subscribe(_this.topic, function (sdkEvent) {

        _this.onMessageRecieved(sdkEvent);
      });
      
    }, this.errorCallBack);
  }

  diconnectWs() : void {

    if (this.stompClient !== null) {

      this.stompClient.disconnect();
    }

    console.log("disconnected");
  }

  errorCallBack(error) : void {

    console.log("errorCallBack -> " + error);

    setTimeout(() => { 
      this.connectWs(); 
    }, 5000);
  }

  sendWs(message: any) : void {

    console.log("calling ws");

    this.stompClient.send("/app/live-feed", {}, JSON.stringify(message));
  }

  onMessageRecieved(message): void {

    console.log("message recived from server :: " + message);

    this.liveFeed.handleMessage(JSON.stringify(message.body));
  }
}
