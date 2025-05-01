package com.cloudtrainerjpa320.filter;

import com.cloudtrainerjpa320.model.Marker;
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
public class MarkerFilter implements Specification<Marker> {

    private String name;

    @Override
    public Predicate toPredicate(Root<Marker> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + name.toLowerCase() + "%")
            );
        }

        return predicates.isEmpty() ?
                criteriaBuilder.conjunction() :
                criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}