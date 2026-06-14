package io.github.khawajaabdullah;

import java.io.ByteArrayInputStream;
import java.util.Properties;

public class ApplicationConfiguration {

  private final Properties properties;

  public ApplicationConfiguration() {
    properties = new Properties();
    loadDefaultProperties();
  }

  private void loadDefaultProperties() {
    try (var inputStream = new ByteArrayInputStream(Util.loadResourceAsBytes(Constant.APPLICATION_DEFAULT_PROPERTIES_FILE_PATH))) {
      properties.load(inputStream);
    } catch (Exception e) {
      throw new ContribotNanoException("Failed to load default properties: %s".formatted(e.getMessage()), e);
    }
  }

  public String getValue(String key) {
    var envKey = key.toUpperCase().replace(".", "_");
    var envValue = System.getenv(envKey);
    return envValue != null && !envValue.isBlank() ? envValue : properties.getProperty(key);
  }

}
