## 실전! 스프링 부트와 JPA 활용1 
<hr/>

###  lombok plugin 
lombok 은 단순한 코드 반복 등을 막아 코드량을 효과적으로 줄일 수 있는 플러그인으로 사용하는 것이 좋음.
- 프로젝트 생성시 dependencies 에 lombok 추가

- settings > plugins 검색 > lombok 설치
- settings > annotation processors 검색 > Enable annotation processing 체크

lombok 동작 체크

- @Getter @Setter 설정
- .setData .getData 사용

```java
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Hello {
    private String name;
}
```

```java
@SpringBootApplication
public class JpashopApplication {
    
	public static void main(String[] args) {
		
		Hello hello = new Hello();
		hello.setName("john");
		System.out.println(hello.getName());
		
		SpringApplication.run(JpashopApplication.class, args);
	}
}
```

john 이 정상적으로 출력됨

<hr/>

### Thymeleaf

spring 에서 추천하는 뷰템플릿

강점

> Natural templates
>> html mark-up 을 훼손하지 않고 사용함 (태그 안에 문법적용)<br/>
>> jsp 은 브라우저에서 열리지않지만, Thymeleaf 는 열림

단점

> 문법적으로 어려울 수 있으므로 문서를 많이 참고해야함.

<hr/>

### java 내 memory DB 사용

main > test > resource 생성 후 application.yml 로 DB 정의, 테스트코드 내에서 우선권을 가짐

url 에서 **jdbc:h2:mem:test** 으로 세팅

```yml
spring:
  datasource:
    url: jdbc:h2:mem:test # in memory mode 로 설정되어 사용됨 (h2 는 java로 이루어져있어 jvm 에서도 동작함
    username: sa
    password:
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop # 자동으로 table 생성하고 종료시점에 table 드랍(자원정리)
    properties:
      hibernate:
        #  show_sql: true
        format_sql: true

logging:
  level:
    org.hibernate.SQL: debug # jpa, hibernate 가 처리하는 sql을 보여줌
```

spring boot에서는 application.yml 파일만 만들고 아무 코드 없어도 작동하게 됨.
적어두는 것이 좋아서 작성함

<hr/>

### 사용자 설정 Exception 생성 가능.

extends RuntimeException 사용!

```java
public class NotEnoughStockException extends RuntimeException {
    
    public NotEnoughStockException() {
    }
    public NotEnoughStockException(String message) {
        super(message);
    }
    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }
    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}
```

<hr/>

### Tip !

보통 우리들이 개발할 때 Item Service 에서 재고수량을 가져와서 재고를 더하거나 뺴서 값을 생성한 다음
setter 를 통해서 재고수량을 수정하는 형태로 코딩을 많이 했을 것이다. 하지만 객체지향적으로 봤을 때는 
Service 가 아닌 Entity 에서, 즉 데이터를 가지고 있는 쪽에서(Entity) 비즈니스 로직을 구성하는 것이 응집력이 있어서 가장 좋다.

**단축키**

ctrl+alt+m : 부분 코드를 메서드로 자동생성
ctrl+alt+p : 해당 변수를 메서드의 파라미터로 자동설정