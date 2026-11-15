package org.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * Модель списка желаний
 * Десериализация ответа от API.
 */

@Data
@NoArgsConstructor
public class Wishlist {
    private UUID id; // Уникальный идентификатор списка (UUID)
    private String title; // Название списка
    private String description; // Описание списка
    private User owner; // Владелец списка (вложенный объект User)
    private List<Gift> gifts; // Список подарков внутри этого списка
}
