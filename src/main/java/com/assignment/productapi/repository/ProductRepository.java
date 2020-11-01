package com.assignment.productapi.repository;

import com.assignment.productapi.model.Product;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Transactional
@Repository
public class ProductRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Product> findProducts(String type, String city, Integer minPrice, Integer maxPrice, String color, Integer gbLimitMin, Integer gbLimitMax) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        List<Predicate> predicates = new ArrayList<>(7);

        if (!StringUtils.isEmpty(type))
            predicates.add(cb.equal(product.get("type"), type));

        if (!StringUtils.isEmpty(city))
            predicates.add(cb.like(product.get("storeAddress"), "%" + city + "%"));

        if (minPrice != null)
            predicates.add(cb.ge(product.get("price"), minPrice));

        if (maxPrice != null)
            predicates.add(cb.le(product.get("price"), maxPrice));

        if (!StringUtils.isEmpty(color))
            predicates.add(cb.equal(product.get("color"), color));

        if (gbLimitMin != null)
            predicates.add(cb.ge(product.get("gbLimit"), gbLimitMin));

        if (gbLimitMax != null)
            predicates.add(cb.le(product.get("gbLimit"), gbLimitMax));

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    public Product save(Product product) {
        entityManager.persist(product);
        return product;
    }
}
