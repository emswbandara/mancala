import { Player } from "./player";

export class Game {
    gameId!: string;
    currentPlayer!: string;
    status!: string;
    winner!: string;
    player1!: Player;
    player2!: Player;
}