package com.gmail.murilo145farias.DesafioAPI;

import com.gmail.murilo145farias.DesafioAPI.exception.IdNaoValidoServiceException;
import org.junit.Test;
import java.util.UUID;
import com.gmail.murilo145farias.util.UtilAPI;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UtilApiTest {
    @Test
    public void whenValidUuidIsGiven()
    {
        String stringId = "123e4567-e89b-12d3-a456-426655440000";
        assertEquals(UUID.class, UtilAPI.validarId(stringId).getClass());
    }
    @Test
    public void whenInvalidUuidIsGiven()
    {
        String stringId = "128393023";
        assertThrows(IdNaoValidoServiceException.class, () ->UtilAPI.validarId(stringId));
    }
}
