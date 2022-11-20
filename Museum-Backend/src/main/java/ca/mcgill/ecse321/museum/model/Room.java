package ca.mcgill.ecse321.museum.model;

import javax.persistence.*;

/**
 * Model class for a Room, the code was partially generated by Umple.
 *
 * @author Siger
 * @author Kevin
 */
@Entity
public class Room {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Room Attributes
  private long roomId;
  private String roomName;
  private int currentNumberOfArtwork;
  private RoomType roomType;

  // Room Associations
  private Museum museum;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Room() {
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  @GeneratedValue
  @Id
  public long getRoomId() {
    return roomId;
  }

  @Column(nullable = false)
  public String getRoomName() {
    return roomName;
  }

  @Column(nullable = false)
  public int getCurrentNumberOfArtwork() {
    return currentNumberOfArtwork;
  }

  @Column(nullable = false)
  public RoomType getRoomType() {
    return roomType;
  }

  /* Code from template association_GetOne */
  @ManyToOne()
  @JoinColumn(name = "museum_id", referencedColumnName = "museumId", nullable = false)
  public Museum getMuseum() {
    return museum;
  }

  public boolean setRoomId(long aRoomId) {
    boolean wasSet = false;
    roomId = aRoomId;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomName(String aRoomName) {
    boolean wasSet = false;
    roomName = aRoomName;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentNumberOfArtwork(int aCurrentNumberOfArtwork) {
    boolean wasSet = false;
    currentNumberOfArtwork = aCurrentNumberOfArtwork;
    wasSet = true;
    return wasSet;
  }

  public boolean setRoomType(RoomType aRoomType) {
    boolean wasSet = false;
    roomType = aRoomType;
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setMuseum(Museum aNewMuseum) {
    boolean wasSet = false;
    if (aNewMuseum != null) {
      museum = aNewMuseum;
      wasSet = true;
    }
    return wasSet;
  }
}
