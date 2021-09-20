package com.bol.mancala.dto.mapper;

import com.bol.mancala.dto.GameResponseDTO;
import com.bol.mancala.model.Game;

public class GameMapper {

    public GameResponseDTO mapToDTO(Game game) {

        GameResponseDTO gameResponseDTO = new GameResponseDTO();
        gameResponseDTO.setGameId(game.getId());
        gameResponseDTO.setCurrentPlayer(game.getCurrentPlayer().getName());
        gameResponseDTO.setStatus(game.getStatus().name());
        gameResponseDTO.setPlayer1(new PlayerMapper().mapToDTO(game.getPlayer1()));
        gameResponseDTO.setPlayer2(new PlayerMapper().mapToDTO(game.getPlayer2()));
        return gameResponseDTO;
    }
}
