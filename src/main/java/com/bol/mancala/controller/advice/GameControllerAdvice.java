package com.bol.mancala.controller.advice;

import com.bol.mancala.GameConstants;
import com.bol.mancala.dto.ErrorDTO;
import com.bol.mancala.exception.GameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GameControllerAdvice {

    @ExceptionHandler(value = GameException.class)
    public ResponseEntity<Object> handleGameException(GameException e) {

        if (GameConstants.ERROR_CODE_GAME_NOT_FOUND.equals(e.getErrorCode())) {
            return new ResponseEntity<>(createError(e), HttpStatus.NOT_FOUND);
        } else if (GameConstants.ERROR_CODE_INVALID_PLAYER_NAME.equals(e.getErrorCode())) {
            return new ResponseEntity<>(createError(e), HttpStatus.BAD_REQUEST);
        } else if (GameConstants.ERROR_CODE_PLAYER_NAME_CONFLICT.equals(e.getErrorCode())) {
            return new ResponseEntity<>(createError(e), HttpStatus.CONFLICT);
        } else if (GameConstants.ERROR_CODE_INVALID_PIT_ID.equals(e.getErrorCode())) {
            return new ResponseEntity<>(createError(e), HttpStatus.BAD_REQUEST);
        } else if (GameConstants.ERROR_CODE_INVALID_MOVE.equals(e.getErrorCode())) {
            return new ResponseEntity<>(createError(e), HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(createError(e), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ErrorDTO createError(GameException e) {

        return new ErrorDTO(e.getErrorCode(), e.getMessage());
    }
}
