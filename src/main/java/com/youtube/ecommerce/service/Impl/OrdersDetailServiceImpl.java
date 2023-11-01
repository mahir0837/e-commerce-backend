package com.youtube.ecommerce.service.Impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import com.youtube.ecommerce.configuration.JwtRequestFilter;
import com.youtube.ecommerce.dao.CartDao;
import com.youtube.ecommerce.dao.OrdersDetailServiceDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.dto.OrderDetailDto;
import com.youtube.ecommerce.entity.*;
import com.youtube.ecommerce.mapper.MapperUtil;
import com.youtube.ecommerce.service.CartService;
import com.youtube.ecommerce.service.OrdersDetailService;
import com.youtube.ecommerce.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrdersDetailServiceImpl implements OrdersDetailService {
    @Autowired
    private OrdersDetailServiceDao ordersDetailServiceDao;
    @Autowired
    private ProductService productService;
    @Autowired
    private UserDao userService;
    @Autowired
    private CartService cartService;
    private static final String ORDER_PLACE = "Placed";
    private static final String ORDER_PROCESSING = "Processing";
    private static final String ORDER_QUALITY_CHECK = "Quality Check";
    private static final String ORDER_DELIVERED = "Delivered";
    private static final String KEY = "rzp_test_meaW6VHnuhen4k";
    private static final String STRIPE_SECRETKEY="sk_test_51NR2GTK83yIBaSPaR5C38m50f5ONvrz2VyeL4oLY6wAe6MHiFsWPMhuP1bBmlaQlq5ZPXwh08A2t1c3zEuzNgdlz006FWvN7Ko";
    private static final String KEY_SECRET = "ifJbq0EstFf9eGSXU13cw9xz";
    private static final String CURRENCY = "USD";
    @Autowired
    private CartDao cartDao;
    @Autowired
    private MapperUtil mapperUtil;

    @Override
    public void placeOrder(OrderInput orderInput, boolean isSingleProductChekout) {

        List<OrderProductQuantity> orderProductQuantityList = orderInput.getOrderProductQuantityList();
        for (OrderProductQuantity o : orderProductQuantityList) {
            Product product = productService.getPRoductById(o.getProductId());
            String currentUserName = JwtRequestFilter.CURRENT_USER;
            User user = userService.findById(currentUserName).get();
            OrderDetail order = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACE,
                    calculateOrderAmount(o) * 1.13,
                    orderInput.getOrderDate().plusDays(4),
                    generateTrackingNumber(o.getProductId()),
                    product,
                    user,
                    orderInput.getPaymentMethod()

            );
            System.out.println(order.getTransectionId());
            if (!isSingleProductChekout) {
                List<Cart> carts = cartService.getCardDetailById();
                List<Cart> cartList = carts.stream().map(c -> mapperUtil.convert(c, new Cart())).collect(Collectors.toList());
                cartList.forEach(p -> cartDao.deleteById(p.getCartId()));
            }
            ordersDetailServiceDao.save(order);
        }

    }

    @Override
    public List<OrderDetailDto> getOrderDetails() {
        String userName = JwtRequestFilter.CURRENT_USER;
        User user = userService.findById(userName).get();
        return ordersDetailServiceDao.findByUser(user).stream()
                .map(od -> mapperUtil.convert(od, new OrderDetailDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDetailDto> gettAllOrder(String status) {
        List<OrderDetail> orderDetailList = new ArrayList<>();

        if (status.equals("All")) {
            ordersDetailServiceDao.findAll().forEach(
                    x -> orderDetailList.add(x)
            );
            return orderDetailList.stream().map(ol -> mapperUtil.convert(ol, new OrderDetailDto()))
                    .collect(Collectors.toList());
        } else {
            ordersDetailServiceDao.findByOrderStatus(status).forEach(
                    x -> orderDetailList.add(x)
            );
            return orderDetailList.stream().map(ol -> mapperUtil.convert(ol, new OrderDetailDto()))
                    .collect(Collectors.toList());
        }

    }

    @Override
    public OrderDetailDto markOrderAsDelivere(Long id, int addDays) {
        OrderDetail orderDetail = ordersDetailServiceDao.findById(id).get();
        orderDetail.setOrderStatus(ORDER_DELIVERED);
        orderDetail.setOrderDate(orderDetail.getOrderDate());
        orderDetail.setOrderDate(orderDetail.getOrderDate().plusDays(addDays));
        ordersDetailServiceDao.save(orderDetail);
        return mapperUtil.convert(orderDetail, new OrderDetailDto());
    }


    public String createTransection(OrderDetailDto order, String userName, double amount) {
        Stripe.apiKey=STRIPE_SECRETKEY;
        try {

            PaymentIntentCreateParams params = new PaymentIntentCreateParams.Builder()

                    .setAmount((long) (amount * 100))
                    .setCurrency("usd")
                    .setPaymentMethod(order.getPaymentMethod())
                    .setConfirm(true)
                    .setDescription("Payment For Order By " + userName+" transaction code: "+order.getPaymentMethod())
                    .build();

            PaymentIntent paymentIntent = PaymentIntent.create(params);

            return paymentIntent.getPaymentMethod();

    } catch (StripeException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public OrderDetailDto markOrderAsProcessing(Long id, int addDays) {
        OrderDetail orderDetail = ordersDetailServiceDao.findById(id).get();
        orderDetail.setOrderStatus(ORDER_PROCESSING);
        orderDetail.setOrderDate(orderDetail.getOrderDate());
        orderDetail.setOrderDate(orderDetail.getOrderDate().plusDays(addDays));
        ordersDetailServiceDao.save(orderDetail);
        return mapperUtil.convert(orderDetail, new OrderDetailDto());
    }

    @Override
    public OrderDetailDto markOrderAsQualityCheck(Long id, int addDays) {
        OrderDetail orderDetail = ordersDetailServiceDao.findById(id).get();
        orderDetail.setOrderStatus(ORDER_QUALITY_CHECK);
        orderDetail.setOrderDate(orderDetail.getOrderDate());
        orderDetail.setOrderDate(orderDetail.getOrderDate().plusDays(addDays));
        ordersDetailServiceDao.save(orderDetail);
        return mapperUtil.convert(orderDetail, new OrderDetailDto());
    }

    private Double calculateOrderAmount(OrderProductQuantity o) {
        Product product = productService.getPRoductById(o.getProductId());
        return product.getProductDiscountedPrice() * o.getQuantity();
    }

    public static String generateTrackingNumber(Long id) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String currentDate = dateFormat.format(new Date());
        Random random = new Random();
        int randomPart = 1000 + random.nextInt(20000);
        String trackingNumber = "TR-" + id + currentDate + randomPart;

        return trackingNumber;
    }
}
