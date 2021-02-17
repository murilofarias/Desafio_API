package com.gmail.murilo145farias.DesafioAPI.exception;

public class NaoExisteDaoException extends RuntimeException {

    public NaoExisteDaoException(String message) {
        super(message);
    }
}