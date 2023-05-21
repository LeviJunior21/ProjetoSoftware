package com.ufcg.psoft.mercadofacil.dto.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.valid.CodigoAcesso;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientePostPutRequestDTO {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nomeCompleto")
    @NotBlank(message = "Nome vazio invalido")
    private String nomeCompleto;

    @JsonProperty("enderecoPrincipal")
    @NotBlank(message = "Endereco vazio invalido")
    private String enderecoPrincipal;

    @JsonProperty("codigoAcesso")
    @CodigoAcesso
    private Integer codigoAcesso;
}
