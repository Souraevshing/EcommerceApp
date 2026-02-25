# Order Microservice

## Build the ap using maven or gradlew tool to generate the jar file.
## Here image name is customized
```shell
mvn spring-boot:build-image \
-Dspring-boot.build-image.imageName=ecommerce-order-service \
-DskipTests
```

## Build the docker image
## Here port and name is customized. And create docker network to run postgres and service in same network
```shell
 docker run -d \
--name order-service \
--network ecommerce-app-net \
-- -p 8882:8882 \
-- ecommerce-order-service
```
