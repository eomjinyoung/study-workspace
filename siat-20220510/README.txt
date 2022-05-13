1일차: 
	- Standalone에서 웹 애플리케이션까지의 소프트웨어 아키텍처 특징을 설명
	- Standalone 구조의 특징
      - Standalone + File 
      - Client / Server(DBMS) 구조 등장
      - Client / Application Server / DBMS 구조 등장
      - Web Browser / Web Server / Application Server / DBMS 구조 등장
      - Web Browser / Web Server / 서블릿 컨테이너 / 서블릿 / DBMS 구조 등장
2일차:
	- 기존 프로젝트를 Gradle 프로젝트로 변환
	- 빌드 도구 개요 
	  - Ant, Maven, Gradle 의 등장 이유와 주요 특징 설명
      - Gradle 사용하여 프로젝트 폴더 준비
3일차:   
     	- Gradle로 의존 라이브러리를 다루는 방법
   	- JDBC 코드를 Mybatis 라이브러리로 대체
        - Mybatis 라이브러리 추가
        - mybatis-config.xml 파일 생성 및 항목에 대한 설명
        - BoardMapper.xml 파일 생성 및 항목에 대한 설명
	- BoardDao 클래스에 Mybatis 적용
        - getListCount() 
        - getBoardList()
4일차:
      - BoardDao 클래스에 Mybatis 적용(계속) 
        - getBoardList() 상세 설명
        - getDetail()
        - getMaxNo() 추가
        - boardInsert()
        - boardModify()
        - boardDelete()


[Gradle 프로젝트 준비]

1. gradle 빌드 도구를 사용하여 프로젝트 폴더를 준비하기

> mkdir mtwoBoard2
> cd mtwoBoard2
> gradle init

Select type of project to generate:
  1: basic
  2: application
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4] 2  <======

Select implementation language:
  1: C++
  2: Groovy
  3: Java
  4: Kotlin
  5: Scala
  6: Swift
Enter selection (default: Java) [1..6] 3  <======

Split functionality across multiple subprojects?:
  1: no - only one application project
  2: yes - application and library projects
Enter selection (default: no - only one application project) [1..2] 1 <======

Select build script DSL:
  1: Groovy
  2: Kotlin
Enter selection (default: Groovy) [1..2] 1 <======

Generate build using new APIs and behavior (some features may change in the next minor release)? (default: no) [yes, no]  <==== 그냥 엔터

Select test framework:
  1: JUnit 4
  2: TestNG
  3: Spock
  4: JUnit Jupiter
Enter selection (default: JUnit Jupiter) [1..4] 1 <======

Project name (default: mtwoBoard3):  <==== 그냥 엔터
Source package (default: mtwoBoard3): net.board  <======

> Task :init
Get more help with your project: https://docs.gradle.org/7.4.2/samples/sample_building_java_applications.html

BUILD SUCCESSFUL in 26s
2 actionable tasks: 2 executed


2. 이클립스IDE로 임포트하기

1) build.gradle 파일 편집

plugins { 
    id 'java' 
    id 'eclipse-wtp'
    id 'war'
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'junit:junit:4.13.2'
    implementation 'com.google.guava:guava:30.1.1-jre'
}

eclipse {
    project {
        name = 'mtwoBoard2'    
    }
}

war {
    archiveBaseName = 'mtwoBoard2'
}

2) 이클립스 IDE용 프로젝트 설정 파일 생성하기

> gradle eclipse

생성되는 파일들:
.project
.classpath
.settings/
  *.xml

3) 이클립스 IDE에서 프로젝트를 임포트 한다.

File > import... 클릭 


3. 웹 프로젝트의 의존 라이브러리를 추가하기

1) http://search.maven.org 사이트에서 다음 라이브러리를 검색하여 build.gradle 파일에 등록한다.
  - Servlet API 라이브러리 추가하기  (javax.servlet-api)
  - JSTL 라이브러리 추가하기(javax.servlet:jstl)
  - Apache commons-fileupload-1.4 라이브러리 추가하기(commons-fileupload 1.4)
  - oreilly-servlet 라이브러리 추가하기(servlets.com:cos:05Nov2002)

다음과 같이 build.gradle 파일에 의존 라이브러리를 등록한다.

dependencies {
    implementation 'commons-fileupload:commons-fileupload:1.4'
    implementation 'javax.servlet:jstl:1.2'
    implementation 'servlets.com:cos:05Nov2002'
    implementation 'javax.servlet:javax.servlet-api:4.0.1'

    // Use JUnit test framework.
    testImplementation 'junit:junit:4.13.2'

    // This dependency is used by the application.
    implementation 'com.google.guava:guava:30.1.1-jre'
}


2) 이클립스 설정 파일을 갱신한다.

> gradle eclipse


3) 이클립스 IDE에서 프로젝트를 갱신한다.



[Mybatis 라이브러리 설정]

1. 프로젝트에 Mybatis 퍼시스턴스 프레임워크 관련 라이브러리를 등록한다.
1) mybatis 라이브러리 검색(search.maven.com) 및 build.gradle에 등록
2) 이클립스 설정 파일 갱신 및 프로젝트 리프래시

2. Mybatis 설정
1) Mybatis 설정 파일 생성 및 편집(mybatis.org 사이트의 문서를 참조)
   src/main/resources/config/mybatis-config.xml 

2) SQL Mapper 파일 생성 및 편집
   src/main/resources/net/board/db/BoardMapper.xml 

[DAO에 Mybatis 적용]

1. getListCount() 에 적용
2. getBoardList() 에 적용



 