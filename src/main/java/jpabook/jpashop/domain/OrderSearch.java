package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;  //회원의 이름
    private OrderStatus orderStatus;    //주문 상태는[ORDER, CANCEL]
}
