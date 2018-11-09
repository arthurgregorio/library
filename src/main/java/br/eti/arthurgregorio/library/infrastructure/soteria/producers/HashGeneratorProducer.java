package br.eti.arthurgregorio.library.infrastructure.soteria.producers;

import br.eti.arthurgregorio.library.infrastructure.soteria.hash.*;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import static br.eti.arthurgregorio.library.infrastructure.soteria.hash.Algorithm.AlgorithmType.BCRYPT;
import static br.eti.arthurgregorio.library.infrastructure.soteria.hash.Algorithm.AlgorithmType.PBKDF2;
import static br.eti.arthurgregorio.library.infrastructure.soteria.hash.Algorithm.AlgorithmType.SHA;

/**
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 1.0.0, 06/11/2018
 */
@ApplicationScoped
public class HashGeneratorProducer {

    /**
     *
      * @return
     */
    @Default
    @Produces
    @Algorithm(BCRYPT)
    HashGenerator bcrypGenerator() {
        return new BCryptHashGenerator();
    }

    /**
     *
     * @return
     */
    @Produces
    @Algorithm(PBKDF2)
    HashGenerator pbkdfGenerator() {
        return new PbkdfHashGenerator();
    }

    /**
     *
     * @param point
     * @return
     */
    @Produces
    @Algorithm(SHA)
    HashGenerator shaGenerator(InjectionPoint point) {

        final SHALevel annotation = point.getAnnotated().getAnnotation(SHALevel.class);
        final SHALevel.Level level = annotation.value();

        return new ShaHashGenerator(level.getLevel());
    }
}
