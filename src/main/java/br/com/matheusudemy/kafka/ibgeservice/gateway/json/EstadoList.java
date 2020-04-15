package br.com.matheusudemy.kafka.ibgeservice.gateway.json;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoList {

    private List<EstadoJSON> list;
}
