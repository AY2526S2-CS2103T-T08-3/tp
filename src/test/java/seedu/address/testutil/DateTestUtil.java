package seedu.address.testutil;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import seedu.address.logic.parser.ParserUtil;

/**
 * A utility class for expiry dates in test cases.
 */
public class DateTestUtil {

    /**
     * Fixed expiry date for test longevity.
     */
    public static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-04-08T00:00:00Z"),
            ZoneId.of("Asia/Singapore")
    );

    public static void useFixedClock() {
        ParserUtil.setClock(FIXED_CLOCK);
    }

    public static void resetClock() {
        ParserUtil.resetClock();
    }
}
