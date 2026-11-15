package org.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO для создания/обновления подарка.
 * Для отправки JSON в теле POST/PUT запросов.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GiftDto {
    private String name; // Обязательное поле
    private String description; // Опциональное
    private String imageUrl; // Опциональное
    private Double price; // Обязательное поле
    private String storeUrl; // Опциональное (валидируется регуляркой)
    private Boolean reserved; // Опциональное
}
