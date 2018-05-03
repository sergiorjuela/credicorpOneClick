package com.mega.credicorp.riesgos.reportes;

import java.text.DecimalFormat;

import javax.swing.JOptionPane;

import com.mega.modeling.analysis.content.Dataset;
import com.mega.modeling.analysis.content.Dimension;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.Text;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.grcu.GRCDataProcessing;
import com.mega.soho.grcu.colors.GRCColorsUtility;
import com.mega.toolkit.errmngt.ErrorLogFormater;

/*
 * Esta clase se generar para llevar a cabo la creación, consulta y llenado
 * de la tabla de criticidad que consolida el numero de riesgos, riesgos residuales
 * y los valores en dolares que representan estos objetos.
 */

public class TotalTable {
	protected Dataset dataset;
	protected Dimension dimx;
	protected Dimension dimy;
	protected int datasetId = 0;
	protected View view;
	public static final String PATTERN = "###,###.##";
	protected MegaRoot mgRoot;
	private PersistentLog log;

	public TotalTable() {
		try {
			// Obtiene la instancia del log
			this.log = PersistentLog.getInstance();
		} catch (Exception e1) {
			try {
				/*
				 * En caso de que ls instancia no exista, se inicializa a través
				 * del metodo getInstance al cual se le suministra la ruta del
				 * fichero.
				 */
				this.log = PersistentLog
						.getInstance("C:/ReportLog/HeatMap.txt");
			} catch (Exception e) {
				ErrorLogFormater err = new ErrorLogFormater();
				err.logError(e);
			}
		}
		// Asigna el objeto log obtenido al local privado log
		initLog(this.log);
	}

	/*
	 * Metodo que asigna un objeto log obtenido desde la clase PersistentLog al
	 * objeto local privado log
	 */
	protected void initLog(PersistentLog log) {
		this.log = log;
	}

	public View getTotalTableView(MegaRoot root, ReportContent reportContent,
			Criticidad criticidad, String referenceAtt) {

		/*
		 * Asigna el root al atributo local y almacena en el log una bandera
		 * para identificar el inicio de generación de la tabla
		 */
		this.mgRoot = root;
		try {
			try {
				this.log.record("getTotalTableView  \r\n ");
			} catch (NullPointerException localNullPointerException) {
			}
			
			/* 
			 * Se valida que el objeto criticidad sea diferente de nulo
			 * si no lo es se procede a generar las vista por el metodo
			 * viewTotalTable, la cual sera retornada al final.
			 */
			if (criticidad != null) {
				this.view = viewTotalTable(root, criticidad, reportContent,
						referenceAtt);
			}
		} catch (Exception localException) {
		}
		return this.view;
	}

