package io.github.khawajaabdullah;

public class GithubIssueSearchQuery {

  private final String language;
  private final String label;
  private final String state;
  private final String createdAfter;
  private final String unassigned;

  private GithubIssueSearchQuery(GithubIssueSearchQueryBuilder githubIssueSearchQueryBuilder) {
    this.language = githubIssueSearchQueryBuilder.language;
    this.label = githubIssueSearchQueryBuilder.label;
    this.state = githubIssueSearchQueryBuilder.state;
    this.createdAfter = githubIssueSearchQueryBuilder.createdAfter;
    this.unassigned = githubIssueSearchQueryBuilder.unassigned;
  }

  public static GithubIssueSearchQueryBuilder builder() {
    return new GithubIssueSearchQueryBuilder();
  }

  public String toString() {
    var githubIssueSearchQueryStringBuilder = new StringBuilder();
    githubIssueSearchQueryStringBuilder.append("is:issue").append(" ");
    if (language != null) {
      githubIssueSearchQueryStringBuilder.append("language:").append(language).append(" ");
    }
    if (label != null) {
      githubIssueSearchQueryStringBuilder.append("label:").append("\"").append(label).append("\"").append(" ");
    }
    if (state != null) {
      githubIssueSearchQueryStringBuilder.append("state:").append(state).append(" ");
    }
    if (createdAfter != null) {
      githubIssueSearchQueryStringBuilder.append("created:>=").append(createdAfter).append(" ");
    }
    if ("true".equalsIgnoreCase(unassigned)) {
      githubIssueSearchQueryStringBuilder.append("no:assignee").append(" ");
    }
    return githubIssueSearchQueryStringBuilder.toString().trim();
  }

  public static class GithubIssueSearchQueryBuilder {

    private String language;
    private String label;
    private String state;
    private String createdAfter;
    private String unassigned;

    private GithubIssueSearchQueryBuilder() {
    }

    public GithubIssueSearchQueryBuilder withLanguage(String language) {
      this.language = language;
      return this;
    }

    public GithubIssueSearchQueryBuilder withLabel(String label) {
      this.label = label;
      return this;
    }

    public GithubIssueSearchQueryBuilder withState(String state) {
      this.state = state;
      return this;
    }

    public GithubIssueSearchQueryBuilder withUnassigned(String unassigned) {
      this.unassigned = unassigned;
      return this;
    }

    public GithubIssueSearchQueryBuilder withCreatedAfter(String createdAfter) {
      this.createdAfter = createdAfter;
      return this;
    }

    public GithubIssueSearchQuery build() {
      return new GithubIssueSearchQuery(this);
    }

  }

}
