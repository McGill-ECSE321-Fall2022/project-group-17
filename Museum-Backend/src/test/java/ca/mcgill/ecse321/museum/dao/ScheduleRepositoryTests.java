package ca.mcgill.ecse321.museum.dao;

import ca.mcgill.ecse321.museum.model.Employee;
import ca.mcgill.ecse321.museum.model.Museum;
import ca.mcgill.ecse321.museum.model.Schedule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test the persistence layer for the ScheduleRepository. Testing reading and writing of
 * objects, attributes and references to the database.
 * Also tested museum and employee references owning a schedule.
 *
 * @author Siger
 */
@SpringBootTest
public class ScheduleRepositoryTests {
  @Autowired
  private ScheduleRepository scheduleRepository;

  @Autowired
  private MuseumRepository museumRepository;

  @Autowired
  private EmployeeRepository employeeRepository;

  @AfterEach
  public void clearDatabase() {
    museumRepository.deleteAll();
    employeeRepository.deleteAll();
    scheduleRepository.deleteAll();
  }

  /**
   * Test that a schedule can be persisted and loaded from the database.
   *
   * @author Siger
   */
  @Test
  public void testPersistAndLoadSchedule() {
    //create Schedule
    Schedule schedule = new Schedule();

    //save Schedule
    scheduleRepository.save(schedule);
    long scheduleId = schedule.getScheduleId();

    //reset
    schedule = null;

    //read Schedule from database
    schedule = scheduleRepository.findScheduleByScheduleId(scheduleId);

    //assert that Schedule exists in database
    assertNotNull(schedule);
  }

  /**
   * Test that a schedule can be persisted and loaded from the database.
   * Also test that a museum can own a schedule.
   *
   * @author Siger
   */
  @Test
  public void testPersistAndLoadScheduleForMuseum() {
    //create Schedule
    Schedule schedule = new Schedule();

    //create Museum associated with Schedule
    String name = "Rougon-Macquart";
    double visitFee = 6.99;
    Museum museum = new Museum();
    museum.setName(name);
    museum.setVisitFee(visitFee);
    museum.setSchedule(schedule);

    //save Museum
    museum = museumRepository.save(museum);
    long museumId = museum.getMuseumId();
    long scheduleId = museum.getSchedule().getScheduleId();

    //reset
    museum = null;

    //read Museum from database
    museum = museumRepository.findMuseumByMuseumId(museumId);

    //read Schedule from database
    Schedule repoSchedule = scheduleRepository.findScheduleByScheduleId(scheduleId);

    //assert that Museum has correct attributes
    assertNotNull(museum);
    assertEquals(museumId, museum.getMuseumId());
    assertEquals(name, museum.getName());
    assertEquals(visitFee, museum.getVisitFee());

    //assert that Museum has correct Schedule
    assertNotNull(museum.getSchedule());
    assertNotNull(repoSchedule);
    assertEquals(scheduleId, schedule.getScheduleId());
    assertEquals(scheduleId, repoSchedule.getScheduleId());
  }

  /**
   * Test that a schedule can be persisted and loaded from the database.
   * Also test that an employee can own a schedule.
   *
   * @author Siger
   */
  @Test
  public void testPersistAndLoadScheduleForEmployee() {
    //create Schedule
    Schedule schedule = new Schedule();

    //create Employee associated with Schedule
    String email = "emile.zola@Assommoir.fr";
    String name = "Émile Zola";
    String password = "Gervaise";
    Employee employee = new Employee();
    employee.setEmail(email);
    employee.setName(name);
    employee.setPassword(password);
    employee.setSchedule(schedule);

    //save Employee
    employee = employeeRepository.save(employee);
    long employeeId = employee.getMuseumUserId();
    long scheduleId = employee.getSchedule().getScheduleId();

    //reset
    employee = null;

    //read Employee from database
    employee = employeeRepository.findEmployeeByMuseumUserId(employeeId);

    //read Schedule from database
    Schedule repoSchedule = scheduleRepository.findScheduleByScheduleId(scheduleId);

    //assert that Employee exists in database
    assertNotNull(employee);
    assertEquals(employeeId, employee.getMuseumUserId());
    assertEquals(email, employee.getEmail());
    assertEquals(name, employee.getName());
    assertEquals(password, employee.getPassword());

    //assert that Employee has correct Schedule
    assertNotNull(employee.getSchedule());
    assertNotNull(repoSchedule);
    assertEquals(scheduleId, schedule.getScheduleId());
    assertEquals(scheduleId, repoSchedule.getScheduleId());
  }
}