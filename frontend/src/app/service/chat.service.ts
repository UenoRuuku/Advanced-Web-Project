import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { WebsocketService } from "./websocket.service";
import "rxjs/add/operator/map";

const CHAT_URL_BASE = 'ws://localhost:8085/chat';

@Injectable()
export class ChatService {
  public messages: Subject<any>;

  constructor(wsService: WebsocketService) {
    this.messages = <Subject<any>>(
      wsService.connect(CHAT_URL_BASE+`/${localStorage.getItem("token")}`).map((response: MessageEvent): any => {
        let data = JSON.parse(response.data);
        return {
          author: data.author,
          at: data.at,
          to: data.to,
          message: data.message,
        };
      })
    );
  }

}
