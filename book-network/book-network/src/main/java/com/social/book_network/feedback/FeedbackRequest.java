package com.social.book_network.feedback;

import jakarta.validation.constraints.*;
import lombok.*;

public record FeedbackRequest(
        @Positive(message = "200")
        @Min(value = 0, message = "201")
        @Max(value = 5, message = "202")
        double note,

        @NotNull(message = "203")
        @NotEmpty(message = "203")
        @NotBlank(message = "203")
        String comment,

        @NotNull(message = "204")
        Integer bookId
) {
}
