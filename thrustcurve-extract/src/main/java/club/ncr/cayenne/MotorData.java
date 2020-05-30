package club.ncr.cayenne;

import club.ncr.cayenne.auto._MotorData;
import org.apache.cayenne.access.DataContext;
import org.apache.cayenne.exp.Expression;
import org.apache.cayenne.exp.ExpressionFactory;
import org.apache.cayenne.query.Ordering;
import org.apache.cayenne.query.SelectQuery;
import org.apache.cayenne.query.SortOrder;
import org.thrustcurve.ThrustCurveAPI;
import org.thrustcurve.api.data.TCMotorData;
import org.thrustcurve.api.json.JsonObject;
import org.thrustcurve.api.json.JsonValue;

import java.io.IOException;
import java.util.List;

public class MotorData extends _MotorData {

	public static MotorData getByFileId(DataContext ctx, int fileId) {
		SelectQuery query= new SelectQuery(MotorData.class);

		query.andQualifier(MotorData.TC_FILE_ID.eq(fileId));

		List<MotorData> results = (List<MotorData>)ctx.performQuery(query);
		if (results.size() == 0) {
			return null;
		}
		return results.get(0);
	}
	public void update(TCMotorData data) {
		if (data.getData() != null) {
			try {
				setData(ThrustCurveAPI.decodeData(data.getData()));
				//md.setData(Base64.getDecoder().decode(data.getData()));
			} catch (IOException decodeError) {
				System.err.println(data.getData());
				decodeError.printStackTrace();
			} catch (IllegalArgumentException decodeError) {
				System.err.println(data.getData());
				decodeError.printStackTrace();
			}
		}
		setTcFileID(data.getFileId());
		setDataUrl(data.getDataUrl());
		setInfoUrl(data.getInfoUrl());
		setLicense(data.getLicense());
		setSource(data.getSource());
	}

	public static MotorData createNew(Motor motor, MotorDataFormat format, TCMotorData data, DataContext ctx) {
		MotorData record= new MotorData();
		ctx.registerNewObject(record);

		motor.addToData(record);
		record.setMotor(motor);
		record.setFormat(format);
		record.update(data);

		ctx.commitChanges();

		return record;

	}

	public JsonValue toJsonValue() {
		
		JsonObject json= new JsonObject();
		
		json.set("tc-info-url", getInfoUrl());
		json.set("tc-data-url", getDataUrl());
		
		json.set("format", getFormat().getName());
		// json.set("file-extension", getFormat().getFileExtension());
		
		json.set("source", getSource());
		json.set("tc-id", getTcFileID());
		
		return json;
		
	}

}
