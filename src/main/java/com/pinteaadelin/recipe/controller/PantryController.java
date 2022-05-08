package com.pinteaadelin.recipe.controller;

import com.pinteaadelin.recipe.model.PantryIngredient;
import com.pinteaadelin.recipe.model.request.IngredientRequestDto;
import com.pinteaadelin.recipe.service.PantryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/pantry/ingredients")
public class PantryController {

    private final PantryService pantryService;

    public PantryController(PantryService pantryService) {
        this.pantryService = pantryService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PantryIngredient>> getAll() {
        return new ResponseEntity<>(pantryService.getAll(), OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PantryIngredient> add(@Valid @RequestBody IngredientRequestDto ingredient) {
        return new ResponseEntity<>(pantryService.add(ingredient), CREATED);
    }

    @PutMapping(value = "/{id}/updateQuantity/{quantity}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PantryIngredient> updateQuantity(@PathVariable("id") Integer id,
                                                           @PathVariable("quantity") Integer quantity) {
        return new ResponseEntity<>(pantryService.updateQuantity(id, quantity), OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        pantryService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }

}
