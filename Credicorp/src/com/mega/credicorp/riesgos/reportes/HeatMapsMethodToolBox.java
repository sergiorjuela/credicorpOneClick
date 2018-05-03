package com.mega.credicorp.riesgos.reportes;

import java.util.LinkedHashMap;
import java.util.Map;

import com.mega.modeling.analysis.content.Dataset;
import com.mega.modeling.analysis.content.Dimension;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.Text;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.erm.reports.riskidentification.IdentificationMethodsToolBox;
import com.mega.soho.erm.reports.riskidentification.IdentificationParameters;
import com.mega.soho.grcu.colors.GRCColorsUtility;


/*
 * Esta clase ofrece un conjunto de metodos graficos que permiten 
 * la generación de las celdas del mapa de calor, la consulta de colores
 * en hexadecimal, la generación de titulos y vista entre otros
 */
public class HeatMapsMethodToolBox
{
  @SuppressWarnings({ "unchecked", "rawtypes" })
  
  
 
public Hmap createHeatMapsCell(MegaRoot root, String firstMetAttribute, String secondMetAttribute, String resultMetaattribute, String tableName)
  {
    
	/*
	 * Instancia un objeto de tipo Hmap y cea un hashmap de tipo string --> HCell  
	 */
	Hmap hmap = new Hmap();
    Map<String, HCell> heatMap = new LinkedHashMap();
    
    //Obtiene una coleción con todos los meta atributos
    MegaCollection mAttributes = root.getCollection("~O20000000Y10[MetaAttribute]", new Object[0]);
    /*
     * Obtiene de la coleción previamente generada los tres meta atributos
     * involucrados en el mapa de riesgo. 
     */
    MegaObject mgFirstMetAttribute = mAttributes.get(firstMetAttribute);
    MegaObject mgSecongMetAttribute = mAttributes.get(secondMetAttribute);
    MegaObject mgResultMetaattribute = mAttributes.get(resultMetaattribute);
    mAttributes.release();
    /*
     * En un map de tipo integer --> string almacena los colores del mapa
     * utilizando el meta atributo resultado y los colores definidos en este 
     * en el campo parametrización
     */
    Map<Integer, String> resultMetaattributeColors = getMavsColors(mgResultMetaattribute);
    
    /*
     * Obtiene en dos colecciones los valores de los dos meta atributos a contrastar
     */
    MegaCollection mavsFirstMetAttribute = mgFirstMetAttribute.getCollection("~(0000000C830[MetaAttributeValue]", new Object[] { Integer.valueOf(-1), "order" });
    MegaCollection mavsSecongMetAttribute = mgSecongMetAttribute.getCollection("~(0000000C830[MetaAttributeValue]", new Object[] { "order" });
    
    mgFirstMetAttribute.release();
    mgSecongMetAttribute.release();
    
    /*
     * Utilizando el parametro de entrada tableName asigna a la instancia hmap el nombre 
     * de la tabla (mapa de calor)
     */
    hmap.setTableName(tableName);
    
    /*
     * Asigna las colecciones de valores de los meta atributos a contrastar en el hmap
     */
    hmap.setMavFirstMaAttribute(mavsFirstMetAttribute);
    hmap.setMavSecondMaAttribute(mavsSecongMetAttribute);
    
    
    for (MegaObject mavFirstMetAttribute : mavsFirstMetAttribute)
    {
      for (MegaObject mavSecondMetAttribute : mavsSecongMetAttribute)
      {
    	  
    	/*
    	 * Almacena en dos variables de tipo double el valor interno de los meta atributos
    	 */
        Double intValueOfmavFirstMetAttribute = Double.valueOf(mavFirstMetAttribute.getProp("~L20000000L50[Internal Value]"));
        Double intValueOfmavSecondMetAttribute = Double.valueOf(mavSecondMetAttribute.getProp("~L20000000L50[Internal Value]"));
       /*
        * Se instancia un objeto de tipo HCell 
        */
        
        HCell hcell = new HCell();
        
        /*
         * En una variable temporal temoResult se almacena el producto de los dos valores internos 
         * de los meta atributos
         */
        Double tempResult = Double.valueOf(intValueOfmavFirstMetAttribute.doubleValue() * intValueOfmavSecondMetAttribute.doubleValue());
       
        /*
         * Determina el color de la celda a traves del metodo getCellColor el cual recibe el valor del 
         * producto y el mapa de colores, posteriormente este string se asigna a la celda.
         */
        String cellColor = getCellColor(Integer.valueOf(tempResult.intValue()), resultMetaattributeColors);
        hcell.setColor(cellColor);
        
        /*
         * Se genera un tupla con los dos valores internos de los meta atributos  y dentro 
         * de esa coordenada  en el mapa heatMap se coloca el objeto celda que se acaba de instanciar.
         */
        String heatMapCellKey = intValueOfmavFirstMetAttribute + "," + intValueOfmavSecondMetAttribute;
        heatMap.put(heatMapCellKey, hcell);
        mavSecondMetAttribute.release();
      }
      mavFirstMetAttribute.release();
    }
    /*
     * finalmente al objeto hmap se le asigna a traves del metodo setMavsMap
     * el mapa de string --> celda y se retorna el objeto hmap.
     */
    hmap.setMavsMap(heatMap);
    return hmap;
  }
  
