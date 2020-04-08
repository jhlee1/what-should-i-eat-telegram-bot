package lee.joohan.whattoeattelegrambot.repository;

import java.util.List;
import lee.joohan.whattoeattelegrambot.domain.CorporateCard;
import lee.joohan.whattoeattelegrambot.domain.dao.CorporateCardStatus;

/**
 * Created by Joohan Lee on 2020/04/08
 */
public interface CorporateCardRepositoryCustom {
  List<CorporateCardStatus> findCardStatuses();

}
