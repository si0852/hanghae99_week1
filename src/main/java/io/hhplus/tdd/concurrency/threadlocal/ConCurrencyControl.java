package io.hhplus.tdd.concurrency.threadlocal;

import io.hhplus.tdd.concurrency.ConCurrencyStatus;

public interface ConCurrencyControl {

    ConCurrencyStatus begin();

    void end(ConCurrencyStatus conCurrencyStatus);
}
