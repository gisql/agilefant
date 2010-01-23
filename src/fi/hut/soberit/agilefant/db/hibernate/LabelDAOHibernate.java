package fi.hut.soberit.agilefant.db.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import fi.hut.soberit.agilefant.db.LabelDAO;
import fi.hut.soberit.agilefant.model.Label;
import fi.hut.soberit.agilefant.model.Story;

@Repository("labelDAO")
public class LabelDAOHibernate extends GenericDAOHibernate<Label> implements
LabelDAO {

    public LabelDAOHibernate() {
        super(Label.class);
    }

    public boolean labelExists(String labelName, Story story) {
        Criteria crit = getCurrentSession().createCriteria(Label.class);
        crit.add(Restrictions.eq("story", story));
        crit.add(Restrictions.eq("name", labelName.toLowerCase()));
        crit.setProjection(Projections.projectionList().add(Projections.count("id")));
        Object ret = crit.uniqueResult();
        int count = (Integer)ret;
        return count > 0;
    }
    
    public List<Label> lookupLabelsLike(String labelName) {
        Criteria crit = getCurrentSession().createCriteria(Label.class);
        crit.add(Restrictions.like("name", labelName.toLowerCase() + "%"));
        crit.addOrder(Order.asc("name"));
        //crit.setProjection(Projections.distinct(Projections.property("name")));
        return asList(crit);
    }

}
    