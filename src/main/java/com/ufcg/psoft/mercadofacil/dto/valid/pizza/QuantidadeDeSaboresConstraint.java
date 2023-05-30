package com.ufcg.psoft.mercadofacil.dto.valid.pizza;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springframework.core.annotation.Order;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {QuantidadadeSaboresPorTamanhoValidator.class})
@Order(1)
public @interface QuantidadeDeSaboresConstraint {
    String message() default "Tamanho de pizza invalido";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
