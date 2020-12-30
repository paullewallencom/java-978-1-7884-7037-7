package com.acme.orders.api.models;

import com.acme.orders.api.models.db.OrderEntity;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class OrderDAO extends AbstractDAO<OrderEntity> {

    private Timer findAllTimer;

    public OrderDAO(SessionFactory sessionFactory, MetricRegistry metricRegistry) {
        super(sessionFactory);

        this.findAllTimer = metricRegistry.timer(OrderDAO.class.getName() + ".query-find-all");
    }

    @SuppressWarnings("unchecked")
    public List<OrderEntity> findAll(Integer limit, Integer offset) {

        Query<OrderEntity> query = namedQuery("OrderEntity.findAll");

        if (limit != null && limit > 0) {

            query = query.setMaxResults(limit);
        }

        if (offset != null && offset > 0) {

            query = query.setFirstResult(offset);
        }

        final Timer.Context context = findAllTimer.time();

        try {
            return list(query);
        } finally {
            context.stop();
        }
    }

    public Long findAllCount() {
        return (Long) namedQuery("OrderEntity.findAllCount").uniqueResult();
    }

    public OrderEntity findById(String id) {
        return get(id);
    }

    public OrderEntity create(OrderEntity order) {
        return persist(order);
    }
}
