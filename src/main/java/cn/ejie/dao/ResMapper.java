package cn.ejie.dao;

import cn.ejie.po.Res;
import cn.ejie.pocustom.ResCustom;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10.
 */
public interface ResMapper {

    public List<Res> findAllRes() throws Exception;

    public void addRes(ResCustom resCustom) throws Exception;

    public String findResByResName(String resUrl) throws Exception;
}
