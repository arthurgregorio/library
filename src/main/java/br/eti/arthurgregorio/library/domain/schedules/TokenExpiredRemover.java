package br.eti.arthurgregorio.library.domain.schedules;

import br.eti.arthurgregorio.library.domain.services.TokenService;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.inject.Inject;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 07/11/2018
 */
@Singleton
public class TokenExpiredRemover {

    @Inject
    private TokenService tokenService;

    /**
     *
     */
    @Schedule(persistent = false)
    public void removeHourly() {
        this.tokenService.removeExpired();
    }
}
