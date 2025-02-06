package com.social.book_network.feedback;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackResponse {

    private double note;
    private String comment;
    private boolean ownFeedback;
}
