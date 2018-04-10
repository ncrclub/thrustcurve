package club.ncr.motors;

import club.ncr.website.db.Motor;
import club.ncr.website.db.MotorCase;
import club.ncr.website.db.MotorCertOrg;
import club.ncr.website.db.MotorData;
import club.ncr.website.db.MotorDataFormat;
import club.ncr.website.db.MotorDiameter;
import club.ncr.website.db.MotorImpulse;
import club.ncr.website.db.MotorMfg;
import club.ncr.website.db.MotorName;
import club.ncr.website.db.MotorPropellant;
import club.ncr.website.db.MotorType;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.thrustcurve.api.data.TCMotorData;
import org.thrustcurve.api.data.TCMotorRecord;
import org.thrustcurve.api.search.SearchResults;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

public class MotorDbCache {

	private HashMap<String, MotorMfg> manufacturers= new HashMap<String, MotorMfg>();
	private HashMap<String, MotorCertOrg> certOrgs= new HashMap<String, MotorCertOrg>();
	private HashMap<String, MotorDiameter> diameters= new HashMap<String, MotorDiameter>();
	private HashMap<String, MotorName> names= new HashMap<String, MotorName>();
	private HashMap<String, MotorImpulse> impulses= new HashMap<String, MotorImpulse>();
	private HashMap<String, MotorPropellant> propellants= new HashMap<String, MotorPropellant>();
	private HashMap<String, MotorType> types= new HashMap<String, MotorType>();
	private HashMap<String, MotorCase> cases= new HashMap<String, MotorCase>();
	private HashMap<String, MotorDataFormat> formats= new HashMap<String, MotorDataFormat>();
	
	private DataContext ctx;
	private boolean readOnly= false;

	private static ServerRuntime runtime= new ServerRuntime("/cayenne.xml");

	public MotorDbCache(DataContext ctx) {

		if (ctx == null) {
			ctx= (DataContext)runtime.getContext();
		}
		
		this.ctx= ctx;
		
		// prime the impulses
		for (char impulse= 'A'; impulse <= 'R'; impulse++) {
			MotorImpulse.createNew(""+ impulse, ctx);
		}
		
		for (MotorMfg mfg : MotorMfg.get(ctx, null)) { manufacturers.put(mfg.getName(), mfg); }
		for (MotorCertOrg org : MotorCertOrg.get(ctx, null)) { certOrgs.put(org.getName(), org); }
		for (MotorDiameter diam : MotorDiameter.get(ctx, null)) { diameters.put(""+ diam.getDiameter(), diam); }
		for (MotorName name : MotorName.get(ctx, null)) { names.put(name.getName(), name); }
		for (MotorImpulse imp : MotorImpulse.get(ctx, null)) { impulses.put(imp.getImpulse(), imp); }
		for (MotorPropellant prop : MotorPropellant.get(ctx, null)) { propellants.put(prop.getName(), prop); }
		for (MotorType type : MotorType.get(ctx, null)) { types.put(type.getName(), type); }
		for (MotorCase motorCase : MotorCase.get(ctx, null)) { cases.put(motorCase.getName(), motorCase); }
		for (MotorDataFormat format : MotorDataFormat.get(ctx, null)) { formats.put(format.getName(), format); }
		

	}

	public MotorMfg getManufacturer(String name, String abbv) {
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

	public MotorDiameter getDiameter(int diam) {
		MotorDiameter record= diameters.get(""+ diam);
		
		if (record == null && !readOnly) {
			record= MotorDiameter.createNew(diam, ctx);
			diameters.put(""+ diam, record);
		}
		
		return record;
	}

	public MotorName getCommonName(String name, MotorImpulse impulse) {
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
		
		MotorPropellant record= propellants.get(name);
		
		if (record == null && !readOnly) {
			record= MotorPropellant.createNew(name, ctx);
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

	
	public Collection<MotorMfg> getManufacturers() { return manufacturers.values(); }
	public Collection<MotorCase> getMotorCases() { return cases.values(); }
	public Collection<MotorCertOrg> getCertOrganizations() { return certOrgs.values(); }
	public Collection<MotorDiameter> getDiameters() { return diameters.values(); }
	public Collection<MotorDataFormat> getDataFormats() { return formats.values(); }
	public Collection<MotorImpulse> getImpulses() { return impulses.values(); }
	public Collection<MotorName> getMotorNames() { return names.values(); }
	public Collection<MotorPropellant> getPropellants() { return propellants.values(); }
	public Collection<MotorType> getMotorTypes() { return types.values(); }

	

	
	public void update(SearchResults results) {
		
		for (TCMotorRecord record : results) {
			
			MotorMfg mfg= getManufacturer(record.getManufacturer(), record.getManufacturerAbbv());
			MotorCertOrg certOrg= getCertOrganization(record.getCertificationOrganization());
			MotorPropellant prop= getPropellant(record.getPropellant());
			MotorImpulse impulse= getImpulse(record.getImpulseClass());
			MotorName name= getCommonName(record.getCommonName(), impulse);
			MotorDiameter diameter= getDiameter((int)(Double.parseDouble(record.getDiameter())));
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
				
			// log.info(record.toXml());
			for (TCMotorData data : record.getData()) {
				MotorDataFormat format= getMotorDataFormat(data.getFormat());
				MotorData md= new MotorData();
				ctx.registerNewObject(md);
				
				motor.addToData(md);
				md.setMotor(motor);
				try {
					md.setData(new BASE64Decoder().decodeBuffer(data.getData()));
				} catch (IOException iox) {
				}
				md.setFormat(format);
				md.setDataUrl(data.getDataUrl());
				md.setInfoUrl(data.getInfoUrl());
				md.setLicense(data.getLicense());
				md.setSource(data.getSource());
			}
			
			ctx.commitChanges();
			
		}
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly= readOnly;
	}

}
