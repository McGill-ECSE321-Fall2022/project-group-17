package ca.mcgill.ecse321.museum.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

/**
 * Model class for a Loan, the code was partially generated by Umple
 * 
 * @author Siger
 * @author Kevin
 */
@Entity
public class Loan {

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // Loan Attributes
  private long loanId;
  private boolean requestAccepted;

  // Loan Associations
  private Visitor visitor;
  private Artwork artwork;

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public Loan() {}

  // ------------------------
  // INTERFACE
  // ------------------------

  @Id
  @GeneratedValue
  public long getLoanId() {
    return loanId;
  }

  @Column(nullable = false)
  public boolean getRequestAccepted() {
    return requestAccepted;
  }

  /* Code from template association_GetOne */
  @ManyToOne()
  @JoinColumn(name = "visitor_id", referencedColumnName = "museumUserId", nullable = false)
  public Visitor getVisitor() {
    return visitor;
  }

  /* Code from template association_GetOne */
  @OneToOne()
  @JoinColumn(name = "artwork_id", referencedColumnName = "artworkId", nullable = false)
  public Artwork getArtwork() {
    return artwork;
  }

  public boolean setLoanId(long aLoanId) {
    boolean wasSet = false;
    loanId = aLoanId;
    wasSet = true;
    return wasSet;
  }

  public boolean setRequestAccepted(boolean aRequestAccepted) {
    boolean wasSet = false;
    requestAccepted = aRequestAccepted;
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setVisitor(Visitor aNewVisitor) {
    boolean wasSet = false;
    if (aNewVisitor != null) {
      visitor = aNewVisitor;
      wasSet = true;
    }
    return wasSet;
  }

  /* Code from template association_SetUnidirectionalOne */
  public boolean setArtwork(Artwork aNewArtwork) {
    boolean wasSet = false;
    if (aNewArtwork != null) {
      artwork = aNewArtwork;
      wasSet = true;
    }
    return wasSet;
  }
}
