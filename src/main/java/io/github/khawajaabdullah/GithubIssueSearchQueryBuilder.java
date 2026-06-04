package io.github.khawajaabdullah;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class GithubIssueSearchQueryBuilder {

  private String language;
  private String label;
  private String state;
  private String createdAfter;
  private boolean unassigned;

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

  public GithubIssueSearchQueryBuilder withUnassigned(boolean unassigned) {
    this.unassigned = unassigned;
    return this;
  }

  public GithubIssueSearchQueryBuilder withCreatedAfter(LocalDateTime createdAfter) {
    this.createdAfter = createdAfter.toInstant(ZoneOffset.UTC).toString();
    return this;
  }

  public static GithubIssueSearchQueryBuilder builder() {
    return new GithubIssueSearchQueryBuilder();
  }

  public String build() {
    StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("is:issue").append(" ")
        .append("language:").append(language).append(" ")
        .append("label:").append("\"").append(label).append("\"").append(" ")
        .append("state:").append(state).append(" ")
        .append("created:>=").append(createdAfter).append(" ");
    if (unassigned) {
      queryBuilder.append("no:assignee");
    }
    return queryBuilder.toString().trim();
  }

}
