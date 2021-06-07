import { Component, OnInit } from "@angular/core";
import { WebsocketService } from "../../service/websocket.service";
import { ChatService } from "../../service/chat.service";
import * as moment from "moment";

class ShowingMessage {
  isMyself: boolean;
  author: string;
  at: string[];
  to: string[];
  message: string;
}

class SendingMessage {
  at: string[];
  to: string[];
  message: string;
}

@Component({
  selector: "app-chat",
  templateUrl: "./chat.component.html",
  styleUrls: ["./chat.component.css"],
  providers: [WebsocketService, ChatService],
})
export class ChatComponent implements OnInit {
  sendingMessage: SendingMessage = {
    at: null,
    to: null,
    message: "",
  };
  showingMessages: ShowingMessage[] = [];

  userList: string[] = [localStorage.getItem("username")];
  showUserList: string[] = [localStorage.getItem("username")];

  atList: Set<string> = new Set();

  constructor(private chatService: ChatService) {
    chatService.messages.subscribe((msg) => {
      switch(msg.type){
        case 'message': {
          if (msg.author === localStorage.getItem("username")) {
            msg.isMyself = true;
          }
          msg.time = moment(new Date()).format("hh:mm A");
          this.showingMessages.push(msg);
          this.updateOverflowMessages();
          break;
        }
        case 'userList': {
          this.userList = msg.users
          if(this.userList.length>8){
            this.showUserList = this.userList.slice(0,8)
            let leftUserNum = this.userList.length-8
            this.showUserList[8] = `+${leftUserNum}`
          }else{
            this.showUserList = this.userList
          }
          break;
        }
      }
    });
  }

  ngOnInit(): void {}

  sendMessage(): void {
    console.log(this.atList)
    console.log("new message from client to websocket: ", this.sendingMessage);
    this.chatService.messages.next(this.sendingMessage);
    this.sendingMessage = {
      at: null,
      to: null,
      message: "",
    };
  }

  updateOverflowMessages(): void {
    let chatBox = document.getElementById("chat-box");
    let lastScrollTop = chatBox.scrollTop;
    let interval = setInterval(function(){
      chatBox.scrollTop++;
      if(chatBox.scrollTop===lastScrollTop){
        clearInterval(interval); // 停止，防止无法向上滚动查看历史消息
      }else{
        lastScrollTop = chatBox.scrollTop
      }
    },1);
  }

  onSelect(suggestion: string): void {
    console.log("onSelect "+suggestion)
    this.atList.add(suggestion)
    this.sendingMessage.at = Array.from(this.atList)
  }
}
