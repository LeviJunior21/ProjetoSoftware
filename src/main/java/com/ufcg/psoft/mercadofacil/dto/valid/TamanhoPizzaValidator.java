package com.ufcg.psoft.mercadofacil.dto.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TamanhoPizzaValidator implements ConstraintValidator<TamanhoPizza, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value.equals("MEDIO") || value.equals("GRANDE")) {
            return true;
        }
        return false;
    }
}
