package com.bol.mancala.repository;

import com.bol.mancala.exception.GameException;
import com.bol.mancala.model.Game;

import java.util.List;

public interface GameRepository {

    Game findById(String id) throws GameException;

    List<Game> findAll() throws GameException;

    void deleteById(String id);

    void save(Game game);

    void updateById(String id, Game game);

    boolean existsById(String id);
}
