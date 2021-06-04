import { Component } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { NzMessageService } from "ng-zorro-antd/message";
import { Router } from "@angular/router";

class RegisterResponse {
  token: string;
  constructor(token: string) {
    this.token = token;
  }
}

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

  constructor(
    private http: HttpClient,
    private message: NzMessageService,
    private router: Router
  ) {}

  onSubmit() {
    if (this.checkPassword !== this.password) {
      this.message.create("warning", `两次输入密码不相同！`);
      return;
    }
    this.registerLoading = true;

    const formData = new FormData();
    formData.append("username", this.username);
    formData.append("password", this.password);

    this.http.post("/user/register", formData).subscribe(
      <RegisterResponse>(val) => {
        localStorage.setItem("token", val.token);
        localStorage.setItem("username", this.username);
        this.router.navigate(["/game"]);
        this.registerLoading = false;
      },
      (error) => {
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
