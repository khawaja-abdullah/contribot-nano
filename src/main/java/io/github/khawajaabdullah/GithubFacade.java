package io.github.khawajaabdullah;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GithubFacade {

  private static final System.Logger LOGGER = System.getLogger(GithubFacade.class.getName());

  private final HttpClient httpClient;
  private final ApplicationConfiguration applicationConfiguration;

  public GithubFacade(HttpClient httpClient, ApplicationConfiguration applicationConfiguration) {
    this.httpClient = httpClient;
    this.applicationConfiguration = applicationConfiguration;
  }

  public String searchIssues(String query) {
    var url = "%s%s?q=%s&sort=created&order=desc&per_page=%s".formatted(
        applicationConfiguration.getValue(Constant.GITHUB_API_BASE_URL_KEY),
        applicationConfiguration.getValue(Constant.GITHUB_API_ISSUE_SEARCH_PATH_KEY),
        URLEncoder.encode(query, StandardCharsets.UTF_8),
        applicationConfiguration.getValue(Constant.GITHUB_API_ISSUE_SEARCH_PAGE_SIZE_KEY)
    );
    LOGGER.log(System.Logger.Level.INFO, "Constructed GitHub API URL: {0}", url);
    var httpRequest = HttpRequest.newBuilder()
        .header(Constant.ACCEPT_HEADER, Constant.GITHUB_MEDIA_TYPE)
        .header(Constant.AUTHORIZATION_HEADER, "Bearer %s".formatted(applicationConfiguration.getValue(Constant.GITHUB_API_TOKEN)))
        .uri(URI.create(url))
        .GET()
        .build();
    HttpResponse<String> httpResponse = RetryableExecutor.executeWithRetry(
        () -> httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()),
          response -> response.statusCode() == 429 || (response.statusCode() >= 500 && response.statusCode() < 600),
        Integer.parseInt(applicationConfiguration.getValue(Constant.GITHUB_API_ISSUE_SEARCH_MAX_RETRIES_KEY)),
        Long.parseLong(applicationConfiguration.getValue(Constant.GITHUB_API_ISSUE_SEARCH_RETRY_BASE_DELAY_MILLIS_KEY)),
        Long.parseLong(applicationConfiguration.getValue(Constant.GITHUB_API_ISSUE_SEARCH_RETRY_MAX_DELAY_MILLIS_KEY))
    );
    if (httpResponse.statusCode() != 200) {
      throw new ContribotNanoException("GitHub API returned non-OK status: %d, response body: %s".formatted(
          httpResponse.statusCode(), httpResponse.body()
      ));
    }
    return httpResponse.body();
  }

}
