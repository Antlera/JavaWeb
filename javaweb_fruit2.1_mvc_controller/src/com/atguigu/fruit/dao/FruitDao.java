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
    //获取所有水果的库存信息
    List<Fruit> getFruitList(Integer pageNo,String keyword);
    //根据Fid获取对应的水果
    Fruit getFruitByFid(Integer fid);
    //更新水果信息
    void updateFruit(Fruit fruit);
    //删除水果信息
    void delFruit(Integer fid);
    //添加水果信息
    void addFruit(Fruit fruit);
    //查询库存总记录条数
    Long getFruitCount(String keyword);
}
