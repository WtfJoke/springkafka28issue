# Spring Kafka POC ContainerFactory


## Post a Movie (Default Settings: Schema Registry (Avro))
`curl -X POST localhost:8080/movie`

## Post a Show (Plain JSON)
`curl -X POST localhost:8080/show`

### Bug Info
Posting a show will result in an exception in Spring Boot 2.6.1 (Spring Kafka 2.8). 
Whereas posting a show in Spring Boot 2.5.7 (Spring Kafka 2.7.9) works as expected.