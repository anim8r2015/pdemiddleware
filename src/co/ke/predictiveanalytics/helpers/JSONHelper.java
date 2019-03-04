package co.ke.predictiveanalytics.helpers;

import org.json.JSONObject;

import co.ke.predictiveanalytics.model.DataModel;

public class JSONHelper {
	
	public JSONHelper() {
		
	}
	
	public DataModel convertJSONToModel(JSONObject j) {
		DataModel dm = new DataModel();
		dm.setRecordid(j.getString("recordid"));
		dm.setRecdatetime(j.getString("recdatetime"));
		dm.setReccategory(j.getString("reccategory"));
		dm.setRecdirection(j.getString("recdirection"));
		dm.setReccontactname(j.getString("reccontactname"));
		dm.setReccontactnumber(j.getString("reccontactnumber"));
		dm.setRecsyncdatetime(j.getString("recsyncdatetime"));
		dm.setReclatitude(j.getString("reclatitude"));
		dm.setReclongitude(j.getString("reclongitude"));
		dm.setRecsyncedbool(j.getString("recsyncedbool"));
		dm.setRecduration(j.getString("recduration"));
		dm.setRecurl(j.getString("recurl"));
		dm.setRecvisits(j.getString("recvisits"));
		dm.setRecvisitdate(j.getString("recvisitdate"));
		dm.setRecbookmark(j.getString("recbookmark"));
		dm.setRectitle(j.getString("rectitle"));
		dm.setRecmessage(j.getString("recmessage"));
		dm.setRecbrowsercreated(j.getString("recbrowsercreated"));
		dm.setRecphoneid(j.getString("recphoneid"));
		return dm;
	}

}