	private View viewTotalTable(MegaRoot root, Criticidad criticidad,
			ReportContent res, String referenceAtt) {
		try {
			int mattSize = 0;
			
			/*
			 * Obtiene en un objeto de la coleccion de atributos el meta
			 * atributo de referencia 
			 */
			MegaObject mgReferenceMetaattribute = root.getCollection(
					"~O20000000Y10[MetaAttribute]", new Object[0]).get(
					referenceAtt);
			
			/*
			 * Obtiene los valores del meta atributo 
			 */
			MegaCollection mAttributeVals = mgReferenceMetaattribute
					.getCollection("~(0000000C830[MetaAttributeValue]",
							new Object[] { Integer.valueOf(-1), "order" });
			//cuenta el numero de valores del meta atributo
			mattSize = mAttributeVals.size();
			
			//Almacena el log el nombre del metaatributo y su numero de valores
			try {
				this.log.record("referenceAtt " + referenceAtt + " \r\n ");
			} catch (NullPointerException localNullPointerException) {
			}
			try {
				this.log.record("mAttributeVals.size() "
						+ mAttributeVals.size() + " \r\n ");
			} catch (NullPointerException localNullPointerException1) {
			}
			
			//Genera un titulo a la tabla
			Text tituloCriticidad = new Text(
					"<br/><br/><div  style=\background-color:E8E6DE;font-size:11px;font-family:arial;font-weight:bold\"> <b> Criticidad </b></div>",
					false);
			tituloCriticidad.isHtml(true);

			//Genera un dataset y dos dimenciones
			this.dataset = new Dataset(tituloCriticidad);
			this.dimx = new Dimension("");
			this.dimy = new Dimension("");
			
			//Asigna tamaños a las dimesiones y estas al dataset

			this.dimx.setSize(mattSize + 1);
			this.dimy.setSize(5);
			this.dataset.addDimension(this.dimx);
			this.dataset.addDimension(this.dimy);
			
			
			//Asigna los titulos en cada posición de y
			this.dimy.addItem(getTitlesTexts("No. Riesgos <br/> Inherente "));
			this.dimy.addItem(getTitlesTexts("US $ <br/> Inherente "));
			this.dimy.addItem(getTitlesTexts("No. Riesgos <br/> Residual"));
			this.dimy.addItem(getTitlesTexts("US $ <br/> Residual"));
			
			/*
			 * Genera los titulos del costado derecho de la tabla eje x
			 * de acuerdo al color y el nombre del meta atributo
			 */
			for (int x = 1; x <= mAttributeVals.size(); x++) {
				String valColor = "#"+GRCColorsUtility.Color2Hex(GRCColorsUtility.getRGBfromParam(mAttributeVals.get(Integer.valueOf(x))));
				JOptionPane.showMessageDialog(null,"color: "+valColor);
			    this.dimx.addItem(getTitlesTextsWithBackground(mAttributeVals.get(Integer.valueOf(x)).getProp("~n97OO26RrO00[Espaï¿½ol]"), valColor));
			}
			
			//Agrega una fila mas la cual totaliza los valores
			this.dimx.addItem(getTitlesTexts(GRCDataProcessing.getCodeTemplate(
					"~5ARhLtwZGr5Q[total]", this.mgRoot)));

			/*Agrega uno por uno los valores que van en la tabla de totales
			 * utilizando el objeto instanciado criticidad.
			 */
			
			this.dataset.addItem(getTextsFromInteger(criticidad.getNoRiesgosCriticos()),
					"1,1");
			this.dataset.addItem(
					getTextsFromDouble(criticidad.getUsCriticos()), "1,2");
			this.dataset.addItem(getTextsFromInteger(criticidad
					.getNoRiesgosCriticosResidual()), "1,3");
			this.dataset.addItem(
					getTextsFromDouble(criticidad.getUsCriticosResidual()),
					"1,4");

			this.dataset.addItem(
					getTextsFromInteger(criticidad.getNoRiesgosAltos()), "2,1");
			this.dataset.addItem(getTextsFromDouble(criticidad.getUsAltos()),
					"2,2");
			this.dataset
					.addItem(getTextsFromInteger(criticidad
							.getNoRiesgosAltosResidual()), "2,3");
			this.dataset.addItem(
					getTextsFromDouble(criticidad.getUsAltosResidual()), "2,4");

			this.dataset.addItem(
					getTextsFromInteger(criticidad.getNoRiesgosRelevantes()),
					"3,1");
			this.dataset.addItem(
					getTextsFromDouble(criticidad.getUsRelevantes()), "3,2");
			this.dataset.addItem(getTextsFromInteger(criticidad
					.getNoRiesgosRelevantesResidual()), "3,3");
			this.dataset.addItem(
					getTextsFromDouble(criticidad.getUsRelevantesResidual()),
					"3,4");

			this.dataset.addItem(
					getTextsFromInteger(criticidad.getNoRiesgosModerados()),
					"4,1");
			this.dataset.addItem(
					getTextsFromDouble(criticidad.getUsModerados()), "4,2");
			this.dataset.addItem(getTextsFromInteger(criticidad
					.getNoRiesgosModeradosResidual()), "4,3");
			this.dataset.addItem(
					getTextsFromDouble(criticidad.getUsModeradosResidual()),
					"4,4");

			this.dataset.addItem(
					getTextsFromInteger(criticidad.getNoRiesgosBajo()), "5,1");
			this.dataset.addItem(getTextsFromDouble(criticidad.getUsBajo()),
					"5,2");
			this.dataset.addItem(
					getTextsFromInteger(criticidad.getNoRiesgosBajoResidual()),
					"5,3");
			this.dataset.addItem(
					getTextsFromDouble(criticidad.getUsBajoResidual()), "5,4");

			this.dataset.addItem(
					getTextsFromInteger(criticidad.getNoRiesgosTotales()),
					"6,1");
			this.dataset.addItem(getTextsFromDouble(criticidad.getUsTotales()),
					"6,2");
			this.dataset.addItem(getTextsFromInteger(criticidad
					.getNoRiesgosTotalesResidual()), "6,3");
			this.dataset.addItem(
					getTextsFromDouble(criticidad.getUsTotalesResidual()),
					"6,4");
			
			//Le agrega el dataset al reportcontent
			this.datasetId = res.addDataset(this.dataset);
			/*
			 * crea una nueva vista le pasa el id del data set y se la asigna
			 * a la variable local, luego la renderiza como tabla y la retorna 
			 */
			this.view = new View(this.datasetId);
			this.view.addRenderer("~WCgdfMQqBzc1[table]");
		} catch (Exception e) {
			try {
				this.log.record(e.getMessage() + " \r\n ");
			} catch (NullPointerException localNullPointerException2) {
			}
		}
		return this.view;
	}

	//Genera un titulo a traves de un objeto text y le da un color predeterminado
	private Text getTitlesTexts(String str) {
		String html_str = "<div  style=\"font-size:11px;font-family:arial;font-weight:bold\"> "
				+ str + "</div>";
		Text tex_str = new Text(html_str, false);
		tex_str.isHtml(true);
		tex_str.getItemRenderer().addParameter("color", "E8E6DE");
		return tex_str;
	}

	//Genera un titulo a traves de un objeto text y le da un color que se pasa como parametro
	private Text getTitlesTextsWithBackground(String str, String valueColor) {
		String html_str = "<div  style=\"background-color:"
				+ valueColor
				+ ";font-size:11px;font-family:arial;font-weight:bold; align=left\"> "
				+ str + "</div>";
		Text tex_str = new Text(html_str, false);
		tex_str.isHtml(true);
		tex_str.getItemRenderer().addParameter("color",valueColor);
		return tex_str;
	}
	
