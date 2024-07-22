#### src/main/resources/application-config.properties
```
spring.datasource.url=jdbc:mysql://[hostname]:[port]/[database]
spring.datasource.username=[db_id]
spring.datasource.password=[db_pw]
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

jwt.secret=[key]
file.directory=[path]
google.client.id:[id]
google.client.pw:[pw]
kakao.client.id:[id]
kakao.redirect.uri:[uri]
```



## 요청 예시

#### **POST /login**
```json
{
    "email": "user@email.com",
    "password": "password"
}
```

#### **Get /test**
```
GET /test
Authorization: Bearer <JWT_TOKEN>
```

#### **POST /api/v1/oauth2/google**
```
POST /api/v1/oauth2/google
```

#### **POST /api/v1/oauth2/kakao**
```
POST /api/v1/oauth2/kakao
```

## 응답 예시

#### **POST /login**
```json
{
    "token": "[jwt_token]"
}
```

#### **GET /test**
```json
{
    "sub": "[email]",
    "roles": "USER",
    "iat": 1721607791,
    "exp": 1721611391
}
```

#### **POST /api/v1/oauth2/google**
```
{
    "url": "https://accounts.google.com/o/oauth2/v2/auth?client_id=googleClientId&redirect_uri=http://localhost:8080/api/v1/oauth2/google&response_type=code&scope=email%20profile%20openid&access_type=offline"
}
```

#### **POST /api/v1/oauth2/kakao**
```
{
    "url": "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=client_id&redirect_uri=redirect_uri"
}
```

## 로그인 성공 시 응답 예시

#### **POST /api/v1/oauth2/google (로그인 성공)**
```json
{
    "token": "[jwt_token]"
}
```

#### **POST /api/v1/oauth2/kakao (로그인 성공)**
```json
{
    "token": "[jwt_token]"
}
```

## 문제점

#####리다이렉트 주소를 수정해야 하지만 어디로 수정해야 할지 모릅니다.
#####네이버 소셜 로그인 검수가 실패했습니다.
