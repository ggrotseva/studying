package com.example.advquerying.service;

import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    @Autowired
    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findByBrand(String brand) {
        return this.shampooRepository.findByBrand(brand);
    }

    @Override
    public List<Shampoo> findByBrandAndSize(String brand, String size) {
        Size parsed = Size.valueOf(size.toUpperCase());

        return this.shampooRepository.findByBrandAndSize(brand, parsed);
    }

    @Override
    public List<Shampoo> findBySizeOrderById(String size) {
        Size parsed = Size.valueOf(size.toUpperCase());

        return this.shampooRepository.findBySizeOrderById(parsed);
    }

    @Override
    public List<Shampoo> findByIngredients(List<String> ingredients) {
        return this.shampooRepository.findByIngredients(ingredients);
    }

    @Override
    public List<Shampoo> findBySizeOrLabelId(String size, long labelId) {
        Size parsed = Size.valueOf(size.toUpperCase());

        return this.shampooRepository.findBySizeOrLabelId(parsed, labelId);
    }


    @Override
    public List<Shampoo> findByPriceGreaterThanOrderByPriceDesc(String price) {
        BigDecimal parsed = new BigDecimal(price);

        return this.shampooRepository.findByPriceGreaterThanOrderByPriceDesc(parsed);
    }


    @Override
    public long countByPriceLowerThan(String price) {
        BigDecimal parsed = new BigDecimal(price);

        return this.shampooRepository.countByPriceLessThan(parsed);
    }

    @Override
    public List<Shampoo> findByIngredientCountLessThan(String count) {
        int parsed = Integer.parseInt(count);

        return this.shampooRepository.findByIngredientCountLessThan(parsed);
    }

}
