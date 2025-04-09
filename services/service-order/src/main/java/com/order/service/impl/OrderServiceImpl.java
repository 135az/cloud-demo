package com.order.service.impl;

import com.order.Order;
import com.order.service.OrderService;
import com.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    DiscoveryClient discoveryClient;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    LoadBalancerClient loadBalancerClient;

    @Override
    public Order createOrder(Long productId, Long userId) {
        // Product product = getProduct(productId);
        // Product product = getProductBalanced(productId);
        Product product = getProductBalancedAnnotation(productId);
        Order order = new Order();
        order.setId(1L);
        // 总金额
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(product.getNum())));
        order.setUserId(userId);
        order.setNickName("小新");
        order.setAddress("春日部");
        // 商品列表
        order.setProducts(List.of(product));
        return order;
    }

    /**
     * 远程调用获取商品信息
     */
    private Product getProduct(Long productId) {
        // 获取到商品服务所在的ip+端口
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance serviceInstance = instances.get(0);
        String url = "http://" + serviceInstance.getHost() + ":" + serviceInstance.getPort() + "/product/get/" + productId;
        // 发送远程请求
        log.info("发送远程请求：{}", url);
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }

    /**
     * 远程调用获取商品信息 - 负载均衡
     */
    private Product getProductBalanced(Long productId) {
        // 获取到商品服务所在的ip+端口
        ServiceInstance choose = loadBalancerClient.choose("service-product");
        String url = "http://" + choose.getHost() + ":" + choose.getPort() + "/product/get/" + productId;
        // 发送远程请求
        log.info("发送远程请求：{}", url);
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }

    /**
     * 远程调用获取商品信息 - @LoadBalanced注解实现负载均衡
     */
    private Product getProductBalancedAnnotation(Long productId) {
        String url = "http://service-product/product/get/" + productId;
        // 发送远程请求
        log.info("发送远程请求：{}", url);
        Product product = restTemplate.getForObject(url, Product.class);

        return product;
    }

}
