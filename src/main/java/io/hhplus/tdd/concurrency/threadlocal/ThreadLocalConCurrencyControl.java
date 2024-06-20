package io.hhplus.tdd.concurrency.threadlocal;

import io.hhplus.tdd.concurrency.ConCurrencyId;
import io.hhplus.tdd.concurrency.ConCurrencyStatus;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ThreadLocalConCurrencyControl implements ConCurrencyControl{

    private ThreadLocal<ConCurrencyId> controlIdHolder = new ThreadLocal<>();

    @Override
    public ConCurrencyStatus begin() {
        syncControlId();
        ConCurrencyId conCurrencyId = controlIdHolder.get();
        log.info("conCurrencyId" + conCurrencyId);
        return new ConCurrencyStatus(conCurrencyId);
    }

    private void syncControlId() {
        ConCurrencyId controlId = controlIdHolder.get();
        if (controlId == null) {
            controlIdHolder.set(new ConCurrencyId());
        }else {
            controlIdHolder.set(controlId.createNextId());
        }
    }

    private void logTrace(ConCurrencyStatus conCurrencyStatus) {
        log.info("getConCurrencyId : " + conCurrencyStatus.getConCurrencyId());
    }

    @Override
    public void end(ConCurrencyStatus conCurrencyStatus) {
        logTrace(conCurrencyStatus);
        ConCurrencyId conCurrencyId = controlIdHolder.get();
        if (conCurrencyId.isFirstLevel()) {
            controlIdHolder.remove();
        }else {
            controlIdHolder.set(conCurrencyId.createPreviousId());
        }
    }
}
