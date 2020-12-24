package jpabook.jpashop.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class OrderSimpleQueryRepository {
    private final EntityManager em;
    public List<OrderSimpleQueryDto> findOrderDtos() {
        return em.createQuery("select new jpabook.jpashop.api.OrderSimpleQueryDto(o.id, m.username, o.orderDate, o.status, d.address)" +
                " from Order o" + " join o.member m" + " join o.delivery d", OrderSimpleQueryDto.class)
                .getResultList();
    }
}

