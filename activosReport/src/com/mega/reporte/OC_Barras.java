package com.mega.reporte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;


import com.mega.modeling.analysis.Analysis;
import com.mega.modeling.analysis.AnalysisRenderingToolbox;
import com.mega.modeling.analysis.AnalysisReportToolbox;
import com.mega.modeling.analysis.content.Dataset;
import com.mega.modeling.analysis.content.Dimension;
import com.mega.modeling.analysis.content.MegaObjectProperty;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.Value;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.analysis.reports.pmo.InitiativeValueComparator;
import com.mega.modeling.api.MegaCOMObject;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.vocabulary.OCActivoInformacion;

public class OC_Barras {
	
	
	private List<MegaObject> selectedTaggs;
	private List<MegaObject> selectedActivos;
	private boolean CustomizeBar = false;
	private boolean OrderBardsAcs = true;
	private boolean horizontal = false;
	private String depth = "10";
	private String shape = "1";
	private MegaCollection objeto;

	private enum barSort {
		MinToMax, MaxToMin, None
	}

	private barSort sortMethod = barSort.None;

	OC_Barras(MegaRoot root,MegaCollection objeto, ReportContent reportContent, Analysis analysis,String titulo) {
		this.selectedActivos = new ArrayList<MegaObject>();
		this.selectedTaggs = new ArrayList<MegaObject>();
		this.objeto = objeto;
		
		// Get Context
		MegaCOMObject oContext = analysis.getMegaContext();
		short iContext = AnalysisRenderingToolbox.getGenerationMode(oContext);

		MegaCollection activosExistentes = this.objeto;
		for (MegaObject activo : activosExistentes) {
			this.selectedActivos.add(activo);
		}

		// Define Bar Order
		this.barSort();
		// sort the bar chart
		MegaObject[] sortedActivo = new MegaObject[this.selectedActivos.size()];
		String queryEtiquetas = "Select [MetaAttributeValue] Where [MetaAttribute].[Name] = \"OC_Riesgo_Activo\"";
		MegaCollection etiquetas = root.getSelection(queryEtiquetas);

		for (MegaObject etiqueta : etiquetas) {
			this.selectedTaggs.add(etiqueta);
		}

		int i = 0;
		for (MegaObject activo : this.selectedActivos) {
			sortedActivo[i] = activo;
			i++;
		}
		
		HerramientasGraficas HGraficas = new HerramientasGraficas(iContext);
		//Generar DataSet Global
		Dataset globalDataSet = new Dataset("");
		View globalView = HGraficas.generarTablaVistaReporte(reportContent,	globalDataSet, 2, 1);
		

		// Bars
		Dataset dataSetBarras = new Dataset("");
		reportContent.addDataset(dataSetBarras);
		Dimension dimension1barra = new Dimension("");
		Dimension dimension2barra = new Dimension("");

		dataSetBarras.addDimension(dimension1barra);
		dataSetBarras.addDimension(dimension2barra);


		// Bar chart view
		View viewBarras = new View(reportContent.addDataset(dataSetBarras));
		viewBarras.addRenderer(AnalysisReportToolbox.rBarChart);
		dimension2barra.setSize(1);
		this.generarBarras(sortedActivo,dimension1barra,dataSetBarras);
		
		
		
	    View tablesTitles = HGraficas.generarTitulo(titulo,"24",reportContent, "FFD700");
			    	    globalDataSet.addItem(tablesTitles,"1,1");
	    globalDataSet.addItem(viewBarras, "2,1");
		// Graphical Ratio
		this.setTechParam(viewBarras);
		
		reportContent.addView(globalView);

		
	}

	public void generarBarras(MegaObject[] ObjetoOrganizado, Dimension dimensionbarra, Dataset dataSetBarras ) {
		int j = 1;
		// fill data in graphic

		Map<String, Integer> valores = new HashMap<String, Integer>();

		for (final MegaObject tag : this.selectedTaggs) {
			this.sortBarChart(this.sortMethod, tag,ObjetoOrganizado);

			dimensionbarra.addItem(new MegaObjectProperty(tag.megaField(),
					"~H3l5fU1F3n80[Value Name]"));

			for (MegaObject activo :ObjetoOrganizado) {
								
				if (activo.getProp(OCActivoInformacion.MA_OCRiesgoActivo)
						.equals(tag.getProp(OC_MetaClase.MA_INTERNAL_VALUE))) {
					try {
						if (valores.get(tag
								.getProp("~H3l5fU1F3n80[Value Name]")) != null) {

							int NuevoValor = valores.get(tag
									.getProp("~H3l5fU1F3n80[Value Name]"));

							NuevoValor = NuevoValor + 1;
							valores.put(
									tag.getProp("~H3l5fU1F3n80[Value Name]"),
									NuevoValor);
						} else {
							valores.put(
									tag.getProp("~H3l5fU1F3n80[Value Name]"), 1);
						}
					} catch (NumberFormatException e) {

					}
				}
			}
			
		  
			
			if (valores.get(tag
					.getProp("~H3l5fU1F3n80[Value Name]")) != null){
			dataSetBarras.addItem(
					new Value(valores.get(tag
							.getProp("~H3l5fU1F3n80[Value Name]"))),
					Integer.toString(j) + ",1");
			j++;
			}
			
		}
	}
	
	private void barSort() {
		if (this.CustomizeBar) {
			if (this.OrderBardsAcs) {
				this.sortMethod = barSort.MinToMax;
			} else {
				this.sortMethod = barSort.MaxToMin;
			}
		} else {
			this.sortMethod = barSort.None;
		}

	}

	private void setTechParam(final View view) {
		view.addParameter("width", "850");
		view.addParameter("3Ddepth", this.depth);
		view.addParameter("barshape", this.shape);
		view.addParameter("fontangle", "45");
		if (!this.horizontal) {
			view.addParameter("direction", "vertical");
		} else {
			view.addParameter("direction", "horizontal");
		}
		view.addParameter("sectorstyle", "6");
		view.addParameter("colors", "");

	}

	private void sortBarChart(final barSort selectedSort, final MegaObject tag,
			final MegaObject[] sortedInitiative) {
		switch (selectedSort) {
		case MaxToMin: {
			Arrays.sort(sortedInitiative, new InitiativeValueComparator(tag,
					true));
			break;
		}
		case MinToMax: {
			Arrays.sort(sortedInitiative, new InitiativeValueComparator(tag,
					false));
			break;
		}
		case None: {
			break;
		}
		}

	}

}
