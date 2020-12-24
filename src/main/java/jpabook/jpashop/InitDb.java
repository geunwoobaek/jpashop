package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {
    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInit1();
        initService.dbInit2();
    }
    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;


        private Member CreateMember(String name, String city, String street, String zipcode) {
                Member member = new Member();
                member.setUsername(name);
                member.setAddress(new Address(city, street, zipcode));
                return member;
        }
        private Book createBook(String name, int price, int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }
        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
        public void dbInit1(){
            Member member=CreateMember("userA", "서울", "1", "1111");
            em.persist(member);
            Book book1 = createBook("SPRING1 BOOK", 20000, 200);
            em.persist(book1);
            Book book2 = createBook("SPRING2 BOOK", 40000, 300);
            em.persist(book2);
            Delivery delivery = createDelivery(member);
            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);
            Order order = Order.createOrder(member, delivery, orderItem1,
                    orderItem2);
            em.persist(order);


        }
        public void dbInit2(){
        Member member=CreateMember("geunwoo","busan","2","2222");
        Book book1 = createBook("JPA BOOK1", 20000, 200);
        Book book2 = createBook("JPA BOOK2", 30000, 200);
        OrderItem item1=OrderItem.createOrderItem(book1,20000,1);
        OrderItem item2=OrderItem.createOrderItem(book2,30000,2);
        Delivery delivery=createDelivery(member);
        em.persist(member);
        em.persist(book1);
        em.persist(book2);
        Order orders=new Order().createOrder(member,delivery,item1,item2);
        em.persist(orders);
        }
    }
}
