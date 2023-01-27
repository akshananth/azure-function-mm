package com.ej.mm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;
import java.util.logging.Logger;


/**
 * Azure Functions with HTTP Trigger.
 */
public class OnboardClient {
    /**
     * This function listens at endpoint "/api/OnboardClient". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/OnboardClient
     * 2. curl {your host}/api/OnboardClient?name=HTTP%20Query
     */
    @FunctionName("OnboardClient")
    //@QueueOutput(name = "client", queueName = "clientlist", connection = "AzureWebJobsStorage")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws JsonProcessingException {
        Logger logger = context.getLogger();
        logger.info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String clientInfo = request.getBody().orElse(null);

        ObjectMapper objectMapper = new ObjectMapper();

        if (clientInfo == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass client details in the request body").build();
        } else {
            Customer newClient = objectMapper.readValue(clientInfo, Customer.class);
            logger.info("New client onboard request received");

            try (Client client = ClientBuilder.newClient()) {

                WebTarget resource = client.target(System.getenv("OnboardingServiceURL"));

                logger.info("New client onboard request sent to java aks");
                Response response = resource.request().post(Entity.entity(newClient, MediaType.APPLICATION_JSON), Response.class);
                logger.info("New client onboard request sent to java aks response status ::" + response.getStatusInfo());
                if(response.getStatusInfo().equals(Response.Status.OK)) {
                   String onBoardId = response.readEntity(String.class);
                   return request.createResponseBuilder(HttpStatus.OK).body("Client Onboarded with client id:: " + onBoardId).build();
                  //  String onBoardId = response.readEntity(String.class);
                  //  return request.createResponseBuilder(HttpStatus.OK).body("Client Onboarded with client id:: " + objectMapper.readValue(onBoardId, Customer.class).getNewClientId()).build();

                }
                response.close();
            }

            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Error while onboarding").build();

        }
    }


}

