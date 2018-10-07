package io.github.jhipster.application.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;

import io.github.jhipster.application.domain.Pet;
import io.github.jhipster.application.repository.PetRepository;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Pet.
 */
@RestController
@RequestMapping("/api")
public class PetResource {

    private final Logger log = LoggerFactory.getLogger(PetResource.class);

    private static final String ENTITY_NAME = "pet";

    private final PetRepository petRepository;

    public PetResource(final PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * POST  /pets : Create a new pet.
     *
     * @param pet the pet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pet, or with status 400 (Bad Request) if the pet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pets")
    @Timed
    public ResponseEntity<Pet> createPet(@Valid @RequestBody final Pet pet) throws URISyntaxException {
        log.debug("REST request to save Pet : {}", pet);
        if (pet.getId() != null) {
            throw new BadRequestAlertException("A new pet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        final Pet result = petRepository.save(pet);
        return ResponseEntity.created(new URI("/api/pets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pets : Updates an existing pet.
     *
     * @param pet the pet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pet,
     * or with status 400 (Bad Request) if the pet is not valid,
     * or with status 500 (Internal Server Error) if the pet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pets")
    @Timed
    public ResponseEntity<Pet> updatePet(@Valid @RequestBody final Pet pet) throws URISyntaxException {
        log.debug("REST request to update Pet : {}", pet);
        if (pet.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        final Pet result = petRepository.save(pet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pets : get all the pets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pets in body
     */
    @GetMapping("/pets")
    @Timed
    public List<Pet> getAllPets() {
        log.debug("REST request to get all Pets");
        return petRepository.findAll();
    }

    /**
     * GET  /pets/:id : get the "id" pet.
     *
     * @param id the id of the pet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pet, or with status 404 (Not Found)
     */
    @GetMapping("/pets/{id}")
    @Timed
    public ResponseEntity<Pet> getPet(@PathVariable final Long id) {
        log.debug("REST request to get Pet : {}", id);
        final Optional<Pet> pet = petRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pet);
    }

    /**
     * DELETE  /pets/:id : delete the "id" pet.
     *
     * @param id the id of the pet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pets/{id}")
    @Timed
    public ResponseEntity<Void> deletePet(@PathVariable final Long id) {
        log.debug("REST request to delete Pet : {}", id);

        petRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET /pets/owners/{ownerId} : get all the pets by owner id.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of actions in
     *         body
     */
    @GetMapping("/pets/owners/{ownerId}")
    @Timed
    public List<Pet> getAllPetsForOwner(@PathVariable final Long ownerId) {
        log.debug("REST request to get all pets for owner : {}", ownerId);

        final List<Pet> actions = petRepository.findByOwnerId(ownerId);

        return actions;
    }
}
