package com.example.bookstore.service;

import com.example.bookstore.controller.OrderResponseObj;
import com.example.bookstore.controller.ResponseObj;
import com.example.bookstore.entity.Book;
import com.example.bookstore.entity.BookOrder;
import com.example.bookstore.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public OrderResponseObj orderBook(List<BookOrder> bookOrder, String username) {
        try {
            Random rand = new Random();
            if (bookOrder.size() < 1) {
                return new OrderResponseObj("200", "Order size should be greater than 1", LocalDateTime.now().toString());
            } else {
                bookOrder.forEach(order -> {

                    TypedQuery<BookOrder> theQuery = entityManager.createQuery("from BookOrder B WHERE B.user = :user AND B.bookName = :bookname AND B.status <> :status", BookOrder.class);
                    theQuery.setParameter("user", username);
                    theQuery.setParameter("bookname", order.getBookName());
                    theQuery.setParameter("status", "Delivered");

                    List<BookOrder> bookOrdersList = theQuery.getResultList();
                    if (bookOrdersList.size() >= 1) {
                        log.info("An order already exists with  book : " + order.getBookName() + " so cannot add");
                    } else {
                        order.setOrderId(rand.nextInt(100000));
                        order.setUser(username);
                        order.setStatus("Processing");
                        entityManager.persist(order);
                    }
                });
                return new OrderResponseObj("200", "Successfully ordered books", LocalDateTime.now().toString());
            }
        } catch (ServiceException ex) {
            log.info("Exception occured in order book: {}", ex.getMessage());
            return new OrderResponseObj("200", "error ordering books " + ex.getMessage(), LocalDateTime.now().toString());

        }
    }

    @Transactional
    @Override
    public OrderResponseObj getOrderDetails(String bookName, String username){
        try{
            TypedQuery<BookOrder> theQuery = entityManager.createQuery("from BookOrder B WHERE B.user = :user AND B.bookName = :bookname", BookOrder.class);
            theQuery.setParameter("user", username);
            theQuery.setParameter("bookname", bookName);

            List<BookOrder> bookOrdersList = theQuery.getResultList();
            return new OrderResponseObj("200", "retrieved all books", LocalDateTime.now().toString(), bookOrdersList);
        }
        catch (ServiceException ex){
            log.info("Exception occured in getOrderDetails: {}", ex.getMessage());
            return new OrderResponseObj("400", "error fetching books " + ex.getMessage(), LocalDateTime.now().toString());

        }
    }

    @Transactional
    @Override
    public OrderResponseObj getAllOrders(String username){
        try{
            TypedQuery<BookOrder> theQuery = entityManager.createQuery("from BookOrder B WHERE B.user = :user", BookOrder.class);
            theQuery.setParameter("user", username);

            List<BookOrder> bookOrdersList = theQuery.getResultList();
            return new OrderResponseObj("200", "retrieved all books", LocalDateTime.now().toString(), bookOrdersList);
        }
        catch (ServiceException ex){
            log.info("Exception occured in getAllOrders: {}", ex.getMessage());
            return new OrderResponseObj("400", "error fetching books " + ex.getMessage(), LocalDateTime.now().toString());

        }
    }

    @Transactional
    @Override
    public OrderResponseObj updateOrder(String orderId, String status, String username){
        try{
            TypedQuery<BookOrder> theQuery = entityManager.createQuery("from BookOrder B WHERE B.user = :user AND B.orderId = :orderId", BookOrder.class);
            theQuery.setParameter("user", username);
            theQuery.setParameter("orderId", orderId);

            List<BookOrder> bookOrdersList = theQuery.getResultList();
            if(null != bookOrdersList && bookOrdersList.size() >= 1){
                       bookOrdersList.stream().forEach(bookOrder -> {
                      bookOrder.setStatus(status);
                      entityManager.persist(bookOrder);
                });
            }
            else{
                return new OrderResponseObj("200", "order does not exist", LocalDateTime.now().toString());

            }
            return new OrderResponseObj("200", "updated order", LocalDateTime.now().toString());
        }
        catch (ServiceException ex){
            log.info("Exception occured in getAllOrders: {}", ex.getMessage());
            return new OrderResponseObj("400", "error fetching books " + ex.getMessage(), LocalDateTime.now().toString());
        }
    }


}
