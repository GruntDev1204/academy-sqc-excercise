package com.da_thao.project_test.config;

import com.da_thao.project_test.dto.code_response.impl.ErrorCode;
import com.da_thao.project_test.exception.ApiException;
import com.da_thao.project_test.request_param.filter_data.impl.QueryFilterData;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@NoArgsConstructor
public class HibernateConnection<Model> {
    // Sử dụng Singleton để đảm bảo chỉ có một instance của sessionFactory
    private static volatile SessionFactory sessionFactory;

    static {
        try {
            // Cấu hình Hibernate và tạo sessionFactory
            sessionFactory = new Configuration()
                    .configure("hibernate.conf.xml")
                    .buildSessionFactory();
        } catch (HibernateException e) {
            // Sử dụng Logging thay vì in ra lỗi trực tiếp
            System.err.println("Error initializing Hibernate SessionFactory: " + e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Trả về instance của SessionFactory.
     * Kiểm tra null để tránh lỗi khi gọi phương thức trước khi sessionFactory được khởi tạo.
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateConnection.class) {
                if (sessionFactory == null) {
                    sessionFactory = new Configuration()
                            .configure("hibernate.conf.xml")
                            .buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }

    /**
     * Mở một phiên làm việc mới.
     */
    private Session getSession() {
        return getSessionFactory().openSession();
    }

    /**
     * for any change
     */
    private Transaction startTask(Session session) {
        if (session == null || !session.isOpen()) {
            throw new IllegalStateException("Session is null or not open. Cannot start transaction.");
        }
        return session.beginTransaction();
    }

    private void commitTask(Transaction transaction) {
        transaction.commit();
    }

    public void useTask(String taskName, Model data) {
        Transaction transaction = null;
        try (Session session = this.getSession()) {
            if (session == null || !session.isOpen()) {
                throw new IllegalStateException("Session is null or not open. Cannot start transaction.");
            }

            if (taskName == null || taskName.isEmpty()) {
                throw new IllegalStateException("Task name is null or empty. Cannot start transaction.");
            }

            transaction = this.startTask(session);
            switch (taskName) {
                case "create":
                    session.save(data);
                    break;
                case "update":
                    session.update(data);
                    break;
                case "delete":
                    session.delete(data);
                    break;
                default:
                    throw new IllegalStateException("Task name is not valid. Cannot start transaction.");
            }

            this.commitTask(transaction);

        } catch (IllegalStateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Invalid state: " + e.getMessage(), e);
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            assert taskName != null;
            switch (taskName) {
                case "create" -> throw new ApiException(ErrorCode.CREATE_FAILED);
                case "update", "delete" -> throw new ApiException(ErrorCode.UPDATE_FAILED);
                default -> throw new RuntimeException("Failed to execute task: " + taskName, e);
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("An unexpected error occurred: " + e.getMessage(), e);
        }
    }


    /**
     * for select
     */
    private void setParameter(QueryFilterData queryData, Query<Model> query) {
        for (Map.Entry<String, Object> entry : queryData.params().entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }
    //for filter
    @SuppressWarnings({"deprecated"})
    public List<Model> getQuery(final QueryFilterData queryFilterData, final String hql, Class<Model> clazz) {
        try (Session session = this.getSession()) {
            Query<Model> query = session.createQuery(hql, clazz);
            this.setParameter(queryFilterData, query);
            return query.getResultList();
        } catch (HibernateException e) {
            throw new RuntimeException("Failed to execute hql: " + hql, e);
        }

    }

    //for no filter
    @SuppressWarnings({"deprecated", "unchecked"})
    public List<Model> getQuery(final String hql) {
        try (Session session = this.getSession()) {
            session.createQuery(hql);
            return session.createQuery(hql).getResultList();
        } catch (HibernateException e) {
            throw new ApiException(ErrorCode.GENERAL_GET_FAILED);
        }
    }

    //only by id
    public Model getQuery(Long id, Class<Model> modelClass) {
        try (Session session = this.getSession()) {
            return session.get(modelClass, id);
        } catch (Exception e) {
            throw new ApiException(ErrorCode.GENERAL_GET_FAILED);
        }
    }

}
