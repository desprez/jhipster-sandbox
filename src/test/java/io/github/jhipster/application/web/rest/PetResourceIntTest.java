package io.github.jhipster.application.web.rest;

import static io.github.jhipster.application.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.jhipster.application.JhipsterSandBoxApp;
import io.github.jhipster.application.domain.Owner;
import io.github.jhipster.application.domain.Pet;
import io.github.jhipster.application.repository.OwnerRepository;
import io.github.jhipster.application.repository.PetRepository;
import io.github.jhipster.application.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the PetResource REST controller.
 *
 * @see PetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JhipsterSandBoxApp.class)
public class PetResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SPECIES = "AAAAAAAAAA";
    private static final String UPDATED_SPECIES = "BBBBBBBBBB";

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPetMockMvc;

    private Pet pet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PetResource petResource = new PetResource(petRepository);
        restPetMockMvc = MockMvcBuilders.standaloneSetup(petResource)
                .setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
                .setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
                .build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it, if
     * they test an entity which requires the current entity.
     */
    public static Pet createEntity(final EntityManager em) {
        final Pet pet = new Pet().name(DEFAULT_NAME).species(DEFAULT_SPECIES);
        return pet;
    }

    @Before
    public void initTest() {
        pet = createEntity(em);
    }

    @Test
    @Transactional
    public void createPet() throws Exception {
        final int databaseSizeBeforeCreate = petRepository.findAll().size();

        // Create the Pet
        restPetMockMvc.perform(post("/api/pets").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet))).andExpect(status().isCreated());

        // Validate the Pet in the database
        final List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate + 1);
        final Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPet.getSpecies()).isEqualTo(DEFAULT_SPECIES);
    }

    @Test
    @Transactional
    public void createPetWithExistingId() throws Exception {
        final int databaseSizeBeforeCreate = petRepository.findAll().size();

        // Create the Pet with an existing ID
        pet.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPetMockMvc.perform(post("/api/pets").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet))).andExpect(status().isBadRequest());

        // Validate the Pet in the database
        final List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        final int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setName(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet))).andExpect(status().isBadRequest());

        final List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSpeciesIsRequired() throws Exception {
        final int databaseSizeBeforeTest = petRepository.findAll().size();
        // set the field null
        pet.setSpecies(null);

        // Create the Pet, which fails.

        restPetMockMvc.perform(post("/api/pets").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet))).andExpect(status().isBadRequest());

        final List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPets() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get all the petList
        restPetMockMvc.perform(get("/api/pets?sort=id,desc")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pet.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].species").value(hasItem(DEFAULT_SPECIES.toString())));
    }

    @Test
    @Transactional
    public void getPet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", pet.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(pet.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
                .andExpect(jsonPath("$.species").value(DEFAULT_SPECIES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPet() throws Exception {
        // Get the pet
        restPetMockMvc.perform(get("/api/pets/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        final int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Update the pet
        final Pet updatedPet = petRepository.findById(pet.getId()).get();
        // Disconnect from session so that the updates on updatedPet are not directly
        // saved in db
        em.detach(updatedPet);
        updatedPet.name(UPDATED_NAME).species(UPDATED_SPECIES);

        restPetMockMvc.perform(put("/api/pets").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPet))).andExpect(status().isOk());

        // Validate the Pet in the database
        final List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
        final Pet testPet = petList.get(petList.size() - 1);
        assertThat(testPet.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPet.getSpecies()).isEqualTo(UPDATED_SPECIES);
    }

    @Test
    @Transactional
    public void updateNonExistingPet() throws Exception {
        final int databaseSizeBeforeUpdate = petRepository.findAll().size();

        // Create the Pet

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPetMockMvc.perform(put("/api/pets").contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pet))).andExpect(status().isBadRequest());

        // Validate the Pet in the database
        final List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePet() throws Exception {
        // Initialize the database
        petRepository.saveAndFlush(pet);

        final int databaseSizeBeforeDelete = petRepository.findAll().size();

        // Get the pet
        restPetMockMvc.perform(delete("/api/pets/{id}", pet.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        final List<Pet> petList = petRepository.findAll();
        assertThat(petList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void getAllPetsForOwner() throws Exception {

        final Pet medor = new Pet().name("Medor").species("Dog");
        petRepository.saveAndFlush(medor);
        final Pet kitty = new Pet().name("Kitty").species("Cat");
        petRepository.saveAndFlush(kitty);
        final Owner owner = new Owner().name("John Doe").addPet(medor).addPet(kitty);
        // Initialize the database
        ownerRepository.saveAndFlush(owner);

        // Get the pet/pets/owners/{ownerId}
        final MvcResult result = restPetMockMvc
                .perform(get("/api/pets/owners/{id}", owner.getId()).accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk()).andReturn();

        final ObjectMapper mapper = new ObjectMapper();

        final List<Pet> petList = mapper.readValue(result.getResponse().getContentAsString(),
                new TypeReference<List<Pet>>() {
                });

        assertThat(petList).hasSize(2);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pet.class);
        final Pet pet1 = new Pet();
        pet1.setId(1L);
        final Pet pet2 = new Pet();
        pet2.setId(pet1.getId());
        assertThat(pet1).isEqualTo(pet2);
        pet2.setId(2L);
        assertThat(pet1).isNotEqualTo(pet2);
        pet1.setId(null);
        assertThat(pet1).isNotEqualTo(pet2);
    }
}
