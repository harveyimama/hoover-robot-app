# hoover-robot-app
 App for executing robot instructions 
 
 # What you need to run it 
- JRE / JDK - Java
- MongoDB 
- Maven 
 
# How to build and run 

- Pull code from the main branch 

- go to src/main/resources/application.properties  to configure mongodb connection parameters add the following 
   - spring.data.mongodb.username=\<your user>
   - spring.data.mongodb.password=\<your password>

- run below to build code 

```
 mvn clean install 
```
## Running jar

- run below to run the build 

```
java -jar target/hoover-0.0.1-SNAPSHOT.jar
```

## Using Docker 
- run the following  commands 

```
 docker build -t app/version:1.0 .
 docker image ls 
 docker run -p 8080:8080 <image id from step above>
```

#To Test 

- run below using curl or use postman to make request 

```
curl --location 'http://localhost:8080/v1/drive' --header 'Content-Type: application/json' --data '{
  "roomSize" : [5, 5],
  "coords" : [1, 2],
  "patches" : [
    [1, 0],
    [2, 2],
    [2, 3]
  ],
  "instructions" : "NNESEESWNWW"
}'
```


