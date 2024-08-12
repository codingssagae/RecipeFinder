package csec.myrecipe.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import csec.myrecipe.domain.Member;
import csec.myrecipe.dto.RecipeDTO;
import csec.myrecipe.repository.MemberRepository;
import csec.myrecipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    private final MemberRepository memberRepository;

    private final String keyId = "7818e60fe9724787a329";
    private final String serviceId = "COOKRCP01";

    @GetMapping("/")
    private String home(){
        return "search";
    }

    @GetMapping("/recipes/search")
    public String searchRecipes(@RequestParam String ingredient, Model model) {
        List<RecipeDTO> recipes = recipeService.fetchRecipes(keyId,serviceId,ingredient);
        model.addAttribute("recipes", recipes);
        return "recipeResults";
    }

    @PostMapping("/recipes/save")
    public String saveRecipe(@RequestParam String username, @RequestParam String recipeData) {
        Member member = memberRepository.findByUsername(username);
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode recipeJson = objectMapper.readTree(recipeData);
            recipeService.saveRecipe(member,recipeJson);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/ingredients";
    }
}
