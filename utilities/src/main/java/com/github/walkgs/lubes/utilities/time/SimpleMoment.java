package com.github.walkgs.lubes.utilities.time;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class SimpleMoment implements Moment {

    private final ZoneId zoneId;
    private final TimeUnit type;
    private long moment;

    public static Moment of(ZoneId zoneId, TimeUnit type, long moment) {
        return new SimpleMoment(zoneId, type, moment);
    }

    @Override
    public long getBase() {
        return type.getBase();
    }

    @Override
    public void increment(long time) {
        this.moment += time;
    }

    @Override
    public void increment(Moment moment) {
        this.moment += moment.getMoment();
    }

    @Override
    public void decrement(long time) {
        this.moment -= time;
    }

    @Override
    public void decrement(Moment moment) {
        this.moment -= moment.getMoment();
    }
    @Override
    public String toString() {
        final long[] values = toArray();
        final int size = values.length;
        final StringBuilder builder = new StringBuilder();
        for (int index = 0; index < size; index++) {
            final long value = values[index];
            builder.append(value > 9 ? value : "0" + value).append(index < (size - 1) ? ":" : "");
        }
        return builder.toString();
    }

}
