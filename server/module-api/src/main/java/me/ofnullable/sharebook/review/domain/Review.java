package me.ofnullable.sharebook.review.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ofnullable.sharebook.common.domain.Auditable;
import me.ofnullable.sharebook.common.domain.SimpleAccountInfo;
import me.ofnullable.sharebook.review.dto.UpdateReviewRequest;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends Auditable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bookId;

    @Embedded
    private SimpleAccountInfo reviewer;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private Integer score;

    @Builder
    public Review(Long bookId, SimpleAccountInfo reviewer, String contents, Integer score) {
        this.bookId = bookId;
        this.reviewer = reviewer;
        this.contents = contents;
        this.score = score;
    }

    public Review update(UpdateReviewRequest dto) {
        if (dto.getScore() != null) {
            this.score = dto.getScore();
        }
        if (StringUtils.hasText(dto.getContents())) {
            this.contents = dto.getContents();
        }
        return this;
    }

}
