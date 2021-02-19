package service;

import com.azure.messaging.servicebus.ServiceBusMessage;
import config.Config;
import messaging.MessagingUtils;
import models.ServiceRequest;
import models.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class MessagingController {

    private static final Logger LOGGER= LoggerFactory.getLogger(MessagingController.class);

    @GetMapping("/exampleGet")
    public ServiceResponse exampleGet() {

        LOGGER.info("Received GET request to /exampleGet");

        ServiceResponse response = new ServiceResponse();

        response.setUuid(1L);
        response.setAdditionalStatusCode("200");
        response.setAdditionalStatusMsg("Successful hit to the /exampleGet endpoint.");

        return response;
    }

    @PostMapping("/msgWebhook")
    public ServiceResponse processWebhookRequest(@RequestBody ServiceRequest requestBody) {

        LOGGER.info("Received POST request to /msgWebhook");
        LOGGER.info("Request body contents: " + requestBody.toString());

        //TODO log these values
        long reqUuid = requestBody.getUuid();
        String reqMsgType = requestBody.getMessageType();
        String reqMsgBody = requestBody.getMessageBody();

        if (reqMsgType.equals("QUEUE")) {
            LOGGER.info("Creating ServiceBusMessage with uuid: " + reqUuid);
            ServiceBusMessage sbm = MessagingUtils.createMessage(requestBody);
            LOGGER.info("Sending service bus message with uuid: " + reqUuid);
            MessagingUtils.sendMessage(sbm);
        }

        ServiceResponse response = new ServiceResponse();
        response.setUuid(reqUuid);
        response.setAdditionalStatusCode("200");
        response.setAdditionalStatusMsg("POST request to /msgWebhook recieved! Webhook request UUID: " + reqUuid + " msgType: " + reqMsgType + " msgBody: " + reqMsgBody);

        return response;
    }

    @GetMapping("/readQueue")
    public ServiceResponse readQueue() {
        List<String> messageBodies = new ArrayList<>();
        try {
            messageBodies = MessagingUtils.receiveMessages();
        } catch (InterruptedException e) {
            LOGGER.error(e.getMessage());
        }

        ServiceResponse response = new ServiceResponse();

        response.setUuid(new Random().nextLong());
        response.setAdditionalStatusCode("200");
        response.setAdditionalStatusMsg("Messages: " + messageBodies.toString());

        return response;
    }

    @GetMapping("/saveSecret")
    @ResponseStatus(HttpStatus.OK)
    public String saveSecret(@RequestParam String key, @RequestParam String value) {
        LOGGER.info("Recieved secret key/value! key: " + key + " value: " + value);

        Config config = new Config();
        config.setSecretKeyValue(key, value);

        LOGGER.info("Retrieving secret with key " + key + " from the Azure Key Vault...");
        LOGGER.info("value: " + config.getSecret(key));

        return "Secret saved";
    }

    @GetMapping("/getEnvVar")
    @ResponseStatus(HttpStatus.OK)
    public String readEnvVar() {
        LOGGER.info("Received GET to /readEnvVar");

        String envvar = System.getenv("EXAMPLE_ENV_VAR");

        if (envvar != null) {
            return envvar;
        }

        
        return "Env var not found";
    }
}
