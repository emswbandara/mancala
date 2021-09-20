import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from "@angular/router";
import { PlayRequest } from 'src/app/model/play-request';
import { Game } from '../../model/game';
import { GameService } from '../../service/game.service';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent implements OnInit {

  game!: Game;
  playRequest: PlayRequest | undefined;

  constructor(private route: ActivatedRoute, private router: Router, private gameService: GameService) {
    this.route.params.subscribe(params => this.findGame(params['id']));
  }

  public findGame(id: string) {

    this.gameService.findById(id).subscribe(data => {
      this.game = data;
    });
  }

  public play(id: number): void {
    this.playRequest = new PlayRequest();
    this.playRequest.pitId = id;

    this.gameService.play(this.game.gameId, this.playRequest).subscribe(data => {
      this.game = data;
    });
  }

  public getWinner(id: string): void {

    this.gameService.findWinnerById(id).subscribe(data => {
      this.game = data;
    });
  }

  public restartGame(id: string): void {

    this.gameService.restart(id).subscribe(data => {
      this.game = data;
    });
  }

  public changeState(game: Game): void {

    if (game.status == 'RUNNING') {
      this.gameService.pause(game.gameId).subscribe(data => {
      });
    } else if (game.status == 'PAUSED') {
      this.gameService.resume(game.gameId).subscribe(data => {
      });
    }
    this.reloadGame(game.gameId);
  }

  public withdrawGame(id: string): void {

    this.gameService.withdraw(id).subscribe(data => {
    });
    this.navigateToGames();
  }

  navigateToGames() {
    this.router.navigate(['/active-games']);
  }

  reloadGame(gameId: string) {
    this.findGame(gameId);
  }

  reloadGames() {
    this.gameService.findAll().subscribe(data => {
    });
  }

  ngOnInit(): void {
  }

}
