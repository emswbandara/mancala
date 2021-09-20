import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GameService } from '../../service/game.service';
import { GameRequest } from '../../model/game-request';

@Component({
  selector: 'app-game-create-form',
  templateUrl: './game-create-form.component.html',
  styleUrls: ['./game-create-form.component.css']
})
export class GameCreateFormComponent {

  gameRequest: GameRequest;

  constructor(
    private route: ActivatedRoute,
      private router: Router,
        private gameService: GameService) {
    this.gameRequest = new GameRequest();
  }

  onSubmit() {
   this.gameService.create(this.gameRequest).subscribe(result => this.navigateToGame(result.gameId));
  }

  navigateToGame(id: string) {
    this.router.navigate(['/active-games/' + id]);
  }

}
