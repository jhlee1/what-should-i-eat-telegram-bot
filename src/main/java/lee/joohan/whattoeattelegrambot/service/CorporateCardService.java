package lee.joohan.whattoeattelegrambot.service;

import java.util.List;
import java.util.Optional;
import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.NotBorrowedAnyCardException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.NotFoundCorporateCardException;
import lee.joohan.whattoeattelegrambot.repository.CorporateCardRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
    corporateCardRepository.save(corporateCard);
  }

  @Transactional
  public void putBack(ObjectId userId) {
    List<CorporateCard> corporateCards = Optional.ofNullable(corporateCardRepository.findByCurrentUserId(userId))
        .filter(CollectionUtils::isNotEmpty)
        .orElseThrow(() -> new NotBorrowedAnyCardException(userId.toString()));

    corporateCards.forEach(card -> card.putBack(userId));

    corporateCardRepository.saveAll(corporateCards);
  }

  @Transactional(readOnly = true)
  public List<CorporateCardStatus> listCardStatuses() {
    return corporateCardRepository.findCardStatuses();
  }
}
