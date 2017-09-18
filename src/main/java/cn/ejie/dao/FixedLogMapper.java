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

    /**
     *
     * @param eqId
     * @return
     * @throws Exception
     */
    public int countByEqId(String eqId) throws Exception;

    /**
     *
     * @param eqId
     * @return
     * @throws Exception
     */
    public List<FixedLogCustom> findAllByEqId(String eqId) throws Exception;

}
