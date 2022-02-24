package au.com.asx.demo.controllers;

import au.com.dius.pact.consumer.Pact;
import au.com.dius.pact.consumer.PactProviderRuleMk2;
import au.com.dius.pact.consumer.PactVerification;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.model.RequestResponsePact;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class UserInfoPactTest {

    @Rule
    public PactProviderRuleMk2 mockProvider = new PactProviderRuleMk2("test_provider", "localhost", 8080, this);

    @Pact(consumer = "user_api_test")
    public RequestResponsePact createPact(PactDslWithProvider builder) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        return builder
                .given("test GET")
                .uponReceiving("GET REQUEST")
                .path("/pact")
                .method("GET")
                .willRespondWith()
                .status(200)
                .headers(headers)
                .body("{\n" +
                        "    \"title\": \"Mr\",\n" +
                        "    \"firstname\": \"Sean40\",\n" +
                        "    \"lastname\": \"Penn40\",\n" +
                        "    \"gender\": \"Male\",\n" +
                        "    \"address\": {\n" +
                        "        \"street\": \"40 George St\",\n" +
                        "        \"city\": \"Sydney\",\n" +
                        "        \"state\": \"NSW\",\n" +
                        "        \"postcode\": \"2000\"\n" +
                        "    }\n" +
                        "}")
                .given("test POST")
                .uponReceiving("POST REQUEST")
                .method("POST")
                .headers(headers)
                .body("{ \"firstname\": \"Michael\" }")
                .path("/pact")
                .willRespondWith()
                .body("{\n" +
                        "    \"title\": \"Mr\",\n" +
                        "    \"firstname\": \"Michael\",\n" +
                        "    \"lastname\": \"Penn40\",\n" +
                        "    \"gender\": \"Male\",\n" +
                        "    \"address\": {\n" +
                        "        \"street\": \"40 George St\",\n" +
                        "        \"city\": \"Sydney\",\n" +
                        "        \"state\": \"NSW\",\n" +
                        "        \"postcode\": \"2000\"\n" +
                        "    }\n" +
                        "}")
                .status(200)
                .toPact();
    }

    @Test
    @PactVerification()
    public void givenGetPost_whenSendRequest_shouldReturn200WithBody() {
        ResponseEntity<String> response = new RestTemplate().getForEntity(mockProvider.getUrl() + "/pact", String.class);

        Assertions.assertThat(response.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(response.getHeaders().get("Content-Type").contains("application/json")).isTrue();
        Assertions.assertThat(response.getBody()).contains("firstname", "Sean40");

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        String jsonBody = "{ \"firstname\": \"Michael\" }";

        ResponseEntity<String> postResponse = new RestTemplate().exchange(mockProvider.getUrl() + "/pact", HttpMethod.POST, new HttpEntity<>(jsonBody, httpHeaders), String.class);

        Assertions.assertThat(postResponse.getStatusCode().value()).isEqualTo(200);
        Assertions.assertThat(postResponse.getBody()).contains("firstname", "Michael");
    }
}
