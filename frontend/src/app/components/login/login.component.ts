import { Component } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { NzMessageService } from "ng-zorro-antd/message";
import { Router } from "@angular/router";

class LoginResponse {
  token: string;
  constructor(token: string) {
    this.token = token;
  }
}

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

  constructor(
    private http: HttpClient,
    private message: NzMessageService,
    private router: Router
  ) {}

  onSubmit() {
    if (this.username === "" || this.password === "") {
      this.message.create("warning", `用户名或密码不能为空！`);
      return;
    }
    this.loginLoading = true;

    const formData = new FormData();
    formData.append("username", this.username);
    formData.append("password", this.password);

    this.http.post("/user/login", formData).subscribe(
      <LoginResponse>(val) => {
        localStorage.setItem("token", val.token);
        localStorage.setItem("username", this.username);
        this.router.navigate(["/game"]);
        this.loginLoading = false;
      },
      (error) => {
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
