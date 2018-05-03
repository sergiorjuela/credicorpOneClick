package com.mega.reporte;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mega.modeling.analysis.AnalysisReportToolbox;
import com.mega.modeling.analysis.content.Dataset;
import com.mega.modeling.analysis.content.Dimension;
import com.mega.modeling.analysis.content.Image;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.Text;
import com.mega.modeling.analysis.content.Value;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.erm.reports.heatmapsparameters.HCell;
import com.mega.soho.erm.reports.heatmapsparameters.Hmap;
import com.mega.soho.erm.reports.heatmapsparameters.TablePresentationExport;
import com.mega.soho.erm.reports.riskidentification.IdentificationMethodsToolBox;
import com.mega.soho.erm.reports.riskidentification.IdentificationParameters;
import com.mega.soho.grcu.colors.GRCColorsUtility;
import com.mega.soho.grcu.constants.GRCConstants;
import com.mega.soho.grcu.constants.GRCMetaClass;
import com.mega.reporte.*;

public class HerramientasGraficas {

	private short iContext;

	public HerramientasGraficas(short iContext) {
		this.iContext = iContext;
	}

	public Hmap createHeatMapsCell(final MegaRoot root,
			final String firstMetAttribute, final String secondMetAttribute,
			final String resultMetaattribute) {

		Hmap hmap = new Hmap();
		Map<String, String> measureContexts = new HashMap<String, String>();
		hmap.setMeasureContexts(measureContexts);
		Map<String, HCell> heatMap = new LinkedHashMap<String, HCell>();
		MegaCollection mAttributes = root
				.getCollection(OC_MetaClase.MC_METAATTRIBUTE);
		MegaObject mgFirstMetAttribute = mAttributes.get(firstMetAttribute);
		MegaObject mgSecongMetAttribute = mAttributes.get(secondMetAttribute);
		MegaObject mgResultMetaattribute = mAttributes.get(resultMetaattribute);
		mAttributes.release();
		Map<Integer, String> resultMetaattributeColors = this
				.getMavsColors(mgResultMetaattribute);

		/*
		 * Imprimimos el Map con un Iterador Iterator <Integer> it =
		 * resultMetaattributeColors.keySet().iterator(); while(it.hasNext()){
		 * Integer key = it.next(); JOptionPane.showMessageDialog(null,"Clave: "
		 * + key + " -> Valor: " + resultMetaattributeColors.get(key)); }
		 */
		MegaCollection mavsFirstMetAttribute = mgFirstMetAttribute
				.getCollection(OC_MetaClase.MAE_META_ATTRIBUTE_VALUE, -1,
						"order");
		MegaCollection mavsSecongMetAttribute = mgSecongMetAttribute
				.getCollection(OC_MetaClase.MAE_META_ATTRIBUTE_VALUE, "order");
		String mgFirstMetAttributeName = this
				.getMetaAttributename(mgFirstMetAttribute
						.getProp(OC_MetaClase.MA_GUI_NAME));
		String mgSecongMetAttributeName = this
				.getMetaAttributename(mgSecongMetAttribute
						.getProp(OC_MetaClase.MA_GUI_NAME));
		String tableName = mgFirstMetAttributeName + "/"
				+ mgSecongMetAttributeName;
		mgFirstMetAttribute.release();
		mgSecongMetAttribute.release();
		hmap.setTableName(tableName);
		hmap.setMavFirstMaAttribute(mavsFirstMetAttribute);
		hmap.setMavSecondMaAttribute(mavsSecongMetAttribute);
		for (MegaObject mavFirstMetAttribute : mavsFirstMetAttribute) {
			for (MegaObject mavSecondMetAttribute : mavsSecongMetAttribute) {
				String heatMapCellKey = mavFirstMetAttribute
						.getProp(OC_MetaClase.MA_HEX_ID_ABS)
						+ ","
						+ mavSecondMetAttribute
								.getProp(OC_MetaClase.MA_HEX_ID_ABS);
				Integer intValueOfmavFirstMetAttribute = Integer
						.valueOf(mavFirstMetAttribute
								.getProp(OC_MetaClase.MA_INTERNAL_VALUE));
				Integer intValueOfmavSecondMetAttribute = Integer
						.valueOf(mavSecondMetAttribute
								.getProp(OC_MetaClase.MA_INTERNAL_VALUE));
				HCell hcell = new HCell();
				String cellColor = this.getCellColor(
						intValueOfmavFirstMetAttribute
								* intValueOfmavSecondMetAttribute,
						resultMetaattributeColors);
				hcell.setColor(cellColor);
				heatMap.put(heatMapCellKey, hcell);
				mavSecondMetAttribute.release();
			}
			mavFirstMetAttribute.release();
		}
		hmap.setMavsMap(heatMap);
		return hmap;
	}

