package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 연관관계의 주인을 정하는 방법은 mappedBy 속성을 사용
    // 주인은 mappedBy 속성을 사용하지 않음
    // 주인이 아니면 mappedBy 속성을 사용해서 속성의 값으로 연관관계의 주인을 정할 수 있음
    // 양방향의 경우 fk가 있는 곳을 연관관계의 주인으로 설정
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