	//Recibe un entero le da formato decimal y lo incluye en un objeto text
	private Text getTextsFromInteger(Integer str) {
		DecimalFormat myFormatter = new DecimalFormat("###,###.##");
		String html_str = "<div  style=\"font-size:11px;font-family:arial;font-weight:bold;text-align:right\">"
				+ myFormatter.format(str) + "</div>";
		Text tex_str = new Text(html_str, false);
		tex_str.isHtml(true);
		return tex_str;
	}
	
	//Recibe un double le da formato decimal y lo incluye en un objeto text
	private Text getTextsFromDouble(Double str) {
		int strInt = (int) Math.round(str.doubleValue());
		DecimalFormat myFormatter = new DecimalFormat("###,###.##");
		String html_str = "<div  style=\"font-size:11px;font-family:arial;font-weight:bold;text-align:right\">"
				+ myFormatter.format(strInt) + "</div>";
		Text tex_str = new Text(html_str, false);
		tex_str.isHtml(true);
		return tex_str;
	}

	
	/*
	 * Se genera un data se y una vista global se asignan las dimensiones y sus tamaños
	 * se genera una vista adicional denominada tablesreportViews a esta ultima se le 
	 * asignan parametros gráficos. Se genera una tabla presentanción que contiene un 
	 * mapa de calor este se agrega a la vista tablesreportViews y esta a la vista global
	 */
	public View getReferenceView(MegaRoot root, ReportContent reportContent,
			Hmap inherentRiskMap) {
		
		//Data Set y vista globales
		Dataset globalDataSet = new Dataset("");
		View globalView = getReportView(reportContent, globalDataSet);
		reportContent.addView(globalView);
		Dataset reportViews = new Dataset("");
		Dimension dimension1 = new Dimension("");
		Dimension dimension2 = new Dimension("");
		reportViews.addDimension(dimension1);
		reportViews.addDimension(dimension2);
		dimension1.setSize(1);
		dimension2.setSize(2);
		
		//Generación Vista tablesreportViews
		View tablesreportViews = new View(reportContent.addDataset(reportViews));
		tablesreportViews.addParameter("borderWidth", "0");
		tablesreportViews.addRenderer("~WCgdfMQqBzc1[table]");
		
		//Generación de titulo de la tabla
		View tablesTitles = getTitleView(" Impacto US$ ", reportContent);
		
		//Adicion de las vistas al dataset global
		globalDataSet.addItem(tablesTitles, "1,1");
		globalDataSet.addItem(tablesreportViews, "2,1");
		//generación de la tabla del mapa de calor
		TablePresentation tableInherentRisk = new TablePresentation();
		View v_tableInherentRisk = tableInherentRisk.createReferenceTable(root,	reportContent, inherentRiskMap);
		// se agrega la tabla de presentación a la vista tablesreportViews
		reportViews.addItem(v_tableInherentRisk, "1,1");

		//Es raro solo retorna la vista de la tabla.
		return tablesreportViews;
	}

	
	/*
	 * Genera una vista que contiene un titulo
	 */
	private View getTitleView(String hMapInherentRisk,
			ReportContent reportContent) {
		Dataset paramDatasettableTitle = new Dataset("");
		Dimension dimensiontableTitle1 = new Dimension("");
		Dimension dimensiontableTitle2 = new Dimension("");
		paramDatasettableTitle.addDimension(dimensiontableTitle1);
		paramDatasettableTitle.addDimension(dimensiontableTitle2);
		dimensiontableTitle1.setSize(1);
		dimensiontableTitle2.setSize(1);
		Text tex_hMapInherentRisk = new Text(
				"<div  style=\"text-align: center;font-size:9px;font-family:arial\"> <b>"
						+ hMapInherentRisk + "</b> </div>", false);
		tex_hMapInherentRisk.isHtml(true);
		tex_hMapInherentRisk.getItemRenderer().addParameter("color", "E8E6DE");
		paramDatasettableTitle.addItem(tex_hMapInherentRisk, "1,1");
		View diagramViewtableTitle = new View(
				reportContent.addDataset(paramDatasettableTitle));
		diagramViewtableTitle.addParameter("borderWidth", "0");
		diagramViewtableTitle.addParameter("tablewidth", "100%");
		diagramViewtableTitle.addRenderer("~WCgdfMQqBzc1[table]");
		return diagramViewtableTitle;
	}
	
	//Genera una vista de tipo tabla.

	private View getReportView(ReportContent reportContent, Dataset dataset) {
		Dimension dimension1 = new Dimension("");
		Dimension dimension2 = new Dimension("");
		dataset.addDimension(dimension1);
		dataset.addDimension(dimension2);
		dimension1.setSize(2);
		dimension2.setSize(1);
		View diagramView = new View(reportContent.addDataset(dataset));
		diagramView.addParameter("borderWidth", "0");
		diagramView.addRenderer("~WCgdfMQqBzc1[table]");
		return diagramView;
	}
}
