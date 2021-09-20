package com.bol.mancala.repository;

import com.bol.mancala.GameConstants;
import com.bol.mancala.exception.GameException;
import com.bol.mancala.model.Game;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryGameRepository implements GameRepository {

    ConcurrentHashMap<String, Game> gameRepository = new ConcurrentHashMap<>();

    @Override
    public Game findById(String id) throws GameException {

        Game game = gameRepository.get(id);
        try {
            return game.clone();
        } catch (CloneNotSupportedException e) {
            // log error.
           throw new GameException(GameConstants.ERROR_CODE_SERVER_ERROR, "Unexpected server error.");
        }
    }

    @Override
    public List<Game> findAll() throws GameException {

        List<Game> clone = new ArrayList<>();
        for (Game value : gameRepository.values()) {
            try {
                clone.add(value.clone());
            } catch (CloneNotSupportedException e) {
                // log error.
                throw new GameException(GameConstants.ERROR_CODE_SERVER_ERROR, "Unexpected server error.");
            }
        }
        return clone;
    }

    @Override
    public void deleteById(String id) {

        gameRepository.remove(id);
    }

    @Override
    public void save(Game game) {

        gameRepository.put(game.getId(), game);
    }

    @Override
    public void updateById(String id, Game game) {

        gameRepository.replace(id, game);
    }

    @Override
    public boolean existsById(String id) {

        return gameRepository.containsKey(id);
    }
}
