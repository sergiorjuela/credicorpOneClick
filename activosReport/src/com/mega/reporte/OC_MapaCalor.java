package com.mega.reporte;

import java.util.Map;

import javax.swing.JOptionPane;

import com.mega.modeling.analysis.Analysis;
import com.mega.modeling.analysis.AnalysisRenderingToolbox;
import com.mega.modeling.analysis.AnalysisReportToolbox;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.api.MegaCOMObject;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.erm.reports.heatmapsparameters.HCell;
import com.mega.soho.erm.reports.heatmapsparameters.Hmap;
import com.mega.vocabulary.OCActivoInformacion;

public class OC_MapaCalor {

	// Parametros Generales
	private Hmap atributo;
	private Map<String, HCell> atributoMap;
	private MegaCollection Objeto;
	private Boolean isExcel = false;

	OC_MapaCalor(MegaRoot root, MegaCollection Objeto,
			Analysis analysis, ReportContent reportContent) {

		// Get Context
		MegaCOMObject oContext = analysis.getMegaContext();
		short iContext = AnalysisRenderingToolbox.getGenerationMode(oContext);
		
		//JOptionPane.showMessageDialog(null,"contexto: " + iContext);

		// Excel case
		if (analysis.getDr().toString().contains("XLS")) {
			this.isExcel = true;
		}

		this.Objeto = Objeto;
		HerramientasGraficas Hgraficas = new HerramientasGraficas(iContext);

		this.atributo = Hgraficas.createHeatMapsCell(root,
				OCActivoInformacion.MA_OCActivoRepudio,
				OCActivoInformacion.MA_OCActivoUso,
				OCActivoInformacion.MA_OCRiesgoActivo);

		// Obtenemos los valores del Hmap riesgoActivoMap
		this.atributoMap = this.atributo.getMavsMap();

		// Llenamos las celdas del mapa
		this.LlenarCeldasMapaCalor(root);
		
		

		Boolean isHtml = (iContext != AnalysisReportToolbox.cRtfDeliverable)
				&& (!this.isExcel);
		Hgraficas.setViewsMapaCalor(root, reportContent, isHtml, this.atributo,iContext);

	}

	private void LlenarCeldasMapaCalor(MegaRoot root) {

		String queryRepudio = "Select [MetaAttributeValue] Where [MetaAttribute].[Name] = \"OC_Activo_Repudio\"";
		MegaCollection MetaAValuesRepudio = root.getSelection(queryRepudio);
		String queryUso = "Select [MetaAttributeValue] Where [MetaAttribute].[Name] = \"OC_Activo_Uso\"";
		MegaCollection MetaAValuesUso = root.getSelection(queryUso);
		for (MegaObject activo : Objeto) {

			if (activo.getProp(OCActivoInformacion.MA_OCActivoRepudio) != null
					&& activo.getProp(OCActivoInformacion.MA_OCActivoUso) != null) {
				String IdhxdRepudio = "";
				String IdhxdUso = "";
				for (int j = 1; j < MetaAValuesRepudio.count() + 1; j++) {
					if (activo.getProp(OCActivoInformacion.MA_OCActivoRepudio)
							.equals(MetaAValuesRepudio.item(j).getProp(
									OC_MetaClase.MA_INTERNAL_VALUE))) {
						IdhxdRepudio = MetaAValuesRepudio.item(j).getProp(
								OC_MetaClase.MA_HEX_ID_ABS);

						for (MegaObject valueUse : MetaAValuesUso) {
							if (activo
									.getProp(OCActivoInformacion.MA_OCActivoUso)
									.equals(valueUse
											.getProp(OC_MetaClase.MA_INTERNAL_VALUE))) {
								IdhxdUso = valueUse
										.getProp(OC_MetaClase.MA_HEX_ID_ABS);
							}
						}
						String llaveCelda = IdhxdRepudio + "," + IdhxdUso;

						if (this.atributoMap.get(llaveCelda) != null) {
							Map<String, String> valueContexts = atributo
									.getMeasureContexts();
							String nodeKey = "OC_"
									+ activo.getProp(OC_MetaClase.MA_HEX_ID_ABS)
									+ llaveCelda;
							String sameNode = valueContexts.get(nodeKey);
							if (sameNode != null) {

								valueContexts.put(nodeKey, "OC_" + llaveCelda);
								this.atributoMap.get(llaveCelda)
										.getValueContexts().remove(nodeKey);
								this.atributoMap.get(llaveCelda)
										.getValueContexts()
										.put(nodeKey, "OC_" + llaveCelda);

							} else {

								valueContexts.put(nodeKey, "OC_" + llaveCelda);
								this.atributoMap.get(llaveCelda)
										.getValueContexts()
										.put(nodeKey, "OC_" + llaveCelda);
							}

						}

					}

				}

			}
		}
	}

}
