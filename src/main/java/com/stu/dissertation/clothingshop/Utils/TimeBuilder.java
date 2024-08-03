package com.stu.dissertation.clothingshop.Utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class TimeBuilder {
    private static LocalDateTime fromUnixTimestamp(long timestamp) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC);
    }
    // Tính thời gian còn lại tính từ bây giờ đến ngày hết hạn
    public static long betweenNow(long time) {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime thatTime = fromUnixTimestamp(time);
        return ChronoUnit.SECONDS.between(now, thatTime);
    }
}
