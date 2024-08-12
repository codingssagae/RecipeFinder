package csec.myrecipe.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import csec.myrecipe.domain.Ingredient;
import csec.myrecipe.domain.Member;
import csec.myrecipe.domain.Recipe;
import csec.myrecipe.dto.RecipeDTO;
import csec.myrecipe.repository.IngredientRepository;
import csec.myrecipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RestTemplate restTemplate;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    private final String apiUrl = "http://openapi.foodsafetykorea.go.kr/api/{keyId}/{serviceId}/json/{startIdx}/{endIdx}";

    public List<RecipeDTO> fetchRecipes(String keyId, String serviceId, String ingredient) {
        int startIdx = 1;
        int endIdx = 10;
        List<RecipeDTO> recipes = new ArrayList<>();

        String url = apiUrl.replace("{keyId}", keyId)
                .replace("{serviceId}", serviceId)
                .replace("{startIdx}", String.valueOf(startIdx))
                .replace("{endIdx}", String.valueOf(endIdx)) + "/RCP_PARTS_DTLS=" + ingredient;

        try{
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("response = " + response);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);
            JsonNode rows = root.path("COOKRCP01").path("row");
            if (rows.isArray()){
                for (JsonNode row : rows){
                    RecipeDTO recipeDTO = objectMapper.treeToValue(row, RecipeDTO.class);
                    recipes.add(recipeDTO);
                }
            }
        }catch (IOException e){
            e.printStackTrace();;
        }

        return recipes;
    }

    @Transactional
    public void saveRecipe(Member member, JsonNode recipeData){

        Recipe recipe = new Recipe();
        recipe.setName(recipeData.path("RCP_NM").asText());
        recipe.setInstructions(recipeData.path("MANUAL01").asText());
        recipe.setImageUrl(recipeData.path("ATT_FILE_NO_MAIN").asText());
        recipe.setMember(member);

        Recipe savedRecipe = recipeRepository.save(recipe);

        String[] ingredients = recipeData.path("RCP_PARTS_DTLS").asText().split(", ");
        for (String ingredientName : ingredients) {
            Ingredient ingredient = new Ingredient();
            ingredient.setName(ingredientName);
            ingredient.setRecipe(savedRecipe);
            ingredient.setMember(member);
            ingredientRepository.save(ingredient);
        }


    }


}
