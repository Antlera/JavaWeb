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
    public List<Fruit> getFruitList() {
        String sql = "select * from t_fruit";
        List<Fruit> fruits = super.executeQuery(sql);
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
}