	private String getMetaAttributename(final String metaName) {
		if (metaName.contains("\\")) {
			return metaName.substring(0, metaName.indexOf("\\"));
		}
		return metaName;
	}

	/**
	 * @param metaAttribute
	 * @return
	 */
	private Map<Integer, String> getMavsColors(final MegaObject metaAttribute) {
		Map<Integer, String> mavsColor = new LinkedHashMap<Integer, String>();
		MegaCollection metaAttributeValues = metaAttribute.getCollection(
				OC_MetaClase.MAE_META_ATTRIBUTE_VALUE, "order",
				OC_MetaClase.MA_INTERNAL_VALUE);

		/*
		 * Debug de prueba for (MegaObject metaAttributeValue :
		 * metaAttributeValues) { String colors =
		 * metaAttributeValue.getProp(GRCMetaAttribut.MA_PARAMETRISATION);
		 * JOptionPane.showMessageDialog(null,"valor: " + colors);
		 * 
		 * }
		 * 
		 * Debug de prueba
		 */

		for (MegaObject metaAttributeValue : metaAttributeValues) {
			String color = GRCColorsUtility.Color2Hex(GRCColorsUtility
					.getRGBfromParam(metaAttributeValue));
			Integer intValue = Integer.valueOf(metaAttributeValue
					.getProp(OC_MetaClase.MA_INTERNAL_VALUE));
			mavsColor.put(intValue, color);
			metaAttributeValue.release();
		}
		metaAttributeValues.release();
		return mavsColor;
	}

	/**
	 * @param result
	 * @param mavsColor
	 * @return
	 */
	private String getCellColor(final Integer result,
			final Map<Integer, String> mavsColor) {
		for (Integer internVal : mavsColor.keySet()) {
			if (result <= internVal) {
				return mavsColor.get(internVal);
			}
		}
		return "000000";
	}

	/*
	 * Crea dos objetos de tipo dimesión los cuales se los añade al dataset e
	 * instancia una nueva vista de diagrama a la cual le pasa el objeto
	 * reportcontent al cual previamente se le añada el data set
	 */
	View generarTablaVistaReporte(final ReportContent reportContent,
			final Dataset dataset, int filas, int columnas) {
		Dimension dimension1 = new Dimension("");
		Dimension dimension2 = new Dimension("");
		dataset.addDimension(dimension1);
		dataset.addDimension(dimension2);
		dimension1.setSize(filas);
		dimension2.setSize(columnas);
		View diagramView = new View(reportContent.addDataset(dataset));
		diagramView.addParameter("borderWidth", "0");
		diagramView.addParameter("tablewidth", "95%");
		diagramView.addRenderer(AnalysisReportToolbox.rTable);
		return diagramView;
	}

	View generarTitulo(final String titulo, String size,
			final ReportContent reportContent, String colorFondo) {

		final Dataset paramDatasettableTitle = new Dataset("");
		Dimension dimensiontableTitle1 = new Dimension("");
		Dimension dimensiontableTitle2 = new Dimension("");
		paramDatasettableTitle.addDimension(dimensiontableTitle1);
		paramDatasettableTitle.addDimension(dimensiontableTitle2);
		dimensiontableTitle1.setSize(1);
		dimensiontableTitle2.setSize(2);
		Text tex_hMapInherentRisk = new Text(
				"<div  style=\"text-align: center;font-size:" + size
						+ "px;font-family:arial\"> <b>" + titulo
						+ "</b> </div>", false);
		tex_hMapInherentRisk.isHtml(true);
		tex_hMapInherentRisk.getItemRenderer()
				.addParameter("color", colorFondo);
		paramDatasettableTitle.addItem(tex_hMapInherentRisk, "1,1");

		View diagramViewtableTitle = new View(
				reportContent.addDataset(paramDatasettableTitle));
		diagramViewtableTitle.addParameter("borderWidth", "0");
		diagramViewtableTitle.addParameter("tablewidth", "100%");
		diagramViewtableTitle.addRenderer(AnalysisReportToolbox.rTable);
		return diagramViewtableTitle;
	}

