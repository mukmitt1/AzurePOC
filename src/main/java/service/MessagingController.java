package service;

import models.ServiceRequest;
import models.ServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Provider;

@RestController
public class MessagingController {
    //TODO logging goes here

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

        ServiceResponse response = new ServiceResponse();
        response.setUuid(reqUuid);
        response.setAdditionalStatusCode("200");
        response.setAdditionalStatusMsg("POST request to /msgWebhook recieved! Webhook request UUID: " + reqUuid + " msgType: " + reqMsgType + " msgBody: " + reqMsgBody);

        return response;
    }
}
