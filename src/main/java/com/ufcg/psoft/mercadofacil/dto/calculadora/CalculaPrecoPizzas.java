package com.ufcg.psoft.mercadofacil.dto.calculadora;

import com.ufcg.psoft.mercadofacil.dto.valid.pizza.TamanhoPizza;
import com.ufcg.psoft.mercadofacil.model.Pizza;
import com.ufcg.psoft.mercadofacil.model.Sabor;

import java.util.Set;

public class CalculaPrecoPizzas {
    public double calculaPrecoPizzas(Set<Pizza> pizzas) {
        double precoTotal = 0;
        for (Pizza pizza : pizzas) {
            double precoPizza = 0;

            if (pizza.getTamanho().equals("MEDIA")) {

                if (pizza.getSabor().size() != 1) {
                    throw new IllegalArgumentException("Uma pizza media deve ter um sabor");
                }
                precoPizza = pizza.getSabor().iterator().next().getPreco();
            } else if (pizza.getTamanho().equals("GRANDE")) {

                if (pizza.getSabor().size() < 1 || pizza.getSabor().size() > 2) {
                    throw new IllegalArgumentException("Uma pizza grande deve ter entre um ou dois sabores");
                }
                double somaPrecosSabores = 0;
                for (Sabor sabor : pizza.getSabor()) {
                    somaPrecosSabores += sabor.getPreco();
                }
                precoPizza = somaPrecosSabores / pizza.getSabor().size();
            }

            precoTotal += precoPizza;
        }
        return precoTotal;
    }
}
