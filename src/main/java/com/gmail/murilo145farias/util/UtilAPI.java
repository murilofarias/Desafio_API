package com.gmail.murilo145farias.util;

import com.gmail.murilo145farias.DesafioAPI.exception.IdNaoValidoServiceException;

import java.util.UUID;

public class UtilAPI {
    public  static UUID validarId(String stringId) {
        UUID id;
        try {
            id = UUID.fromString(stringId);
        } catch(IllegalArgumentException ex)
        {
            throw new IdNaoValidoServiceException("O id informado não está no formato UUID");
        }
        return id;
    }
}
