package club.theabyss.server.global.utils.customGlyphs;

import java.util.HashMap;

public class AmberWatMargins {
    private final static HashMap<Integer, String> customMargins = new HashMap<>() {{
        put(0, "1");
        put(1, "2");
        put(2, "4");
        put(3, "8");
        put(4, "9");
        put(5, "A");
        put(6, "B");
        put(7, "C");
        put(8, "D");
        put(9, "E");
        put(10, "F");
    }};

    public static String marginToChars(float margin_) {
        int margin = Integer.signum((int) (margin_*2)) * Math.round(Math.abs(margin_));
        boolean isNegative = margin < 0;
        if (isNegative) margin = Math.abs(margin);
        HashMap<Integer, Integer> bits = new HashMap<>();
        for (int bit = 0; bit < 10; bit++) {
            bits.put(bit, (margin>>bit) & 1);
        }
        bits.put(10, margin>>10);

        StringBuilder result = new StringBuilder();
        for (int key : bits.keySet()) {
            if (bits.get(key) > 0)
                result.append((char) Integer.parseInt("F8" + (isNegative ? "0" : "2") + new String(new char[bits.get(key)]).replace("\0", customMargins.get(key)), 16));
        }
        return result.toString();
    }
}
