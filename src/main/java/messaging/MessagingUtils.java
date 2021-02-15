package messaging;

import com.azure.messaging.servicebus.*;
import models.ServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class MessagingUtils {

   private static final Logger LOGGER= LoggerFactory.getLogger(MessagingUtils.class);

   static List<String> messages;

   static String connectionString = "Endpoint=sb://umbcssbusns.servicebus.windows.net/;SharedAccessKeyName=RootManageSharedAccessKey;SharedAccessKey=saoLSDQHoRDECaiGC8kKXw8XE/V7QdwG2rtGqRnOM7M=";
   static String queueName = "notificationqueue";

   public static ServiceBusMessage createMessage(ServiceRequest req) {
//      return new ServiceBusMessage("Msg UUID: " + req.getUuid() + " Msg type: " + req.getMessageType()
//         + " Msg body: " + req.getMessageBody());
      return new ServiceBusMessage("Message contents: " + req.toString());
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

   public static List<String> receiveMessages() throws InterruptedException
   {

      CountDownLatch countdownLatch = new CountDownLatch(1);

      messages = new ArrayList<String>();

      // consumer that processes a single message received from Service Bus
      Consumer<ServiceBusReceivedMessageContext> messageProcessor = context -> {
         ServiceBusReceivedMessage message = context.getMessage();
         LOGGER.info("Received message: " + message.getBody().toString());
         messages.add(message.getBody().toString());
      };

      // create an instance of the processor through the ServiceBusClientBuilder
      ServiceBusProcessorClient processorClient = new ServiceBusClientBuilder()
              .connectionString(connectionString)
              .processor()
              .queueName(queueName)
              .processMessage(messageProcessor)
              .processError(context -> processError(context, countdownLatch))
              .buildProcessorClient();

      LOGGER.info("Starting the processor");
      processorClient.start();

      TimeUnit.SECONDS.sleep(10);
      LOGGER.info("Stopping and closing the processor");
      processorClient.close();

      return messages;
   }

   /**
    * Processes an exception that occurred in the Service Bus Processor.
    *
    * @param context Context around the exception that occurred.
    */
   private static void processError(ServiceBusErrorContext context, CountDownLatch countdownLatch) {
      System.out.printf("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
              context.getFullyQualifiedNamespace(), context.getEntityPath());

      if (!(context.getException() instanceof ServiceBusException)) {
         System.out.printf("Non-ServiceBusException occurred: %s%n", context.getException());
         return;
      }

      ServiceBusException exception = (ServiceBusException) context.getException();
      ServiceBusFailureReason reason = exception.getReason();

      if (reason == ServiceBusFailureReason.MESSAGING_ENTITY_DISABLED
              || reason == ServiceBusFailureReason.MESSAGING_ENTITY_NOT_FOUND
              || reason == ServiceBusFailureReason.UNAUTHORIZED) {
         System.out.printf("An unrecoverable error occurred. Stopping processing with reason %s: %s%n",
                 reason, exception.getMessage());

         countdownLatch.countDown();
      } else if (reason == ServiceBusFailureReason.MESSAGE_LOCK_LOST) {
         System.out.printf("Message lock lost for message: %s%n", context.getException());
      } else if (reason == ServiceBusFailureReason.SERVICE_BUSY) {
         try {
            // Choosing an arbitrary amount of time to wait until trying again.
            TimeUnit.SECONDS.sleep(1);
         } catch (InterruptedException e) {
            System.err.println("Unable to sleep for period of time");
         }
      } else {
         System.out.printf("Error source %s, reason %s, message: %s%n", context.getErrorSource(),
                 reason, context.getException());
      }
   }
}
