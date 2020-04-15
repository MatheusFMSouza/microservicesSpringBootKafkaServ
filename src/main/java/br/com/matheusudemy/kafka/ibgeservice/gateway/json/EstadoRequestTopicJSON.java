package br.com.matheusudemy.kafka.ibgeservice.gateway.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EstadoRequestTopicJSON {

    private String ud;

}
