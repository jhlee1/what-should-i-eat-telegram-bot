package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistCorporateCardException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.NotBorrowedAnyCardException;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.NotFoundCorporateCardException;
import lee.joohan.whattoeattelegrambot.repository.CorporateCardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by Joohan Lee on 2020/04/07
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class CorporateCardService {
  private final CorporateCardRepository corporateCardRepository;

  public Mono<CorporateCard> create(int cardNum) {
    return corporateCardRepository.findByCardNum(cardNum)
        .<CorporateCard>then(Mono.error(() -> new AlreadyExistCorporateCardException(cardNum)))
        .switchIfEmpty(corporateCardRepository.save(new CorporateCard(cardNum)));
  }

  public Mono<Void> delete(int cardNum) {
    return corporateCardRepository.findByCardNum(cardNum)
        .switchIfEmpty(Mono.error(() -> new NotFoundCorporateCardException(cardNum)))
        .flatMap(corporateCardRepository::delete); //TODO: 정보 보존을 위해서 데이터 삭제가 아니라 다른 테이블로 옮기도록 처리하기 ex) deletedCards
  }

  @Transactional
  public Mono<CorporateCard> use(int cardNum, ObjectId userId) {
    return corporateCardRepository.findByCardNum(cardNum)
        .switchIfEmpty(Mono.error(() -> new NotFoundCorporateCardException(cardNum)))
        .flatMap(card -> {
          card.use(userId);

          return corporateCardRepository.save(card);
        });
  }

  @Transactional
  public Mono<CorporateCard> putBack(int cardNum, ObjectId userId) {
    return corporateCardRepository.findByCardNum(cardNum)
        .switchIfEmpty(Mono.error(() ->
                new NotBorrowedAnyCardException(userId.toHexString())
            )
        ).flatMap(card -> {
          card.putBack(userId);

          return corporateCardRepository.save(card);
        });
  }

  @Transactional
  public Flux<CorporateCard> putBackOwnCard(ObjectId userId) {
    return corporateCardRepository.findByIsBorrowedIsTrueAndCurrentUserId(userId)
        .switchIfEmpty(Mono.error(() -> new NotBorrowedAnyCardException(userId.toHexString())))
        .flatMap(corporateCard -> {
          corporateCard.putBack(corporateCard.getCurrentUserId());

          return corporateCardRepository.save(corporateCard);
        });
  }

  @Transactional(readOnly = true)
  public Flux<CorporateCardStatus> listCardStatuses() {
    return corporateCardRepository.findCardStatuses();
  }
}
