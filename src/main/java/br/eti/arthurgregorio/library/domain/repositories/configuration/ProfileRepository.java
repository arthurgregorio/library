package br.eti.arthurgregorio.library.domain.repositories.configuration;

import br.eti.arthurgregorio.library.domain.entities.configuration.Profile;
import br.eti.arthurgregorio.library.infrastructure.deltaspike.repositories.DeltaSpikeRepository;
import org.apache.deltaspike.data.api.Repository;

/**
 * The {@link Profile} repository
 *
 * @author Arthur Gregorio
 *
 * @version 1.0.0
 * @since 2.0.0, 23/10/2018
 */
@Repository
public interface ProfileRepository extends DeltaSpikeRepository<Profile> { }
