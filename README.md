## Contribot Nano

- [Contribot](https://github.com/khawaja-abdullah/contribot), but in core Java and zero external dependencies. 
- Looks up GitHub issues based on user defined filters and generates an HTML report containing a curated issues list. 
- GraalVM native image compatible.

### Build & Run (regular jar)
```bash
mvn clean package
java -jar target/contribot-nano-${version}.jar
```

### Build & Run (GraalVM native image)
```bash
mvn clean package -Pnative
./target/contribot-nano-native
```

### License
Licensed under the Apache License, Version 2.0. See [LICENSE](https://github.com/khawaja-abdullah/contribot-nano/blob/main/LICENSE) for details.