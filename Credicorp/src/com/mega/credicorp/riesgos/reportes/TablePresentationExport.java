package com.mega.credicorp.riesgos.reportes;

import java.util.ArrayList;
import java.util.Map;

import com.mega.modeling.analysis.content.Dataset;
import com.mega.modeling.analysis.content.Dimension;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.Text;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.grcu.GRCDataProcessing;
import com.mega.soho.grcu.GRCReportViewUtility;


/*
 * Esta clase en escencia realiza la misma labor que la clase
 * TablaPresentation sin emabrgo algunas caracteristicas del como lo hace
 * son diferentes.
 */
public class TablePresentationExport
{
  private MegaRoot mgRoot;
  private Dataset heatMapDataset;
  private Dimension dimFirstMaAttribute;
  private Dimension dimSecondMaAttribute;
  private ReportContent rContent;
  private Hmap hMap;
  
  public View createReportTable(MegaRoot root, ReportContent reportContent, Hmap heatMap)
  {
    this.mgRoot = root;
    this.rContent = reportContent;
    this.hMap = heatMap;
    this.heatMapDataset = new Dataset("");
    this.dimFirstMaAttribute = new Dimension("");
    this.dimSecondMaAttribute = new Dimension("");
    this.dimFirstMaAttribute.setSize(this.hMap.getMavFirstMaAttribute().size() + 2);
    this.dimSecondMaAttribute.setSize(this.hMap.getMavSecondMaAttribute().size() + 2);
    this.heatMapDataset.addDimension(this.dimFirstMaAttribute);
    this.heatMapDataset.addDimension(this.dimSecondMaAttribute);
    fillHeatMap();
    
    View vTable = new View(reportContent.addDataset(this.heatMapDataset));
    vTable.addRenderer("~WCgdfMQqBzc1[table]");
    vTable.getItemRenderer().addParameter("class", "charttable");
    
    return vTable;
  }
  
  private void fillHeatMap()
  {
	//Obtiene el map de celdas vinculado al hmap
    Map<String, HCell> mavsMap = this.hMap.getMavsMap();
   
    /*
     * Define las variables ligne, column y total value contexts con 1,1 y 0
     * y setTitles con false
     */
    int ligne = 2;
    int column = 2;
    int totalValueContexts = 0;
    boolean setTitles = false;
    
    
    //Almacena en el objeto de tipo text el nombre de la tabla del hmap
    Text tableName = getTitlesTexts(this.hMap.getTableName());
    
    //Agrega al dataset el item tablename en la posición 1,1
    this.heatMapDataset.addItem(tableName, "1,1");
    for (MegaObject mavFirstMetAttribute : this.hMap.getMavFirstMaAttribute())
    {
    	 //Obtiene por cada matv un titulo y lo agrega en la coordenada X.	
      Text tex_mavName = getTitlesTexts(mavFirstMetAttribute.getProp("~H3l5fU1F3n80[Value Name]"));
      //Agrega al data set el titulo obtenido
      this.heatMapDataset.addItem(tex_mavName, ligne + ",1");
      /*
       * La variable total line se inicializa en 0 y se va incrementando a medida
       * que se llenan la celdas de la fila
       */
      int totalLine = 0;
      for (MegaObject mavSecondMetAttribute : this.hMap.getMavSecondMaAttribute())
      {
        if (!setTitles)
        {
        
        //Obtiene por cada matv un titulo y lo agrega en la coordenada Y del data set.
          tex_mavName = getTitlesTexts(mavSecondMetAttribute.getProp("~H3l5fU1F3n80[Value Name]"));
          this.heatMapDataset.addItem(tex_mavName, "1," + column);
        }
        
        //Arma el valor de la llave de la celda
        String heatMapCellKey = Double.valueOf(mavFirstMetAttribute.getProp("~L20000000L50[Internal Value]")).toString() + "," + 
          Double.valueOf(mavSecondMetAttribute.getProp("~L20000000L50[Internal Value]")).toString();
        
        /*
         * Define un objeto de tipo celca y le asigna el objeto celda almacenado
         * en el map vinculado a la llave previamente estructurada  
         */
        HCell hcell = (HCell)mavsMap.get(heatMapCellKey);
        /*
         * Incrementa el valor  de la linea de acuerdo al tamaño del valores
         * de contexto de la celda
         */
        totalLine += hcell.getValueContexts().size();
        /*
         * Genera un objeto de tipo texto en donde le pasa el tamaño del arreglo
         * este tamaño representa en nuero de coincidencias por nodo.
         */
        Text value = new Text("<b><center>" + hcell.getValueContexts().size() + "</center></b>", false);
        value.isHtml(true);
        /*
         * Le pone el color a la celda y le agrega la propiedad
         * drilldown que genera un evento cuando se selecciona la casilla
         */
        value.getItemRenderer().addParameter("color", hcell.getColor());
        value.getItemRenderer().addParameter("drilldown", getDrillDown(hcell.getValueContexts()).toString());
        /*
         * Agrega al data set el objeto text en la posición ligne (2) y column (2) 
         * y posteriormente aumenta el contador de columna. 
         */
        this.heatMapDataset.addItem(value, ligne + "," + column);
        column++;
      }
      
      /*
       * Genera un titulo con el valor del totalline el cual es el acumulado
       * de toda la linea y lo coloca en la ultima posición en la cual se 
       * totaliza
       */
      Text ittotal = getTitlesTexts(String.valueOf(totalLine));
      this.heatMapDataset.addItem(ittotal, ligne + "," + column++);
      /*
       * a la variable totalValueContexts se suma el valor de la linea 
       * para obtener un acumulado del valor total.
       */
      totalValueContexts += totalLine;
      /*
       * Incrementa la linea y la columna la inicializa en 2 para reiniciar
       * el recorrido de izquierda a derecha en la segunda fila.
       */
      column = 2;
      ligne++;
    //Cambia el valor de setTitles a true para que los titulos ya no se generen
      setTitles = true;
    }
     //Agrega un texto vacio que se ubica en la parte superior derecha 
    this.heatMapDataset.addItem(getTitlesTexts(" "), "1," + (this.hMap.getMavSecondMaAttribute().size() + 2));
    //Agrega un texto que se ubica en la parte inferior izquierda "Total"
    Text totalTitle = getTitlesTexts(GRCDataProcessing.getCodeTemplate("~5ARhLtwZGr5Q[total]", this.mgRoot));
    this.heatMapDataset.addItem(totalTitle, this.hMap.getMavFirstMaAttribute().size() + 2 + ",1");
    //Calcula el total de la columnas y lo agrega a la tabla 
    setTotalColumns();
    //Genera un objeto text con el total calculado y lo agrega a la tabla
    Text totalAll = getTitlesTexts(" " + String.valueOf(totalValueContexts));
    this.heatMapDataset.addItem(totalAll, this.hMap.getMavFirstMaAttribute().size() + 2 + "," + (this.hMap.getMavSecondMaAttribute().size() + 2));
    
    //GEnera la vista con el data set y la renderiza
    View heatMapView = new View(this.rContent.addDataset(this.heatMapDataset));
    heatMapView.addRenderer("~WCgdfMQqBzc1[table]");
    heatMapView.getItemRenderer().addParameter("class", "charttable");
  }
  
