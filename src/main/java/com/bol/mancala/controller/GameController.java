package com.bol.mancala.controller;

import com.bol.mancala.dto.GameRequestDTO;
import com.bol.mancala.dto.GameResponseDTO;
import com.bol.mancala.dto.PlayRequestDTO;
import com.bol.mancala.dto.mapper.GameMapper;
import com.bol.mancala.exception.GameException;
import com.bol.mancala.model.Game;
import com.bol.mancala.model.Player;
import com.bol.mancala.service.GameService;
import com.bol.mancala.service.PlayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("games")
public class GameController {

    @Autowired
    private GameService gameService;

    @Autowired
    private PlayService playService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GameResponseDTO createGame(@RequestBody GameRequestDTO gameRequestDTO) throws GameException {

        Game game = gameService.createGame(gameRequestDTO.getPlayer1Name(), gameRequestDTO.getPlayer2Name());

        return new GameMapper().mapToDTO(game);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GameResponseDTO> listGames() throws GameException {

        return gameService.listGames().stream().map(game -> new GameMapper().mapToDTO(game)).collect(Collectors.toList());
    }

    @GetMapping(value = "/{gameId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameResponseDTO getGame(@PathVariable String gameId) throws GameException {

        Game game = gameService.getGame(gameId);
        return new GameMapper().mapToDTO(game);
    }

    @DeleteMapping(value = "/{gameId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    public void deleteGame(@PathVariable String gameId) throws GameException {

        gameService.deleteGame(gameId);
    }

    @PostMapping(value = "/{gameId}/play", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameResponseDTO playGame(@PathVariable String gameId, @RequestBody PlayRequestDTO playRequestDTO) throws GameException {

        Game game = playService.sow(gameService.getGame(gameId), playRequestDTO.getPitId());
        gameService.updateGame(gameId, game);
        return new GameMapper().mapToDTO(game);
    }

    @GetMapping(value = "/{gameId}/winner", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameResponseDTO getWinner(@PathVariable String gameId) throws GameException {

        Game game = gameService.getGame(gameId);
        game = playService.applyWinningRules(game);
        gameService.updateGame(gameId, game);
        Player player = playService.checkWinner(game);
        GameResponseDTO gameResponseDTO = new GameMapper().mapToDTO(game);
        gameResponseDTO.setWinner(player.getName());
        return gameResponseDTO;
    }

    @PostMapping(value = "/{gameId}/pause", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void pauseGame(@PathVariable String gameId) throws GameException {

        gameService.pauseGame(gameId);
    }

    @PostMapping(value = "/{gameId}/resume", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void resumeGame(@PathVariable String gameId) throws GameException {

        gameService.resumeGame(gameId);
    }

    @PostMapping(value = "/{gameId}/restart", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public GameResponseDTO restartGame(@PathVariable String gameId) throws GameException {

        return new GameMapper().mapToDTO(gameService.restartGame(gameId));
    }
}
