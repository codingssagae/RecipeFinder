package csec.myrecipe.repository;

import csec.myrecipe.domain.Ingredient;
import csec.myrecipe.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findByMember(Member member);
}
