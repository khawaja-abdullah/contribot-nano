package io.github.khawajaabdullah;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public final class Util {

  private static final String GITHUB_ISSUE_REPORT_SKELETON_TEMPLATE;
  private static final String GITHUB_ISSUE_REPORT_ROW_TEMPLATE;
  private static final String GITHUB_ISSUE_REPORT_EMPTY_ROW_TEMPLATE;

  static {
    GITHUB_ISSUE_REPORT_SKELETON_TEMPLATE = new String(loadResourceAsBytes(Constant.GITHUB_ISSUE_REPORT_SKELETON_TEMPLATE_PATH), StandardCharsets.UTF_8);
    GITHUB_ISSUE_REPORT_ROW_TEMPLATE = new String(loadResourceAsBytes(Constant.GITHUB_ISSUE_REPORT_ROW_TEMPLATE_PATH), StandardCharsets.UTF_8);
    GITHUB_ISSUE_REPORT_EMPTY_ROW_TEMPLATE = new String(loadResourceAsBytes(Constant.GITHUB_ISSUE_REPORT_EMPTY_ROW_TEMPLATE_PATH), StandardCharsets.UTF_8);
  }

  private Util() {
  }

  public static String getTimeBeforeHours(long hours) {
    return LocalDateTime.now(ZoneOffset.UTC).minusHours(hours).toString();
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
      return GITHUB_ISSUE_REPORT_SKELETON_TEMPLATE.replace(
          Constant.GITHUB_ISSUE_REPORT_SKELETON_TEMPLATE_ISSUE_PLACEHOLDER,
          GITHUB_ISSUE_REPORT_EMPTY_ROW_TEMPLATE
      );
    }
    var githubIssueReportTableRows = new StringBuilder();
    for (var githubIssue : githubIssues) {
      var githubIssueReportTableRow = GITHUB_ISSUE_REPORT_ROW_TEMPLATE
          .replace(Constant.GITHUB_ISSUE_REPORT_ROW_TEMPLATE_TITLE_PLACEHOLDER, githubIssue.title())
          .replace(Constant.GITHUB_ISSUE_REPORT_ROW_TEMPLATE_URL_PLACEHOLDER, githubIssue.htmlUrl());
      githubIssueReportTableRows.append(githubIssueReportTableRow);
    }
    return GITHUB_ISSUE_REPORT_SKELETON_TEMPLATE.replace(
        Constant.GITHUB_ISSUE_REPORT_SKELETON_TEMPLATE_ISSUE_PLACEHOLDER,
        githubIssueReportTableRows.toString()
    );
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
