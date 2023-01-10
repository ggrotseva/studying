package com.example.advquerying.service;


import com.example.advquerying.entities.Ingredient;

import java.math.BigDecimal;
import java.util.List;

public interface IngredientService {

    List<Ingredient> selectByNameStartingWith(String name);

    List<Ingredient> findByNames(List<String> names);

    void deleteByName(String name);

    void updatePrice();
}
