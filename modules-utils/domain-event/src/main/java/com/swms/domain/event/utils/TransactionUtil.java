package com.swms.domain.event.utils;


import com.swms.common.utils.exception.WmsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.Callable;

@Component
@ConditionalOnClass(DataSourceTransactionManager.class)
public class TransactionUtil {

    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 提交一个事务（带返回值）
     *
     * @param job      事务执行代码块
     * @param rollback 回滚代码块
     * @param <T>
     *
     * @return
     */
    public <T> T doTransaction(Callable<T> job, Runnable rollback) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            T t = job.call();
            transactionManager.commit(status);

            return t;
        } catch (Exception e) {
            transactionManager.rollback(status);
            if (rollback != null) {
                rollback.run();
            }
            throw new WmsException(e.getMessage());
        }
    }

    /**
     * 提交一个事务（无返回值）
     *
     * @param job      事务执行代码块
     * @param rollback 回滚代码块
     */
    public void doTransaction(Runnable job, Runnable rollback) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            job.run();
            transactionManager.commit(status);

        } catch (Exception e) {
            transactionManager.rollback(status);
            if (rollback != null) {
                rollback.run();
            }
            throw new WmsException(e.getMessage());
        }
    }


    public void doTransactionRequiresNew(Runnable job) {

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            job.run();
            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
            throw new WmsException(e.getMessage());
        }
    }

    /**
     * 提交一个事务（带返回值）
     *
     * @param job 事务执行代码块
     * @param <T>
     *
     * @return
     */
    public <T> T doTransaction(Callable<T> job) {
        return doTransaction(job, null);
    }

    /**
     * 提交一个事务
     *
     * @param job 事务执行代码块
     *
     * @return
     */
    public void doTransaction(Runnable job) {
        doTransaction(job, null);
    }


    /**
     * 判断当前线程是否处于事物中，是就等事物提交后再执行，否则直接执行
     *
     * @param runnable
     */
    public static void executeAfterCommit(Runnable runnable) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
                @Override
                public void afterCommit() {
                    runnable.run();
                }
            });
        } else {
            runnable.run();
        }
    }

}
