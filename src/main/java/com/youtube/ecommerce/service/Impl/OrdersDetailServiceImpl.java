package com.youtube.ecommerce.service.Impl;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.youtube.ecommerce.configuration.JwtRequestFilter;
import com.youtube.ecommerce.dao.CartDao;
import com.youtube.ecommerce.dao.OrdersDetailServiceDao;
import com.youtube.ecommerce.dao.UserDao;
import com.youtube.ecommerce.dto.CartDto;
import com.youtube.ecommerce.dto.OrderDetailDto;
import com.youtube.ecommerce.dto.ProductDto;
import com.youtube.ecommerce.entity.*;
import com.youtube.ecommerce.mapper.MapperUtil;
import com.youtube.ecommerce.service.CartService;
import com.youtube.ecommerce.service.OrdersDetailService;
import com.youtube.ecommerce.service.ProductService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    private static final String ORDER_DELIVERED = "Delivered";
    private static final String KEY = "rzp_test_YHuumQZsuiS1YQ";
    private static final String KEY_SECRET = "JGcbmMWgbioj5EXklVnEKC4h";
    private static final String CURRENCY = "USD";
    @Autowired
    private CartDao cartDao;
    @Autowired
    private MapperUtil mapperUtil;

    @Override
    public void placeOrder(OrderInput orderInput, boolean isSingleProductChekout) {

        List<OrderProductQuantity> orderProductQuantityList = orderInput.getOrderProductQuantityList();
        for (OrderProductQuantity o : orderProductQuantityList) {
            Product product = mapperUtil.convert(productService.getPRoductById(o.getProductId()),new Product());
            String currentUserName = JwtRequestFilter.CURRENT_USER;
            User user = userService.findById(currentUserName).get();
            OrderDetail order = new OrderDetail(
                    orderInput.getFullName(),
                    orderInput.getFullAddress(),
                    orderInput.getContactNumber(),
                    orderInput.getAlternateContactNumber(),
                    ORDER_PLACE,
                    calculateOrderAmount(o) * 1.13,
                    product,
                    user,
                    orderInput.getTransectionId()
            );
            if (!isSingleProductChekout) {
                List<Cart> carts = cartService.getCardDetailById();
               List<Cart> cartList=carts.stream().map(c->mapperUtil.convert(c,new Cart())).collect(Collectors.toList());
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
                .map(od->mapperUtil.convert(od,new OrderDetailDto()))
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDetailDto> gettAllOrder(String status) {
        List<OrderDetail> orderDetailList = new ArrayList<>();

        if (status.equals("All")) {
            ordersDetailServiceDao.findAll().forEach(
                    x -> orderDetailList.add(x)
            );
            return orderDetailList.stream().map(ol->mapperUtil.convert(ol,new OrderDetailDto()))
                    .collect(Collectors.toList());
        } else {
            ordersDetailServiceDao.findByOrderStatus(status).forEach(
                    x -> orderDetailList.add(x)
            );
            return orderDetailList.stream().map(ol->mapperUtil.convert(ol,new OrderDetailDto()))
                    .collect(Collectors.toList());
        }

    }

    @Override
    public OrderDetailDto markOrderAsDelivere(Long id) {
        OrderDetail orderDetail = ordersDetailServiceDao.findById(id).get();
        orderDetail.setOrderStatus(ORDER_DELIVERED);
        ordersDetailServiceDao.save(orderDetail);
        return mapperUtil.convert(orderDetail,new OrderDetailDto());
    }

    @Override
    public TransectionDetails createTransection(Double amount) {
        try {
            RazorpayClient razorpayClient = new RazorpayClient(KEY, KEY_SECRET);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("amount", (amount * 100));
            jsonObject.put("currency", CURRENCY);
            Order order = razorpayClient.orders.create(jsonObject);
            return prepareTransectionDetails(order);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private TransectionDetails prepareTransectionDetails(Order order) {
        String orderId = order.get("id");
        String currency = order.get("currency");
        Integer amount = order.get("amount");

        return new TransectionDetails(
               orderId, currency, amount
       );
    }

    private Double calculateOrderAmount(OrderProductQuantity o) {
        Product product = productService.getPRoductById(o.getProductId());
        return product.getProductDiscountedPrice() * o.getQuantity();
    }


}
