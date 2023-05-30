package com.ufcg.psoft.mercadofacil.dto.valid.pizza;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {QuantidadadeSaboresPorTamanhoValidator.class})
@Order(10)
public @interface QuantidadeDeSabores {
    String message() default "A quantidade de sabores não é adimitida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
