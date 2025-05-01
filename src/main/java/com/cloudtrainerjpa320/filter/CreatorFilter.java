package com.cloudtrainerjpa320.filter;

import com.cloudtrainerjpa320.model.Creator;
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
public class CreatorFilter implements Specification<Creator> {

    private String login;
    private String firstname;
    private String lastname;

    @Override
    public Predicate toPredicate(Root<Creator> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (login != null && !login.isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("login")),
                    "%" + login.toLowerCase() + "%")
            );
        }

        if (firstname != null && !firstname.isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("firstname")),
                    "%" + firstname.toLowerCase() + "%")
            );
        }

        if (lastname != null && !lastname.isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("lastname")),
                    "%" + lastname.toLowerCase() + "%")
            );
        }

        return predicates.isEmpty() ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}