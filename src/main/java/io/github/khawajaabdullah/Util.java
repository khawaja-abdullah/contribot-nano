package io.github.khawajaabdullah;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public final class Util {

  private static final String GITHUB_ISSUE_REPORT_SKELETON;
  private static final String GITHUB_ISSUE_REPORT_ROW_TEMPLATE;
  private static final String GITHUB_ISSUE_REPORT_EMPTY_ROW_TEMPLATE;

  static {
    GITHUB_ISSUE_REPORT_SKELETON = new String(loadResourceAsBytes("/templates/github_issue_report_skeleton.html"), StandardCharsets.UTF_8);
    GITHUB_ISSUE_REPORT_ROW_TEMPLATE = new String(loadResourceAsBytes("/templates/github_issue_report_row_template.html"), StandardCharsets.UTF_8);
    GITHUB_ISSUE_REPORT_EMPTY_ROW_TEMPLATE = new String(loadResourceAsBytes("/templates/github_issue_report_empty_row_template.html"), StandardCharsets.UTF_8);
  }

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
      return GITHUB_ISSUE_REPORT_SKELETON.replace("{{ISSUES}}", GITHUB_ISSUE_REPORT_EMPTY_ROW_TEMPLATE);
    }
    var githubIssueReportTableRows = new StringBuilder();
    for (var githubIssue : githubIssues) {
      var githubIssueReportTableRow = GITHUB_ISSUE_REPORT_ROW_TEMPLATE
          .replace("{{TITLE}}", githubIssue.title())
          .replace("{{URL}}", githubIssue.htmlUrl());
      githubIssueReportTableRows.append(githubIssueReportTableRow);
    }
    return GITHUB_ISSUE_REPORT_SKELETON.replace("{{ISSUES}}", githubIssueReportTableRows.toString());
  }

  public static byte[] loadResourceAsBytes(String path) {
    try (var inputStream = Util.class.getResourceAsStream(path)) {
      if (inputStream != null) {
        return inputStream.readAllBytes();
      } else {
        throw new ContribotNanoException("%s file not found in classpath".formatted(path));
      }
    } catch (IOException e) {
      throw new ContribotNanoException("Failed to load resource file: %s".formatted(path), e);
    }
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
