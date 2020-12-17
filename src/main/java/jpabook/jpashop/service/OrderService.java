package jpabook.jpashop.service;


import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.repository.MemberRepository;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memeberId,Long itemId, int count){
        Member member=memberRepository.findOne(memeberId);
        Item item=itemRepository.findOne(itemId);
        Delivery delivery=new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);
        OrderItem orderItem=OrderItem.createOrderItem(item,item.getPrice(),count);
        Order order=Order.createOrder(member,delivery,orderItem);
        orderRepository.save(order);
        return order.getId();
    }
    @Transactional
    public void cancelOrder(Long orderId){
        Order order=orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAllByCriteria(orderSearch);
    }
}