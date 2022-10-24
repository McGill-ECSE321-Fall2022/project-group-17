/* PLEASE DO NOT EDIT THIS CODE */
/* This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language! */

package ca.mcgill.ecse321.museum.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;

// line 59 "model.ump"
// line 153 "model.ump"
@Entity
public class Artwork {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Artwork Attributes
  private long artworkId;
  private String name;
  private String artist;
  private boolean isAvailableForLoan;
  private double loanFee;
  private String image;

  // Artwork Associations
  private Room room;
  private MuseumSystem museumSystem;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  //no arg constructor
  public Artwork(){}

  public Artwork(long aArtworkId, String aName, String aArtist, boolean aIsAvailableForLoan,
      double aLoanFee, String aImage, Room aRoom, MuseumSystem aMuseumSystem) {
    artworkId = aArtworkId;
    name = aName;
    artist = aArtist;
    isAvailableForLoan = aIsAvailableForLoan;
    loanFee = aLoanFee;
    image = aImage;
    if (!setRoom(aRoom)) {
      throw new RuntimeException(
          "Unable to create Artwork due to aRoom. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddMuseumSystem = setMuseumSystem(aMuseumSystem);
    if (!didAddMuseumSystem) {
      throw new RuntimeException(
          "Unable to create artwork due to museumSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setArtworkId(long aArtworkId) {
    boolean wasSet = false;
    artworkId = aArtworkId;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName) {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setArtist(String aArtist) {
    boolean wasSet = false;
    artist = aArtist;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsAvailableForLoan(boolean aIsAvailableForLoan) {
    boolean wasSet = false;
    isAvailableForLoan = aIsAvailableForLoan;
    wasSet = true;
    return wasSet;
  }

  public boolean setLoanFee(double aLoanFee) {
    boolean wasSet = false;
    loanFee = aLoanFee;
    wasSet = true;
    return wasSet;
  }

  public boolean setImage(String aImage) {
    boolean wasSet = false;
    image = aImage;
    wasSet = true;
    return wasSet;
  }

  @Id
  @GeneratedValue
  public long getArtworkId() {
    return artworkId;
  }

  public String getName() {
    return name;
  }

  public String getArtist() {
    return artist;
  }

  public boolean getIsAvailableForLoan() {
    return isAvailableForLoan;
  }

  public double getLoanFee() {
    return loanFee;
  }

  public String getImage() {
    return image;
  }

  /* Code from template association_GetOne */
  @ManyToOne(optional = false)
  public Room getRoom() {
    return room;
  }

  /* Code from template association_GetOne */
  @ManyToOne(optional = false)
  public MuseumSystem getMuseumSystem() {
    return museumSystem;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setRoom(Room aNewRoom) {
    boolean wasSet = false;
    if (aNewRoom != null) {
      room = aNewRoom;
      wasSet = true;
    }
    return wasSet;
  }

  /* Code from template association_SetOneToMany */
  public boolean setMuseumSystem(MuseumSystem aMuseumSystem) {
    boolean wasSet = false;
    if (aMuseumSystem == null) {
      return wasSet;
    }

    MuseumSystem existingMuseumSystem = museumSystem;
    museumSystem = aMuseumSystem;
    if (existingMuseumSystem != null && !existingMuseumSystem.equals(aMuseumSystem)) {
      existingMuseumSystem.removeArtwork(this);
    }
    museumSystem.addArtwork(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    room = null;
    MuseumSystem placeholderMuseumSystem = museumSystem;
    this.museumSystem = null;
    if (placeholderMuseumSystem != null) {
      placeholderMuseumSystem.removeArtwork(this);
    }
  }


  public String toString() {
    return super.toString() + "[" + "artworkId" + ":" + getArtworkId() + "," + "name" + ":"
        + getName() + "," + "artist" + ":" + getArtist() + "," + "isAvailableForLoan" + ":"
        + getIsAvailableForLoan() + "," + "loanFee" + ":" + getLoanFee() + "," + "image" + ":"
        + getImage() + "]" + System.getProperties().getProperty("line.separator") + "  " + "room = "
        + (getRoom() != null ? Integer.toHexString(System.identityHashCode(getRoom())) : "null")
        + System.getProperties().getProperty("line.separator") + "  " + "museumSystem = "
        + (getMuseumSystem() != null
            ? Integer.toHexString(System.identityHashCode(getMuseumSystem()))
            : "null");
  }
}