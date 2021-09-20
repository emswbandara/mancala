import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { GameListComponent } from './component/game-list/game-list.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { GameService } from './service/game.service';
import { GameComponent } from './component/game/game.component';
import { GameCreateFormComponent } from './component/game-create-form/game-create-form.component';
import { HttpErrorInterceptor } from './model/http-error';

@NgModule({
  declarations: [
    AppComponent,
    GameListComponent,
    GameComponent,
    GameCreateFormComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [GameService, {
    provide: HTTP_INTERCEPTORS,
    useClass: HttpErrorInterceptor,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule { }
