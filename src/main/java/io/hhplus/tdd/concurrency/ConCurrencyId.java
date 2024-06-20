package io.hhplus.tdd.concurrency;

import java.util.UUID;

public class ConCurrencyId {

    private String id;
    private int level;

    public ConCurrencyId() {
        this.id = createId();
        this.level = 0;
    }

    private ConCurrencyId(String id, int level) {
        this.id = id;
        this.level = level;
    }

    private String createId(){
        return UUID.randomUUID().toString().substring(0, 8);
    }

    public ConCurrencyId createNextId(){
        return new ConCurrencyId(id, level+1);
    }

    public ConCurrencyId createPreviousId() {
        return new ConCurrencyId(id, level - 1);
    }

    public boolean isFirstLevel() {
        return level == 0;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }
}
