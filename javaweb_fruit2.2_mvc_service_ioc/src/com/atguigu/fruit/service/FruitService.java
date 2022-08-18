package com.atguigu.fruit.service;

import com.atguigu.fruit.pojo.Fruit;

import java.util.List;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/26.
 * @package_name com.atguigu.fruit.biz
 **/
public interface FruitService {
    //获取指定页面的库存列表信息
    List<Fruit> getFruitList(String keyword, Integer pageNo);
    //添加库存记录信息
    void addFruit(Fruit fruit);
    //根据id查看指定库存记录
    Fruit getFruitByFid(Integer fid);
    //删除特定库存记录
    void delFruit(Integer fid);
    //获取总页数
    Long getPageCount(String keyword);
    //修改特定库存记录
    void updateFruit(Fruit fruit);
    //获取库存记录总数
    Long getFruitCount(String keyword);
}
