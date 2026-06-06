package io.github.khawajaabdullah;

import java.net.http.HttpClient;

public class ContribotNanoApplication {

  private static final System.Logger LOGGER = System.getLogger(ContribotNanoApplication.class.getName());

  public static void main(String[] args) {
    try (HttpClient httpClient = HttpClient.newHttpClient()) {
      ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
      GithubFacade githubFacade = new GithubFacade(httpClient, applicationConfiguration);
      GithubIssueSearchQuery githubIssueSearchQuery = GithubIssueSearchQuery.builder()
          .withLanguage(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_SEARCH_QUERY_LANGUAGE_KEY))
          .withLabel(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_SEARCH_QUERY_LABEL_KEY))
          .withState(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_SEARCH_QUERY_STATE_KEY))
          .withCreatedAfter(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_SEARCH_QUERY_CREATED_AFTER_KEY))
          .withUnassigned(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_SEARCH_QUERY_UNASSIGNED_KEY))
          .build();
      String rawQuery = githubIssueSearchQuery.toString();
      LOGGER.log(System.Logger.Level.INFO, "Constructed GitHub issue search raw query: {0}", rawQuery);
      String issues = githubFacade.searchIssues(rawQuery);
      LOGGER.log(System.Logger.Level.INFO, "Fetched issues: {0}", issues);
      LOGGER.log(System.Logger.Level.INFO, "Contribot Nano application started successfully!");
    } catch (ContribotNanoException e) {
      LOGGER.log(System.Logger.Level.ERROR, "Failed to start Contribot Nano application: {0}", e.getMessage());
    }
  }

}
