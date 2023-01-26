package com.ej.mm;

import java.util.*;
import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;

/**
 * Azure Functions with HTTP Trigger.
 */
public class GetClientInfo {
    /**
     * This function listens at endpoint "/api/SendClientInfo". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/SendClientInfo
     * 2. curl {your host}/api/SendClientInfo?name=HTTP%20Query
     */
    @FunctionName("GetClientInfo")
    public HttpResponseMessage run(
            @HttpTrigger(name = "req", methods = {HttpMethod.GET}, authLevel = AuthorizationLevel.ANONYMOUS) HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        String clientInfo = request.getQueryParameters().get("clientinfo");

        if (clientInfo == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a name on the query string or in the request body").build();
        } else {
            return request.createResponseBuilder(HttpStatus.OK).body("client details are " + clientInfo).build();
        }
    }
}