  @SuppressWarnings("rawtypes")
  
  
  
private Map<Integer, String> getMavsColors(MegaObject metaAttribute)
  {
    @SuppressWarnings("unchecked")
    
    //Define un mapa de colores de tipo integer --> string
	Map<Integer, String> mavsColor = new LinkedHashMap();
    /*
     * Consulta la coleccion de mata valores del atributo y los ordena 
     * por valor interno 
     */
    MegaCollection metaAttributeValues = metaAttribute.getCollection("~(0000000C830[MetaAttributeValue]", new Object[] { "order", "~L20000000L50[Internal Value]" });
   
    /*
     * 
     */
    for (MegaObject metaAttributeValue : metaAttributeValues)
    {
      
      /*
       * Obtiene en una variable string la representación hexadecimal a traves del
       * campo parametrización del valor del meta atributo
       */
      String color = GRCColorsUtility.Color2Hex(GRCColorsUtility.getRGBfromParam(metaAttributeValue));
      /*
       * Define un variable intValue que almacena la llave con la que agregara el
       * color al mapa, en este caso el valor interno del meta atributo.
       */
      Integer intValue = Integer.valueOf(metaAttributeValue.getProp("~L20000000L50[Internal Value]"));
      // guarda en el mapa de colores la llave (valor interno) con el color (representación Hexa)
      mavsColor.put(intValue, color);
      metaAttributeValue.release();
    }
    metaAttributeValues.release();
    //retorna el map de colores
    return mavsColor;
  }
  
  
  /*
   * Toma un valor entero y un mapa de valores tipo integer--> string, 
   * el valor entero resultado de la multiplicación debe estar dentro de
   * la escala de enteros llave del mapa, por ello, se compara el producto
   * con los valores de las llaves del mapa cuando sea menor igual indica
   * que esta en ese rango y se retorna el color de la almacena en la llave
   * que fue objeto de comparación
   * 
   */
  private String getCellColor(Integer result, Map<Integer, String> mavsColor)
  {
    for (Integer internVal : mavsColor.keySet()) {
      if (result.intValue() <= internVal.intValue()) {
        return (String)mavsColor.get(internVal);
      }
    }
    return "000000";
  }
  
  
  /*
   * Genera una vista goblal de tipo tabla que cuenta con dos dimesiones
   */
  private View getReportView(ReportContent reportContent, Dataset dataset)
  {
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
  
  /*
   * Genera una vista con dos dimesiones que retorna los titulos de las tablas
   * o mapas de calor
   */
  private View getTitlesView(String hMapInherentRisk, String hMapnetRisk, ReportContent reportContent)
  {
    Dataset paramDatasettableTitle = new Dataset("");
    Dimension dimensiontableTitle1 = new Dimension("");
    Dimension dimensiontableTitle2 = new Dimension("");
    paramDatasettableTitle.addDimension(dimensiontableTitle1);
    paramDatasettableTitle.addDimension(dimensiontableTitle2);
    dimensiontableTitle1.setSize(1);
    dimensiontableTitle2.setSize(2);
    Text tex_hMapInherentRisk = new Text("<div  style=\"text-align: center;font-size:9px;font-family:arial\"> <b>" + hMapInherentRisk + "</b> </div>", false);
    tex_hMapInherentRisk.isHtml(true);
    tex_hMapInherentRisk.getItemRenderer().addParameter("color", "E8E6DE");
    Text tex_hMapnetRisk = new Text("<div  style=\"text-align: center;font-size:9px;font-family:arial\"> <b>" + hMapnetRisk + "</b> </div>", false);
    tex_hMapnetRisk.isHtml(true);
    tex_hMapnetRisk.getItemRenderer().addParameter("color", "E8E6DE");
    paramDatasettableTitle.addItem(tex_hMapInherentRisk, "1,1");
    paramDatasettableTitle.addItem(tex_hMapnetRisk, "1,2");
    View diagramViewtableTitle = new View(reportContent.addDataset(paramDatasettableTitle));
    diagramViewtableTitle.addParameter("borderWidth", "0");
    diagramViewtableTitle.addParameter("tablewidth", "100%");
    diagramViewtableTitle.addRenderer("~WCgdfMQqBzc1[table]");
    return diagramViewtableTitle;
  }
  
  /*
   * Haciendo uso del metodo getReportView obtiene una vista global, porteriomente
   * genera dos nuevas vistas una en la cual almacenara las tablas del mapa de calor
   * y otra con los titulos haciendo uso del metodo tablesTitles, luego genera las
   * tablas de presentación de los mapas de calor las agrega a su respectiva vista 
   * y finalmente agrega la vista de tablas y titulos a la vista global
   */
  public void setViews(MegaRoot root, ReportContent reportContent, Boolean isHtml, Hmap inherentRiskMap, Hmap netRiskMap)
  {
    if (isHtml.booleanValue())
    {
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
      View tablesreportViews = new View(reportContent.addDataset(reportViews));
      tablesreportViews.addParameter("borderWidth", "0");
      tablesreportViews.addRenderer("~WCgdfMQqBzc1[table]");
      View tablesTitles = getTitlesView(inherentRiskMap.getTableName(), netRiskMap.getTableName(), reportContent);
      globalDataSet.addItem(tablesTitles, "1,1");
      globalDataSet.addItem(tablesreportViews, "2,1");
      
      TablePresentation tableInherentRisk = new TablePresentation();
      View v_tableInherentRisk = tableInherentRisk.createReportTable(root, reportContent, inherentRiskMap);
      
      TablePresentation tableNetRisk = new TablePresentation();
      View v_tableNetRisk = tableNetRisk.createReportTable(root, reportContent, netRiskMap);
      
      reportViews.addItem(v_tableInherentRisk, "1,1");
      reportViews.addItem(v_tableNetRisk, "1,2");
    }
    else
    {
      TablePresentationExport tableInherentRisk = new TablePresentationExport();
      View v_tableInherentRisk = tableInherentRisk.createReportTable(root, reportContent, inherentRiskMap);
      TablePresentationExport tableNetRisk = new TablePresentationExport();
      View v_tableNetRisk = tableNetRisk.createReportTable(root, reportContent, netRiskMap);
      reportContent.addView(v_tableInherentRisk);
      reportContent.addView(v_tableNetRisk);
    }
  }
  
  
  /*
   * Retona una coleccion de reisgos, en caso de que los parametros de contexto 
   * tenga un tamaño igual a cero, consulta todos los riesgos del entorno, en caso
   * contrario a traves de la clase IdentificationMethodsToolBox.getRisksCollection
   * obtiene los riesgos que se van a mostrar y los retorna en una coleccion.
   */
  public static MegaCollection getRisks(MegaRoot root, IdentificationParameters idParameters)
  {
    MegaCollection allmoAssessedObjects;
    if ((idParameters.getProcesses().size() == 0) && (idParameters.getOrgUnits().size() == 0) && (idParameters.getObjectives().size() == 0) && (idParameters.getRisktypes().size() == 0))
    {
      allmoAssessedObjects = root.getCollection("~W5faeGPxySL0[Risk]", new Object[0]);
    }
    else
    {
      MegaCollection contexts = root.getSelection("", new Object[0]);
      contexts.insert(idParameters.getProcesses());
      contexts.insert(idParameters.getOrgUnits());
      contexts.insert(idParameters.getObjectives());
      contexts.insert(idParameters.getRisktypes());
      allmoAssessedObjects = IdentificationMethodsToolBox.getRisksCollection(contexts);
      contexts.release();
    }
    return allmoAssessedObjects;
  }
}
