package com.ej.mm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;

import java.util.Optional;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Azure Functions with HTTP Trigger.
 */
public class PersistToDbController {
    /**
     * This function listens at endpoint "/api/SendClientInfo". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/SendClientInfo
     * 2. curl {your host}/api/SendClientInfo?name=HTTP%20Query
     */
    @FunctionName("PersistToDbController")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.POST}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) throws JsonProcessingException {
        Logger logger = context.getLogger();

        ObjectMapper objectMapper = new ObjectMapper();

        String clientInfo = request.getBody().orElse(null);
        Customer newClient = objectMapper.readValue(clientInfo, Customer.class);
        logger.info("PersistToDbController :: Received request to onboard client from Azure function");

        if (clientInfo == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            logger.info("PersistToDbController :: Oboard successful.");
            newClient.setNewClientId(new Random().nextLong());
            return request.createResponseBuilder(HttpStatus.OK).body(newClient.getNewClientId()).build();
        }
    }
}
