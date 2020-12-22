package lee.joohan.whattoeattelegrambot.domain.delivery;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Document("delivery_restaurant")
public class DeliveryRestaurant {
    @Id
    private ObjectId id;

    private String name;

    private ObjectId creatorId;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;


    @Builder
    public DeliveryRestaurant(String name, ObjectId creatorId) {
        this.name = name;
        this.creatorId = creatorId;
    }
}
