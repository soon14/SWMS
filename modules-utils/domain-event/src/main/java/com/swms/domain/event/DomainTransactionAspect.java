package com.swms.domain.event;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.utils.JsonUtils;
import com.swms.domain.event.domain.DomainEventPO;
import com.swms.domain.event.domain.repository.DomainEventPORepository;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Component
@Aspect
@ConditionalOnClass(JpaRepository.class)
@Slf4j
public class DomainTransactionAspect {

    @Autowired
    private DomainEventPORepository domainEventPORepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Around("@annotation(com.swms.domain.event.annotation.DomainTransaction)")
    public Object afterReturningServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {

        Object[] args = joinPoint.getArgs();
        if (args == null || args.length < 1) {
            return joinPoint.proceed();
        }

        Object arg = args[0];
        if (!(arg instanceof DomainEvent domainEvent)) {
            return joinPoint.proceed();
        }

        // record domain event
        DomainEventPO domainEventPO = new DomainEventPO();
        domainEventPO.setId(domainEvent.getEventId());
        domainEventPO.setEvent(JsonUtils.obj2String(arg));

        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            domainEventPORepository.save(domainEventPO);
            Object result = joinPoint.proceed();
            transactionManager.commit(status);
            return result;
        } catch (Exception e) {
            log.error("proceed error:", e);
            if (TransactionSynchronizationManager.isActualTransactionActive()) {
                transactionManager.rollback(status);
            }
            throw new WmsException(e.getMessage());
        }
    }
}
