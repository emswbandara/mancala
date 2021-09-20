import { Component, OnInit } from '@angular/core';
import { Game } from '../../model/game';
import { GameService } from '../../service/game.service';
import { ActivatedRoute, Router } from "@angular/router";

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrls: ['./game-list.component.css']
})
export class GameListComponent implements OnInit {

  games: Game[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private gameService: GameService) { }

  public navigateToGames(gameId: string) {
    this.router.navigate(['/active-games/' + gameId]);
  }

  ngOnInit(): void {
    this.gameService.findAll().subscribe(data => {
      this.games = data;
    })
  }

}
