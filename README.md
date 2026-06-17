# Contribot Nano

- [Contribot](https://github.com/khawaja-abdullah/contribot), but in core Java and zero external dependencies. 
- GraalVM native image compatible.
- Looks up GitHub issues based on user defined filters and writes them to an HTML report. 

## Regular build

```bash
mvn clean package
java -jar target/contribot-nano-1.0-SNAPSHOT.jar
```

## Native build

```bash
mvn clean package -Pnative
./target/contribot-nano-native
```

## License

Licensed under the Apache License, Version 2.0. See [LICENSE](https://github.com/khawaja-abdullah/contribot-nano/blob/main/LICENSE) for details.