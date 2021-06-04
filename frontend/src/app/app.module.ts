import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from "@angular/router";
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NZ_I18N } from 'ng-zorro-antd/i18n';
import { en_US } from 'ng-zorro-antd/i18n';
import { registerLocaleData } from '@angular/common';
import en from '@angular/common/locales/en';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LayoutComponent } from './components/layout/layout.component';
import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzMessageModule } from 'ng-zorro-antd/message';
import { httpInterceptorProviders } from './http-interceptors/index';
import { GameComponent } from './game/game.component';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { ChatComponent } from './components/chat/chat.component';
import { NzConfig, NZ_CONFIG } from 'ng-zorro-antd/core/config';

const ngZorroConfig: NzConfig = {
  // 注意组件名称没有 nz 前缀
  message: { nzTop: 120 },
  notification: { nzTop: 240 }
};

registerLocaleData(en);

@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    LoginComponent,
    RegisterComponent,
    GameComponent,
    ChatComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    NzLayoutModule,
    NzMenuModule,
    NzCardModule,
    NzInputModule,
    NzIconModule,
    NzButtonModule,
    NzMessageModule,
    NzGridModule,
    NzDividerModule,
    RouterModule.forRoot([
      { path: "", component: LayoutComponent,
        children: [{
          path: 'game', component: GameComponent,
          //path: '/profile', component: ProfileComponent,
        }],
    },
      { path: "login", component: LoginComponent },
      { path: "register", component: RegisterComponent }
    ])
  ],
  providers: [{ provide: NZ_I18N, useValue: en_US }, httpInterceptorProviders, { provide: NZ_CONFIG, useValue: ngZorroConfig }],
  bootstrap: [AppComponent]
})
export class AppModule { }
