/* PLEASE DO NOT EDIT THIS CODE */
/* This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language! */

package ca.mcgill.ecse321.museum.model;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

// line 30 "model.ump"
// line 143 "model.ump"
@Entity
public class Manager extends MuseumUser {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Manager Associations
  private MuseumSystem museumSystem;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  // Empty constructor
  public Manager() {}

  public Manager(String aEmail, String aName, String aPassword, long aMuseumUserId,
      MuseumSystem aMuseumSystem) {
    super(aEmail, aName, aPassword, aMuseumUserId);
    if (aMuseumSystem == null || aMuseumSystem.getManager() != null) {
      throw new RuntimeException(
          "Unable to create Manager due to aMuseumSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    museumSystem = aMuseumSystem;
  }

  public Manager(String aEmail, String aName, String aPassword, long aMuseumUserId,
      Museum aMuseumForMuseumSystem) {
    super(aEmail, aName, aPassword, aMuseumUserId);
    museumSystem = new MuseumSystem(aMuseumForMuseumSystem, this);
  }

  // ------------------------
  // INTERFACE
  // ------------------------
  /* Code from template association_GetOne */
  @OneToOne(optional = false)
  public MuseumSystem getMuseumSystem() {
    return museumSystem;
  }

  public void delete() {
    MuseumSystem existingMuseumSystem = museumSystem;
    museumSystem = null;
    if (existingMuseumSystem != null) {
      existingMuseumSystem.delete();
    }
    super.delete();
  }

}