  private void setTotalColumns()
  {
	  
	  /*
	     * Inicializa el objeto ligne en el tamaño mas dos para que la linea
	     * sea la ultima de la tabla.
	     */
    int ligne = this.hMap.getMavSecondMaAttribute().size() + 2;
    int column = 2;
  //Inicializa la columna en dos
    for (MegaObject mavSecondMetAttribute : this.hMap.getMavSecondMaAttribute())
    {
    	//Define contador de total por columna
      int totalColumn = 0;
      for (MegaObject mavFirstMetAttribute : this.hMap.getMavFirstMaAttribute())
      {
    		//Arma la llave de la celda   
        String heatMapCellKey = Double.valueOf(mavFirstMetAttribute.getProp("~L20000000L50[Internal Value]")).toString() + "," + 
          Double.valueOf(mavSecondMetAttribute.getProp("~L20000000L50[Internal Value]")).toString();
      //Obtiene el objeto celda con la llave generada
        HCell hcell = (HCell)this.hMap.getMavsMap().get(heatMapCellKey);
        //Aumenta el valor total con el valor del tamaño del arreglo de la celda
        totalColumn += hcell.getValueContexts().size();
        // define el objeto text y lo adiciona al final de la columna
        Text ittotal = getTitlesTexts(String.valueOf(totalColumn));
        this.heatMapDataset.addItem(ittotal, ligne + "," + column);
      }
      column++;
    }
  }
  
  /*
   * Recibe un string, el cual a traves de un objeto Text lo convierte en un titulo
   * y le asigna un color predeterminado.
   */
  private Text getTitlesTexts(String str)
  {
    String html_str = "<b><center>" + str + "</center></b>";
    Text tex_str = new Text(html_str, false);
    tex_str.isHtml(true);
    tex_str.getItemRenderer().addParameter("color", "E8E6DE");
    return tex_str;
  }
  
  /*
   *Recibe una lista de string que representa los nodos almacenados en esa posicion
   *genera un stringbuffer el cual llena de forma consecuente con cada string nodo de 
   *la lista 
   */
  private StringBuffer getDrillDown(ArrayList<String> nodes)
  {
    StringBuffer objectsId = new StringBuffer("~)0Gb81(DEL3o[Assessment Value Context]:");
    for (String nodeMegaField : nodes) {
      objectsId.append(nodeMegaField + ",");
    }
    GRCReportViewUtility.deleteSemiColon(objectsId);
    return objectsId;
  }
}
