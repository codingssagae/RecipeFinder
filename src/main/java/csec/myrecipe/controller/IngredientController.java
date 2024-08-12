package csec.myrecipe.controller;

import csec.myrecipe.domain.Ingredient;
import csec.myrecipe.domain.Member;
import csec.myrecipe.domain.Recipe;
import csec.myrecipe.repository.IngredientRepository;
import csec.myrecipe.repository.MemberRepository;
import csec.myrecipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final MemberRepository memberRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    @GetMapping
    public String listIngredients(Model model) {
        Member member = memberRepository.findByUsername("testuser");
        List<Ingredient> ingredients = ingredientRepository.findByMember(member);
        model.addAttribute("ingredients", ingredients);
        return "ingredientList";
    }

    @GetMapping("/recipes")
    public String getRecipes(@RequestParam String ingredient, Model model) {
        Member member = memberRepository.findByUsername("testuser");
        List<Recipe> recipes = recipeRepository.findByMember(member);
        model.addAttribute("recipes", recipes);
        return "recipeResults";
    }

}
