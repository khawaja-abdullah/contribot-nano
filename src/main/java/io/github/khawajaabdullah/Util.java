package io.github.khawajaabdullah;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public final class Util {

  private Util() {
  }

  public static List<GithubIssue> mapToGithubIssues(String jsonString) {
    var matcher = Constant.GITHUB_ISSUE_FILTERED_RESPONSE_PATTERN.matcher(jsonString);
    List<GithubIssue> githubIssues = new ArrayList<>();
    while (matcher.find()) {
      var htmlUrl = matcher.group(1);
      var title = matcher.group(2);
      githubIssues.add(new GithubIssue(htmlUrl, title));
    }
    return githubIssues;
  }

  public static String generateGithubIssueReport(List<GithubIssue> githubIssues) {
    if (githubIssues == null || githubIssues.isEmpty()) {
      return Constant.GITHUB_ISSUE_REPORT_HTML_TEMPLATE.formatted(Constant.GITHUB_ISSUE_EMPTY_ROW_TEMPLATE);
    }
    var githubIssueReportTableRows = new StringBuilder();
    for (var githubIssue : githubIssues) {
      githubIssueReportTableRows.append(Constant.GITHUB_ISSUE_ROW_TEMPLATE.formatted(githubIssue.title(), githubIssue.htmlUrl()));
    }
    return Constant.GITHUB_ISSUE_REPORT_HTML_TEMPLATE.formatted(githubIssueReportTableRows.toString());
  }

  public static void writeToFile(String outputPathString, String content) {
    var outputPath = Path.of(outputPathString);
    var absoluteOutputPath = outputPath.toAbsolutePath();
    try {
      var parentDir = absoluteOutputPath.getParent();
      if (parentDir != null) {
        Files.createDirectories(parentDir);
      }
      Files.writeString(outputPath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      throw new ContribotNanoException("Failed to write to path: %s".formatted(absoluteOutputPath), e);
    }
  }

}
