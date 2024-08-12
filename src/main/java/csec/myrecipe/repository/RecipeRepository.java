package csec.myrecipe.repository;

import csec.myrecipe.domain.Member;
import csec.myrecipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByMember(Member member);
}
