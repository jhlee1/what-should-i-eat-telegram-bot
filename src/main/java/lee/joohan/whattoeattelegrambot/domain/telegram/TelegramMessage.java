package lee.joohan.whattoeattelegrambot.domain.telegram;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Created by Joohan Lee on 2020/05/31
 */

@Getter
@JsonInclude(Include.NON_EMPTY)
@Document
public class TelegramMessage {
  private int messageId;
  private TelegramUser from;
  private Date date;
  private TelegramChat chat;
  private TelegramUser forwardedFrom;
  private TelegramChat forwardFromChat;
  private Date forwardDate;
  private String text;
  private Object entities;
  private Object captionEntities;
  private Object audio;
  private Object document;
  private Object photo;
  private Object sticker;
  private Object video;
  private Object contact;
  private Object location;
  private Object venue;
  private TelegramChat pinnedMessage;
  private TelegramUser newChatMembers;
  private TelegramUser leftChatMember;
  private String newChatTitle;
  private Object newChatPhoto;
  private Object deleteChatPhoto;
  private Object groupchatCreated;
  private Object replyToMessage;
  private Object voice;
  private String caption;
  private Object superGroupCreated;
  private Object channelChatCreated;
  private Object migrateToChatId;
  private Object migrateFromChatId;
  private Date editDate;
  private Object game;
  private Object forwardFromMessageId;
  private Object invoice;
  private Object successfulPayment;
  private Object videoNote;
  private String authorSignature;
  private String forwardSignature;
  private String mediaGroupId;
  private String connectedWebsite;
  private Object passportData;

  public TelegramMessage(Message message) {
    Optional.ofNullable(message.getFrom())
        .ifPresent(it -> from = new TelegramUser(it));

    Optional.ofNullable(message.getChat())
        .ifPresent(it -> chat = new TelegramChat(it));

    messageId = message.getMessageId();
    text = message.getText();
    date = Date.from(Instant.ofEpochSecond(message.getDate()));
  }
}
