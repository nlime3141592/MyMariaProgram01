1. DTO(Data Transfer Object, 데이터 전달 객체)
네트워크 통신 등에서 데이터를 상대에게 전달할 목적으로 사용하는 객체입니다.

2. DTO의 특징
- 인스턴스 필드 변수만 포함합니다. 다른 static 변수를 포함할 수 없습니다.
- 만약 인스턴스 필드 변수가 private일 경우 해당 필드 변수의 값을 제어할 수 있도록 getter/setter 함수만 구현하도록 합니다. 이 외의 다른 함수는 절대 포함하지 않습니다.
- 반드시 필요하지 않은 경우를 제외하고는 생성자를 사용하지 않습니다.

3. DTO 사용 및 통신 원리
- 말단 프로그램(이용자/운영자 프로그램)의 Business Logic 모듈은 DataBase에서 원하는 정보를 얻기 위해 Network Interface 모듈이 포함한 데이터 요청 함수를 호출합니다. 이 함수의 반환형은 DTO입니다.
- Network Interface 모듈은 Socket을 통해 서버에 원하는 정보를 요청합니다.
- 서버는 수신한 정보에 따라 적절한 Query 클래스를 선택합니다.
- Query 클래스는 임의의 DTO 클래스를 생성합니다.
- 서버는 임의의 DTO 클래스를 byte[]로 변환합니다.
- 서버는 Socket을 통해 말단 프로그램과 연결된 Network Interface 모듈에 byte[]를 전송합니다.
- Network Interface 모듈은 byte[]를 DTO 클래스로 변환합니다.
- Network Interface 모듈은 변환한 DTO 클래스를 Business Logic에 반환합니다.
- Business Logic은 DTO 클래스를 사용해 원하는 작업을 처리할 수 있습니다.

4. 추후 계획
deu.java002_02.dto 패키지는 공통 Business Logic에 포함되는 패키지입니다. 추후에 MariaProgram 프로젝트에서 제거할 예정입니다.
deu.java002_02.dto 패키지는 공통 Business Logic 프로젝트를 빌드해 만들어진 모듈에 포함될 예정입니다.

5. 상세한 사용 설명
- MariaMain 클래스에서 호스트 정보를 변경하세요.
- users table에 blacklist라는 새로운 열을 생성했습니다. 명령어는 아래와 같습니다.
alter table users add column blacklist bool not null default false
- QueryUtility 클래스는 Query에 필요한 각종 편의 기능을 제공하는 클래스입니다.
- 쿼리 문을 파일에 저장하고 파일에서 쿼리 문을 로드해 사용할 수 있습니다. sql 파일의 경로는 QueryUtility.SQL_ROOT_PATH 변수를 확인하세요.
- sqltxt_QueryUser_byName.txt 파일의 내용은 아래와 같습니다. 자세한 내용은 UserQuery.byName() 함수를 참조하세요.
select * from users where nickname = '%s'
- sqltxt_QueryUser_byUUID.txt 파일의 내용은 아래와 같습니다. 자세한 내용은 UserQuery.byUUID() 함수를 참조하세요.
select * from users where serverid = '%d'
- 왜 '%s'와 '%d'가 데이터베이스 질의어에 포함되어 있는지 알고 싶다면 QueryUtility.getSqlFormat() 함수를 참조하세요.
- users 테이블의 메타데이터는 아래와 같습니다.
+-----------+-------------+------+-----+---------------------+----------------+
| Field     | Type        | Null | Key | Default             | Extra          |
+-----------+-------------+------+-----+---------------------+----------------+
| serverid  | int(11)     | NO   | PRI | NULL                | auto_increment |
| nickname  | varchar(64) | NO   |     | NULL                |                |
| account   | varchar(64) | NO   | UNI | NULL                |                |
| password  | varchar(64) | NO   |     | NULL                |                |
| regdate   | timestamp   | NO   |     | current_timestamp() |                |
| blacklist | tinyint(1)  | NO   |     | 0                   |                |
+-----------+-------------+------+-----+---------------------+----------------+