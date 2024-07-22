코드 테스트용입니다.

#### src/main/resources/application-config.properties
```
spring.datasource.url=jdbc:mysql://[hostname]:[port]/[database]
spring.datasource.username=[db_id]
spring.datasource.password=[db_pw]
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

jwt.secret=[key]
file.directory=[path]
```



## 요청 예시

#### **POST /authenticate**
```json
{
    "username": "user1",
    "password": "password1"
}
```

#### **GET /chat/messages**
```
GET /chat/messages
Authorization: Bearer <JWT_TOKEN>
```

#### **POST /chat/messages**
```json
{
    "sender": "user1",
    "receiver": "user2",
    "content": "Hello, user2!"
}
```

## 응답 예시

#### **POST /authenticate**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTcxNjcxOTU0MywiZXhwIjoxNzE2NzU1NTQzfQ.x7w9eXrkG1vrfc4G4r2Q1L1sliImoX6GIJ1ncysEo8Q"
}
```

#### **GET /chat/messages**
```json
[
    {
        "id": 1,
        "sender": "user1",
        "receiver": "user2",
        "content": "Hello, user2!",
        "timestamp": 1625247600000
    },
    {
        "id": 2,
        "sender": "user2",
        "receiver": "user1",
        "content": "Hi, user1!",
        "timestamp": 1625247660000
    },
    {
        "id": 3,
        "sender": "user1",
        "receiver": "user2",
        "content": "How are you?",
        "timestamp": 1625247700000
    }
]
```

#### **POST /chat/messages**
```json
{
    "id": 1,
    "sender": "user1",
    "receiver": "user2",
    "content": "Hello, user2!",
    "timestamp": 1625247600000
}
```

#### **POST /chat/messages/file**

- `Content-Type: multipart/form-data`

```curl
curl -X POST "http://yourapi.com/chat/messages/file" \
     -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTcxNjcxOTU0MywiZXhwIjoxNzE2NzU1NTQzfQ.x7w9eXrkG1vrfc4G4r2Q1L1sliImoX6GIJ1ncysEo8Q" \
     -F "sender=user1" \
     -F "receiver=user2" \
     -F "content=Hello, user2 with file!" \
     -F "file=@/path/to/file.txt;type=text/plain"
```
