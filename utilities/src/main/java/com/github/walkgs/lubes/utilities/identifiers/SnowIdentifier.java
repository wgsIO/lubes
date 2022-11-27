package com.github.walkgs.lubes.utilities.identifiers;

import com.github.walkgs.lubes.utilities.Applicable;
import com.github.walkgs.lubes.utilities.math.Math;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicLong;

public class SnowIdentifier implements Applicable<SnowIdentifier> {

    private static final long UNUSED_BITS = 1;
    private static final long TIMESTAMP_BITS = 41;
    private static final long DATACENTER_ID_BITS = 5;
    private static final long WORKER_ID_BITS = 5;
    private static final long SEQUENCE_BITS = 12;
    private static final long MAX_DATACENTER_ID = ~(-1 << DATACENTER_ID_BITS);
    private static final long MAX_WORKER_ID = ~(-1 << WORKER_ID_BITS);
    private static final long MAX_SEQUENCE = ~(-1 << SEQUENCE_BITS);
    private static final long TIMESTAMP_SHIFT = SEQUENCE_BITS + DATACENTER_ID_BITS + WORKER_ID_BITS;
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    private static final long EPOCH = 1661473483674L;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final String FORMAT = "%s, #%d, @(%d,%d)";

    private final AtomicLong waitCount = new AtomicLong(0);
    private final long datacenterId;
    private final long workerId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public synchronized long nextId() {
        long currentTimestamp = System.currentTimeMillis();
        assert !(currentTimestamp < lastTimestamp) : new IllegalArgumentException(String.format("Clock moved backwards. Refusing to generate id for %d milliseconds", lastTimestamp - currentTimestamp));
        if (currentTimestamp == lastTimestamp) {
            sequence = (sequence + 1) & MAX_SEQUENCE;
            if (sequence == 0)
                currentTimestamp = waitNextMillis(currentTimestamp);
        } else
            sequence = 0;
        lastTimestamp = currentTimestamp;
        return ((currentTimestamp - EPOCH) << TIMESTAMP_SHIFT) | (datacenterId << DATACENTER_ID_SHIFT) | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    public SnowIdentifier(long datacenterId, long workerId) {
        assert (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) :  new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATACENTER_ID));
        assert (workerId > MAX_WORKER_ID || workerId < 0) : new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        this.datacenterId = datacenterId;
        this.workerId = workerId;
    }

    public long getWaitCount() {
        return waitCount.get();
    }

    protected long waitNextMillis(long currTimestamp) {
        waitCount.incrementAndGet();
        while (currTimestamp <= lastTimestamp) {
            currTimestamp = System.currentTimeMillis();
        }
        return currTimestamp;
    }

    public long[] parseId(long id) {
        long[] result = new long[5];
        result[4] = ((id & Math.safeDiode(UNUSED_BITS, TIMESTAMP_BITS)) >> TIMESTAMP_SHIFT);
        result[0] = result[4] + EPOCH;
        result[1] = (id & Math.safeDiode(UNUSED_BITS + TIMESTAMP_BITS, DATACENTER_ID_BITS)) >> DATACENTER_ID_SHIFT;
        result[2] = (id & Math.safeDiode(UNUSED_BITS + TIMESTAMP_BITS + DATACENTER_ID_BITS, WORKER_ID_BITS)) >> WORKER_ID_SHIFT;
        result[3] = (id & Math.safeDiode(UNUSED_BITS + TIMESTAMP_BITS + DATACENTER_ID_BITS + WORKER_ID_BITS, SEQUENCE_BITS));
        return result;
    }

}
