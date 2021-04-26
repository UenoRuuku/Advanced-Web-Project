import { Component } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { NzMessageService } from "ng-zorro-antd/message";

@Component({
  selector: "app-login",
  templateUrl: "./login.component.html",
  styleUrls: ["./login.component.css"],
})
export class LoginComponent {
  passwordVisible: boolean = false;
  username: string = "";
  password: string = "";
  loginLoading: boolean = false;

  constructor(private http: HttpClient, private message: NzMessageService) {}

  onSubmit() {
    if (this.username === "" || this.password === ""){
      this.message.create('warning', `用户名或密码不能为空！`);
      return;
    }
    this.loginLoading = true;
    this.http.post("/login", {
      username: this.username,
      password: this.password,
    })
      .subscribe(
        (val) => {
          console.log("success", val);
          this.loginLoading = false;
        },
        error => {
          console.log("error", error);
          this.loginLoading = false;
        },
        () => {
          console.log("completed");
          this.loginLoading = false;
        }
      );
  }
}
