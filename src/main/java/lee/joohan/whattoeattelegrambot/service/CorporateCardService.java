package lee.joohan.whattoeattelegrambot.service;

import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;
import lee.joohan.whattoeattelegrambot.exception.corporate_card.NotBorrowedAnyCardException;
import lee.joohan.whattoeattelegrambot.repository.CorporateCardRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

/**
 * Created by Joohan Lee on 2020/04/07
 */

@Service
@RequiredArgsConstructor
public class CorporateCardService {
  private final CorporateCardRepository corporateCardRepository;

  @Transactional
  public Mono<CorporateCard> use(Mono<Tuple2<Integer, ObjectId>> cardNumberUserIdMono) {
    return corporateCardRepository.findByCardNum(cardNumberUserIdMono.map(Tuple2::getT1))
        .switchIfEmpty(cardNumberUserIdMono
            .map(Tuple2::getT1)
            .map(CorporateCard::new)
        ).flatMap(card ->
            cardNumberUserIdMono.map(mono -> {
                  card.use(mono.getT2());

                  return card;
                }
            )
        )
        .flatMap(corporateCardRepository::save);
  }

  @Transactional
  public void putBack(Mono<Tuple2<Integer, ObjectId>> cardNumberUserIdMono) {
    cardNumberUserIdMono.zipWith(
        cardNumberUserIdMono
            .flatMap(cardNumberUserId ->
                corporateCardRepository.findByCardNum(cardNumberUserIdMono.map(Tuple2::getT1))
            )
            .switchIfEmpty(Mono.error(() ->
                new NotBorrowedAnyCardException(cardNumberUserIdMono.block().getT2().toString()))
            )
    ).map(cardTuple -> {
      cardTuple.getT2()
          .putBack(
              cardTuple
                  .getT1()
                  .getT2());
      return corporateCardRepository.save(cardTuple.getT2());
    });
  }

//  @Transactional
//  public  putBack(Mono<ObjectId> userId) {
//    List<CorporateCard> corporateCards = Optional.ofNullable(corporateCardRepository.findByCurrentUserId(userId))
//        .filter(CollectionUtils::isNotEmpty)
//        .orElseThrow(() -> new NotBorrowedAnyCardException(userId.toString()));
//
//    corporateCards.forEach(card -> card.putBack(userId));
//
//    corporateCardRepository.saveAll(corporateCards);
//  }

  @Transactional(readOnly = true)
  public Flux<CorporateCardStatus> listCardStatuses() {
    return corporateCardRepository.findCardStatuses();
  }
}
