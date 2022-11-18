package ca.mcgill.ecse321.museum.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.museum.dao.MuseumRepository;
import ca.mcgill.ecse321.museum.dao.ScheduleRepository;
import ca.mcgill.ecse321.museum.dto.MuseumDto;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MuseumIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired 
    private MuseumRepository museumRepository;
    @Autowired 
    private ScheduleRepository scheduleRepository;
    
    /**
     * Clear the database before and after each test
     * @author VZ
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        museumRepository.deleteAll();
        scheduleRepository.deleteAll();

    }

    /**
     * Test to successfully create, get and edit a museum
     * 
     * @author VZ
     */
    @Test
    public void testCreateGetAndEditMuseum() {
         Long id = testCreateMuseum();
         testGetMuseum(id);
         testEditMuseum(id);
    }

    /**
     * helper test method that returns the id of the created museum
     * @return id of the created museum
     * @author VZ
     */
    public Long testCreateMuseum() {
        ResponseEntity<MuseumDto> response = 
        client.postForEntity("/museum/app?name=Museum&visitFee=10", null , MuseumDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals("Museum", response.getBody().getMuseumName(), "Response has correct name");
        assertEquals(10, response.getBody().getVisitFee(), "Response has correct visit fee");
        assertTrue(response.getBody().getMuseumId() > 0, "Response has valid ID");

        return response.getBody().getMuseumId();
    }

    /**
     * helper test method gets a museum by id
     * @param id of the museum to get
     * @author VZ
     */
    public void testGetMuseum(Long id) {
        ResponseEntity<MuseumDto> response = 
        client.getForEntity("/museum/" + id, MuseumDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals("Museum", response.getBody().getMuseumName(), "Response has correct name");    
        assertEquals(10, response.getBody().getVisitFee(), "Response has correct visit fee");
        assertEquals(id, response.getBody().getMuseumId(), "Response has correct ID");
    }

    /**
     * helper test method that edits a museum given its id
     * @param id of the museum to edit
     * @author VZ 
     */
    public void testEditMuseum(Long id) {
        MuseumDto museumDto = new MuseumDto();
        museumDto.setMuseumId(id);
        ResponseEntity<MuseumDto> response = 
        client.postForEntity("/museum/app/edit/"+ id+"/?name=RQ&visitFee=20", null , MuseumDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals("RQ", response.getBody().getMuseumName(), "Response has correct name");
        assertEquals(20, response.getBody().getVisitFee(), "Response has correct visit fee");
        assertEquals(id, response.getBody().getMuseumId(), "Response has correct ID");
    }

    /**
     * Test to unsuccessfully get a museum with an invalid id
     * 
     * @author VZ
     */
    @Test
    public void testGetMuseumWithInvalidId() {
        ResponseEntity<String> response = 
        client.getForEntity("/museum/1", String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals("Museum does not exist", response.getBody(), "Response has correct error message");
    }

    /**
     * Test to unsuccessfuly create a museum with no name
     * 
     * @author VZ
     */
    @Test
    public void testCreateMuseumWithInvalidName() {
        ResponseEntity<String> response = 
        client.postForEntity("/museum/app?name=&visitFee=10", null , String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Response has correct status");
        assertNotNull(response.getBody(), "Response has body");
        assertEquals("Name cannot be empty!", response.getBody(), "Response has correct error message");
    }

    /**
     * Test to unsuccessfully create an invalid museum without a visit fee
     * 
     * @author VZ
     */
    @Test
    public void testCreateInvalidMuseumWithoutVisitFee(){
        ResponseEntity<MuseumDto> response = 
        client.postForEntity("/museum/app?name=Museum", null , MuseumDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(0.0, response.getBody().getVisitFee());
    }

    /**
     * Test to unsuccessfully create a museum with an invalid visit fee
     * 
     * @author VZ
     */
    @Test
    public void testCreateInvalidMuseumWithInvalidVisitFee(){
        ResponseEntity<MuseumDto> response = 
        client.postForEntity("/museum/app?name=Museum&visitFee=%2D10", null , MuseumDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(0.0, response.getBody().getVisitFee());
    }
    /**
     * Test to unsuccessfully edit an invalid museum without a name
     * 
     * @author VZ
     */
    @Test
    public void testEditInvalidMuseumWithoutName(){
        Long id = testCreateMuseum();
        MuseumDto museumDto = new MuseumDto();
        museumDto.setMuseumId(id);
        ResponseEntity<MuseumDto> response = 
        client.postForEntity("/museum/app/edit/"+ id+"/?visitFee=20", null , MuseumDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(null, response.getBody().getMuseumName());
    }
    /**
     * Test to unsuccessfully edit an invalid museum without a visit fee
     */
    @Test
    public void testEditInvalidMuseumWithoutVisitFee(){
        Long id = testCreateMuseum();
        MuseumDto museumDto = new MuseumDto();
        museumDto.setMuseumId(id);
        ResponseEntity<MuseumDto> response = 
        client.postForEntity("/museum/app/edit/"+ id+"/?name=RQ", null , MuseumDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(0.0, response.getBody().getVisitFee());
    }

    /**
     * Test to unsuccessfully edit an invalid museum with an invalid visit fee
     * 
     * @author VZ
     */
    @Test
    public void testEditInvalidMuseumWithInvalidVisitFee(){
        Long id = testCreateMuseum();
        MuseumDto museumDto = new MuseumDto();
        museumDto.setMuseumId(id);
        ResponseEntity<MuseumDto> response = 
        client.postForEntity("/museum/app/edit/"+ id+"/?name=RQ&visitFee=%2D10", null , MuseumDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(0.0, response.getBody().getVisitFee());
    }
    /**
     * Test get all museums successfully
     * @author VZ
     */
    @Test
    public void testGetAllMuseums() {
        testCreateMuseum();
        testCreateMuseum();
        testCreateMuseum();
        ResponseEntity<MuseumDto[]> response = 
        client.getForEntity("/museums", MuseumDto[].class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody(), "Response has body");
        assertEquals(3, response.getBody().length, "Response has correct number of museums");
    }
}