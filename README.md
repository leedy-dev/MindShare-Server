
## 개발 환경 및 기술 스택

- 언어: Java 17
- 프레임워크: Spring Boot 3, Spring Security
- 빌드 도구: Gradle
- ORM: JPA
- 쿼리 빌더: QueryDSL

## 프로젝트 구조
mindshare-server
- mindshare-core 공통 클래스 및 설정
- mindshare-security 로그인 로직 (port: 8081)
- mindshare-ams 계정 관리 (port: 8082)
- mindshare-cms 게시물 관리 (port: 8083)

database
- mindshare-db h2 database 실행 (DB 별도 실행, 모듈 X) (port: 8090)

## 프로젝트 실행 방법 (Windows 기준)
실행 요약
1. 데이터베이스 실행
2. 각 모듈 실행

실행 방법
1. 터미널 실행 (cmd, powershell, git bash, ...)
2. mindshare root 폴더로 이동
    - cd 디렉토리/mindshare
3. 빌드 실행
    - gradlew clean build
4. 데이터베이스 실행 (선행 필수)
    - cd mindshare-db
    - start gradlew.bat bootRun
5. 루트 디렉토리로 이동
    - cd ../
6. 각 모듈 실행
    - start gradlew.bat :mindshare-security:bootRun
    - start gradlew.bat :mindshare-ams:bootRun
    - start gradlew.bat :mindshare-cms:bootRun


## API 엔드포인트 목록 및 간단한 설명
- `로그인`과 `사용자 등록`을 제외한 모든 API 는 인증이 필요합니다.
- 사용자 등록 후 로그인 api를 호출하면 `accessToken`과 `refreshToken`이 발급됩니다.
- 발급된 accessToken을 요청 Header의 `Authorization` `Bearer {accessToken}` 형식으로 함께 요청하시면 인증됩니다. 
- AMS
  - 사용자 등록 API 제외 ADMIN 권한 필요
- CMS
  - 인증 필요 (MEMBER, ADMIN 권한 가능)

### 1. Security (로그인)
- ### API 엔드포인트 
   - `POST` http://localhost:8081/api/auth/login
     - 로그인 api
       - `request`
         - uid: 아이디
         - password: 비밀번호
       - `response`
         - accessToken (Header)
         - refreshToken (Cookie)


### 2. AMS (Account Management System)
- ### Entity
  - `User`
    - id : Long : 사용자 시퀀스 ID
    - userType : String(NOT NULL) : 사용자 유형(권한) : MEMBER | ADMIN
    - uid : String(NOT NULL) : 사용자 고유 로그인 ID
    - password : String(NOT NULL) : 비밀번호
    - userInfo : UserInfo Entity
    - 등록, 수정 정보
      - createDateTime : LocalDateTime : 등록일시
      - createUserId : String : 등록자 ID
      - updateDateTime : LocalDateTime : 수정일시
      - updateUserId : String : 수정자 ID
  - `UserInfo`
    - id : Long : 사용자 정보 시퀀스 ID
    - user : User Entity : userId로 매핑
    - name : String(NOT NULL) : 사용자 명
    - email : String(NOT NULL) : 이메일
- ### API 엔드포인트
  - `POST` http://localhost:8082/ams/api/user
    - 사용자 등록 API
      - `request`
        - userType (MEMBER | ADMIN)
        - uid
        - password
          - userInfo
            - name
            - email
      - `response`
        - id
        - userType
        - uid
        - password
          - userInfo
            - name
            - email
  - `GET` http://localhost:8082/ams/api/user/{id}
      - 사용자 단건 조회 API
        - `param`
          - id (사용자 id)
        - `response`
          - id
          - userType
          - uid
          - password
            - userInfo
              - name
              - email
  - `PUT` http://localhost:8082/ams/api/user/{id}
    - 사용자 수정 API
      - `param`
        - id (사용자 id)
      - `request`
        - userType
        - userInfo
          - name
          - email
      - `response`
        - id
        - userType
        - userInfo
          - name
          - email
  - `DELETE` http://localhost:8082/ams/api/user/{id}
    - 사용자 삭제 API
      - param
        - id (사용자 id)
      - response
        - id


### 3. CMS (Content Management System)
- ### Entity
  - `Board`
    - id : Long : 게시물 ID
    - title : String(NOT NULL) : 제목 
    - content : String(NOT NULL) : 내용
    - viewCount : Integer : 조회 수
    - 등록, 수정 정보
      - createDateTime : LocalDateTime : 등록일시
      - createUserId : String : 등록자 ID
      - updateDateTime : LocalDateTime : 수정일시
      - updateUserId : String : 수정자 ID
- ### API 엔드포인트
  - `POST` http://localhost:8083/cms/api/board
    - 게시물 등록 API
      - `request`
        - title
        - content
      - `response`
        - id
        - title
        - content
        - viewCount
  - `GET` http://localhost:8083/cms/api/board/{id}
    - 게시물 단건 조회 API
      - `param`
        - id (게시물 id)
      - `response`
        - id
        - title
        - content
        - viewCount
  - `GET` http://localhost:8083/cms/api/board
    - 게시물 목록 API
      - `param`
        - title (like)
        - pageable
      - `response`
        - content (게시물 목록)
          - id
          - title
          - content
          - viewCount
        - pageable
  - `PUT` http://localhost:8083/cms/api/board/{id}
      - 게시물 수정 API
      - `param`
        - id (게시물 id)
      - `request`
        - title
        - content
      - `response`
        - id
        - title
        - content
        - viewCount
  - `DELETE` http://localhost:8083/cms/api/board/{id}
      - 게시물 삭제 API
        - `param`
          - id (게시물 id)
          - `response`
            - id
    
## 고민한 점이나 아쉬운 점
1. 모듈 설계 시 역할을 적절하게 분배하지 못해 core 모듈에 너무 많은 책임이 부가되었습니다.
   - common 모듈을 추가하여 core 모듈은 공통 설정의 역할을 하고, common 모듈은 공통 컴포넌트와 클래스를 반영하는 것을 고려해보았습니다.
   - 하지만 공통 모듈에서 변경이 일어나면 모든 서비스에 영향이 간다는 점은 멀티 모듈 프로젝트의 중요 쟁점이라고 생각합니다.


2. '공통 데이터베이스를 어떻게 구성해야 하는가?'가 주요 고민 중 하나였습니다.
   - 대부분의 환경에서 테스트할 수 있는 방안으로 h2 데이터베이스를 내부적으로 실행하고 tcp로 통신하도록 설정하였습니다.


3. refresh token과 lock key 관리를 위해 redis가 필요하다고 생각하여 redis처럼 동작하는 컴포넌트를 구성하였습니다.
   - package: com.mindshare.core.common.redis
   - key-value 형식으로 데이터를 저장하고 만료시간을 부여하였습니다.
   - 1시간마다 스케줄러로 만료된 데이터는 삭제하도록 하였습니다.
   - get 요청 시 만료된 데이터일 경우 삭제하도록 하였습니다.


4. 트랜잭션 관리는 JPA로 동작하고, 동적 조건절이 필요한 조회 쿼리는 QueryDSL을 사용하였습니다.
