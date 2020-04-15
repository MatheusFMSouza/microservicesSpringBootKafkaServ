package br.com.matheusudemy.kafka.ibgeservice.gateway.resource;

import br.com.matheusudemy.kafka.ibgeservice.gateway.json.EstadoList;
import br.com.matheusudemy.kafka.ibgeservice.service.estado.ConsultarEstadoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    private ConsultarEstadoService consultarEstadoService;


    @GetMapping("/")
    public EstadoList consultarEstados() throws InterruptedException, ExecutionException, JsonProcessingException {
        return consultarEstadoService.execute();
    }


}
