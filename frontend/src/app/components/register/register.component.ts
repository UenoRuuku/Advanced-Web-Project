import { Component } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { NzMessageService } from "ng-zorro-antd/message";

@Component({
  selector: "app-register",
  templateUrl: "./register.component.html",
  styleUrls: ["./register.component.css"],
})
export class RegisterComponent {
  username: string = "";
  password: string = "";
  checkPassword: string = "";
  registerLoading: boolean = false;
  passwordVisible: boolean = false;
  checkPasswordVisible: boolean = false;

  constructor(private http: HttpClient, private message: NzMessageService) {}

  onSubmit() {
    if (this.checkPassword !== this.password) {
      this.message.create('warning', `两次输入密码不相同！`);
      return;
    }
    this.registerLoading = true;
    this.http.post("/regitser",{
      username: this.username,
      password: this.password,
    })
    .subscribe(
      (val) => {
        console.log("success", val);
        this.registerLoading = false;
      },
      error => {
        console.log("error", error);
        this.registerLoading = false;
      },
      () => {
        console.log("completed");
        this.registerLoading = false;
      }
    );
  }
}
