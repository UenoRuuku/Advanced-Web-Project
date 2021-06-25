import { Injectable } from "@angular/core";
import { Observable, Subject } from "rxjs";
import { WebsocketService } from "./websocket.service";
import { NzMessageService } from 'ng-zorro-antd/message';
import "rxjs/add/operator/map";

const CHAT_URL_BASE = 'ws://localhost:8085/chat';
const HANOI_URL_BASE = 'ws://localhost:8085/hanoi'

@Injectable()
export class ChatService {
  public messages: Subject<any>;

  constructor(wsService: WebsocketService, private message: NzMessageService) {
    this.messages = <Subject<any>>(
      wsService.connect(CHAT_URL_BASE+`/${localStorage.getItem("token")}`).map((response: MessageEvent): any => {
        let data = JSON.parse(response.data);
        console.log(data)
        if(data.users){
          data.type="userList";
          return data;
        }else if(data.message){
          if(new Set(data.at).has(localStorage.getItem("username")) || new Set(data.at).has("everybody")){
            this.message.info(`${data.author}: ${data.message}`);
          }
          data.type="message";
          return data;
        }
      })
    );
  }

}

@Injectable()
export class HanoiService {
    public messages: Subject<any>;

    constructor(wsService: WebsocketService, private message: NzMessageService) {
        this.messages = <Subject<any>>(
            wsService.connect(HANOI_URL_BASE+`/${localStorage.getItem("token")}`).map((response: MessageEvent): any => {
                let data = JSON.parse(response.data);
                // console.log(data);
                return data;
            })
        );
    }

}