package messaging;

import com.azure.messaging.servicebus.*;
import com.azure.messaging.servicebus.models.*;
import models.ServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import service.MessagingController;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class MessagingUtils {

   private static final Logger LOGGER= LoggerFactory.getLogger(MessagingUtils.class);

   static String connectionString = "Endpoint=sb://umbcssbusns.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=saoLSDQHoRDECaiGC8kKXw8XE/V7QdwG2rtGqRnOM7M=";
   static String queueName = "notificationqueue";

   public static ServiceBusMessage createMessage(ServiceRequest req) {
      return new ServiceBusMessage("Msg UUID: " + req.getUuid() + " Msg type: " + req.getMessageType()
         + " Msg body: " + req.getMessageBody());
   }

   public static void sendMessage(ServiceBusMessage sbm)
   {
      // create a Service Bus Sender client for the queue
      ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
              .connectionString(connectionString)
              .sender()
              .queueName(queueName)
              .buildClient();

      // send one message to the queue
      senderClient.sendMessage(sbm);
      LOGGER.info("Sent a single message to the queue: " + queueName);
   }

   public static void receiveMessages() throws InterruptedException
   {
      // consumer that processes a single message received from Service Bus
      Consumer<ServiceBusReceivedMessageContext> messageProcessor = context -> {
         ServiceBusReceivedMessage message = context.getMessage();
         LOGGER.info("Received message: " + message.getBody().toString());
      };

      // handles any errors that occur when receiving messages
      Consumer<Throwable> errorHandler = throwable -> {
         System.out.println("Error when receiving messages: " + throwable.getMessage());
//         if (throwable instanceof ServiceBusReceiverException) {
//            ServiceBusReceiverException serviceBusReceiverException = (ServiceBusReceiverException) throwable;
//            System.out.println("Error source: " + serviceBusReceiverException.getErrorSource());
//         }
//         Exception e = (Exception) throwable;
//         System.out.println(e.getMessage());
      };

      // create an instance of the processor through the ServiceBusClientBuilder
      ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
              .connectionString(connectionString)
              .processor()
              .queueName(queueName)
              .processMessage(messageProcessor)
              //.processError(errorHandler)
              .buildProcessorClient();

      LOGGER.info("Starting the processor");
      processorClient.start();

      TimeUnit.SECONDS.sleep(10);
      LOGGER.info("Stopping and closing the processor");
      processorClient.close();
   }

}
