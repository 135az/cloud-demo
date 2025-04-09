package com.order.feign.failback;

import com.order.feign.ProductFeignClient;
import com.product.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Slf4j
@Component
public class ProductFeignClientFailBack implements ProductFeignClient {
    @Override
    public Product getProductById(Long id) {
        log.info("ProductFeignClientFailBack 兜底数据--------------------------");
        return new Product() {{
            setId(-1L);
            setProductName("ProductFeignClientFailBack 兜底数据");
            setNum(-1);
            setPrice(BigDecimal.ZERO);
        }};
    }
}
