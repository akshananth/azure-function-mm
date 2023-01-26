package com.ej.mm;

import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


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
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
       String clientInfo = request.getBody().orElse(null);

        ObjectMapper objectMapper = new ObjectMapper();

        if (clientInfo == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass client details in the request body").build();
        } else {
            Customer newClient = objectMapper.readValue(clientInfo, Customer.class);
            context.getLogger().info("new client added");

           // Form form = new Form();
            //form.add("id", "1");
           // form.add("name", "supercobra");
           // String clientAsString = objectMapper.writeValueAsString(newClient);
            Client client = ClientBuilder.newClient();
            WebTarget resource = client.target("http://localhost:8080/LoginApp/Login.jsp");
            Response response = resource.request().post(Entity.text(clientInfo), Response.class);

            context.getLogger().info("Received Message From my-test-queue : " + response.getStatusInfo());

            //return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + newClient).build();
            return request.createResponseBuilder(HttpStatus.OK).body("Hello, " + newClient.getFirstName()+" "+newClient.getLastName()).build();
           // return request.createResponseBuilder(HttpStatus.OK).body("Hello, ").build();
        }
    }


}

