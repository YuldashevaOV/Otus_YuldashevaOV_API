package org.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Модель подарка
 * Десериализация ответа от API.
 */

@Data
@NoArgsConstructor
public class Gift {
    private UUID id; // Уникальный идентификатор подарка
    private String name; // Название подарка
    private String description; // Описание подарка
    private String imageUrl; // Ссылка на картинку подарка
    private String storeUrl; // Ссылка на магазин
    private Double price; // Цена
    private Boolean reserved; // Зарезервирован ли подарок
    private Wishlist wishList; // Список желаний, к которому относится подарок
}
