package br.com.matheusudemy.kafka.ibgeservice.service.estado;

import br.com.matheusudemy.kafka.ibgeservice.gateway.json.EstadoList;
import br.com.matheusudemy.kafka.ibgeservice.gateway.json.EstadoRequestTopicJSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class ConsultarEstadoService {

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> kafkaTemplate;

    //Fila que vai ser enviado para o outro microservice
    @Value("${kafka.topic.request-topic}")
    private String requestTopic;

    //Fila que vai ser aguardado a resposta
    @Value("${kafka.topic.requestreply-topic}")
    private String requestReplyTopic;


    public EstadoList execute() throws JsonProcessingException, ExecutionException, InterruptedException {
        long tempoInicial = System.currentTimeMillis();

        //Convertendo OBJ para string
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(EstadoRequestTopicJSON.builder().build());

        //montando o producer que ira ser enviado para o kafka
        //Montando o envio da fila
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(requestTopic, jsonString);

        //Montando o aguardamento da fila
        record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));

        //enviando
        RequestReplyFuture<String, String,String> sendAndReceive = kafkaTemplate.sendAndReceive(record);

        //Recendo o retorno
        SendResult<String, String> sendResult = sendAndReceive.getSendFuture().get();
        sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() +":"+header.value().toString()));

        ConsumerRecord<String, String> consumerRecord = sendAndReceive.get();

        EstadoList listJsonReturn = mapper.readValue(consumerRecord.value(), EstadoList.class);

        System.out.printf("Retorno dos estados pelo KAFKA%.3f ms%n", (System.currentTimeMillis() - tempoInicial) / 1000d);

        return listJsonReturn;

    }
}
