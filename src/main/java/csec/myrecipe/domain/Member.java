package csec.myrecipe.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Recipe> recipes;

}
