package de.xzise.bukkit.voteik;

public final class MutableInteger extends Number {

    private static final long serialVersionUID = 1877823395691583265L;
    
    public int value;
    
    public MutableInteger() {
        this.value = 0;
    }
    
    public MutableInteger(Number number) {
        this.value = number.intValue();
    }
    
    public MutableInteger(int value) {
        this.value = value;
    }
    
    public void setInteger(int value) {
        this.value = value;
    }
    
    public void inc() {
        this.value++;
    }
    
    @Override
    public int intValue() {
        return this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public float floatValue() {
        return this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }
    
}