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
import { GameComponent } from './components/game/game.component';
import { NzGridModule } from 'ng-zorro-antd/grid';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { ChatComponent } from './components/chat/chat.component';
import { NzConfig, NZ_CONFIG } from 'ng-zorro-antd/core/config';
import { MessageComponent } from './components/message/message.component';
import { NzAvatarModule } from 'ng-zorro-antd/avatar';
import { NzPopoverModule } from 'ng-zorro-antd/popover';
import { NzMentionModule } from 'ng-zorro-antd/mention';
import { ProfileComponent } from './components/profile/profile.component';
import { GameHistoryComponent } from './components/game-history/game-history.component';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzSpinModule } from 'ng-zorro-antd/spin';
import { NzPipesModule } from 'ng-zorro-antd/pipes';
import { NzStatisticModule } from 'ng-zorro-antd/statistic';
import { HanoiHistoryComponent } from './components/hanoi-history/hanoi-history.component';
import { GameSceneComponent } from './components/game-scene/game-scene.component';

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
    MessageComponent,
    ProfileComponent,
    GameHistoryComponent,
    HanoiHistoryComponent,
    GameSceneComponent,
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
    NzAvatarModule,
    NzInputModule,
    NzPopoverModule,
    NzIconModule,
    NzButtonModule,
    NzMessageModule,
    NzTableModule,
    NzSpinModule,
    NzGridModule,
    NzPipesModule,
    NzStatisticModule,
    NzMentionModule,
    NzDividerModule,
    RouterModule.forRoot([
      {
        path: "", component: LayoutComponent,
        children: [{
          path: '', component: GameComponent,
        }, {
          path: 'profile', component: ProfileComponent,
        }],
      },
      { path: "login", component: LoginComponent },
      { path: "register", component: RegisterComponent },
      { path: "game", component: GameSceneComponent }
    ])
  ],
  providers: [{ provide: NZ_I18N, useValue: en_US }, httpInterceptorProviders, { provide: NZ_CONFIG, useValue: ngZorroConfig }],
  bootstrap: [AppComponent]
})
export class AppModule { }
