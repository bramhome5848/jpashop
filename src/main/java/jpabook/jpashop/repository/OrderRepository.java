package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    @Autowired
    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    //동적쿼리 생성시 jpql은 쿼리를 만들기 위해 문자를 자르고 붙이고 수행 -> 권장X
    //jpa criteria -> StringBuilder를 통해서 쿼리를 조힙하는 것과 유사 -> 권장X
    //컴파일 타임에 동적 쿼리를 잘 해결할 수 있을까 -> QueryDsl library
//    public List<Order> findAll(OrderSearch orderSearch) {
//
//
//    }
}
