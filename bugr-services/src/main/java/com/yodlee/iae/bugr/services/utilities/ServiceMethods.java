package com.yodlee.iae.bugr.services.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.inject.Named;

import org.springframework.context.annotation.Scope;

import com.yodlee.iae.bugr.services.synthetic.util.SynUtil;

@Named
@Scope("prototype")
public class ServiceMethods {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	public double getImpactPercentage(String impactString) {
		String impactPercentageString = null;
		double impactPercentageFP = 0.0;
		if (impactString.contains("Impact Percentage:")) {
			impactPercentageString = impactString.substring(impactString.indexOf("Impact Percentage:") + 18).trim();
			if (impactPercentageString.matches("-?\\d+(\\.\\d+)?"))
				impactPercentageFP = Double.parseDouble(impactPercentageString);
			else {
				String[] impactsArray = impactString.split(";");
				for (String impactStr : impactsArray) {
					if (impactStr.contains("Impact Percentage:")) {
						impactPercentageString = impactStr.substring(impactStr.indexOf("Impact Percentage:") + 18)
								.trim();
						if (impactPercentageString.matches("-?\\d+(\\.\\d+)?"))
							impactPercentageFP = Double.parseDouble(impactPercentageString);
						break;
					}
				}
			}
		}
		return impactPercentageFP;
	}

	/**
	 * Returns updated At field from impact
	 * 
	 * @param impact
	 * @return
	 */
	public static Optional<Date> getUpdatedAtDate(String impact) {
		if (impact != null && impact.contains(SynUtil.UPDATED_AT)) {
			impact = impact.substring(impact.lastIndexOf(SynUtil.UPDATED_AT) + SynUtil.UPDATED_AT.length());
			try {//yyyy-MM-dd HH:mm
				return Optional.of(DATE_FORMAT.parse(impact));
			} catch (ParseException e) {
				//
			}
		}
		return Optional.ofNullable(null);
	}

}
