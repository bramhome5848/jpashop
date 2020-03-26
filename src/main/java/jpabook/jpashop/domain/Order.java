package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    //@XtoOne -> default : FetchType.EAGER 이므로 FetchType.LAZY로 변경해야함
    //@OneToMany -> default : FetchType.LAZY
    //@ManyToOne -> default : FetchType.EAGER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //CascadeType
    //Order를 저장할 때 함께 저장
    //mappedBy 속성은 양방향 매핑일 때만 사용?
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate;    //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //== 연관관계 메서드 == //
    //양쪽 관계에서 컨트롤 하는 쪽이 가지고 있는 것이 좋다
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

}
