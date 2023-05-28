package com.github.walkgs.lubes.utilities.time;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

@Getter
@RequiredArgsConstructor
public enum TimeUnit {

    YEAR("yyyy", 31104000000L),
    MONTH("MM",2592000000L),
    DAY("dd",86400000),
    HOUR("HH",3600000),
    MINUTE("mm",60000),
    SECOND("ss",1000),
    MILLISECOND("SSS", 1);

    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    private final String type;
    private final long base;

    public Moment now(ZoneId zoneId) {
        final String date = toString(zoneId);
        final String[] values = date.split(":");
        final TimeUnit[] units = TimeUnit.values();
        long time = 0;
        for (int index = 0; index < values.length; index++) {
            final TimeUnit unit = units[index];
            time += Long.parseLong(values[index]) * (unit.base / base);
        }
        return new SimpleMoment(zoneId, this, base, time);
    }

    public Moment now() {
        return now(DEFAULT_ZONE_ID);
    }

    public long get(ZoneId zoneId) {
        final Moment now = now(zoneId);
        final long[] date = now.toArray();
        return date[date.length - 1];
    }

    public long get() {
        return get(DEFAULT_ZONE_ID);
    }

    public long parse(int value) {
        return value * base;
    }

    public String toString(ZoneId zoneId) {
        final int rank = ordinal();
        final TimeUnit[] units = TimeUnit.values();
        final StringBuilder formatBuilder = new StringBuilder();
        final int size = units.length - (units.length - (rank + 1));
        for (int index = 0; index < size; index++)
            formatBuilder.append(units[index].type).append(index < (size - 1) ? ":" : "");
        final SimpleDateFormat format = new SimpleDateFormat(formatBuilder.toString());
        final Date localDate = Date.from(Instant.now().atZone(zoneId).toInstant());
        return format.format(localDate);
    }

    @Override
    public String toString() {
        return toString(DEFAULT_ZONE_ID);
    }

}
