package com.example.advquerying;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.service.IngredientService;
import com.example.advquerying.service.ShampooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final ShampooService shampooService;
    private final IngredientService ingredientService;

    @Autowired
    public Main(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scan = new Scanner(System.in);

//        String name = scan.nextLine();
        this.ingredientService.updatePrice();

//        List<String> ingredients = new ArrayList<>();
//
//        while (!name.isBlank()) {
//            ingredients.add(name);
//            name = scan.nextLine();
//        }
//
//        for (Ingredient ingredient : this.ingredientService.findByNames(ingredients)) {
//            System.out.println(ingredient);
//        }
//        System.out.println(this.shampooService.countByPriceLowerThan(price));

//        for (Shampoo shampoo : this.shampooService.findByIngredientCountLessThan(count)) {
//            System.out.println(shampoo);
//        }

    }
}
