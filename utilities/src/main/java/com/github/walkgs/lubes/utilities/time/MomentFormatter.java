package com.github.walkgs.lubes.utilities.time;

import com.github.walkgs.lubes.utilities.formatters.Formatter;

import java.time.ZoneId;

public interface MomentFormatter extends Formatter<String, Moment> {

    String format(long moment);

    String format(ZoneId zoneId, long moment);
    String format(TimeUnit unit, long moment);
    String format(ZoneId zoneId, TimeUnit unit, long moment);

}