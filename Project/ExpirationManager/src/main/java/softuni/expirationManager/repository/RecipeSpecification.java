package softuni.expirationManager.repository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import softuni.expirationManager.model.dtos.recipe.RecipeSearchDTO;
import softuni.expirationManager.model.entities.RecipeEntity;
import softuni.expirationManager.model.enums.RecipeType;

public class RecipeSpecification implements Specification<RecipeEntity> {

    private final RecipeSearchDTO recipeSearchDTO;

    public RecipeSpecification(RecipeSearchDTO recipeSearchDTO) {
        this.recipeSearchDTO = recipeSearchDTO;
    }

    @Override
    public Specification<RecipeEntity> and(Specification<RecipeEntity> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<RecipeEntity> or(Specification<RecipeEntity> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<RecipeEntity> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder cb) {

        Specification<RecipeEntity> where = Specification.where(null);

        if (recipeSearchDTO.getSearch() != null && !recipeSearchDTO.getSearch().isEmpty()) {
            where = where.or(nameContains(recipeSearchDTO.getSearch()));
            where = where.or(ingredientsContain(recipeSearchDTO.getSearch()));
        }

        if (recipeSearchDTO.getTypes() != null && recipeSearchDTO.getTypes().size() == 1) {
            where = where.and(isOfType(recipeSearchDTO.getTypes().get(0)));
        }

        return where.toPredicate(root, query, cb);
    }

    private Specification<RecipeEntity> nameContains(String searchWord) {
        return (r, q, cb) -> cb.and(cb.like(r.get("name"), addWildcards(searchWord)));
    }

    private Specification<RecipeEntity> ingredientsContain(String searchWord) {
        return (r, q, cb) -> cb.and(cb.like(r.get("ingredientsDescription"), addWildcards(searchWord)));
    }

    private Specification<RecipeEntity> isOfType(RecipeType type) {
        return (r, q, cb) -> cb.and(cb.equal(r.get("type"), type));
    }

    private String addWildcards(String searchWord) {
        return '%' + searchWord + '%';
    }
}
