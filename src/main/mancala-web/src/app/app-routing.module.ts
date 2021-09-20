import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameListComponent } from './component/game-list/game-list.component';
import { GameComponent } from './component/game/game.component';
import { GameCreateFormComponent } from './component/game-create-form/game-create-form.component';


const routes: Routes = [
  { path: 'active-games', component: GameListComponent },
  { path: 'new-game', component: GameCreateFormComponent },
  { path: 'active-games/:id', component: GameComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
