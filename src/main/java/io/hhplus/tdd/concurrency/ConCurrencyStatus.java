package io.hhplus.tdd.concurrency;

public class ConCurrencyStatus {

    private ConCurrencyId conCurrencyId;

    public ConCurrencyStatus(ConCurrencyId conCurrencyId) {
        this.conCurrencyId = conCurrencyId;
    }

    public ConCurrencyId getConCurrencyId() {
        return conCurrencyId;
    }
}
