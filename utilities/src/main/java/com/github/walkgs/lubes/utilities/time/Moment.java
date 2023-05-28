package com.github.walkgs.lubes.utilities.time;

import java.time.ZoneId;

public interface Moment {

    ZoneId getZoneId();
    long getBase();
    long getMoment();
    TimeUnit getType();

    void increment(long time);
    void increment(Moment moment);
    void decrement(long time);
    void decrement(Moment moment);

    default long toMillis() {
        return getMoment() / (TimeUnit.MILLISECOND.getBase() / getBase());
    }

    default long toSeconds() {
        return getMoment() / (TimeUnit.SECOND.getBase() / getBase());
    }

    default long toMinutes() {
        return getMoment() / (TimeUnit.MINUTE.getBase() / getBase());
    }

    default long toHours() {
        return getMoment() / (TimeUnit.HOUR.getBase() / getBase());
    }

    default long toDays() {
        return getMoment() / (TimeUnit.DAY.getBase() / getBase());
    }

    default long toMonths() {
        return getMoment() / (TimeUnit.MONTH.getBase() / getBase());
    }

    default long toYears() {
        return getMoment() / (TimeUnit.YEAR.getBase() / getBase());
    }

    default long get(TimeUnit unit) {
        final int ordinal = unit.ordinal();
        final long[] moment = toArray();
        return ordinal < moment.length ? moment[ordinal] : 0;
    }

    default long[] toArray() {
        long moment = getMoment();
        final long base = getBase();
        final int rank = getType().ordinal();
        final TimeUnit[] units = TimeUnit.values();
        final long[] results = new long[Math.min(rank + 1, units.length)];
        for (int index = 0; index < results.length; index++) {
            final TimeUnit unit = units[index];
            final long unitBase = unit.getBase() / base;
            final long result = moment / unitBase;
            moment -= result * unitBase;
            results[index] = result;
        }
        return results;
    }

}
