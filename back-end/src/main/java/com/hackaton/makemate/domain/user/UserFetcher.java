package com.hackaton.makemate.domain.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import com.google.common.net.HttpHeaders;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFetcher {
  private final ExecutorService executorService =
      Executors.newSingleThreadExecutor(
          new ThreadFactoryBuilder().setNameFormat("UserFetcherThread-%s").build());

  private final HttpClient httpClient = HttpClient.newHttpClient();
  private final Logger logger = LoggerFactory.getLogger(UserFetcher.class);
  private final Map<String, String> AUTH_PARAMS = new HashMap<>();

  {
    AUTH_PARAMS.put("device_os", "android");
    AUTH_PARAMS.put("full_domain", "app.trainual-stg.com/qa-team");
    AUTH_PARAMS.put("email", "domingodnls@gmail.com");
    AUTH_PARAMS.put("password", "Qa1234567!");
  }
  ;

  public UserFetcher() {}

  public void fetchUsersAsync(Consumer<List<User>> usersConsumer) {
    executorService.submit(
        () -> {
          try {
            final String authToken = generateToken();

            if (authToken == null) return;

            HttpRequest request =
                HttpRequest.newBuilder()
                    // Ohhh, i hard codded query go cry!
                    .uri(URI.create("https://app.trainual-stg.com/qa-team/users?per=30"))
                    .headers(
                        HttpHeaders.CONTENT_TYPE,
                        "application/json",
                        HttpHeaders.AUTHORIZATION,
                        authToken)
                    .GET()
                    .build();

            List<User> rawUsers = new ArrayList<>();

            HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.body());
            Faker faker = new Faker();

            if (rootNode.isArray()) {
              for (JsonNode node : rootNode) {
                String name = node.get("name").asText();
                String avatar = node.get("avatar").asText();

                rawUsers.add(new User(null, name, "", faker.date().birthday(), "", avatar));
              }

              usersConsumer.accept(rawUsers);
            }
          } catch (Exception e) {
            // I AM SURE THAT'S NOT MY PROBLEM
            logger.warn("Failed to fetche ordes");
          }
        });
  }

  private String generateToken() {
    ObjectMapper mapper = new ObjectMapper();
    String requestBody = null;

    try {
      requestBody = mapper.writeValueAsString(AUTH_PARAMS);

      HttpRequest request =
          HttpRequest.newBuilder()
              .uri(URI.create("https://app.trainual-stg.com/qa-team/sessions"))
              .header("Content-Type", "application/json")
              .POST(BodyPublishers.ofString(requestBody))
              .build();

      HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
      JsonNode node = mapper.readTree(response.body());

      JsonNode token = node.get("token");
      JsonNode email = node.get("email");

      return String.format("Token token=%s, email=%s", token, email);
    } catch (Exception e) {
      logger.warn("Fetching failed ASK developers to fix this shit");
      return null;
    }
  }
}
