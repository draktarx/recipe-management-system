package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@JsonPropertyOrder({"name",
                    "category",
                    "date",
                    "description",
                    "ingredients",
                    "directions"})
public class Recipe {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Description is mandatory")
    private String description;

    @NotBlank(message = "Category is mandatory")
    private String category;

    @ElementCollection
    @NotEmpty(message = "Ingredients list must contain at least one item")
    private List<String> ingredients;

    @ElementCollection
    @NotEmpty(message = "Directions list must contain at least one item")
    private List<String> directions;

    @UpdateTimestamp
    private LocalDateTime date;

    @JsonIgnore
    private String author;

    public Recipe(String name,
                  String description,
                  String category,
                  List<String> ingredients,
                  List<String> directions,
                  String author) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.ingredients = ingredients;
        this.directions = directions;
        this.author = author;
    }

}
