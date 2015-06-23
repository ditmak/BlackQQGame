package com.csl.qq.card.dao.impl;

import java.util.Collection;

import com.csl.qq.card.dao.FormulaDAO;
import com.csl.qq.card.domain.Formula;
import com.csl.util.DAO.impl.BaseDAOImpl;

public class FormulaDAOImpl extends BaseDAOImpl<Formula> implements FormulaDAO {
    @Override
    public Collection<Formula> getFormulaByThemeId(int themeId) {
        return getSession().createQuery(
                "from Formula f where f.theme.id=?").setInteger(0, themeId).list();
    }

    @Override
    public Integer getShortestTime(int themeId) {
        return (Integer)getSession().createQuery("select min(cost) from Formula where theme_id = ?").setLong(0, themeId).uniqueResult();
    }
}