	public void setViewsMapaCalor(final MegaRoot root,
			final ReportContent reportContent, final Boolean isHtml,
			final Hmap riesgoActivo, final short iContext) {

		Dataset globalDataSet = new Dataset("");
		View globalView = this.generarTablaVistaReporte(reportContent,
				globalDataSet, 6, 1);
		reportContent.addView(globalView);

		// Generando Titulo Principal
		View tituloPrincipal = this.generarTitulo(
				"Perfil Consolidado de Riesgos", "24", reportContent, "FFFFFF");

		// Generando Imagemes del pie de pagina
		View ImagenesInferiores = this.generarImagenesInferiores(reportContent,
				iContext);

		// Generando Linea de Color Amarillo
		View LineaIntemerdia = this.generarLineaIntermedia(reportContent,
				"FFD700");

		// Generamos el reporte de contenido

		View contenidoReporte = this.generarContenidoReporte(reportContent,root, riesgoActivo, isHtml);
		
		//String Json =  " {\"labels\": [\"M1\", \"T222\", \"W333\", \"T44\", \"F\", \"S\", \"S\"], \"datasets\": [{ \"label\": \"Activos\",\"data\": [12, 19, 3, 17, 6, 3, 7], \"backgroundColor\": \"rgba(153,255,51,0.6)\" }, {  \"label\": \"Riesgos\", \"data\": [2, 29, 5, 5, 2, 3, 10], \"backgroundColor\":\"rgba(255,153,0,0.6)\" }] }";
		
		Text texto2 = new Text("<center><iframe class = \"diferente\" src=\"prueba3.html\" border=\"0\" height=\"40\" width=\"155\"></center>", false);
		texto2.isHtml(true);

		// Agregando Vistas al Dataset
		globalDataSet.addItem(tituloPrincipal, "1,1");
		globalDataSet.addItem(LineaIntemerdia, "2,1");
		globalDataSet.addItem(contenidoReporte, "3,1");
		globalDataSet.addItem(LineaIntemerdia, "4,1");
		globalDataSet.addItem(ImagenesInferiores, "5,1");
		globalDataSet.addItem(texto2, "6,1");

	}

	// Generando vistas, data set y tablas de las Imagemes del pie de pagina
	public View generarImagenesInferiores(ReportContent reportContent,
			short iContext) {

		Dataset consolidadoInformacion = new Dataset("");
		View v_consolidado_informacion = this.generarTablaVistaReporte(
				reportContent, consolidadoInformacion, 1, 3);

		Image ImagenFiduciaria = new Image(this.generarRuta("FiduciariaBogota.png"),
				"");
		ImagenFiduciaria.setIsAbsoluteURL();
		Image ImagenCremientoS = new Image(this.generarRuta("crecimientoSostenible.png"),
				"");
		ImagenCremientoS.setIsAbsoluteURL();
		Image ImagenYoPrevengo = new Image(this.generarRuta("Yoprevengo.png"),
				"");
		ImagenYoPrevengo.setIsAbsoluteURL();
		consolidadoInformacion.addItem(ImagenFiduciaria, "1,1");
		consolidadoInformacion.addItem(ImagenCremientoS, "1,2");
		consolidadoInformacion.addItem(ImagenYoPrevengo, "1,3");

		return v_consolidado_informacion;
	}

