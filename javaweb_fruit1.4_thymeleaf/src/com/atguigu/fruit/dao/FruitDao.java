package com.atguigu.fruit.dao;

import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/22.
 * @package_name com.atguigu.fruit.dao
 **/
public interface FruitDao {
    //获取所有的库存列表信息
    List<Fruit> getFruitList();
}
