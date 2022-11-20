package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;

/**
 * Model class for a Museum, the code was partially generated by Umple.
 *
 * @author Siger
 * @author Kevin
 */
@Entity
public class Museum {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Museum Attributes
  private long museumId;
  private String name;
  private double visitFee;

  // Museum Associations
  private Schedule schedule;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Museum() {
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  @Id
  @GeneratedValue
  public long getMuseumId() {
    return museumId;
  }

  @Column(nullable = false)
  public String getName() {
    return name;
  }

  @Column(nullable = false)
  public double getVisitFee() {
    return visitFee;
  }

  /* Code from template association_GetOne */
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "schedule_id", referencedColumnName = "scheduleId", nullable = false, unique = true)
  public Schedule getSchedule() {
    return schedule;
  }

  public boolean setMuseumId(long aMuseumId) {
    boolean wasSet = false;
    museumId = aMuseumId;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName) {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setVisitFee(double aVisitFee) {
    boolean wasSet = false;
    visitFee = aVisitFee;
    wasSet = true;
    return wasSet;
  }

  /**
   * Setter for Schedule
   *
   * @param schedule - Schedule object to be set
   * @author Siger
   */
  public void setSchedule(Schedule schedule) {
    this.schedule = schedule;
  }
}

