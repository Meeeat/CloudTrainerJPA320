package com.cloudtrainerjpa320.filter;

import com.cloudtrainerjpa320.model.Notice;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeFilter implements Specification<Notice> {

    private Long tweetId;
    private String content;

    @Override
    public Predicate toPredicate(Root<Notice> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (tweetId != null) {
            predicates.add(criteriaBuilder.equal(root.get("tweet").get("id"), tweetId));
        }

        if (content != null && !content.isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("content")),
                    "%" + content.toLowerCase() + "%")
            );
        }

        return predicates.isEmpty() ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}