package si.iitech.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MathUtilsTest {
    
    @Test
    public void testDynamicRound() {
        
        assertEquals(0.000000000045, MathUtils.dynamicRound(0.0000000000448051));
        assertEquals(0.0000000011, MathUtils.dynamicRound(0.000000001127));
        assertEquals(0.00000062, MathUtils.dynamicRound(0.000000621325));
        assertEquals(0.000062, MathUtils.dynamicRound(0.0000621325));
        assertEquals(0.0062, MathUtils.dynamicRound(0.00621325));
        assertEquals(0.621, MathUtils.dynamicRound(0.621325));
        assertEquals(62.62, MathUtils.dynamicRound(62.621325));
    }
    
    @Test
    public void testRound2DecimalPlaces() {
        assertEquals(99.12, MathUtils.round2DecimalPlaces(99.123567));
    }
}
