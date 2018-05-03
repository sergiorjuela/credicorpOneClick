package com.mega.reporte;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.mega.modeling.analysis.AnalysisParameter;
import com.mega.modeling.analysis.AnalysisParameter.AnalysisSimpleTypeValue;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.grcu.GRCDateUtility;

public class IdentificacionParametros {

	private Date beginDate;
	private Date endDate;
	private MegaCollection orgUnits;
	
	
	
	public IdentificacionParametros(MegaRoot root){
		 this.orgUnits = root.getSelection("");
	}

	public void AsignarParametros(
			Map<String, List<AnalysisParameter>> parameters) {
		for (final String paramType : parameters.keySet()) {
			for (final AnalysisParameter paramValue : parameters.get(paramType)) {
				if (paramType.equals(OC_MetaClase.OC_REPORTE_BEGIN_DATE_PARAM)) {
					for (final AnalysisSimpleTypeValue value : paramValue
							.getSimpleValues()) {
						this.setBeginDate(GRCDateUtility
								.resetBeginDateTime((Date) value.getValue()));
					}
				} else if (paramType
						.equals(OC_MetaClase.OC_REPORTE_END_DATE_PARAM)) {
					for (final AnalysisSimpleTypeValue value : paramValue
							.getSimpleValues()) {
						this.setEndDate(GRCDateUtility
								.resetBeginDateTime((Date) value.getValue()));
					}
				} else if (paramType
						.equals(OC_MetaClase.OC_REPORTE_ORGUNIT_PARAM)) {
					for (final MegaObject value : paramValue.getValues()) {
						MegaObject classObject = value.getClassObject();						
						if (classObject.sameID(OC_MetaClase.MC_ORGUNIT)) {
							this.getOrgUnits().insert(value);
						}
						classObject.release();
					}
				}
			}
		}

		if (this.getEndDate() == null) {
			this.setEndDate(GRCDateUtility.resetEndDateTime(new Date()));
		}

		if (this.beginDate == null) {
			this.setBeginDate(GRCDateUtility.resetBeginDateTime(GRCDateUtility
					.getDate("1601/01/01")));
		}
	}

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(final Date _beginDate) {
		this.beginDate = _beginDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date _endDate) {
		this.endDate = _endDate;
	}

	public MegaCollection getOrgUnits() {
		return this.orgUnits;
	}

	public void setOrgUnits(final MegaCollection _orgUnits) {
		this.orgUnits = _orgUnits;
	}

}
