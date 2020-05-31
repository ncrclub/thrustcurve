package club.ncr.motors;

import club.ncr.cayenne.Motor;
import club.ncr.cayenne.MotorCase;
import club.ncr.cayenne.MotorCertOrg;
import club.ncr.cayenne.MotorData;
import club.ncr.cayenne.MotorDataFormat;
import club.ncr.cayenne.MotorDiameter;
import club.ncr.cayenne.MotorImpulse;
import club.ncr.cayenne.MotorMfg;
import club.ncr.cayenne.MotorName;
import club.ncr.cayenne.MotorPropellant;
import club.ncr.cayenne.MotorType;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.thrustcurve.TCApiClient;
import org.thrustcurve.ThrustCurveAPI;
import org.thrustcurve.api.data.TCMotorData;
import org.thrustcurve.api.data.TCMotorRecord;
import org.thrustcurve.api.search.SearchResults;

import java.io.IOException;
import java.io.PrintStream;
import java.util.*;


public class MotorDbCache {

	private Map<String, MotorMfg> manufacturers= new HashMap<String, MotorMfg>();
	private Map<String, MotorCertOrg> certOrgs= new HashMap<String, MotorCertOrg>();
	private Map<Float, MotorDiameter> diameters= new HashMap<>();
	private Map<String, MotorName> names= new HashMap<String, MotorName>();
	private Map<String, MotorImpulse> impulses= new HashMap<String, MotorImpulse>();
	private Map<String, MotorPropellant> propellants= new HashMap<String, MotorPropellant>();
	private Map<String, MotorType> types= new HashMap<String, MotorType>();
	private Map<String, MotorCase> cases= new HashMap<String, MotorCase>();
	private Map<String, MotorDataFormat> formats= new HashMap<String, MotorDataFormat>();

	private List<MotorDiameter> orderedDiameters= new LinkedList<>();
	private List<MotorImpulse> orderedImpulses= new LinkedList<>();
	private List<MotorMfg> orderedManufacturers= new LinkedList<>();

	private DataContext ctx;
	private boolean readOnly= false;

	private static ServerRuntime runtime;

	public MotorDbCache(String cayenneConfigFile) {

	    if (this.runtime == null) {
            this.runtime = ServerRuntime.builder().addConfig(cayenneConfigFile).build();
        }
		this.ctx= (DataContext)runtime.newContext();

		// prime the impulses
		for (char impulse= 'A'; impulse <= 'R'; impulse++) {
			MotorImpulse.createNew(""+ impulse, ctx);
		}


		for (MotorDiameter diam : MotorDiameter.get(ctx, null)) {
			diameters.put(diam.getDiameter(), diam);
			if (diam.getDiameter() != null) {
				orderedDiameters.add(diameters.get(diam.getDiameter()));
			}
		}

		for (MotorImpulse imp : MotorImpulse.get(ctx, null)) {
			impulses.put(imp.getImpulse(), imp);
			orderedImpulses.add(impulses.get(imp.getImpulse()));
		}

		for (MotorCertOrg org : MotorCertOrg.get(ctx, null)) { certOrgs.put(org.getName(), org); }
		for (MotorType type : MotorType.get(ctx, null)) { types.put(type.getName(), type); }
		for (MotorDataFormat format : MotorDataFormat.get(ctx, null)) { formats.put(format.getName(), format); }

		orderedDiameters.sort((a, b) -> (int)(100 * (a.getDiameter() - b.getDiameter())));
		orderedImpulses.sort((a, b) -> (a.getImpulse().compareTo(b.getImpulse())));

	}

	public Motor getMotor(String externalId) {
		return Motor.getByExternalId(externalId, ctx);
	}

	public MotorMfg getManufacturer(String name, String abbv) {
	    if (orderedManufacturers.isEmpty()) {
			for (MotorMfg mfg : MotorMfg.get(ctx, null)) {
				manufacturers.put(mfg.getName(), mfg);
				orderedManufacturers.add(manufacturers.get(mfg.getName()));
			}
			orderedManufacturers.sort((a, b) -> (a.getName().compareTo(b.getName())));
		}

		MotorMfg record= manufacturers.get(name);
		
		if (record == null && !readOnly) {
			record= MotorMfg.createNew(name, abbv, ctx);
			manufacturers.put(name, record);
		}
		
		return record;
	}
	
