package com.tooldynoapps.calcvertex.utils;

public class CalcHelper {

    // ─── FINANCE ─────────────────────────────────────────────
    public static double calcEmi(double principal, double annualRate, int months) {
        if (annualRate == 0) return principal / months;
        double r = annualRate / 12 / 100;
        return (principal * r * Math.pow(1 + r, months)) / (Math.pow(1 + r, months) - 1);
    }

    public static double[] calcProfitLoss(double costPrice, double sellingPrice) {
        double diff = sellingPrice - costPrice;
        double pct  = (diff / costPrice) * 100;
        return new double[]{diff, pct}; // [amount, percentage]
    }

    public static double[] calcGst(double amount, double gstPct) {
        double gstAmt = amount * gstPct / 100;
        double total  = amount + gstAmt;
        return new double[]{gstAmt, total};
    }

    public static double calcSimpleInterest(double p, double r, double t) {
        return (p * r * t) / 100;
    }

    public static double calcCompoundInterest(double p, double r, double t, int n) {
        return p * Math.pow(1 + r / (n * 100), n * t) - p;
    }

    public static double convertCurrency(double amount, double rate) {
        return amount * rate;
    }

    // ─── HEALTH ──────────────────────────────────────────────
    public static double calcBmi(double weightKg, double heightCm) {
        double hm = heightCm / 100;
        return weightKg / (hm * hm);
    }

    public static String getBmiCategory(double bmi) {
        if (bmi < 18.5) return "Underweight";
        if (bmi < 25.0) return "Normal";
        if (bmi < 30.0) return "Overweight";
        return "Obese";
    }

    public static double calcCalories(double weightKg, int minutesMet, double met) {
        // MET * weight(kg) * time(hours)
        return met * weightKg * (minutesMet / 60.0);
    }

    public static double calcWaterIntake(double weightKg, String activityLevel) {
        double base = weightKg * 0.033; // litres
        switch (activityLevel) {
            case "Moderate": return base + 0.5;
            case "Active":   return base + 1.0;
            case "Athlete":  return base + 1.5;
            default:         return base;
        }
    }

    // ─── MATH ────────────────────────────────────────────────
    public static double calcArea(String shape, double a, double b) {
        switch (shape) {
            case "Circle":    return Math.PI * a * a;
            case "Rectangle": return a * b;
            case "Triangle":  return 0.5 * a * b;
            case "Square":    return a * a;
            default:          return 0;
        }
    }

    public static double calcVolume(String shape, double a, double b) {
        switch (shape) {
            case "Cube":      return a * a * a;
            case "Cylinder":  return Math.PI * a * a * b;
            case "Sphere":    return (4.0 / 3) * Math.PI * a * a * a;
            case "Cuboid":    return a * b * b;
            default:          return 0;
        }
    }

    public static double percentOf(double pct, double value) { return pct * value / 100; }
    public static double whatPercent(double part, double total) { return (part / total) * 100; }
    public static double percentChange(double oldVal, double newVal) {
        return ((newVal - oldVal) / oldVal) * 100;
    }

    public static String decimalToBinary(long n)  { return Long.toBinaryString(n); }
    public static String decimalToOctal(long n)   { return Long.toOctalString(n); }
    public static String decimalToHex(long n)     { return Long.toHexString(n).toUpperCase(); }
    public static long   binaryToDecimal(String s){ return Long.parseLong(s, 2); }
    public static long   octalToDecimal(String s) { return Long.parseLong(s, 8); }
    public static long   hexToDecimal(String s)   { return Long.parseLong(s, 16); }

    // ─── EVERYDAY ────────────────────────────────────────────
    public static String calcAge(int birthYear, int birthMonth, int birthDay) {
        java.util.Calendar today = java.util.Calendar.getInstance();
        int years  = today.get(java.util.Calendar.YEAR) - birthYear;
        int months = today.get(java.util.Calendar.MONTH) + 1 - birthMonth;
        int days   = today.get(java.util.Calendar.DAY_OF_MONTH) - birthDay;
        if (days < 0)   { months--; days   += 30; }
        if (months < 0) { years--;  months += 12; }
        return years + " yrs, " + months + " mos, " + days + " days";
    }

    public static long dateDiffDays(java.util.Date d1, java.util.Date d2) {
        long diff = Math.abs(d2.getTime() - d1.getTime());
        return diff / (1000 * 60 * 60 * 24);
    }

    // Unit conversions
    public static double convertLength(double val, String from, String to) {
        // Convert to metres first
        double metres = val;
        switch (from) {
            case "km":  metres = val * 1000; break;
            case "cm":  metres = val / 100;  break;
            case "mm":  metres = val / 1000; break;
            case "mi":  metres = val * 1609.34; break;
            case "ft":  metres = val * 0.3048;  break;
            case "in":  metres = val * 0.0254;  break;
        }
        switch (to) {
            case "km":  return metres / 1000;
            case "cm":  return metres * 100;
            case "mm":  return metres * 1000;
            case "mi":  return metres / 1609.34;
            case "ft":  return metres / 0.3048;
            case "in":  return metres / 0.0254;
            default:    return metres;
        }
    }

    public static double convertWeight(double val, String from, String to) {
        double kg = val;
        switch (from) {
            case "g":   kg = val / 1000; break;
            case "lb":  kg = val * 0.453592; break;
            case "oz":  kg = val * 0.0283495; break;
        }
        switch (to) {
            case "g":   return kg * 1000;
            case "lb":  return kg / 0.453592;
            case "oz":  return kg / 0.0283495;
            default:    return kg;
        }
    }

    public static double convertTemp(double val, String from, String to) {
        double celsius = val;
        if (from.equals("F")) celsius = (val - 32) * 5 / 9;
        if (from.equals("K")) celsius = val - 273.15;
        if (to.equals("F")) return celsius * 9 / 5 + 32;
        if (to.equals("K")) return celsius + 273.15;
        return celsius;
    }

    public static String fmt(double v) {
        if (v == Math.floor(v) && !Double.isInfinite(v)) return String.format("%,.0f", v);
        return String.format("%,.4f", v).replaceAll("0+$", "").replaceAll("\\.$", "");
    }
}
