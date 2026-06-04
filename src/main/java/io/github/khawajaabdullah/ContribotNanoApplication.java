package io.github.khawajaabdullah;

import java.net.http.HttpClient;
import java.time.LocalDateTime;

public class ContribotNanoApplication {

  private static final System.Logger LOGGER = System.getLogger(ContribotNanoApplication.class.getName());

  public static void main(String[] args) {
    try(HttpClient httpClient = HttpClient.newHttpClient()) {
      ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
      GithubFacade githubFacade = new GithubFacade(httpClient, applicationConfiguration);
      String rawQuery = GithubIssueSearchQueryBuilder.builder()
          .withLanguage("Java")
          .withLabel("good first issue")
          .withState("open")
          .withCreatedAfter(LocalDateTime.now().minusHours(24))
          .withUnassigned(true)
          .build();
      LOGGER.log(System.Logger.Level.INFO, "Constructed GitHub issue search raw query: {0}", rawQuery);
      String issues = githubFacade.searchIssues(rawQuery);
      LOGGER.log(System.Logger.Level.INFO, "Fetched issues: {0}", issues);
      LOGGER.log(System.Logger.Level.INFO,"Contribot Nano application started successfully!");
    } catch (ContribotNanoException e) {
      LOGGER.log(System.Logger.Level.ERROR, "Failed to start Contribot Nano application: {0}", e.getMessage());
    }
  }

}
