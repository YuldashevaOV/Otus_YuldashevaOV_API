package org.tests;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.restassured.response.Response;
import org.api.clients.WishlistClient;
import org.api.dto.WishlistDto;
import org.api.models.Wishlist;
import org.extensions.ApiExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;

import static io.restassured.RestAssured.given;

@ExtendWith(ApiExtension.class)
public class WishlistTest {

    @Inject
    private WishlistClient wishlistClient;

    @Inject
    @Named("noAuth")
    private WishlistClient noAuthWishlistClient;

    /**
     * Сценарий 1.1: Списки желаний. Создание списка желаний и поиск его среди списков текущего пользователя
     * Шаги:
     * 1. Авторизоваться (делается в BaseTest.setUp())
     * 2. Создать новый список желаний
     * 3. Найти его среди списков текущего пользователя
     */
    @Test
    @DisplayName("Создание списка желаний и проверка его наличия в списке текущего пользователя")
    public void testCreateWishlistAndFindIt() {
        // Подготовка данных для создания нового списка желаний
        WishlistDto wishlistDto = WishlistDto.builder()
                .title("Сценарий 1.1 Новый список")
                .description("Описание для теста")
                .build();

        // Создание нового списка желаний через API
        Response createResponse = wishlistClient.createNewWishlist(wishlistDto);
        Assertions.assertEquals(200, createResponse.statusCode(), "Неверный статус-код при создании нового списка");

        // Проверка, что в ответе пришли правильные данные
        Wishlist createdWishlist = createResponse.as(Wishlist.class);
        Assertions.assertEquals("Сценарий 1.1 Новый список", createdWishlist.getTitle(), "Название списка не совпадает");
        Assertions.assertNotNull(createdWishlist.getId(), "ID созданного списка не должен быть null");

        // Проверка, что созданный список есть в общем списке пользователя
        Response listResponse = wishlistClient.getWishlistsCurrentUser();
        Assertions.assertEquals(200, listResponse.statusCode(), "Неверный статус-код при получении списков");
        List<Wishlist> wishlists = listResponse.jsonPath().getList("$", Wishlist.class);
        boolean found = wishlists.stream()
                .anyMatch(w -> w.getId().equals(createdWishlist.getId()));
        Assertions.assertTrue(found, "Созданный список не найден среди списков пользователя");

        // Удаление, созданного списка
        Response deleteResponse = wishlistClient.deleteWishlistById(createdWishlist.getId());
        Assertions.assertEquals(200, deleteResponse.statusCode(), "Не удалось удалить созданный список");
    }

    /**
     * Сценарий 1.2: Списки желаний. Создание списка желаний и изменение этого списка
     * Шаги:
     * 1. Авторизоваться
     * 2. Создать новый список желаний
     * 3. Изменить название и описание списка
     */
    @Test
    @DisplayName("Создание списка желаний и изменение этого списка")
    public void testCreateWishlistAndChangeIt() {
        // Подготовка данных для создания нового списка желаний
        WishlistDto wishlistDto = WishlistDto.builder()
                .title("Сценарий 1.2 Новый список")
                .description("Описание для теста")
                .build();

        // Подготовка данных для изменения списка желаний
        WishlistDto updateWishlistDto = WishlistDto.builder()
                .title("Сценарий 1.2 Новый список - изменен")
                .description("Описание для теста изменено")
                .build();

        // Создание нового списка желаний через API
        Response createResponse = wishlistClient.createNewWishlist(wishlistDto);
        Assertions.assertEquals(200, createResponse.statusCode(), "Неверный статус-код при создании нового списка");
        Wishlist createdWishlist = createResponse.as(Wishlist.class);
        Assertions.assertNotNull(createdWishlist.getId(), "ID созданного списка не должен быть null");

        // Получаем список по ID (до изменения)
        Response getBeforeResponse = wishlistClient.getWishlistById(createdWishlist.getId());
        Assertions.assertEquals(200, getBeforeResponse.statusCode(), "Не удалось получить список до обновления");
        Wishlist beforeUpdate = getBeforeResponse.as(Wishlist.class);
        Assertions.assertEquals("Сценарий 1.2 Новый список", beforeUpdate.getTitle(), "Исходное название не совпадает");
        Assertions.assertEquals("Описание для теста", beforeUpdate.getDescription(), "Исходное описание не совпадает");

        // Изменение списка
        Response updateResponse = wishlistClient.updateWishlistById(createdWishlist.getId(), updateWishlistDto);
        Assertions.assertEquals(200, updateResponse.statusCode(), "Не удалось изменить список");

        // Получаем список по ID (после изменения)
        Response getAfterResponse = wishlistClient.getWishlistById(createdWishlist.getId());
        Assertions.assertEquals(200, getAfterResponse.statusCode(), "Не удалось получить список после обновления");
        Wishlist afterUpdate = getAfterResponse.as(Wishlist.class);
        Assertions.assertEquals("Сценарий 1.2 Новый список - изменен", afterUpdate.getTitle(), "Название не изменилось");
        Assertions.assertEquals("Описание для теста изменено", afterUpdate.getDescription(), "Описание не изменилось");

        // Удаление, созданного списка
        Response deleteResponse = wishlistClient.deleteWishlistById(createdWishlist.getId());
        Assertions.assertEquals(200, deleteResponse.statusCode(), "Не удалось удалить созданный список");
    }

