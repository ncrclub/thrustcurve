package club.ncr.dto;

import club.ncr.cayenne.Motor;
import club.ncr.cayenne.MotorCase;
import club.ncr.cayenne.MotorDiameter;
import club.ncr.cayenne.MotorImpulse;
import club.ncr.dto.motor.ImpulseDTO;

import java.util.Objects;


public class ImpulseMetadataDTO implements Comparable<ImpulseMetadataDTO> {

    public final ImpulseDTO impulse;
    public final double min_ns;
    public final double max_ns;

    public ImpulseMetadataDTO(ImpulseDTO impulse) {
        this.impulse = impulse;
        this.max_ns = impulse.maxNewtonSeconds;
        this.min_ns = impulse.minNewtonSeconds;
    }

    public ImpulseMetadataDTO(Motor motor) {
        this(motor.getImpulse());
    }

    public ImpulseMetadataDTO(MotorImpulse impulse) {
        this(ImpulseDTO.valueOf(impulse.getImpulse()));
    }

    @Override
    public int compareTo(ImpulseMetadataDTO o) {
        if (o == null) { return 1; }
        return impulse.compareTo(o.impulse);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImpulseMetadataDTO that = (ImpulseMetadataDTO) o;
        return impulse == that.impulse;
    }

    @Override
    public int hashCode() {
        return Objects.hash(impulse);
    }
}

