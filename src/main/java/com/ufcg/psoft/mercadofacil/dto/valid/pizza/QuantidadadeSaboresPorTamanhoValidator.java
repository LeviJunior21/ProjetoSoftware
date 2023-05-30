package com.ufcg.psoft.mercadofacil.dto.valid.pizza;

import com.ufcg.psoft.mercadofacil.model.Pizza;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuantidadadeSaboresPorTamanhoValidator implements ConstraintValidator<QuantidadeDeSabores, Pizza> {

    @Override
    public boolean isValid(Pizza pizza, ConstraintValidatorContext context) {
        if(pizza == null || pizza.getTamanho() == null){
            return true;
        }
        int quantSabores = pizza.getSabor().size();
        if(pizza.getTamanho().equals("MEDIO") && quantSabores > 1){
            return false;
        }
        if(pizza.getTamanho().equals("GRANDE") && quantSabores > 2){
            return false;
        }
        return true;
    }
}

