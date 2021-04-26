import { Component } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { HttpClient } from "@angular/common/http";

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

  constructor(private http: HttpClient) {}

  onSubmit() {
    this.loginLoading = true;
    this.http.post("/login", {
      username: this.username,
      password: this.password,
    })
      .subscribe(
        (val) => {
          console.log("success", val);
        },
        error => {
          console.log("error", error);
        },
        () => {
          console.log("completed");
          this.loginLoading = false;
        }
      );
  }
}
