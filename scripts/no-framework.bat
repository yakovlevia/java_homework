mvn clean package
java --module-path client/target/client-1.0-SNAPSHOT.jar;api/target/api-1.0-SNAPSHOT.jar; --module ru.hse.scheduled.client/ru.hse.scheduled.client.Application
