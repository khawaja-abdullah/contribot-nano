package io.github.khawajaabdullah;

import java.net.http.HttpClient;

public class ContribotNanoApplication {

  private static final System.Logger LOGGER = System.getLogger(ContribotNanoApplication.class.getName());

  public static void main(String[] args) {
    try (var httpClient = HttpClient.newHttpClient()) {
      var applicationConfiguration = new ApplicationConfiguration();
      var githubFacade = new GithubFacade(httpClient, applicationConfiguration);
      var githubIssueSearchQuery = GithubIssueSearchQuery.builder()
          .withLanguage(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_SEARCH_QUERY_LANGUAGE_KEY))
          .withLabel(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_SEARCH_QUERY_LABEL_KEY))
          .withState(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_SEARCH_QUERY_STATE_KEY))
          .withCreatedAfter(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_SEARCH_QUERY_CREATED_AFTER_KEY))
          .withUnassigned(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_SEARCH_QUERY_UNASSIGNED_KEY))
          .build();
      var githubIssueSearchQueryString = githubIssueSearchQuery.toString();
      LOGGER.log(System.Logger.Level.INFO, "Constructed GitHub issue search query: {0}", githubIssueSearchQueryString);
      String githubIssuesJsonString = githubFacade.searchIssues(githubIssueSearchQueryString);
      LOGGER.log(System.Logger.Level.INFO, "Fetched GitHub issues JSON: {0}", githubIssuesJsonString);
      var githubIssues = Util.mapToGithubIssues(githubIssuesJsonString);
      LOGGER.log(System.Logger.Level.INFO, "Mapped GitHub issues JSON to List<GithubIssue>: {0}", githubIssues);
      var githubIssueReport = Util.generateGithubIssueReport(githubIssues);
      LOGGER.log(System.Logger.Level.INFO, "Generated GitHub issue report: {0}", githubIssueReport);
      Util.writeToFile(applicationConfiguration.getValue(Constant.GITHUB_ISSUE_REPORT_OUTPUT_PATH_KEY), githubIssueReport);
      LOGGER.log(System.Logger.Level.INFO, "GitHub issue report written to output path.");
    } catch (ContribotNanoException e) {
      LOGGER.log(System.Logger.Level.ERROR, "Failed to execute Contribot Nano application: {0}", e.getMessage());
      System.exit(1);
    }
  }

}
