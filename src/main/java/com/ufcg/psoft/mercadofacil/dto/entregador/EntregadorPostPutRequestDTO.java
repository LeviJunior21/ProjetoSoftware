package com.ufcg.psoft.mercadofacil.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class EntregadorPostPutRequestDTO {

    @JsonProperty("nome")
    @NotBlank(message = "Nome obrigatorio")
    private String nome;

    @JsonProperty("placa")
    @NotBlank(message = "Placa do carro obrigatorio")
    private String placa;

    @JsonProperty("veiculo")
    @NotBlank(message = "Veiculo obrigatoio")
    private String veiculo;

    @JsonProperty("cor")
    @NotBlank(message = "Cor do carro obrigatorio")
    private String cor;
}
