package jpabook.jpashop.api;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;
    @GetMapping("/api/v4/orders") //전체 Fetch join후 DTo 옴겨서 distinct로 처리(페이징 불가)
    public void OrdersV1(){

    }
    @GetMapping("/api/v5/orders")  //ToOne만 FetchJoin후 ToMany는 그냥 Lazy초기화후 사용(바로DTO)
    public void OrdersV2(){

    }
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3() { //이럴경우 페이징 처리불가
        List<Order> orders = orderRepository.findAllWithItem();
            List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset",
            defaultValue = "0") int offset, @RequestParam(value = "limit", defaultValue = "100") int limit) {
            List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(toList());
        return result;
    }


}
