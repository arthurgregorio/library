package br.eti.arthurgregorio.library.domain.repositories.tools;

import br.eti.arthurgregorio.library.domain.model.entities.tools.Profile;
import br.eti.arthurgregorio.library.domain.repositories.DefaultRepository;
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
public interface ProfileRepository extends DefaultRepository<Profile> { }
