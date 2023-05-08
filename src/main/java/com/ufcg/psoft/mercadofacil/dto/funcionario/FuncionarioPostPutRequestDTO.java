package com.ufcg.psoft.mercadofacil.dto.funcionario;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.valid.estabelecimento.CodigoAcesso;
import com.ufcg.psoft.mercadofacil.model.Entregador;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioPostPutRequestDTO {

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

    @JsonProperty("entregando")
    private boolean entregando;

    @JsonProperty("entregador")
    private Entregador entregador;

    @JsonProperty("codigoAcesso")
    @CodigoAcesso
    private Integer codigoAcesso;
}