	public MotorCertOrg getCertOrganization(String org) {
		MotorCertOrg record= certOrgs.get(org);
		
		if (record == null && !readOnly) {
			record= MotorCertOrg.createNew(org, ctx);
			certOrgs.put(org, record);
		}
		
		return record;
	}

	public MotorDiameter getDiameter(float diam) {
		MotorDiameter record= diameters.get(diam);

		if (record == null && !readOnly) {
			record= MotorDiameter.createNew(diam, ctx);
			diameters.put(diam, record);
		}

		return record;
	}

	public MotorName getCommonName(String name, MotorImpulse impulse) {
	    if (names.isEmpty()) {
			for (MotorName motorName : MotorName.get(ctx, null)) {
				names.put(motorName.getName(), motorName);
			}
		}
		MotorName record= names.get(name);
		
		if (record == null && !readOnly) {
			record= MotorName.createNew(name, impulse, ctx);
			names.put(name, record);
		}
		
		return record;
	}

	public MotorImpulse getImpulse(String impulse) {
		MotorImpulse record= impulses.get(impulse);
		
		if (record == null && !readOnly) {
			record= MotorImpulse.createNew(impulse, ctx);
			impulses.put(impulse, record);
		}
		
		return record;
	}

	public MotorPropellant getPropellant(String name) {
	    if (propellants.isEmpty()) {
			for (MotorPropellant prop : MotorPropellant.get(ctx, null)) {
				propellants.put(prop.getName(), prop);
			}
		}

		MotorPropellant record= propellants.get(name);
		
		if (record == null && !readOnly) {
			String type = "AP";
		    if ("black powder".equalsIgnoreCase(name)) {
		    	type = "BP";
			} else if (name.toLowerCase().contains("nitrous")) {
		    	type = "HYBRID";
			}
			record= MotorPropellant.createNew(name, type, ctx);
			propellants.put(name, record);
		}
		
		return record;
	}

	public MotorType getType(String name) {
		
		MotorType record= types.get(name);
		
		if (record == null && !readOnly) {
			record= MotorType.createNew(name, ctx);
			types.put(name, record);
		}
		
		return record;
	}

	public MotorCase getMotorCase(String name) {
	    if (cases.isEmpty()) {
			for (MotorCase motorCase : MotorCase.get(ctx, null)) {
				cases.put(motorCase.getName(), motorCase);
			}
		}

		MotorCase record= cases.get(name);
		
		if (record == null && !readOnly) {
			record= MotorCase.createNew(name, ctx);
			cases.put(name, record);
		}
		
		return record;
	}


	public MotorDataFormat getMotorDataFormat(String name) {
		
		MotorDataFormat record= formats.get(name);
		
		if (record == null && !readOnly) {
			record= MotorDataFormat.createNew(name, null, ctx);
			formats.put(name, record);
		}
		
		return record;
	}

	
	public Collection<MotorMfg> getManufacturers() {
	    return MotorMfg.getNameMap(ctx, MotorMfg.NAME.isNotNull()).values();
	}
	public Collection<MotorCase> getMotorCases() { return cases.values(); }
	public Collection<MotorCertOrg> getCertOrganizations() { return certOrgs.values(); }
	public Collection<MotorDiameter> getDiameters() { return orderedDiameters; }
	public Collection<MotorDataFormat> getDataFormats() { return formats.values(); }
	public Collection<MotorImpulse> getImpulses() { return orderedImpulses; }
	public Collection<MotorName> getMotorNames() { return names.values(); }
	public Collection<MotorPropellant> getPropellants() { return propellants.values(); }
	public Collection<MotorType> getMotorTypes() { return types.values(); }


