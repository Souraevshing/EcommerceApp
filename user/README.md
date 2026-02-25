# User Microservice

## Build the ap using maven or gradlew tool to generate the jar file.
## Here image name is customized
```shell
mvn spring-boot:build-image \
-Dspring-boot.build-image.imageName=ecommerce-user-service \
-DskipTests
```

## Build the docker image
## Here port and name is customized. And create docker network to run postgres and service in same network
```shell
 docker run -d \
--name user-service \
--network ecommerce-app-net \
-- -p 8881:8881 \
-- ecommerce-user-service
```
