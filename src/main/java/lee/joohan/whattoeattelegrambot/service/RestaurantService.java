package lee.joohan.whattoeattelegrambot.service;

import java.util.List;
import lee.joohan.whattoeattelegrambot.domain.Menu;
import lee.joohan.whattoeattelegrambot.domain.Restaurant;
import lee.joohan.whattoeattelegrambot.domain.User;
import lee.joohan.whattoeattelegrambot.exception.AlreadyExistRestaurantException;
import lee.joohan.whattoeattelegrambot.exception.NotFoundRestaurantException;
import lee.joohan.whattoeattelegrambot.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Joohan Lee on 2020/02/15
 */

@RequiredArgsConstructor
@Service
public class RestaurantService {
  private final RestaurantRepository restaurantRepository;

  public Restaurant register(String name, User creator) {
    if (restaurantRepository.findByName(name).isPresent()) {
      throw AlreadyExistRestaurantException.fromName(name);
    }

    Restaurant restaurant = Restaurant.builder()
        .name(name)
        .creator(creator)
        .build();

    return restaurantRepository.save(restaurant);
  }

  @Transactional(readOnly = true)
  public List<Restaurant> getAll() {
    return restaurantRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Restaurant get(String name) {
    return restaurantRepository.findByName(name)
        .orElseThrow(() -> NotFoundRestaurantException.fromName(name));
  }

  @Transactional
  public Restaurant changeName(String from, String to, User updater) {
    Restaurant restaurant = restaurantRepository.findByName(from)
        .orElseThrow(() -> NotFoundRestaurantException.fromName(from));

    restaurant.changeName(to);

    return restaurantRepository.save(restaurant);
  }

  @Transactional
  public Restaurant addMenu(String name, Menu menu) {
    Restaurant restaurant = restaurantRepository.findByName(name)
        .orElseThrow();

    restaurant.addMenu(menu);

    restaurantRepository.save(restaurant);
    return restaurant;
  }

  @Transactional
  public Restaurant deleteRestaurant(String name) {
    Restaurant restaurant = restaurantRepository.findByName(name)
        .orElseThrow(() -> NotFoundRestaurantException.fromName(name));

    restaurantRepository.delete(restaurant);

    return restaurant;
  }
}
