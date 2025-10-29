package org.example.calendarapi.entity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record YearPayload(@NotNull(message = "Поле год не может быть пустым")
                          @Min(value = 1600, message = "Год должен быть >= 1600")
                          @Max(value = 9999, message = "Год не может быть > 9999")
                          Integer year) {
}
