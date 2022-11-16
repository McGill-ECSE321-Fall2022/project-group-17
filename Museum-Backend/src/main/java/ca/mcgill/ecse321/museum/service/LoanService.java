package ca.mcgill.ecse321.museum.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ca.mcgill.ecse321.museum.dao.ArtworkRepository;
import ca.mcgill.ecse321.museum.dao.LoanRepository;
import ca.mcgill.ecse321.museum.dao.VisitorRepository;
import ca.mcgill.ecse321.museum.model.Artwork;
import ca.mcgill.ecse321.museum.model.Loan;
import ca.mcgill.ecse321.museum.model.Visitor;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepository;
    @Autowired
    ArtworkRepository artworkRepository;
    @Autowired 
    VisitorRepository visitorRepository;

    @Transactional
    public Loan getLoanById(Long loanId) {
        Loan loan = loanRepository.findLoanByLoanId(loanId);
        if (loan == null) {
            throw new IllegalArgumentException("Loan does not exist");
          }
        return loan;
    }

    @Transactional 
    public List<Loan> getAllLoans() {
        List<Loan> loans = toList(loanRepository.findAll());
        return loans;
    }

    @Transactional
    public Loan createLoan(Loan loan, Long artworkId, Long visitorId) {
        // Error handling associations
        Artwork artwork = artworkRepository.findArtworkByArtworkId(artworkId);
        Visitor visitor = visitorRepository.findVisitorByMuseumUserId(visitorId);
        if (artwork == null) {
            throw new IllegalArgumentException("Artwork does not exist");
        }
        if (artwork.getIsAvailableForLoan() == false) {
            throw new IllegalArgumentException("Artwork is not available for loan");
        }
        if (artwork.getIsOnLoan() == true) {
            throw new IllegalArgumentException("Cannot create a loan request for an artwork that is on loan");
        }
        if (visitor == null) {
            throw new IllegalArgumentException("Visitor does not exist");
        }

        // Error handling loan attributes
        if (loan.getRequestAccepted() != null) {
            throw new IllegalArgumentException("Loan getRequestAccepted must be null because only an employee can define ");
        }
        if (loanRepository.findLoanByArtworkAndVisitor(artwork, visitor) != null) {
            throw new IllegalArgumentException("Cannot create a duplicate loan request");
        }
        loan.setArtwork(artwork);
        loan.setVisitor(visitor);
        Loan persistedLoan = loanRepository.save(loan);

        return persistedLoan;
    }



/**
   * Method to convert an Iterable to a List
   *
   * @param iterable -Iterable
   * @return List
   * @author Tutorial notes
   */
  private <T> List<T> toList(Iterable<T> iterable) {
    List<T> resultList = new ArrayList<T>();
    for (T t : iterable) {
      resultList.add(t);
    }
    return resultList;
  }
}