	/**
	 <pre>
	 weight    gross_weight	total_impulse_ns	thrust_avg	thrust_max	length	 impulse diameter
	 2.53      4.34           1.54                2.48        6.56        53.12    A       6
	 5.86      8.30           4.57                4.56        11.54       71.00    B       10.5
	 9.01      8.76           9.15                6.35        17.01       68.35    C       18
	 14.18     29.74          18.05               10.34       21.87       73.84    D       18
	 25.73     50.66          33.96               19.81       30.42       80.61    E       24
	 34.92     77.84          61.53               43.83       63.53       102.71   F       29
	 64.39     166.49         120.95              85.62       140.29      167.47	 G       29
	 131.56    308.12         238.43              198.14       264.24      272.92	 H       38
	 260.19    473.71         472.26              274.34       365.81      370.43	 I       54
	 454.27    878.01         896.98              417.52       585.24      515.26	 J       54
	 1010.54   2003.38        1908.90             700.79       977.69      540.33	 K       54
	 1959.88   3350.87        3697.10             1057.97       1163.28     702.09	 L       75
	 3625.47   5774.30        7058.64             1823.34       2278.61     872.45	 M       98
	 7493.22   11752.30       14277.46            3110.12       3729.56     1070.94  N       98
	 14454.09  28741.14       29945.05            6451.97       8418.04     1137.73  O      161
     </pre>
	 *
     *
     * @param weight
     * @param grossWeight
     * @param totalImpulse
     * @param thrustAvg
     * @param thrustMax
     * @param length
     * @param imp
     * @param diam
     * @return
     */
	public Motor research(double weight, double grossWeight, double burnTime, double totalImpulse, double thrustAvg, double thrustMax, double length, String imp, float diam) {
		MotorImpulse impulse = getImpulse(imp);
		MotorDiameter diameter = getDiameter(diam);
		MotorMfg mfg= getManufacturer("Research", "Research");
		MotorPropellant prop= getPropellant("Research");
		MotorName name= getCommonName(impulse.getImpulse() +" Research", impulse);
		MotorCase motorCase= getMotorCase("Research");
		MotorType type= getType("research");
		MotorCertOrg certOrg= getCertOrganization(null);

		Expression where = ExpressionFactory.matchExp(Motor.IMPULSE.getName(), impulse)
				.andExp(ExpressionFactory.matchExp(Motor.DIAMETER.getName(), diameter))
				.andExp(ExpressionFactory.matchExp(Motor.TYPE.getName(), type))
				.andExp(ExpressionFactory.matchExp(Motor.COMMON_NAME.getName(), name))
				;

		Motor motor = Motor.get(ctx, where).stream().findFirst().orElse(null);

		if (motor == null) {
			motor = Motor.createNew(
					"R" + impulse.getImpulse() +"_"+ diam
					, mfg
					, name
					, type
					, impulse
					, diameter
					, prop
					, certOrg
			);

			motor.setBurnTime(burnTime);
			motor.setGrossWeight(grossWeight);
			motor.setBrandName("Research Motor");
			motor.setWeight(weight);
			motor.setThrustAvg(thrustAvg);
			motor.setThrustMax(thrustMax);
			motor.setTotalImpulseNs(totalImpulse);
			motor.setDesignation("Research-"+ imp +"-"+ diam);
			motor.setLength(length);
			motor.setCase(motorCase);

			ctx.commitChanges();
		}
		return motor;
	}


