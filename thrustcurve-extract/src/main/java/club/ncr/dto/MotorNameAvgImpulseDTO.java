package club.ncr.dto;

import club.ncr.Tuple;
import club.ncr.cayenne.model.Motor;
import club.ncr.cayenne.model.MotorName;
import club.ncr.dto.motor.ImpulseDTO;

import java.util.Set;
import java.util.stream.Collectors;


public class MotorNameAvgImpulseDTO {

    public final String name;
    public final double avg_burn;
    public final double avg_total_impulse;
    public final double avg_weighted_impulse;
    public final double motors;
    public final double min_total_impulse;
    public final double max_total_impulse;
    public final double impulse_percentile;
    public final ImpulseDTO impulse_class;
    public final double impulse_class_minimum;
    public final double impulse_class_maximum;
    public final double avg_total_thrust;
    public final double max_thrust;

    public MotorNameAvgImpulseDTO(MotorName name) {
        this.name = name.getName();
        Set<Motor> sample = name.getMotors().stream()
                .filter(m -> m.getTotalImpulseNs() > 1 && m.getWeight() > 1)
                .collect(Collectors.toSet());

        this.impulse_class = ImpulseDTO.valueOf(name.getImpulse().getImpulse());
        this.impulse_class_maximum = impulse_class.maxNewtonSeconds;
        this.impulse_class_minimum = impulse_class.minNewtonSeconds;

        if (sample.size() == 0) {
            if (name.getMotors().size() == 0) {
                // no motors by this name
                // query for all motors in the impulse class
                sample = name.getImpulse().getMotors().stream()
                    .filter(m -> m.getTotalImpulseNs() > 1 && m.getWeight() > 1)
                    .collect(Collectors.toSet());
            } else {
                sample = name.getImpulse().getMotors(null, name.getMotors().get(0).getDiameter()).stream()
                        .filter(m -> m.getTotalImpulseNs() > 1 && m.getWeight() > 1 && m.getBurnTime() > 1)
                        .collect(Collectors.toSet());
            }
        } else if (sample.size() < 0) {
            Set<Motor> biggerSample = sample.stream()
                    .map(m -> new Tuple<>(m.getImpulse(), m.getDiameter()))
                    .distinct()
                    .flatMap(m -> m._1.getMotors(null, m._2).stream())
                    .filter(m -> m.getTotalImpulseNs() > 1 && m.getWeight() > 1 && m.getBurnTime() > 1)
                    .collect(Collectors.toSet());
            sample.addAll(biggerSample);
        }

        double totalNs = sample.stream().mapToDouble(m -> m.getTotalImpulseNs()).sum();
        double totalMass = sample.stream().mapToDouble(m -> m.getWeight()).sum();
        this.avg_burn = sample.stream().mapToDouble(m -> m.getBurnTime()).average().orElse(-1);
        double totalWeight = sample.stream().mapToDouble(m -> m.getTotalImpulseNs() * m.getBurnTime()).sum();
        double weightedSum = sample.stream().mapToDouble(m -> m.getTotalImpulseNs() * m.getWeight() * m.getBurnTime()).sum();
        this.avg_total_thrust = sample.stream().mapToDouble(m -> m.getBurnTime() * m.getThrustAvg()).average().orElse(-1);
        this.max_thrust = sample.stream().mapToDouble(m -> m.getThrustMax()).max().orElse(-1);

        this.min_total_impulse = sample.stream().mapToDouble(m -> m.getTotalImpulseNs()).min().orElse(-1);
        this.max_total_impulse = sample.stream().mapToDouble(m -> m.getTotalImpulseNs()).max().orElse(-1);
        this.avg_total_impulse = totalNs / sample.size();
        this.avg_weighted_impulse = weightedSum / totalWeight;
        this.motors = sample.size();
        this.impulse_percentile = (int) (100 * (this.avg_weighted_impulse / this.impulse_class_maximum));
    }

    public MotorNameAvgImpulseDTO(Motor motor) {
        this(motor.getCommonName());
    }

}
