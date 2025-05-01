package com.cloudtrainerjpa320.filter;

import com.cloudtrainerjpa320.model.Tweet;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TweetFilter implements Specification<Tweet> {

    private Long creatorId;
    private String title;
    private String content;
    private LocalDateTime createdFrom;
    private LocalDateTime createdTo;

    @Override
    public Predicate toPredicate(Root<Tweet> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (creatorId != null) {
            predicates.add(criteriaBuilder.equal(root.get("creator").get("id"), creatorId));
        }

        if (title != null && !title.isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%")
            );
        }

        if (content != null && !content.isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("content")),
                    "%" + content.toLowerCase() + "%")
            );
        }

        if (createdFrom != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("created"), createdFrom)
            );
        }

        if (createdTo != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("created"), createdTo)
            );
        }

        return predicates.isEmpty() ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}