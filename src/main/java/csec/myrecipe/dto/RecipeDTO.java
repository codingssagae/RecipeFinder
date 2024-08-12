package csec.myrecipe.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecipeDTO {

    private String recipeId;
    private String recipeName;
    private String ingredients;
    private String imageUrl;

    public RecipeDTO() {
    }

    @JsonCreator
    public RecipeDTO(
            @JsonProperty("RCP_SEQ") String recipeId,
            @JsonProperty("RCP_NM") String recipeName,
            @JsonProperty("RCP_PARTS_DTLS") String ingredients,
            @JsonProperty("ATT_FILE_NO_MAIN") String imageUrl) {
        this.recipeId = recipeId;
        this.recipeName = recipeName;
        this.ingredients = ingredients;
        this.imageUrl = imageUrl;
    }
}
