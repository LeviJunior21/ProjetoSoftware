package com.ufcg.psoft.mercadofacil.dto.valid.estabelecimento;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CodigoAcessoValidator implements ConstraintValidator<CodigoAcesso, Long> {

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        String stringValue = String.valueOf(value);
        return stringValue.length() == 6;
    }
}
