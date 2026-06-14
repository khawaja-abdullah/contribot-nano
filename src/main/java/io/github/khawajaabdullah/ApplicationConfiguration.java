package io.github.khawajaabdullah;

import java.io.ByteArrayInputStream;
import java.util.Properties;

public class ApplicationConfiguration {

  private final Properties properties;

  public ApplicationConfiguration() {
    properties = new Properties();
    loadProperties(Constant.APPLICATION_DEFAULT_PROPERTIES_FILE_PATH);
  }

  private void loadProperties(String path) {
    try (var inputStream = new ByteArrayInputStream(Util.loadResourceAsBytes(path))) {
      properties.load(inputStream);
    } catch (Exception e) {
      throw new ContribotNanoException("Failed to load properties path [%s]: %s".formatted(path, e.getMessage()), e);
    }
  }

  public String getValue(String key) {
    var envKey = key.toUpperCase().replace(".", "_");
    var envValue = System.getenv(envKey);
    return envValue != null && !envValue.isBlank() ? envValue : properties.getProperty(key);
  }

}
