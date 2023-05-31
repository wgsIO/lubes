package com.github.walkgs.lubes.utilities.time.formatters;

import com.github.walkgs.lubes.utilities.placeholders.PlaceHolder;
import com.github.walkgs.lubes.utilities.placeholders.SimplePlaceHolder;
import com.github.walkgs.lubes.utilities.time.Moment;
import com.github.walkgs.lubes.utilities.time.MomentFormatter;
import com.github.walkgs.lubes.utilities.time.SimpleMoment;
import com.github.walkgs.lubes.utilities.time.TimeUnit;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CalendarFormatter implements MomentFormatter {
    private static final PlaceHolder PLACE_HOLDER = new SimplePlaceHolder();
    private static final String[] CURLY_BRACKETS = new String[]{"{", "}"};

    private final String format;

    @Override
    public String format(Moment value) {
        final Map<String, Object> properties = new HashMap<>();
        final long[] moments = value.toArray();
        final TimeUnit[] units = TimeUnit.values();
        for (int index = 0; index < moments.length; index++) {
            final long time = moments[index];
            final TimeUnit unit = units[index];
            properties.put(CURLY_BRACKETS[0] + unit.name().toLowerCase() + CURLY_BRACKETS[1], (time < 9 ? "0" + time : time));
        }
        return PLACE_HOLDER.parse(format, properties);
    }

    @Override
    public String format(long moment) {
        return format(ZoneId.systemDefault(), moment);
    }

    @Override
    public String format(ZoneId zoneId, long moment) {
        return format(zoneId, TimeUnit.MILLISECOND, moment);
    }

    @Override
    public String format(TimeUnit unit, long moment) {
        return format(ZoneId.systemDefault(), unit, moment);
    }

    @Override
    public String format(ZoneId zoneId, TimeUnit unit, long moment) {
        return format(SimpleMoment.of(zoneId, unit, moment));
    }
}
