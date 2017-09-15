package cn.ejie.dao;

import cn.ejie.pocustom.FixedLogCustom;

import java.util.List;

public interface FixedLogMapper {
    /**
     *
     * @return
     * @throws Exception
     */
    public List<FixedLogCustom> findAll() throws Exception;

    /**
     *
     * @param sql
     * @return
     * @throws Exception
     */
    public List<FixedLogCustom> findBySql(String sql) throws Exception;

}