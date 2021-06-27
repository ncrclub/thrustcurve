package club.ncr.dto;

import club.ncr.cayenne.model.MotorPropellant;

import java.util.Objects;


public class PropellantDTO implements Comparable<PropellantDTO> {

    public final Integer id;
    public final String name;
    public final String type;

    public PropellantDTO(MotorPropellant value) {
        this.id = null; // TOOD value.getId();
        this.name = value.getName();
        this.type = value.getType();
    }

    public PropellantDTO(String name, String type) {
        this.id = null;
        this.name = name;
        this.type = type;
    }

    @Override
    public int compareTo(PropellantDTO o) {
        if (o == null) { return 1; }

        int diff = type.compareTo(o.type);
        if (diff == 0) {
            return name.compareTo(o.name);
        }
        return diff;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PropellantDTO that = (PropellantDTO) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(type, that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type);
    }

    @Override
    public String toString() {
        return name +" ("+ type +")";
    }
}
