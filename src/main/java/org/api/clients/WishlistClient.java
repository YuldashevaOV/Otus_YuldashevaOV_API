package org.api.clients;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.api.dto.WishlistDto;

import java.util.UUID;

import static io.restassured.RestAssured.given;

/**
 * Клиент для работы со списками желаний.
 * Содержит методы для создания, получения, обновления и удаления списков.
 * Все методы принимают RequestSpecification (чтобы передать токен авторизации).
 */

public class WishlistClient {

    private final RequestSpecification spec;

    public WishlistClient(RequestSpecification spec) {
        this.spec = spec;
    }

    /**
     * Создать новый список желаний
     */
    public Response createNewWishlist(WishlistDto wishlistDto) {
        return given()
                .spec(spec) // Подставляем базовую спецификацию
                .body(wishlistDto) // Автоматически сериализует объект в JSON
                .when()
                .post("/api/wishlists")
                .then()
                .extract().response(); // Возвращаем Response для дальнейших проверок
    }

    /**
     * Получить список желаний по ID
     */
    public Response getWishlistById(UUID id) {
        return given()
                .spec(spec)
                .pathParam("id", id)
                .when()
                .get("/api/wishlists/{id}")
                .then()
                .extract().response();
    }

    /**
     * Получить все списки желаний текущего пользователя
     */
    public Response getWishlistsCurrentUser() {
        return given()
                .spec(spec)
                .when()
                .get("/api/wishlists")
                .then()
                .extract().response();
    }

    /**
     * Обновить список желаний по ID.
     */
    public Response updateWishlistById(UUID id, WishlistDto wishlistDto) {
        return given()
                .spec(spec)
                .pathParam("id", id)
                .body(wishlistDto)
                .when()
                .put("/api/wishlists/{id}")
                .then()
                .extract().response();
    }

    /**
     * Удалить список желаний по ID.
     */
    public Response deleteWishlistById(UUID id) {
        return given()
                .spec(spec)
                .pathParam("id", id)
                .when()
                .delete("/api/wishlists/{id}")
                .then()
                .extract().response();
    }


}
