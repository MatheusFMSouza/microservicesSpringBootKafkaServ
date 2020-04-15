package br.com.matheusudemy.kafka.ibgeservice.gateway.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RegiaoJSON {

    private Long id;
    private String sigla;
    private String nome;

}
