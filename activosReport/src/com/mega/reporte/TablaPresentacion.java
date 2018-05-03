package com.mega.reporte;

import java.util.Map;

import com.mega.modeling.analysis.AnalysisReportToolbox;
import com.mega.modeling.analysis.content.Dataset;
import com.mega.modeling.analysis.content.Dimension;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.Text;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import com.mega.soho.erm.reports.heatmapsparameters.HCell;
import com.mega.soho.erm.reports.heatmapsparameters.Hmap;
import com.mega.soho.grcu.GRCDataProcessing;
import com.mega.soho.grcu.GRCReportViewUtility;
import com.mega.soho.grcu.colors.GRCColorsUtility;
import com.mega.soho.grcu.constants.GRCCodeTemplate;
import com.mega.soho.grcu.constants.GRCMetaAttribut;
import com.mega.soho.grcu.constants.GRCMetaClass;

public class TablaPresentacion {
	 private MegaRoot      mgRoot;
	  private Dataset       heatMapDataset;
	  private Dimension     dimFirstMaAttribute;
	  private Dimension     dimSecondMaAttribute;
	  private ReportContent rContent;
	  private Hmap          hMap;

	  public View createReportTable(final MegaRoot root, final ReportContent reportContent, final Hmap heatMap) {
	    // Create Table
	    this.mgRoot = root;
	    this.rContent = reportContent;
	    this.hMap = heatMap;
	    this.heatMapDataset = new Dataset("");
	    this.dimFirstMaAttribute = new Dimension("");
	    this.dimSecondMaAttribute = new Dimension("");
	    this.dimFirstMaAttribute.setSize(this.hMap.getMavFirstMaAttribute().size() + 1);
	    this.dimSecondMaAttribute.setSize(this.hMap.getMavSecondMaAttribute().size() + 1);
	    this.heatMapDataset.addDimension(this.dimFirstMaAttribute);
	    this.heatMapDataset.addDimension(this.dimSecondMaAttribute);

	    this.fillHeatMap();

	    // Create Table view
	    final View vTable = new View(reportContent.addDataset(this.heatMapDataset));
	    vTable.addRenderer(AnalysisReportToolbox.rTable);
	    vTable.getItemRenderer().addParameter("class", "charttable");
	    // return table view
	    return vTable;
	  }

	  /**
	 * 
	 */
	  private void fillHeatMap() {
	    Map<String, HCell> mavsMap = this.hMap.getMavsMap();
	    int ligne = 1;
	    int column = 1;
	    int totalValueContexts = 0;
	    boolean setTitles = false;
	    for (MegaObject mavFirstMetAttribute : this.hMap.getMavFirstMaAttribute()) {
	      Text tex_mavName = this.getTitlesTexts(mavFirstMetAttribute.getProp(GRCMetaAttribut.MA_VALUE_NAME));
	      this.dimFirstMaAttribute.addItem(tex_mavName);
	      int totalLine = 0;
	      for (MegaObject mavSecondMetAttribute : this.hMap.getMavSecondMaAttribute()) {
	        if (!setTitles) {
	          tex_mavName = this.getTitlesTexts(mavSecondMetAttribute.getProp(GRCMetaAttribut.MA_VALUE_NAME));
	          tex_mavName.getItemRenderer().addParameter("columnwidth", "80px");
	          this.dimSecondMaAttribute.addItem(tex_mavName);
	        }
	        String heatMapCellKey = mavFirstMetAttribute.getProp(GRCMetaAttribut.MA_HEX_ID_ABS) + "," + mavSecondMetAttribute.getProp(GRCMetaAttribut.MA_HEX_ID_ABS);
	        HCell hcell = mavsMap.get(heatMapCellKey);
	        totalLine = totalLine + hcell.getValueContexts().size();
	        Text value = new Text("<div  style=\"text-align:center;color:#" + GRCColorsUtility.TITLE_COLOR + ";font-size:26px;font-family:arial;font-weight:bold\"> <b>" + hcell.getValueContexts().size() + " </b></div>", false);
	        value.isHtml(true);
	        value.getItemRenderer().addParameter("color", hcell.getColor());
	        value.getItemRenderer().addParameter("drilldown", this.getDrillDown(hcell.getValueContexts()).toString());
	        this.heatMapDataset.addItem(value, ligne + "," + column);
	        column++;
	      }
	      Text ittotal = this.getTitlesTexts(String.valueOf(totalLine));
	      this.heatMapDataset.addItem(ittotal, ligne + "," + column);
	      totalValueContexts = totalValueContexts + totalLine;
	      ligne++;
	      column = 1;
	      setTitles = true;
	    }
	    this.dimSecondMaAttribute.addItem(new Text("", false));
	    Text totalTitle = this.getTitlesTexts(GRCDataProcessing.getCodeTemplate(GRCCodeTemplate.CODE_TEMP_TOTAL, this.mgRoot));
	    this.dimFirstMaAttribute.addItem(totalTitle);
	    this.setTotalColumns();
	    Text totalAll = this.getTitlesTexts(String.valueOf(totalValueContexts));
	    this.heatMapDataset.addItem(totalAll, (this.hMap.getMavFirstMaAttribute().size() + 1) + "," + (this.hMap.getMavSecondMaAttribute().size() + 1));

	    final View heatMapView = new View(this.rContent.addDataset(this.heatMapDataset));
	    heatMapView.addRenderer(AnalysisReportToolbox.rTable);
	    heatMapView.getItemRenderer().addParameter("class", "charttable");
	  }

	  /**
	 * 
	 */
	  private void setTotalColumns() {
	    int ligne = this.hMap.getMavSecondMaAttribute().size() + 1;
	    int column = 1;
	    for (MegaObject mavSecondMetAttribute : this.hMap.getMavSecondMaAttribute()) {
	      int totalColumn = 0;
	      for (MegaObject mavFirstMetAttribute : this.hMap.getMavFirstMaAttribute()) {
	        String heatMapCellKey = mavFirstMetAttribute.getProp(GRCMetaAttribut.MA_HEX_ID_ABS) + "," + mavSecondMetAttribute.getProp(GRCMetaAttribut.MA_HEX_ID_ABS);
	        HCell hcell = this.hMap.getMavsMap().get(heatMapCellKey);
	        totalColumn = totalColumn + hcell.getValueContexts().size();
	        Text ittotal = this.getTitlesTexts(String.valueOf(totalColumn));
	        this.heatMapDataset.addItem(ittotal, ligne + "," + column);
	      }
	      column++;
	    }
	  }

	  /**
	   * @param str
	   * @return
	   */
	  private Text getTitlesTexts(final String str) {
	    String html_str = "<div  style=\"text-align: center;font-size:9px;font-family:arial\"> <b>" + str + "</b> </div>";
	    Text tex_str = new Text(html_str, false);
	    tex_str.isHtml(true);
	    tex_str.getItemRenderer().addParameter("color", GRCColorsUtility.HEATMAP_TITLE);
	    return tex_str;
	  }

	  /**
	   * @param nodes
	   * @return
	   */
	  private StringBuffer getDrillDown(final Map<String, String> nodes) {
	    StringBuffer objectsId = new StringBuffer(GRCMetaClass.MC_AssessmentValueContext + ":");
	    for (String nodeMegaField : nodes.values()) {
	      objectsId.append(nodeMegaField + ",");
	    }
	    GRCReportViewUtility.deleteSemiColon(objectsId);
	    return objectsId;
	  }
	}
