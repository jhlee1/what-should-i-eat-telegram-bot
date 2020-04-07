package lee.joohan.whattoeattelegrambot.service;

import java.util.Optional;
import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import lee.joohan.whattoeattelegrambot.exception.NotBorrowedAnyCardException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundCorporateCardException;
import lee.joohan.whattoeattelegrambot.repository.CorporateCardRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Joohan Lee on 2020/04/07
 */

@Service
@RequiredArgsConstructor
public class CorporateCardService {
  private final CorporateCardRepository corporateCardRepository;

  @Transactional
  public void use(int cardNumber, ObjectId userId) {
    CorporateCard corporateCard = Optional.ofNullable(corporateCardRepository.findByCardNum(cardNumber))
        .orElseGet(() -> new CorporateCard(cardNumber));

    corporateCard.use(userId);

    corporateCardRepository.save(corporateCard);
  }

  @Transactional
  public void putBack(int cardNumber, ObjectId userId) {
    CorporateCard corporateCard = Optional.ofNullable(corporateCardRepository.findByCardNum(cardNumber))
        .orElseThrow(() -> new NotFoundCorporateCardException(cardNumber));

    corporateCard.putBack(userId);
  }

  @Transactional
  public void putBack(ObjectId userId) {
    CorporateCard corporateCard = Optional.ofNullable(corporateCardRepository.findByCurrentUserId(userId))
        .orElseThrow(() -> new NotBorrowedAnyCardException(userId.toString()));

    corporateCard.putBack(userId);
  }
}
