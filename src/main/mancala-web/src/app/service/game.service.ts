import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Game } from '../model/game';
import { GameRequest } from '../model/game-request';
import { PlayRequest } from '../model/play-request';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  private gamesUrl: string;

  constructor(private http: HttpClient) {
    this.gamesUrl = 'http://localhost:8080/games';
  }

  public findAll(): Observable<Game[]> {
    return this.http.get<Game[]>(this.gamesUrl);
  }

  public findById(gameId: string) {
    return this.http.get<Game>(this.gamesUrl + "/" + gameId);
  }

  public play(gameId: string, playRequest:PlayRequest) {
    return this.http.post<Game>(this.gamesUrl + "/" + gameId + "/play", playRequest);
  }

  public create(gameRequest: GameRequest) {
    return this.http.post<Game>(this.gamesUrl, gameRequest);
  }

  public findWinnerById(gameId: string) {
    return this.http.get<Game>(this.gamesUrl + "/" + gameId + "/winner");
  }

  public pause(gameId: string) {
    return this.http.post<null>(this.gamesUrl + "/" + gameId + "/pause", null);
  }

  public resume(gameId: string) {
    return this.http.post<null>(this.gamesUrl + "/" + gameId + "/resume", null);
  }

  public restart(gameId: string) {
    return this.http.post<Game>(this.gamesUrl + "/" + gameId + "/restart", null);
  }

  public withdraw(gameId: string) {
    return this.http.delete(this.gamesUrl + "/" + gameId);
  }

}
