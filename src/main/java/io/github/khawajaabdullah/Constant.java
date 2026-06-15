package io.github.khawajaabdullah;

import java.util.regex.Pattern;

public final class Constant {

  public static final String APPLICATION_DEFAULT_PROPERTIES_FILE_PATH = "/application-default.properties";

  public static final String GITHUB_API_BASE_URL_KEY = "github.api.base.url";
  public static final String GITHUB_API_TOKEN = "github.api.token";
  public static final String GITHUB_API_ISSUE_SEARCH_PATH_KEY = "github.api.issue.search.path";
  public static final String GITHUB_API_ISSUE_SEARCH_PAGE_SIZE_KEY = "github.api.issue.search.page.size";
  public static final String GITHUB_API_ISSUE_SEARCH_MAX_RETRIES_KEY = "github.api.issue.search.max.retries";
  public static final String GITHUB_API_ISSUE_SEARCH_RETRY_BASE_DELAY_MILLIS_KEY = "github.api.issue.search.retry.base.delay.millis";
  public static final String GITHUB_API_ISSUE_SEARCH_RETRY_MAX_DELAY_MILLIS_KEY = "github.api.issue.search.retry.max.delay.millis";

  public static final String GITHUB_ISSUE_SEARCH_QUERY_LANGUAGE_KEY = "github.issue.search.query.language";
  public static final String GITHUB_ISSUE_SEARCH_QUERY_LABEL_KEY = "github.issue.search.query.label";
  public static final String GITHUB_ISSUE_SEARCH_QUERY_STATE_KEY = "github.issue.search.query.state";
  public static final String GITHUB_ISSUE_SEARCH_QUERY_CREATED_AFTER_KEY = "github.issue.search.query.createdAfter";
  public static final String GITHUB_ISSUE_SEARCH_QUERY_CREATED_AFTER_LOOK_BACK_ENABLED_KEY = "github.issue.search.query.createdAfter.lookBack.enabled";
  public static final String GITHUB_ISSUE_SEARCH_QUERY_CREATED_AFTER_LOOK_BACK_HOURS_KEY = "github.issue.search.query.createdAfter.lookBack.hours";
  public static final String GITHUB_ISSUE_SEARCH_QUERY_UNASSIGNED_KEY = "github.issue.search.query.unassigned";

  public static final String GITHUB_ISSUE_REPORT_OUTPUT_PATH_KEY = "github.issue.report.output.path";

  public static final String ACCEPT_HEADER = "Accept";
  public static final String AUTHORIZATION_HEADER = "Authorization";
  public static final String GITHUB_MEDIA_TYPE = "application/vnd.github+json";

  public static final String GITHUB_ISSUE_FILTERED_RESPONSE_REGEX = "\"html_url\"\\s*:\\s*\"(https://github\\.com/[^\"]+?/(?:issues|pull)/\\d+)\".*?\"title\"\\s*:\\s*\"([^\"]+?)\"";
  public static final Pattern GITHUB_ISSUE_FILTERED_RESPONSE_PATTERN = Pattern.compile(Constant.GITHUB_ISSUE_FILTERED_RESPONSE_REGEX);

  public static final String GITHUB_ISSUE_REPORT_SKELETON_TEMPLATE_PATH = "/templates/github_issue_report_skeleton.html";
  public static final String GITHUB_ISSUE_REPORT_ROW_TEMPLATE_PATH = "/templates/github_issue_report_row.html";
  public static final String GITHUB_ISSUE_REPORT_EMPTY_ROW_TEMPLATE_PATH = "/templates/github_issue_report_empty_row.html";
  public static final String GITHUB_ISSUE_REPORT_SKELETON_TEMPLATE_ISSUE_PLACEHOLDER = "{{ISSUES}}";
  public static final String GITHUB_ISSUE_REPORT_ROW_TEMPLATE_TITLE_PLACEHOLDER = "{{TITLE}}";
  public static final String GITHUB_ISSUE_REPORT_ROW_TEMPLATE_URL_PLACEHOLDER = "{{URL}}";

  private Constant() {
  }

}
