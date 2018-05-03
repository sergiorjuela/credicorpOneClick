package com.mega.reporte;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import com.mega.modeling.analysis.Analysis;
import com.mega.modeling.analysis.AnalysisParameter;
import com.mega.modeling.analysis.AnalysisPlugin;
import com.mega.modeling.analysis.AnalysisReportWithContext;
import com.mega.modeling.analysis.AnalysisParameter.AnalysisSimpleTypeValue;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.grcu.GRCDateUtility;
import com.mega.vocabulary.OCActivoInformacion;

/**
 * @author Sergio Orjuela
 */

public class ActivosMacro implements AnalysisReportWithContext {

	MegaRoot root;

	// Parametros Generales
	private MegaCollection oc_activos;
	private ReportContent reportContent;

	@Override
	public ReportContent getReportContent(final MegaRoot megaRoot,
			final Map<String, List<AnalysisParameter>> parameters,
			final Analysis analysis, final Object userData) {

		this.reportContent = new ReportContent("");

		// Initialization
		this.root = megaRoot;

		// Obtenemos los Activos del sistema en una coleccion
		this.oc_activos = this.root.getCollection(OCActivoInformacion.ID);

		// Inicializamos los parametros

		// Test para validar los codigos de entrada de los parametros

		/*
		 * for (final String paramType : parameters.keySet()) { for (final
		 * AnalysisParameter paramValue : parameters.get(paramType)) { for
		 * (final AnalysisSimpleTypeValue value : paramValue .getSimpleValues())
		 * {
		 * 
		 * JOptionPane.showMessageDialog(null, "llave: " + paramType +
		 * " valor "+value.getValue());
		 * 
		 * } } }
		 * 	JOptionPane.showMessageDialog(null, "Fecha Inicio: "
				+ parametros.getBeginDate().toString());
		JOptionPane.showMessageDialog(null, "Fecha Fin: "
				+ parametros.getEndDate().toString());
		 */

		IdentificacionParametros parametros = new IdentificacionParametros(this.root);
		parametros.AsignarParametros(parameters);
	
	
		// -------------------------- Generación Grafica de Barras
		//OC_Barras GraficaBarras = new OC_Barras(this.root, this.oc_activos,
			//	this.reportContent, analysis,
				//"Gráfica de Barras: Número de Activos por Nivel de Riesgo");

		// -------------------------- Generación Grafica de Torta

		//OC_Torta Torta = new OC_Torta(this.root, this.oc_activos,
			//	this.reportContent, analysis,
				//"Gráfica de Torta: Nivel de Riesgo");

		// -------------------------- Generación de Mapa de Calor
		OC_MapaCalor MapaCalor = new OC_MapaCalor(this.root, this.oc_activos,
				analysis, this.reportContent);

		return this.reportContent;

	}
}