package com.swms.domain.event;

import com.swms.common.utils.exception.WmsException;
import com.swms.common.utils.utils.JsonUtils;
import com.swms.domain.event.domain.DomainEventPO;
import com.swms.domain.event.domain.repository.DomainEventPORepository;
import com.swms.domain.event.utils.TransactionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicReference;

@Component
@Aspect
@ConditionalOnClass(JpaRepository.class)
public class DomainTransactionAspect {

    @Autowired
    private DomainEventPORepository domainEventPORepository;

    @Autowired
    private TransactionUtil transactionUtil;

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

        AtomicReference<Object> proceed = new AtomicReference<>();
        transactionUtil.doTransactionRequiresNew(() -> {
            domainEventPORepository.save(domainEventPO);
            try {
                proceed.set(joinPoint.proceed());
            } catch (Throwable e) {
                throw new WmsException(e.getMessage());
            }
        });

        return proceed;
    }
}