	public View generarLineaIntermedia(ReportContent reportContent, String color) {

		Dataset DataSetLinea = new Dataset("");
		View Linea = this.generarTablaVistaReporte(reportContent, DataSetLinea,
				1, 1);
		Text LineaIntermedia = new Text(
				"<div  style=\"text-align: center;font-size:12px;font-family:arial\"> <b>"
						+ "               " + "</b> </div>", false);
		LineaIntermedia.isHtml(true);
		LineaIntermedia.getItemRenderer().addParameter("color", color);
		DataSetLinea.addItem(LineaIntermedia, "1,1");

		return Linea;
	}

	public View generarContenidoReporte(ReportContent reportContent,
			MegaRoot root, Hmap riesgoActivo, Boolean isHtml) {

		// Generamos la vista contenedora de los contenidos del reporte
		Dataset GlobalContentido = new Dataset("");
		View vistaGlobalContentido = this.generarTablaVistaReporte(
				reportContent, GlobalContentido, 1, 2);

		// Generamos la vista del panel derecho (Información Riesgos
		// Criticos, Controles e Indicadores)
		Dataset ContentidoPanelDerecho = new Dataset("");
		View vistaPanelDerecho = this.generarTablaVistaReporte(reportContent,
				ContentidoPanelDerecho, 3, 1);

		vistaPanelDerecho.addParameter("color", "696969");
		vistaPanelDerecho.addParameter("tablewidth", "100%");

		// Generar VistasInternasPanelIzquiedo

		Map<Integer, View> vistas = new HashMap<Integer, View>();
		Map<Integer, Image> imagenes = new HashMap<Integer, Image>();
		Map<Integer, Text> valores = new HashMap<Integer, Text>();
		Map<Integer, Text> texto = new HashMap<Integer, Text>();

		// /Definimos los objetos propios del reporte

		Image Icono1 = new Image(this.generarRuta("riesgo.png"),"");
		Icono1.setIsAbsoluteURL();
		Text texto1 = new Text(
				"<div  style=\"text-align: center;font-size:12px;font-family:arial;\"><font color=\"#FFFFFF\"><b>"
						+ "Riesgos catalogados  críticos  porque su impacto inherente supera el apetito de riesgo."
						+ "</b></font></div>", false);
		Text valor1 = new Text(
				"<div  style=\"text-align: center;font-size:80px;font-family:arial\"><font color=\"#FFFFFF\"><b>"
						+ "18" + "</b><font> </div>", false);
		Image Icono2 = new Image(this.generarRuta("candado.png"),"");
		Icono2.setIsAbsoluteURL();
		Text texto2 = new Text(
				"<div  style=\"text-align: center;font-size:12px;font-family:arial\"><font color=\"#FFFFFF\"><b>"
						+ "Controles  clave fueron seleccionados para dar cobertura a los riesgos críticos."
						+ "</b> </font></div>", false);
		Text valor2 = new Text(
				"<div  style=\"text-align: center;font-size:80px;font-family:arial\"><font color=\"#FFFFFF\"><b>"
						+ "135" + "</b><font></div>", false);
		Image Icono3 = new Image(this.generarRuta("indicador.png"),"");
		Icono3.setIsAbsoluteURL();
		Text texto3 = new Text(
				"<div  style=\"text-align: center;font-size:12px;font-family:arial\"><font color=\"#FFFFFF\"> <b>"
						+ "Indicadores fueron definidos para llevar a cabo el monitoreo  de los riesgos críticos."
						+ "</b></font></div>", false);
		Text valor3 = new Text(
				"<div  style=\"text-align: center;font-size:80px;font-family:arial\"><font color=\"#FFFFFF\"><b>"
						+ "23" + "</b><font></div>", false);

		texto1.isHtml(true);
		valor1.isHtml(true);
		texto2.isHtml(true);
		valor2.isHtml(true);
		texto3.isHtml(true);
		valor3.isHtml(true);

		// LLenamos los map con los objetos creados
		imagenes.put(0, Icono1);
		valores.put(0, texto1);
		texto.put(0, valor1);

		imagenes.put(1, Icono2);
		valores.put(1, texto2);
		texto.put(1, valor2);

		imagenes.put(2, Icono3);
		valores.put(2, texto3);
		texto.put(2, valor3);

		// obtenemos las vistas con el contenido previamente generado

		vistas = this.generarVistaPanelDerecho(imagenes, texto, valores,
				reportContent);

		Integer j = 1;
		Iterator<Integer> it = vistas.keySet().iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			ContentidoPanelDerecho
					.addItem(vistas.get(key), j.toString() + ",1");
			j++;
		}
		// Generamos la vista del panel derecho