    /**
     * Сценарий 1.3: Списки желаний. Создание списка желаний и удаление этого списка
     * Шаги:
     * 1. Авторизоваться
     * 2. Создать новый список желаний
     * 3. Удалить список
     */
    @Test
    @DisplayName("Создание списка желаний и удаление этого списка")
    public void testCreateWishlistAndDeleteIt() {
        // Подготовка данных для создания нового списка желаний
        WishlistDto wishlistDto = WishlistDto.builder()
                .title("Сценарий 1.3 Новый список")
                .description("Описание для теста")
                .build();

        // Создание нового списка желаний через API
        Response createResponse = wishlistClient.createNewWishlist(wishlistDto);
        Assertions.assertEquals(200, createResponse.statusCode(), "Неверный статус-код при создании нового списка");

        // Проверка, что в ответе пришли правильные данные
        Wishlist createdWishlist = createResponse.as(Wishlist.class);
        Assertions.assertEquals("Сценарий 1.3 Новый список", createdWishlist.getTitle(), "Название списка не совпадает");
        Assertions.assertNotNull(createdWishlist.getId(), "ID созданного списка не должен быть null");

        // Удаление, созданного списка
        Response deleteResponse = wishlistClient.deleteWishlistById(createdWishlist.getId());
        Assertions.assertEquals(200, deleteResponse.statusCode(), "Не удалось удалить созданный список");

        // Проверка, что списка нет в общем списке пользователя
        Response listResponse = wishlistClient.getWishlistsCurrentUser();
        Assertions.assertEquals(200, listResponse.statusCode(), "Неверный статус-код при получении списков");
        List<Wishlist> wishlists = listResponse.jsonPath().getList("$", Wishlist.class);
        boolean found = wishlists.stream()
                .anyMatch(w -> w.getId().equals(createdWishlist.getId()));
        Assertions.assertFalse(found, "Созданный список найден среди списков пользователя");
    }

    /**
     * Сценарий 1.4: Списки желаний. Создание списка без названия (Негативный сценарий)
     * Шаги:
     * 1. Авторизоваться
     * 2. Создать новый список желаний с пустым title
     */
    @Test
    @DisplayName("Создание списка без названия (Негативный сценарий)")
    public void testCreateWishlistWithoutTitle() {
        // Подготовка данных для создания нового списка желаний
        WishlistDto wishlistDto = WishlistDto.builder()
                .title("")
                .description("Описание для теста")
                .build();

        // Создание нового списка желаний через API
        Response createResponse = wishlistClient.createNewWishlist(wishlistDto);
        Assertions.assertEquals(403, createResponse.statusCode(), "Неверный статус-код при создании списка без названия");
        Response listResponse = wishlistClient.getWishlistsCurrentUser();
        List<Wishlist> wishlists = listResponse.jsonPath().getList("$", Wishlist.class);
        boolean found = wishlists.stream()
                .anyMatch(w -> "".equals(w.getTitle()));
        Assertions.assertFalse(found, "Список с пустым названием был создан");
    }

    /**
     * Сценарий 1.5: Списки желаний. Создание списка без названия (Негативный сценарий)
     * Шаги:
     * 1. Авторизоваться без токена
     * 2. Создать новый список желаний
     */
    @Test
    @DisplayName("Создание списка без авторизации (Негативный сценарий)")
    public void testCreateWishlistWithoutAuth() {
        WishlistDto wishlistDto = WishlistDto.builder()
                .title("Список без авторизации")
                .description("Описание для теста")
                .build();

        // Отправляем запрос с noAuthSpec (без токена)
        Response response = noAuthWishlistClient.createNewWishlist(wishlistDto);

        Assertions.assertEquals(403, response.statusCode(), "Неверный статус-код при запросе без авторизации");
    }
}
