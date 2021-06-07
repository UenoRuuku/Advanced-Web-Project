import { Component, OnInit } from "@angular/core";
import { WebsocketService } from "../../service/websocket.service";
import { ChatService } from "../../service/chat.service";
import * as moment from "moment";

class ShowingMessage {
  isMyself: boolean;
  author: string;
  at: [string];
  to: [string];
  message: string;
}

class SendingMessage {
  at: [string];
  to: [string];
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
  constructor(private chatService: ChatService) {
    chatService.messages.subscribe((msg) => {
      if (msg.author === localStorage.getItem("username")) {
        msg.isMyself = true;
      }
      msg.time = moment(new Date()).format("hh:mm A");
      this.showingMessages.push(msg);
      this.updateOverflowMessages();
    });
  }

  ngOnInit(): void {}

  sendMessage(): void {
    console.log("new message from client to websocket: ", this.sendingMessage);
    this.chatService.messages.next(this.sendingMessage);
    this.sendingMessage = {
      at: null,
      to: null,
      message: "",
    };
  }

  updateOverflowMessages(): void {
    let messagesWrapper = document.getElementById("messages-wrapper");
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
}