	/**

	 SELECT CONCAT('research(',
	 ROUND(AVG(weight),2), 'd'
	 ,', ', ROUND(AVG(gross_weight),2), 'd'
	 ,', ', ROUND(AVG(burn_time),2), 'd'
	 ,', ', ROUND(AVG(total_impulse_ns),2), 'd'
	 ,', ', ROUND(AVG(thrust_avg),2), 'd'
	 ,', ', ROUND(AVG(thrust_max),2), 'd'
	 ,', ', ROUND(AVG(LENGTH),2), 'd'
	 ,', "', impulse.impulse, '"'
	 ,', ', diameter.diameter, 'f'
	 , ');')
	 FROM
	 motor
	 JOIN motor_impulse impulse ON (impulse.id = motor.impulseid)
	 JOIN motor_diameter diameter ON (diameter.id = motor.diameterid)
	 WHERE
	 gross_weight > 0 AND weight > 0 AND burn_time > 0 AND thrust_avg > 0 AND thrust_max > 0 AND LENGTH > 0 AND total_impulse_ns > 0
	 GROUP BY impulse.impulse, diameter.diameter

	 *
	 */
	public void createResearchMotors() {
		research(0.45d, 1.04d, 0.80d, 0.18d, 0.22d, 2.00d, 26.00d, "A", 6f);
		research(3.70d, 8.65d, 1.47d, 1.86d, 1.58d, 7.72d, 50.00d, "A", 13f);
		research(3.72d, 14.50d, 0.63d, 2.26d, 3.89d, 8.85d, 72.50d, "A", 18f);
		research(6.40d, 17.60d, 1.03d, 4.47d, 4.37d, 11.23d, 72.50d, "B", 18f);
		research(9.90d, 23.40d, 2.28d, 9.09d, 5.34d, 13.59d, 72.62d, "C", 18f);
		research(10.18d, 28.30d, 2.34d, 17.54d, 13.25d, 23.14d, 71.75d, "D", 18f);
		research(24.50d, 41.05d, 4.30d, 18.61d, 4.38d, 14.14d, 92.00d, "D", 20f);
		research(15.20d, 45.38d, 1.99d, 18.47d, 10.15d, 23.45d, 70.00d, "D", 24f);
		research(26.21d, 53.29d, 2.38d, 32.49d, 23.06d, 36.50d, 71.57d, "E", 24f);
		research(24.25d, 93.75d, 1.79d, 36.34d, 20.58d, 35.47d, 109.50d, "E", 29f);
		research(29.80d, 78.62d, 1.58d, 58.11d, 52.77d, 73.95d, 105.70d, "F", 24f);
		research(37.33d, 101.57d, 1.83d, 59.33d, 39.86d, 57.90d, 100.13d, "F", 29f);
		research(44.20d, 126.53d, 4.75d, 69.18d, 16.15d, 27.44d, 107.00d, "F", 32f);
		research(72.31d, 166.89d, 1.31d, 136.34d, 111.25d, 207.39d, 228.00d, "G", 24f);
		research(61.62d, 156.10d, 2.58d, 117.57d, 80.60d, 115.42d, 155.42d, "G", 29f);
		research(51.10d, 131.00d, 8.55d, 87.22d, 10.20d, 20.64d, 107.00d, "G", 32f);
		research(69.02d, 284.81d, 1.56d, 128.98d, 110.89d, 221.02d, 186.75d, "G", 38f);
		research(121.74d, 268.81d, 1.64d, 224.32d, 171.65d, 251.62d, 262.72d, "H", 29f);
		research(142.28d, 402.55d, 1.84d, 250.32d, 205.34d, 357.49d, 280.69d, "H", 38f);
		research(196.18d, 390.52d, 2.35d, 356.65d, 192.62d, 346.28d, 403.00d, "I", 29f);
		research(262.97d, 593.21d, 2.23d, 475.38d, 299.08d, 508.93d, 391.78d, "I", 38f);
		research(270.41d, 726.76d, 3.71d, 507.36d, 211.90d, 294.87d, 208.92d, "I", 54f);
		research(389.55d, 828.76d, 2.33d, 809.14d, 429.68d, 800.49d, 561.88d, "J", 38f);
		research(502.66d, 1178.99d, 3.31d, 995.51d, 428.33d, 720.10d, 509.93d, "J", 54f);
		research(656.00d, 1204.00d, 1.83d, 1402.00d, 828.00d, 1207.50d, 625.49d, "K", 38f);
		research(995.72d, 1841.02d, 3.33d, 1849.83d, 697.86d, 1040.00d, 540.01d, "K", 54f);
		research(1969.00d, 13035.00d, 3.78d, 2015.50d, 512.00d, 1038.00d, 908.00d, "K", 64f);
		research(1005.05d, 3019.10d, 3.68d, 2089.31d, 707.07d, 1122.69d, 580.52d, "K", 75f);
		research(562.50d, 3889.50d, 3.14d, 2248.00d, 741.00d, 2576.65d, 914.50d, "K", 76f);
		research(1287.80d, 3040.02d, 3.20d, 2441.58d, 1058.97d, 1236.82d, 276.40d, "K", 98f);
		research(1453.44d, 2515.21d, 3.19d, 2862.94d, 1080.52d, 1663.18d, 688.45d, "L", 54f);
		research(2200.00d, 2200.00d, 7.10d, 3152.00d, 600.00d, 2071.70d, 1066.00d, "L", 64f);
		research(2169.26d, 4186.21d, 4.11d, 3928.66d, 1149.51d, 1543.12d, 614.18d, "L", 75f);
		research(1768.46d, 4186.49d, 4.42d, 3944.90d, 1018.74d, 1628.60d, 796.00d, "L", 76f);
		research(2434.82d, 4703.80d, 5.71d, 4539.32d, 1337.76d, 1621.46d, 438.99d, "L", 98f);
		research(2600.00d, 4331.00d, 3.88d, 5363.00d, 1378.00d, 2270.00d, 1108.00d, "M", 54f);
		research(3288.00d, 3288.00d, 12.30d, 6463.49d, 900.00d, 1945.28d, 1828.00d, "M", 64f);
		research(3423.70d, 6053.59d, 4.25d, 6571.60d, 1772.50d, 2683.43d, 874.49d, "M", 75f);
		research(2994.53d, 5763.74d, 4.09d, 6725.20d, 1821.43d, 3431.27d, 925.50d, "M", 76f);
		research(4133.70d, 8012.08d, 5.18d, 8075.22d, 2128.94d, 2929.26d, 734.06d, "M", 98f);
		research(7725.65d, 12943.15d, 5.76d, 14574.20d, 3150.14d, 4262.50d, 1087.39d, "N", 98f);
		research(10930.00d, 16842.00d, 6.16d, 21062.20d, 3416.70d, 4750.30d, 1239.00d, "O", 98f);
		research(14471.00d, 23558.00d, 1.31d, 30794.00d, 23507.00d, 29275.00d, 1407.00d, "O", 132f);
		research(13233.00d, 32284.25d, 7.34d, 27228.81d, 4142.14d, 6849.80d, 1360.25d, "O", 152f);
		research(16132.40d, 29323.10d, 6.40d, 33724.82d, 5495.88d, 6234.78d, 885.60d, "O", 161f);
	}
	
