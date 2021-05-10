package club.ncr.motors;

import club.ncr.cayenne.Motor;

import java.util.function.Predicate;

public class Predicates {
    public static Predicate<Motor> motorNameLike(String name) {
        if (name == null || "".equals(name.trim())) {
            return m -> true;
        }
        final String lowerCaseName = name.toLowerCase();
        return m -> (m.getBrandName() != null && m.getBrandName().toLowerCase().contains(lowerCaseName))
                || (m.getDesignation() != null && m.getDesignation().toLowerCase().contains(lowerCaseName))
                || (m.getCommonName() != null && m.getCommonName().getName().toLowerCase().contains(lowerCaseName))
                ;
    }
}
