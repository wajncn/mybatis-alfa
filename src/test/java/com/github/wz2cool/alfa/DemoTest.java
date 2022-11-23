package com.github.wz2cool.alfa;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
public class DemoTest {

    @Autowired
    private ProductDao productDao;

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    @Test
    public void inserlist() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Product product = new Product();
            product.setCategoryId(102312 + i);
            product.setProductId(11111L + i);
            product.setProductName("aaa");
            product.setPrice(BigDecimal.valueOf(1.2));
            products.add(product);
        }
        productDao.insertList(products);
    }

    @Test
    public void selectAll() {
        for (Product product : productDao.selectAll()) {
            System.out.println(product);
        }
    }

    @Test
    public void insert() {
        Product product = new Product();
        product.setCategoryId(1);
        product.setProductId(1123123L);
        product.setProductName("aaa");
        product.setPrice(BigDecimal.valueOf(1.2));
        productDao.insert(product);
    }

}