	public void update(SearchResults results, PrintStream out) {


		for (TCMotorRecord record : results) {
			
			MotorMfg mfg= getManufacturer(record.getManufacturer(), record.getManufacturerAbbv());
			MotorCertOrg certOrg= getCertOrganization(record.getCertificationOrganization());
			MotorPropellant prop= getPropellant(record.getPropellant());
			MotorImpulse impulse= getImpulse(record.getImpulseClass());
			MotorName name= getCommonName(record.getCommonName(), impulse);
			MotorDiameter diameter= getDiameter(Float.parseFloat(record.getDiameter()));
			MotorCase motorCase= getMotorCase(record.getMotorCase());
			MotorType type= getType(record.getType());
			
			Motor motor= Motor.getByExternalId(record.getMotorId(), ctx);
			
			if (motor == null) {
				motor= Motor.createNew(record.getMotorId(), mfg, name, type, impulse, diameter, prop, certOrg);
			}
			
			motor.setBurnTime(record.getBurnTime());
			motor.setGrossWeight(record.getGrossWeight());
			motor.setBrandName(record.getBrandName());
			motor.setWeight(record.getWeight());
			motor.setThrustAvg(record.getAverageThrust());
			motor.setThrustMax(record.getMaxThrust());
			motor.setTotalImpulseNs(record.getTotalImpulse());
			motor.setDesignation(record.getDesignation());
			motor.setLength(record.getLength());
			motor.setCase(motorCase);
			motor.setLastUpdated(new Date());
			out.println("Motor Update: "+ motor.getImpulse().getImpulse() +" "+ motor.getBrandName() +" "+ motor.getDesignation() +" "+ motor.getLastUpdated());

			// log.info(record.toXml());
			for (TCMotorData data : record.getData()) {
				MotorDataFormat format= getMotorDataFormat(data.getFormat());
				MotorData md = MotorData.getByFileId(ctx, data.getFileId());

				if (md == null) {
					md = MotorData.createNew(motor, format, data, ctx);
				} else {
					md.update(data);
				}

			}
			
			ctx.commitChanges();
			
		}
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly= readOnly;
	}

}
