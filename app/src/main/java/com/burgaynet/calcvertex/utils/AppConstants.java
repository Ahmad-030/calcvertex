package com.burgaynet.calcvertex.utils;

public class AppConstants {

    // Category names
    public static final String CAT_FINANCE  = "Finance";
    public static final String CAT_HEALTH   = "Health";
    public static final String CAT_MATH     = "Math";
    public static final String CAT_EVERYDAY = "Everyday";

    // Category accent colours
    public static final int COLOR_FINANCE  = 0xFF00D4AA; // teal
    public static final int COLOR_HEALTH   = 0xFFFF6B6B; // coral red
    public static final int COLOR_MATH     = 0xFF64B5F6; // sky blue
    public static final int COLOR_EVERYDAY = 0xFFFFBE0B; // amber

    public static int getCategoryColor(String category) {
        switch (category) {
            case CAT_FINANCE:  return COLOR_FINANCE;
            case CAT_HEALTH:   return COLOR_HEALTH;
            case CAT_MATH:     return COLOR_MATH;
            case CAT_EVERYDAY: return COLOR_EVERYDAY;
            default:           return COLOR_FINANCE;
        }
    }

    public static String getCategoryEmoji(String category) {
        switch (category) {
            case CAT_FINANCE:  return "💰";
            case CAT_HEALTH:   return "❤️";
            case CAT_MATH:     return "📐";
            case CAT_EVERYDAY: return "⚡";
            default:           return "🔧";
        }
    }
}
