package forumate.controller;

import forumate.app.App;
import forumate.model.Facility;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class User_facility_details {
	
	@FXML protected Label facilityName;
	@FXML protected Label facilityClassification;
	@FXML protected Label closeDay;
	@FXML protected Label weekdaySttm;
	@FXML protected Label weekdayEndtm;
	@FXML protected Label weekendSttm;
	@FXML protected Label weekendEndtm;
	@FXML protected Label fee;
	@FXML protected Label capacity;
	@FXML protected Label additionalFacilityInfo;
	@FXML protected Label howApply;
	@FXML protected Label address;
	@FXML protected Label organizationName;
	@FXML protected Label phoneNumber;
	@FXML protected Label reservations;
	
	Facility facility;
	
	@FXML public void initialize() throws Exception {
		facility = (Facility) App.handle;
		
		facilityName.setText(facility.getFacilityName());
		facilityClassification.setText(facility.getFacilityClassification());
		closeDay.setText(facility.getCloseDay());
		weekdaySttm.setText(facility.getWeekdaySttm());
		weekdayEndtm.setText(facility.getWeekdayEndtm());
		weekendSttm.setText(facility.getWeekendSttm());
		weekendEndtm.setText(facility.getWeekendEndtm());
		fee.setText(facility.getFee());
		capacity.setText(facility.getCapacity());
		additionalFacilityInfo.setText(facility.getAdditionalFacilityInfo());
		howApply.setText(facility.getHowApply());
		address.setText(facility.getAddress());
		organizationName.setText(facility.getOrganizationName());
		phoneNumber.setText(facility.getPhoneNumber());
		reservations.setText(facility.getReservations());
		
		App.network.FacilityReservations(facility.getFacilityId());
	}

}
