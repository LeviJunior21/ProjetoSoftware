package com.ufcg.psoft.mercadofacil.dto.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.mercadofacil.dto.valid.estabelecimento.CodigoAcesso;
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

    @JsonProperty("nomeCompleto")
    @NotBlank(message = "O nome do cliente null é invalido")
    private String nomeCompleto;

    @JsonProperty("enderecoPrincipal")
    @NotBlank(message = "O endereco do cliente null é invalido")
    private String enderecoPrincipal;

    @JsonProperty("codigoAcesso")
    @NotBlank(message = "O codigo de acesso do cliente null eh invalido")
    @CodigoAcesso
    private Integer codigoAcesso;
}
