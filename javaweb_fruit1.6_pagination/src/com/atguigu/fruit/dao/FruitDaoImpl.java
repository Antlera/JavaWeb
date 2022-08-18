package com.atguigu.fruit.dao;

import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.basedao.BaseDAO;

import java.util.List;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/22.
 * @package_name com.atguigu.fruit.dao
 **/
public class FruitDaoImpl extends BaseDAO<Fruit> implements FruitDao {
    @Override
    public List<Fruit> getFruitList(Integer pageNo) {
        String sql = "select * from t_fruit limit ?,5";
        List<Fruit> fruits = super.executeQuery(sql,(pageNo-1)*5);
        return fruits;
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        String sql = "select * from t_fruit where fid = ?";
        Fruit fruit = load(sql, fid);
        return fruit;
    }

    @Override
    public void updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fname = ?,price = ?,fcount =?,remark =? where fid = ?";
        super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark(),fruit.getFid());
    }

    @Override
    public void delFruit(Integer fid) {
        String sql = "delete from t_fruit where fid = ?";
        super.executeUpdate(sql,fid);
    }

    @Override
    public void addFruit(Fruit fruit) {
        String sql = "insert into t_fruit values(0,?,?,?,?)";
        super.executeUpdate(sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark());
    }

    @Override
    public Long getFruitCount() {
        String sql = "select count(*) from t_fruit";
        Object[] objects = super.executeComplexQuery(sql);
        return (Long) objects[0];
    }
}
