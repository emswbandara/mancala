package com.bol.mancala.dto.mapper;

import com.bol.mancala.dto.PitDTO;
import com.bol.mancala.dto.PlayerDTO;
import com.bol.mancala.model.Player;

import java.util.stream.Collectors;

public class PlayerMapper {

    PlayerDTO mapToDTO(Player player) {

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setName(player.getName());
        playerDTO.setBigPit(player.getBigPit().getStones());
        playerDTO.setSmallPits(player.getSmallPits().stream()
                .map(pit -> new PitDTO(pit.getId(), pit.getStones())).collect(Collectors.toList()));
        return playerDTO;
    }
}