		View vistapanelizquierto = this.generarVistaPanelIzquierdo(root,
				reportContent, isHtml, riesgoActivo);

		// Agregamos las vistas de paneles

		GlobalContentido.addItem(vistapanelizquierto, "1,1");
		GlobalContentido.addItem(vistaPanelDerecho, "1,2");

		return vistaGlobalContentido;

	}

	public Map<Integer, View> generarVistaPanelDerecho(
			Map<Integer, Image> imagenes, Map<Integer, Text> texto,
			Map<Integer, Text> valores, ReportContent reportContent) {

		Map<Integer, View> VistasPanel = new HashMap<Integer, View>();

		if (texto.size() == valores.size() && texto.size() == imagenes.size()
				&& valores.size() == imagenes.size()) {

			for (int i = 0; i < texto.size(); i++) {

				// Generamos una vista principal para el panel
				Dataset ContentidoPanelDerecho = new Dataset("");
				View vistaPrincipalPanelDerecho = this
						.generarTablaVistaReporte(reportContent,
								ContentidoPanelDerecho, 3, 1);
				vistaPrincipalPanelDerecho.addParameter("color", "696969");
				vistaPrincipalPanelDerecho.addParameter("tablewidth", "100%");
				// Generamos una vista interna para la imagen y el valor
				Dataset contenidoInterno = new Dataset("");
				View vistaCampo = this.generarTablaVistaReporte(reportContent,
						contenidoInterno, 1, 2);
				contenidoInterno.addItem(texto.get(i), "1,1");
				contenidoInterno.addItem(imagenes.get(i), "1,2");
				vistaCampo.addParameter("color", "696969");
				vistaCampo.addParameter("tablewidth", "100%");
				// Generando Linea de Color Negro
				View lineaIntemerdia = this.generarLineaIntermedia(
						reportContent, "FFFFFF");
				ContentidoPanelDerecho.addItem(vistaCampo, "1,1");
				ContentidoPanelDerecho.addItem(lineaIntemerdia, "2,1");
				ContentidoPanelDerecho.addItem(valores.get(i), "3,1");

				// Agrega la vista al mapa de vistas
				VistasPanel.put(i, vistaPrincipalPanelDerecho);

			}

		}

		return VistasPanel;

	}

	public View generarVistaPanelIzquierdo(MegaRoot root,
			ReportContent reportContent, Boolean isHtml, Hmap riesgoActivo) {

		// Generamos una vista principal para el panel
		Dataset ContentidoPanelDerecho = new Dataset("");
		View vistaPrincipalPanelDerecho = this.generarTablaVistaReporte(
				reportContent, ContentidoPanelDerecho, 5, 1);

		// Generamos el sub-titulo

		View subtitulopanel = this.generarTitulo(
				"Riesgos Criticos y Acciones en Curso	", "18", reportContent,
				"FFFFFF");

		// Generamos la vista de la tabla del mapa de calor

		View tablamapacalor = this.generarMapaCalorPersonalizado(root,
				reportContent, riesgoActivo);

		// Generamos la tabla de información de riesgos alto nivel de exposición
		// y acciones

		View tablainformacion = this.generarTablaInformacion(root,
				reportContent);

		View lineaIntermentia = this.generarLineaIntermedia(reportContent,
				"FFFFFF");

		// Agregamos las vistas al panel derecho principal
		ContentidoPanelDerecho.addItem(subtitulopanel, "1,1");
		ContentidoPanelDerecho.addItem(lineaIntermentia, "2,1");
		ContentidoPanelDerecho.addItem(tablamapacalor, "3,1");
		ContentidoPanelDerecho.addItem(lineaIntermentia, "4,1");
		ContentidoPanelDerecho.addItem(tablainformacion, "5,1");

		return vistaPrincipalPanelDerecho;
	}

	public View generarTablaInformacion(MegaRoot root,
			ReportContent reportContent) {

		// Generamos un data Set y una vista principal
		Dataset tablaInformacion = new Dataset("");
		View vistatablaInformacion = this.generarTablaVistaReporte(
				reportContent, tablaInformacion, 3, 2);
		vistatablaInformacion.addParameter("backgroundcolor", "FFFFFF");
		vistatablaInformacion.addParameter("borderWidth", "1");
		vistatablaInformacion.addParameter("bordercolor", "FFC003");
		vistatablaInformacion.addParameter("aling", "center");

		// generamos componentes

		Image Icono1Titulo1 = new Image(this.generarRuta("iconotitulo1.png"),
				"");
		Icono1Titulo1.setIsAbsoluteURL();
		Image Icono1Titulo2 = new Image(this.generarRuta("iconotitulo2.png"),
				"");
		Icono1Titulo2.setIsAbsoluteURL();
		Text Titulo1 = new Text(
				"<div  style=\"text-align: center;font-size:12px;font-family:arial;\"><font color=\"#000000\"><b>"
						+ "Riesgos alto nivel de exposición."
						+ "</b></font></div>", false);
		Text Titulo2 = new Text(
				"<div  style=\"text-align: center;font-size:12px;font-family:arial;\"><font color=\"#000000\"><b>"
						+ "Acciones vinculadas al riesgo."
						+ "</b></font></div>", false);

		Text pos11 = new Text(
				"<div  style=\"text-align: left;font-size:12px;font-family:arial;\"><font color=\"#000000\"><b>"
						+ "R2 </b>Error o apropiación indebida de recursos en operaciones de pagos FICs, FA  y SOC."
						+ "</font></div>", false);
		Text pos12 = new Text(
				"<div  style=\"text-align: left;font-size:12px;font-family:arial;\"><font color=\"#000000\"><b>"
						+ "R13 </b>Error o inoportunidad en el pago de impuestos y presentación de medios magnéticos FICs, FA, y SOC."
						+ "</font></div>", false);

		Text pos21 = new Text(
				"<div  style=\"text-align: left;font-size:12px;font-family:arial;\"><font color=\"#000000\">"
						+ "* Proyectos Pagos YA.* Optimización de dispesión pagos (web service/Swift)."
						+ "</font></div>", false);
		Text pos22 = new Text(
				"<div  style=\"text-align: left;font-size:12px;font-family:arial;\"><font color=\"#000000\">"
						+ "* Redefinición estructura impuestos.* Revisión de políticas y procedimientos. * Automatización de Procesos."
						+ "</font></div>", false);

		Titulo1.isHtml(true);
		Titulo2.isHtml(true);
		pos11.isHtml(true);
		pos12.isHtml(true);
		pos21.isHtml(true);
		pos22.isHtml(true);

		// Generamos sub-vistas para agregar los titulos los iconos y el
		// contenido del reporte

		// Titulos

		Dataset encabezadoTablaTitulo1 = new Dataset("");
		View vistaencabezadoTablaTitulo1 = this.generarTablaVistaReporte(
				reportContent, encabezadoTablaTitulo1, 1, 2);
		vistaencabezadoTablaTitulo1.addParameter("color", "FFC003");
		encabezadoTablaTitulo1.addItem(Icono1Titulo1, "1,1");
		encabezadoTablaTitulo1.addItem(Titulo1, "1,2");

		Dataset encabezadoTablaTitulo2 = new Dataset("");
		View vistaencabezadoTablaTitulo2 = this.generarTablaVistaReporte(
				reportContent, encabezadoTablaTitulo2, 1, 2);
		vistaencabezadoTablaTitulo2.addParameter("color", "FFC003");
		encabezadoTablaTitulo2.addItem(Icono1Titulo2, "1,1");
		encabezadoTablaTitulo2.addItem(Titulo2, "1,2");

		// Contenido Tabla

		// pos 11

		Dataset contenidopos11 = new Dataset("");
		View vistacontenidopos11 = this.generarTablaVistaReporte(reportContent,
				contenidopos11, 1, 1);
		contenidopos11.addItem(pos11, "1,1");

		// pos 12

		Dataset contenidopos12 = new Dataset("");
		View vistacontenidopos12 = this.generarTablaVistaReporte(reportContent,
				contenidopos12, 1, 1);
		contenidopos12.addItem(pos12, "1,1");

		// pos 12

		Dataset contenidopos21 = new Dataset("");
		View vistacontenidopos21 = this.generarTablaVistaReporte(reportContent,
				contenidopos21, 1, 1);
		contenidopos21.addItem(pos21, "1,1");

		// pos 22

		Dataset contenidopos22 = new Dataset("");
		View vistacontenidopos22 = this.generarTablaVistaReporte(reportContent,
				contenidopos22, 1, 1);
		contenidopos22.addItem(pos22, "1,1");

		// Agregando todas las vistas a la tabla

		tablaInformacion.addItem(vistaencabezadoTablaTitulo1, "1,1");
		tablaInformacion.addItem(vistaencabezadoTablaTitulo2, "1,2");
		tablaInformacion.addItem(vistacontenidopos11, "2,1");
		tablaInformacion.addItem(vistacontenidopos12, "3,1");
		tablaInformacion.addItem(vistacontenidopos21, "2,2");
		tablaInformacion.addItem(vistacontenidopos22, "3,2");

		return vistatablaInformacion;
	}

	public View generarMapaCalorPersonalizado(MegaRoot root,
			ReportContent reportContent, Hmap riesgoActivo) {

		// Generamos una vista Global
		Dataset contenidoMapa = new Dataset("");
		View vistamapa = this.generarTablaVistaReporte(reportContent,
				contenidoMapa, 2, 1);
		vistamapa.addParameter("aling", "center");
		// Generamos un subtitulo del segundo Atributo
		Text segundoAtributo = new Text(
				"<div  style=\"text-align: center;font-size:12px;font-family:arial;\"><font color=\"#000000\">"
						+ "<b>Atributo: Nivel Uso.</b>" + "</font></div>",
				false);
		segundoAtributo.isHtml(true);

		// Generamos una nueva vista para ajustar los compoenenes de izquierda a
		// derecha

		Dataset contenidoInterno = new Dataset("");
		View vistacontenidoInterno = this.generarTablaVistaReporte(
				reportContent, contenidoInterno, 1, 3);
		vistacontenidoInterno.addParameter("aling", "center");
		// Generamos un subtitulo del primer Atributo
		Text primerAtributo = new Text(
				"<div  style=\" text-align: center ;font-size:12px;font-family:arial;\"><font color=\"#000000\">"
						+ "<b><br><br><br>Atributo: Nivel de Repudio."
						+ "</b></font></div>", false);
		primerAtributo.isHtml(true);

		// Generamos una image para ilustrar el nivel de riesgo
		Image nivelRiesgo = new Image(this.generarRuta("nivelriesgo.png"), "sfsdf");
		nivelRiesgo.setIsAbsoluteURL();

		View v_table_activos;
		TablaPresentacion tablaActivos = new TablaPresentacion();

		v_table_activos = tablaActivos.createReportTable(root, reportContent,
				riesgoActivo);
		v_table_activos.addParameter("drilldownaction",
				GRCConstants.HEATMAP_DRILLDOWN_METAWIZARD);

		// Agregamos los componentes al dataset interno
		contenidoInterno.addItem(primerAtributo, "1,1");

		contenidoInterno.addItem(v_table_activos, "1,2");
		contenidoInterno.addItem(nivelRiesgo, "1,3");

		// Agregamos los componentes al dataset global

		contenidoMapa.addItem(segundoAtributo, "1,1");
		contenidoMapa.addItem(vistacontenidoInterno, "2,1");

		return vistamapa;

	}
	
	
	//Gernera la ruta de acuerdo al contexto (Escrtorio o web)
	public String generarRuta(String ruta) {
		if (this.iContext == 3000) {
			ruta = OC_MetaClase.MA_RUTA_AB_IMAGENES +OC_MetaClase.MA_RUTA_RL_IMAGENES+ ruta;
		} else {
			ruta = OC_MetaClase.MA_RUTA_RL_IMAGENES+ruta;
		}
		return ruta;
	}
}
