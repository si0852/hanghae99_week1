package io.hhplus.tdd.concurrency;

import io.hhplus.tdd.concurrency.threadlocal.ThreadLocalConCurrencyControl;
import org.junit.jupiter.api.Test;

public class ThreadLocalConCurrencyControlTest {

    ThreadLocalConCurrencyControl con = new ThreadLocalConCurrencyControl();

    @Test
    void threadLocalTest() {
        ConCurrencyStatus conCurrencyStatus1 = con.begin();
        ConCurrencyStatus conCurrencyStatus2 = con.begin();
        con.end(conCurrencyStatus2);
        con.end(conCurrencyStatus1);
    }

}
