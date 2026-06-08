package io.github.khawajaabdullah;

import java.util.Properties;

public class ApplicationConfiguration {

  private final Properties properties;

  public ApplicationConfiguration() {
    properties = new Properties();
    load(Constant.APPLICATION_DEFAULT_PROPERTIES_FILE_PATH);
  }

  private void load(String filePath) {
    try (var inputStream = getClass().getClassLoader().getResourceAsStream(filePath)) {
      if (inputStream != null) {
        properties.load(inputStream);
      } else {
        throw new ContribotNanoException("%s file not found in classpath".formatted(filePath));
      }
    } catch (Exception e) {
      throw new ContribotNanoException("Failed to load application configuration: %s".formatted(e.getMessage()), e);
    }
  }

  public String getValue(String key) {
    var envKey = key.toUpperCase().replace(".", "_");
    var envValue = System.getenv(envKey);
    return envValue != null && !envValue.isBlank() ? envValue : properties.getProperty(key);
  }

}
