package hello.hellospring.domain;

import javax.persistence.*;

// ORM : Object Relational Mapping
// JPA는 객체와 ORM이라는 기술이다.
@Entity // jpa가 관리하는 entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // pk를 mapping함
    // @Id는 pk값으로 정하겠다는 뜻, @GeneratedValue(strategy = GenerationType.IDENTITY)는 pk값을 DB가 자동생성해주겠다는 뜻
    private Long id; // 임의의 값 => 시스템이 저장하는 아이디

    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
