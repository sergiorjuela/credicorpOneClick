package com.mega.reporte;

import java.util.ArrayList;
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
import com.mega.modeling.analysis.content.Image;
import com.mega.modeling.analysis.content.MegaObjectProperty;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.Text;
import com.mega.modeling.analysis.content.Value;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.api.MegaCOMObject;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.vocabulary.OCActivoInformacion;

public class OC_Torta {

	private List<MegaObject> selectedTaggs;
	private List<MegaObject> selectedObjets;
	private MegaCollection objeto;
	private MegaRoot root;
	private String depth = "50";
	private String sector = "";
	private boolean percent = false;
	private int column = 2;
	private String titulo;
	private short iContext;


	private ReportContent reportContent;

	public OC_Torta(MegaRoot root, MegaCollection objeto,
			ReportContent reportContent, Analysis analysis, String titulo) {
		this.selectedObjets = new ArrayList<MegaObject>();
		this.selectedTaggs = new ArrayList<MegaObject>();
		this.objeto = objeto;
		this.reportContent = reportContent;
		this.titulo = titulo;
		this.root = root;

		// Obtenemos las Etiquetas de la Grafica y las insertamos en un
		// arraylist
		String queryEtiquetas = "Select [MetaAttributeValue] Where [MetaAttribute].[Name] = \"OC_Riesgo_Activo\"";
		// Obtenemos los Objetos Activos que vamos a contar y a graficar
		MegaCollection activosExistentes = this.objeto;
		for (MegaObject activo : activosExistentes) {
			this.selectedObjets.add(activo);
		}

		MegaCollection etiquetas = root.getSelection(queryEtiquetas);

		for (MegaObject etiqueta : etiquetas) {
			this.selectedTaggs.add(etiqueta);
		}

		MegaCOMObject oContext = analysis.getMegaContext();
		this.iContext = AnalysisRenderingToolbox.getGenerationMode(oContext);
		// context of the document
	    this.rtfReport();
	}

	private void rtfReport() {
		this.column = 1;
		// PIE
		Dataset dataSetTorta = new Dataset("");
		this.reportContent.addDataset(dataSetTorta);
		Dimension dimension = new Dimension("");
		dataSetTorta.addDimension(dimension);

		// No es una etiqueta de enumeración
		double totalcriteria = this.selectedObjets.size();
		// Para este ejemplo el criterio esta dado por el numero total de
		// objetos que vengan en la coleccion
		
		
	
		
		HerramientasGraficas Hgraficas = new HerramientasGraficas(this.iContext);
		View tituloTorta = Hgraficas.generarTitulo(this.titulo,"24",this.reportContent, "FFD700");

		
		this.reportContent.addView(tituloTorta);
		Map<String, Integer> valores = new HashMap<String, Integer>();
		
		int dataNumber = 1;
		for (final MegaObject tag : this.selectedTaggs) {
			
			for (MegaObject objeto : this.selectedObjets) {
				
				if (objeto.getProp(OCActivoInformacion.MA_OCRiesgoActivo)
						.equals(tag.getProp(OC_MetaClase.MA_INTERNAL_VALUE))) {
					try {
						if (valores.get(tag
								.getProp("~H3l5fU1F3n80[Value Name]")) != null) {

							int NuevoValor = valores.get(tag
									.getProp("~H3l5fU1F3n80[Value Name]"))+1;

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
					
			try {
				
				if (valores.get(tag
						.getProp("~H3l5fU1F3n80[Value Name]")) != null){
				double percentage = valores.get(tag.getProp("~H3l5fU1F3n80[Value Name]")) / totalcriteria;
				dataSetTorta.addItem(new Value(percentage * 100),String.valueOf(dataNumber));
				dimension.addItem(new MegaObjectProperty(tag.megaField(),"~H3l5fU1F3n80[Value Name]"));
				dataNumber = dataNumber +1;
				}
			
			} catch (NumberFormatException e) {

			}
		}

		// Pie view
		View v11 = new View(this.reportContent.addDataset(dataSetTorta));
		v11.addRenderer(AnalysisReportToolbox.rPieChart);
		this.reportContent.addView(v11);
		
		// Graphical Ratio
		this.setTechParam(v11);
	}


	private void setTechParam(final View view) {
		switch (this.column) {
		case 1: {
			view.addParameter("width", "800");
			view.addParameter("fontsize", "10");
			break;
		}
		case 2: {
			view.addParameter("width", "500");
			view.addParameter("fontsize", "7");
			break;
		}
		}
		view.addParameter("3Ddepth", this.depth);
		view.addParameter("explodesector", this.sector);
		if (this.percent) {
			view.addParameter("legendformat", "{label}:{percent}%");
		} else {
			view.addParameter("legendformat", "{label}");
		}
		view.addParameter("sectorstyle", "6");
		view.addParameter("colors", "");
	}

}
