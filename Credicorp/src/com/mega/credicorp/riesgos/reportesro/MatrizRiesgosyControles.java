package com.mega.credicorp.riesgos.reportesro;

import com.mega.modeling.analysis.Analysis;
import com.mega.modeling.analysis.AnalysisCallback;
import com.mega.modeling.analysis.AnalysisParameter;
import com.mega.modeling.analysis.AnalysisReportWithContext;
import com.mega.modeling.analysis.content.Dataset;
import com.mega.modeling.analysis.content.Dimension;
import com.mega.modeling.analysis.content.Item;
import com.mega.modeling.analysis.content.ReportContent;
import com.mega.modeling.analysis.content.Text;
import com.mega.modeling.analysis.content.View;
import com.mega.modeling.api.MegaCollection;
import com.mega.modeling.api.MegaObject;
import com.mega.modeling.api.MegaRoot;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MatrizRiesgosyControles
  implements AnalysisCallback, AnalysisReportWithContext
{
  private int mainIndex = 0;
  private MegaObject paramCompany = null;
  private MegaObject paramMacroProcess = null;
  private MegaObject paramProcess = null;
  private MegaObject paramSubProcess = null;
  private MegaRoot root;
  private ReportContent content = null;
  private Dimension dim01 = null;
  private Dimension dim02 = null;
  private Dataset dataset01 = null;
  private View view01 = null;
  private Item title01 = null;
  private Item title02 = null;
  private Item title03 = null;
  private Item title04 = null;
  private Item title05 = null;
  private Item title06 = null;
  private Item title07 = null;
  private Item title08 = null;
  private Item title09 = null;
  private Item title10 = null;
  private Item title11 = null;
  private Item title12 = null;
  private Item title13 = null;
  private Item title14 = null;
  private Item title15 = null;
  private Item title16 = null;
  private Item title17 = null;
  private Item title18 = null;
  private Item title19 = null;
  private Item title20 = null;
  private Item title21 = null;
  private Item title22 = null;
  private Item title23 = null;
  private Item title24 = null;
  private Item title25 = null;
  private Item title26 = null;
  private Item title27 = null;
  private Item title28 = null;
  private Item title29 = null;
  private Item title30 = null;
  private Item title31 = null;
  private Item title32 = null;
  private Item title33 = null;
  private Item title34 = null;
  private Item title35 = null;
  private Item title36 = null;
  private Item title37 = null;
  private Item title38 = null;
  private Item title39 = null;
  private Item title40 = null;
  private Item title41 = null;
  private Item title42 = null;
  private Item title43 = null;
  private Item title44 = null;
  private Item title45 = null;
  private Item title46 = null;
  private Item title47 = null;
  private Item title48 = null;
  private Item title49 = null;
  private Item title50 = null;
  private Item title51 = null;
  private Item title52 = null;
  private Item title53 = null;
  private Item title54 = null;
  private Item title55 = null;
  private Item title56 = null;
  private Item title57 = null;
  private Item title58 = null;
  private Item title59 = null;
  private Item title60 = null;
  
  public ReportContent getReportContent(MegaRoot root, Map<String, List<AnalysisParameter>> params, Analysis analysis, Object obj)
  {
    MegaCollection selection01 = null;
    this.root = root;
    initParams(params);
    this.content = new ReportContent("");
    this.dim01 = new Dimension("");
    this.dim02 = new Dimension("");
    if (this.paramCompany != null)
    {
      selection01 = this.root.getSelection(assembleQuery01().toString(), new Object[0]);
      if (selection01.size() > 0)
      {
        this.dataset01 = new Dataset("");
        initTitles();
        initRiskRecords(selection01);
        this.dim01.setSize(this.mainIndex + 1);
        this.dim02.setSize(22);
      }
      else
      {
        this.dataset01 = new Dataset("~8RRwvyVYPfTD[No Hay Informaci�n para Mostrar]");
        this.dim01.setSize(1);
        this.dim02.setSize(1);
      }
    }
    else
    {
      this.dataset01 = new Dataset("~UPRw84YYPbdE[El Par�metro Compa��a es Obligatorio]");
      this.dim01.setSize(1);
      this.dim02.setSize(1);
    }
    this.dataset01.addDimension(this.dim01);
    this.dataset01.addDimension(this.dim02);
    this.view01 = new View(this.content.addDataset(this.dataset01), true, false);
    this.view01.addRenderer("~WCgdfMQqBzc1[table]");
    this.content.addView(this.view01);
    return this.content;
  }
  
  public String Callback(MegaRoot root, String htmlCellContent, MegaCollection columnCollection, MegaCollection lineCollection, Object userData)
  {
    MegaCollection collection01 = null;
    collection01 = getCollectionFromCallback(root, (String)userData);
    if (collection01.size() > 0) {
      collection01.invokeMethod("Open", new Object[0]);
    }
    return htmlCellContent;
  }
  
  private MegaCollection getCollectionFromCallback(MegaRoot root, String sComponentListIdabs)
  {
    MegaCollection colReturn = root.getSelection("", new Object[0]);
    if (sComponentListIdabs.length() > 0)
    {
      String sCurrentList = sComponentListIdabs;
      int iVirgule = sCurrentList.indexOf(",");
      while (iVirgule > 0)
      {
        String sBeg = sCurrentList.substring(0, iVirgule);
        sCurrentList = sCurrentList.substring(iVirgule + 1);
        iVirgule = sCurrentList.indexOf(",");
        colReturn.insert(root.getObjectFromID(sBeg));
      }
      colReturn.insert(root.getObjectFromID(sCurrentList));
    }
    return colReturn;
  }
  
  private void initParams(Map<String, List<AnalysisParameter>> params)
  {
    Iterator localIterator2;
    for (Iterator localIterator1 = params.keySet().iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      String paramType = (String)localIterator1.next();
      localIterator2 = ((List)params.get(paramType)).iterator(); 
      AnalysisParameter paramValue = (AnalysisParameter)localIterator2.next();
      if (paramType.equals("6B8482B8582B0B71")) {
        for (MegaObject value : paramValue.getValues()) {
          this.paramCompany = value;
        }
      } else if (paramType.equals("68AD6AAA583712EA")) {
        for (MegaObject value : paramValue.getValues()) {
          this.paramMacroProcess = value;
        }
      } else if (paramType.equals("6B848291582B0B15")) {
        for (MegaObject value : paramValue.getValues()) {
          this.paramProcess = value;
        }
      } else if (paramType.equals("68AD6B1358371349")) {
        for (MegaObject value : paramValue.getValues()) {
          this.paramSubProcess = value;
        }
      }
    }
  }
  
  private void initRiskRecords(MegaCollection selection01)
  {
    MegaObject risk = null;
    Set<MegaObject> businessProcess = null;
    Set<MegaObject> organizationalProcessP = null;
    Set<MegaObject> organizationalProcess = null;
    Set<MegaObject> riskType = null;
    Set<MegaObject> orgUnit = null;
    Set<MegaObject> applications = null;
    Set<MegaObject> requirements = null;
    Set<MegaObject> sites = null;
    Set<MegaObject> products = null;
    Set<MegaObject> controlSystems = null;
    Set<MegaObject> bcpTaxonomias = null;
    Set<MegaObject> riskFactors = null;
    Set<MegaObject> bcpEros = null;
    Set<MegaObject> riskConsequences = null;
    Set<MegaObject> preventiveControls = null;
    StringBuffer strBusinessProcess = new StringBuffer("");
    StringBuffer strOrganizationalProcessP = new StringBuffer("");
    StringBuffer strOrganizationalProcess = new StringBuffer("");
    StringBuffer strRiskType = new StringBuffer("");
    StringBuffer strOrgUnit = new StringBuffer("");
    StringBuffer strApplications = new StringBuffer("");
    StringBuffer strRequirements = new StringBuffer("");
    StringBuffer strSites = new StringBuffer("");
    StringBuffer strProducts = new StringBuffer("");
    StringBuffer strControlSystems = new StringBuffer("");
    StringBuffer strBcpTaxonomias = new StringBuffer("");
    StringBuffer strRiskFactors = new StringBuffer("");
    StringBuffer strBcpEros = new StringBuffer("");
    StringBuffer strRiskConsequences = new StringBuffer("");
    this.mainIndex = 0;
    for (int i = 1; i <= selection01.size(); i++)
    {
      strBusinessProcess = new StringBuffer("");
      strOrganizationalProcessP = new StringBuffer("");
      strOrganizationalProcess = new StringBuffer("");
      strRiskType = new StringBuffer("");
      strOrgUnit = new StringBuffer("");
      strApplications = new StringBuffer("");
      strRequirements = new StringBuffer("");
      strSites = new StringBuffer("");
      strProducts = new StringBuffer("");
      strControlSystems = new StringBuffer("");
      strBcpTaxonomias = new StringBuffer("");
      strRiskFactors = new StringBuffer("");
      strBcpEros = new StringBuffer("");
      strRiskConsequences = new StringBuffer("");
      risk = selection01.get(Integer.valueOf(i));
      businessProcess = new HashSet();
      organizationalProcess = new HashSet();
      organizationalProcessP = new HashSet();
      riskType = new HashSet();
      orgUnit = new HashSet();
      applications = new HashSet();
      requirements = new HashSet();
      sites = new HashSet();
      products = new HashSet();
      controlSystems = new HashSet();
      bcpTaxonomias = new HashSet();
      riskFactors = new HashSet();
      bcpEros = new HashSet();
      riskConsequences = new HashSet();
      preventiveControls = new HashSet();
      preloadCollection(risk.getCollection("~U7famQPxyea0[Business Process]", new Object[0]), businessProcess);
      setToString(businessProcess, strBusinessProcess);
      preloadCollection(risk.getCollection("~h7fanQPxyOb0[Organizational Process]", new Object[0]), organizationalProcessP);
      setToStringobject(organizationalProcessP, strOrganizationalProcessP);
      preloadCollection(risk.getCollection("~h7fanQPxyOb0[Organizational Process]", new Object[0]), organizationalProcess);
      setToString(organizationalProcess, strOrganizationalProcess);
      preloadCollection(risk.getCollection("~X)tbmKS9z4t0[Risk Type]", new Object[0]), riskType);
      setToString(riskType, strRiskType);
      preloadCollection(risk.getCollection("~o7SZ1ZC)yqj1[Org-Unit]", new Object[0]), orgUnit);
      setToString(orgUnit, strOrgUnit);
      preloadCollection(risk.getCollection("~N4SZ1ZC)y4m1[Application]", new Object[0]), applications);
      setToString(applications, strApplications);
      preloadCollection(risk.getCollection("~DXFWIfOC8jN1[Requirement]", new Object[0]), requirements);
      setToString(requirements, strRequirements);
      preloadCollection(risk.getCollection("~A4SZ1ZC)yKl1[Site]", new Object[0]), sites);
      setToString(sites, strSites);
      preloadCollection(risk.getCollection("~Vfzx6sW3Jf5I[Product]", new Object[0]), products);
      setToString(products, strProducts);
      preloadCollection(risk.getCollection("~pSzyz7284Lk0[Mitigating Control System]", new Object[0]), controlSystems);
      setToString(controlSystems, strControlSystems);
      preloadCollection(risk.getCollection("~wG8zpWF5JjVD[BCP Taxonom�a]", new Object[0]), bcpTaxonomias);
      setToString(bcpTaxonomias, strBcpTaxonomias);
      preloadCollection(risk.getCollection("~5(94loJ151Q0[Source Risk Factor]", new Object[0]), riskFactors);
      setToString(riskFactors, strRiskFactors);
      preloadCollection(risk.getCollection("~1(YJKXr5JjvK[BCP Clasificaci�n ERO]", new Object[0]), bcpEros);
      setToStringobj(bcpEros, strBcpEros);
      preloadCollection(risk.getCollection("~fH6Xvwe5GH6S[Risk Consequence]", new Object[0]), riskConsequences);
      setToString(riskConsequences, strRiskConsequences);
      preloadCollection(risk.getCollection("~PVzy3YK84Hz2[Preventive Control]", new Object[0]), preventiveControls);
      preventiveControlLoop(risk, strBusinessProcess.toString(), strOrganizationalProcessP.toString(), strOrganizationalProcess.toString(), strRiskType.toString(), strOrgUnit.toString(), strApplications.toString(), strRequirements.toString(), strSites.toString(), strProducts.toString(), strControlSystems.toString(), strBcpTaxonomias.toString(), strRiskFactors.toString(), strBcpEros.toString(), strRiskConsequences.toString(), preventiveControls);
    }
  }
  
  private void initTitles()
  {
    this.title01 = new Text("~UhuOswsaP5BC[Macro Procesos]", true);
    this.title01.setOrderable(true);
    this.dim02.addItem(this.title01);
    this.title02 = new Text("~FhuO5zsaP5HC[Procesos]", true);
    this.title02.setOrderable(true);
    this.dim02.addItem(this.title02);
    this.title03 = new Text("~rhuO50taPPNC[Sub Procesoso]", true);
    this.title03.setOrderable(true);
    this.dim02.addItem(this.title03);
    this.title04 = new Text("~TdtXz1vPPHEB[Nombre Riesgo]", true);
    this.title04.setOrderable(true);
    this.dim02.addItem(this.title04);
    this.title05 = new Text("~NatXn4vPPTcB[Descripci�n Detallada]", true);
    this.title05.setOrderable(true);
    this.dim02.addItem(this.title05);
    this.title06 = new Text("~8lhJMzKELLqH[Due�o del Riesgo]", true);
    this.title06.setOrderable(true);
    this.dim02.addItem(this.title06);
    this.title07 = new Text("~bdtXa3vPPHOB[Gestor del Riesgo]", true);
    this.title07.setOrderable(true);
    this.dim02.addItem(this.title07);
    this.title08 = new Text("~CbtXX2vPPvIB[Riesgo Clave]", true);
    this.title08.setOrderable(true);
    this.dim02.addItem(this.title08);
    this.title09 = new Text("~AbtX)3vPPvSB[Modo de Identificaci�n]", true);
    this.title09.setOrderable(true);
    this.dim02.addItem(this.title09);
    this.title10 = new Text("~sbtXb7vPP11C[Tipos de Riesgo]", true);
    this.title10.setOrderable(true);
    this.dim02.addItem(this.title10);
    this.title11 = new Text("~8ctXYtuPPDZ9[Entidades]", true);
    this.title11.setOrderable(true);
    this.dim02.addItem(this.title11);
    this.title12 = new Text("~adtXFxuPPz9A[Aplicaciones]", true);
    this.title12.setOrderable(true);
    this.dim02.addItem(this.title12);
    this.title13 = new Text("~cctXWuuPP5n9[Requerimientos]", true);
    this.title13.setOrderable(true);
    this.dim02.addItem(this.title13);
    this.title14 = new Text("~hbtX55vPPTgB[Sitios]", true);
    this.title14.setOrderable(true);
    this.dim02.addItem(this.title14);
    this.title15 = new Text("~HdtXX5vPP5lB[Productos]", true);
    this.title15.setOrderable(true);
    this.dim02.addItem(this.title15);
    this.title16 = new Text("~sctXAyuPPHJA[Sistemas de Gesti�n]", true);
    this.title16.setOrderable(true);
    this.dim02.addItem(this.title16);
    this.title17 = new Text("~WctXQ6vPPPuB[Evento]", true);
    this.title17.setOrderable(true);
    this.dim02.addItem(this.title17);
    this.title18 = new Text("~YdtX68vPPf5C[BCP Taxonom�as]", true);
    this.title18.setOrderable(true);
    this.dim02.addItem(this.title18);
    this.title19 = new Text("~actXq8vPPvEC[Factores de Riesgo]", true);
    this.title19.setOrderable(true);
    this.dim02.addItem(this.title19);
    this.title20 = new Text("~0btXQ8vPPHAC[BCP Clasificaci�n ERO]", true);
    this.title20.setOrderable(true);
    this.dim02.addItem(this.title20);
    this.title21 = new Text("~0atX59vPPXJC[Consecuencias de Riesgo]", true);
    this.title21.setOrderable(true);
    this.dim02.addItem(this.title21);
    this.title22 = new Text("~bbtX6BvPPjXC[Probabilidad Inherente]", true);
    this.title22.setOrderable(true);
    this.dim02.addItem(this.title22);
    this.title23 = new Text("~5PRuc6abJjtO[Impacto Legal]", true);
    this.title23.setOrderable(true);
    this.dim02.addItem(this.title23);
    this.title24 = new Text("~(PRul6abJjwO[Impacto Reputacional]", true);
    this.title24.setOrderable(true);
    this.dim02.addItem(this.title24);
    this.title25 = new Text("~tQRuv6abJfzO[Impacto Financiero]", true);
    this.title25.setOrderable(true);
    this.dim02.addItem(this.title25);
    this.title26 = new Text("~vCj3w0nfPnk8[Criticidad Inherente]", true);
    this.title26.setOrderable(true);
    this.dim02.addItem(this.title26);
    this.title27 = new Text("~ibtXYCvPPLiC[Riesgo Inherente]", true);
    this.title27.setOrderable(true);
    this.dim02.addItem(this.title27);
    this.title28 = new Text("~fdtXFYuPP9F8[Nombre Control]", true);
    this.title28.setOrderable(true);
    this.dim02.addItem(this.title28);
    this.title29 = new Text("~x6VryuDbPzz3[Control Objetivo]", true);
    this.title29.setOrderable(true);
    this.dim02.addItem(this.title29);
    this.title30 = new Text("~EctXxnuPPLg8[Categor�a Est�ndar]", true);
    this.title30.setOrderable(true);
    this.dim02.addItem(this.title30);
    this.title31 = new Text("~LatXHnuPPPb8[Due�o]", true);
    this.title31.setOrderable(true);
    this.dim02.addItem(this.title31);
    this.title32 = new Text("~SatXsduPPnR8[Control Clave]", true);
    this.title32.setOrderable(true);
    this.dim02.addItem(this.title32);
    this.title33 = new Text("~)btXDeuPPPW8[Control SOX]", true);
    this.title33.setOrderable(true);
    this.dim02.addItem(this.title33);
    this.title34 = new Text("~5oA9Qn2BLTu9[Peridiocidad de Ejecuci�n Recomendada]", true);
    this.title34.setOrderable(true);
    this.dim02.addItem(this.title34);
    this.title35 = new Text("~wdtXvpuPP9v8[Documentos Revisados Como Parte del Control]", true);
    this.title35.setOrderable(true);
    this.dim02.addItem(this.title35);
    this.title36 = new Text("~TbtXHquPPnz8[Evidencias del Control]", true);
    this.title36.setOrderable(true);
    this.dim02.addItem(this.title36);
    this.title37 = new Text("~BdtXpquPPT29[Disposici�n de Diferencias(Excepciones)]", true);
    this.title37.setOrderable(true);
    this.dim02.addItem(this.title37);
    this.title38 = new Text("~HDj3NhnfPfL9[Aplicaci�n]", true);
    this.title38.setOrderable(true);
    this.dim02.addItem(this.title38);
    this.title39 = new Text("~eFj3AnnfPjd9[Eficiencia]", true);
    this.title39.setOrderable(true);
    this.dim02.addItem(this.title39);
    this.title40 = new Text("~0dtXX(uPPPgA[Eficiencia Consolidada]", true);
    this.title40.setOrderable(true);
    this.dim02.addItem(this.title40);
    this.title41 = new Text("~sCj3RpnfPTp9[Tipo de Control]", true);
    this.title41.setOrderable(true);
    this.dim02.addItem(this.title41);
    this.title42 = new Text("~2Ej3eknfPbR9[Cobertura]", true);
    this.title42.setOrderable(true);
    this.dim02.addItem(this.title42);
    this.title43 = new Text("~8Cj3FlnfPTX9[Naturaleza]", true);
    this.title43.setOrderable(true);
    this.dim02.addItem(this.title43);
    this.title44 = new Text("~jDj3onnfPXj9[Eficacia]", true);
    this.title44.setOrderable(true);
    this.dim02.addItem(this.title44);
    this.title45 = new Text("~RatXp(uPPzkA[Eficacia Consolidada]", true);
    this.title45.setOrderable(true);
    this.dim02.addItem(this.title45);
    this.title46 = new Text("~8Ej37tnfPnv9[Efectividad]", true);
    this.title46.setOrderable(true);
    this.dim02.addItem(this.title46);
    this.title47 = new Text("~rbtX3)uPPXpA[Efectividad Consolidada]", true);
    this.title47.setOrderable(true);
    this.dim02.addItem(this.title47);
    this.title48 = new Text("~UpGXeYecJ9lF[Mitiga Probabilidad]", true);
    this.title48.setOrderable(true);
    this.dim02.addItem(this.title48);
    this.title49 = new Text("~COgXdwudJzf6[Probabilidad Residual]", true);
    this.title49.setOrderable(true);
    this.dim02.addItem(this.title49);
    this.title50 = new Text("~(nGX8YecJ9hF[Mitiga Impacto]", true);
    this.title50.setOrderable(true);
    this.dim02.addItem(this.title50);
    this.title51 = new Text("~TQgXqbvdJLL7[Impacto Financiero Residual]", true);
    this.title51.setOrderable(true);
    this.dim02.addItem(this.title51);
    this.title52 = new Text("~BPgXhevdJfa7[Criticidad Residual]", true);
    this.title52.setOrderable(true);
    this.dim02.addItem(this.title52);
    this.title53 = new Text("~BPgXtwudJvi6[Riesgo Residual]", true);
    this.title53.setOrderable(true);
    this.dim02.addItem(this.title53);
    this.title54 = new Text("~55(qTSDrLLoJ[Rechazar]", true);
    this.title54.setOrderable(true);
    this.dim02.addItem(this.title54);
    this.title55 = new Text("~07(qETDrLzsJ[Transferir]", true);
    this.title55.setOrderable(true);
    this.dim02.addItem(this.title55);
    this.title56 = new Text("~44(qrTDrLj8K[Reducir]", true);
    this.title56.setOrderable(true);
    this.dim02.addItem(this.title56);
    this.title57 = new Text("~K5(qPUDrLTBK[Retener]", true);
    this.title57.setOrderable(true);
    this.dim02.addItem(this.title57);
    this.title58 = new Text("~y6(q9VDrLzEK[Aceptar]", true);
    this.title58.setOrderable(true);
    this.dim02.addItem(this.title58);
    this.title59 = new Text("~F4(qiVDrLvHK[Asegurar]", true);
    this.title59.setOrderable(true);
    this.dim02.addItem(this.title59);
    this.title60 = new Text("~vctXUEvPPLvC[Nivel de Riesgo Objetivo]", true);
    this.title60.setOrderable(true);
    this.dim02.addItem(this.title60);
  }
  
  private StringBuffer assembleQuery01()
  {
    StringBuffer query01 = new StringBuffer("");
    StringBuffer filter01 = new StringBuffer("");
    query01.append("Select ~W5faeGPxySL0[Risk] ");
    filter01.append(" Where");
    if (this.paramCompany != null) {
      filter01.append("~o7SZ1ZC)yqj1[Org-Unit].~H20000000550[_HexaIdAbs] = '").append(this.paramCompany.getProp("~H20000000550[_HexaIdAbs]")).append("'");
    }
    if (this.paramMacroProcess != null)
    {
      if (filter01.length() > 6) {
        filter01.append(" And");
      }
      filter01.append(" ~rt6GC8KE9X70[Element at Risk]:~pj)grmQ9pG90[Business Process].~H20000000550[_HexaIdAbs] = '").append(this.paramMacroProcess.getProp("~H20000000550[_HexaIdAbs]")).append("'");
    }
    if (this.paramProcess != null)
    {
      if (filter01.length() > 6) {
        filter01.append(" And");
      }
      filter01.append(" ~rt6GC8KE9X70[Element at Risk]:~gsUiU9B5iiR0[Organizational Process].~H20000000550[_HexaIdAbs] = '").append(this.paramProcess.getProp("~H20000000550[_HexaIdAbs]")).append("'");
    }
    if (this.paramSubProcess != null)
    {
      if (filter01.length() > 6) {
        filter01.append(" And");
      }
      filter01.append(" ~rt6GC8KE9X70[Element at Risk]:~gsUiU9B5iiR0[Organizational Process].~H20000000550[_HexaIdAbs] = '").append(this.paramSubProcess.getProp("~H20000000550[_HexaIdAbs]")).append("'");
    }
    query01.append(filter01);
    return query01;
  }
  
  private void preloadCollection(MegaCollection collection, Set<MegaObject> set)
  {
    if (collection.size() > 0) {
      for (int collectionIndex = 1; collectionIndex <= collection.size(); collectionIndex++) {
        set.add(collection.get(Integer.valueOf(collectionIndex)));
      }
    }
  }
  
  private void setToString(Set<MegaObject> objSet, StringBuffer strObj)
  {
    if (objSet.size() > 0) {
      for (MegaObject obj : objSet)
      {
        if (strObj.length() > 0) {
          strObj.append(" | ");
        }
        strObj.append(obj.getProp("~Z20000000D60[Short Name]"));
      }
    }
  }
  
  private void setToStringobject(Set<MegaObject> objSet, StringBuffer strObj)
  {
    if (objSet.size() > 0) {
      for (MegaObject obj : objSet)
      {
        if (strObj.length() > 0) {
          strObj.append(" | ");
        }
        strObj.append(obj.getProp("~HqUiuEB5iiR3[Aggregation of].~H20000000550[_HexaIdAbs] = "));
      }
    }
  }
  
  private void setToStringobj(Set<MegaObject> objSet, StringBuffer strObj)
  {
    if (objSet.size() > 0) {
      for (MegaObject obj : objSet)
      {
        if (strObj.length() > 0) {
          strObj.append(" | ");
        }
        strObj.append(obj.getProp("~210000000900[Name]"));
      }
    }
  }
  
  private void preventiveControlLoop(MegaObject risk, String businessProcess, String organizationalProcessP, String organizationalProcess, String riskType, String orgUnit, String applications, String requirements, String sites, String products, String controlSystems, String bcpTaxonomias, String riskFactors, String bcpEros, String riskConsequences, Set<MegaObject> preventiveControls)
  {
    if (preventiveControls.size() > 0) {
      for (MegaObject preventiveControl : preventiveControls) {
        processLine(risk, businessProcess, organizationalProcessP, organizationalProcess, riskType, orgUnit, applications, requirements, sites, products, controlSystems, bcpTaxonomias, riskFactors, bcpEros, riskConsequences, preventiveControl);
      }
    } else {
      processLine(risk, businessProcess, organizationalProcessP, organizationalProcess, riskType, orgUnit, applications, requirements, sites, products, controlSystems, bcpTaxonomias, riskFactors, bcpEros, riskConsequences, null);
    }
  }
  
  private void processLine(MegaObject risk, String businessProcess, String organizationalProcessP, String organizationalProcess, String riskType, String orgUnit, String applications, String requirements, String sites, String products, String controlSystems, String bcpTaxonomias, String riskFactors, String bcpEros, String riskConsequences, MegaObject preventiveControl)
  {
    this.mainIndex += 1;
    this.dataset01.addItem(new Text(businessProcess, false), this.mainIndex + ",1");
    this.dataset01.addItem(new Text(organizationalProcessP, false), this.mainIndex + ",2");
    this.dataset01.addItem(new Text(organizationalProcess, false, 10), this.mainIndex + ",3");
    this.dataset01.addItem(new Text(risk.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",4");
    this.dataset01.addItem(new Text(risk.getProp("~f10000000b20[Comment]"), false), this.mainIndex + ",5");
    this.dataset01.addItem(new Text(risk.getProp("~NgcVS1u5JnJB[Due�o del Proceso]"), false), this.mainIndex + ",6");
    this.dataset01.addItem(new Text(risk.getProp("~fecV31u5JXEB[Gestor de Riesgos]"), false), this.mainIndex + ",7");
    this.dataset01.addItem(new Text(KeyRiskToText(risk.getProp("~QSYHf55xFH7H[Key Risk]")), true), this.mainIndex + ",8");
    this.dataset01.addItem(new Text(String.valueOf(risk.getProp("~1OtH5bK45160[Risk Identification Mode]", "EXTERNAL")), false), this.mainIndex + ",9");
    this.dataset01.addItem(new Text(riskType, false), this.mainIndex + ",10");
    this.dataset01.addItem(new Text(orgUnit, false), this.mainIndex + ",11");
    this.dataset01.addItem(new Text(applications, false), this.mainIndex + ",12");
    this.dataset01.addItem(new Text(requirements, false), this.mainIndex + ",13");
    this.dataset01.addItem(new Text(sites, false), this.mainIndex + ",14");
    this.dataset01.addItem(new Text(products, false), this.mainIndex + ",15");
    this.dataset01.addItem(new Text(controlSystems, false), this.mainIndex + ",16");
    this.dataset01.addItem(new Text(risk.getProp("~tKWv0i(PJD89[Evento]"), false), this.mainIndex + ",17");
    this.dataset01.addItem(new Text(bcpTaxonomias, false), this.mainIndex + ",18");
    this.dataset01.addItem(new Text(riskFactors, false), this.mainIndex + ",19");
    this.dataset01.addItem(new Text(bcpEros, false), this.mainIndex + ",20");
    this.dataset01.addItem(new Text(riskConsequences, false), this.mainIndex + ",21");
    this.dataset01.addItem(new Text(RiskRechazarToText(risk.getProp("~t2EyEAVv4v30[Risk Avoidance]")), true), this.mainIndex + ",54");
    this.dataset01.addItem(new Text(RiskTransferToText(risk.getProp("~(0Ey7FVv4j80[Risk Transfer (Sub-contractor)]")), true), this.mainIndex + ",55");
    this.dataset01.addItem(new Text(RiskReductionToText(risk.getProp("~h3EyBEVv4H70[Risk Reduction]")), true), this.mainIndex + ",56");
    this.dataset01.addItem(new Text(RiskAprovecharToText(risk.getProp("~n2MEGGu3JjyL[Aprovechar]")), true), this.mainIndex + ",57");
    this.dataset01.addItem(new Text(RiskAcceptanceToText(risk.getProp("~5uOhruUw4bN0[Risk Acceptance]")), true), this.mainIndex + ",58");
    this.dataset01.addItem(new Text(RiskInsuranceToText(risk.getProp("~j2EyBGVv4LB0[Risk Insurance]")), true), this.mainIndex + ",59");
    this.dataset01.addItem(new Text(String.valueOf(risk.getProp("~9QuMpD7fHbC1[Target Risk \\ERM]", "EXTERNAL")), false), this.mainIndex + ",60");
    if (preventiveControl != null)
    {
      this.dataset01.addItem(new Text(preventiveControl.getProp("~Z20000000D60[Short Name]"), false), this.mainIndex + ",28");
      this.dataset01.addItem(new Text(preventiveControl.getProp("~lUDDXn3KGvGM[Control Objective]]"), false), this.mainIndex + ",29");
      this.dataset01.addItem(new Text(String.valueOf(preventiveControl.getProp("~xezxGkY3JDmK[Categoria Est�ndar]", "EXTERNAL")), false), this.mainIndex + ",30");
      this.dataset01.addItem(new Text(preventiveControl.getProp("~cEsxaklRG1z9[Control Owner]"), false), this.mainIndex + ",31");
      this.dataset01.addItem(new Text(ControlKeyControlToText(preventiveControl.getProp("~TUDD1N3KGLwL[Control Key]")), true), this.mainIndex + ",32");
      this.dataset01.addItem(new Text(ControlSOXControlToText(preventiveControl.getProp("~gfzxJEY3JnZJ[Control SOX]")), true), this.mainIndex + ",33");
      this.dataset01.addItem(new Text(String.valueOf(preventiveControl.getProp("~yzYJuzs5JnaO[Peridiocidad]]", "EXTERNAL")), false), this.mainIndex + ",34");
      this.dataset01.addItem(new Text(String.valueOf(preventiveControl.getProp("~FMWv(R(PJHk8[Documentos Revisados Como Parte del Control]", "DISPLAY")), false), this.mainIndex + ",35");
      this.dataset01.addItem(new Text(String.valueOf(preventiveControl.getProp("~yKWv9T(PJHq8[Evidencias del Control]", "DISPLAY")), false), this.mainIndex + ",36");
      this.dataset01.addItem(new Text(String.valueOf(preventiveControl.getProp("~vMWvmT(PJfv8[Disposici�n de Diferencias <Excepciones>]", "DISPLAY")), false), this.mainIndex + ",37");
      this.dataset01.addItem(new Text(reputationToText(preventiveControl.getProp("~9ezx15Y3J1JJ[Mitiga Probabilidad]")), true), this.mainIndex + ",48");
      this.dataset01.addItem(new Text(reputationToText(preventiveControl.getProp("~Wgzxc4Y3JDEJ[Mitiga Impacto]")), true), this.mainIndex + ",50");
    }
  }
  
  private String RiskInsuranceToText(String prop)
  {
    String text = "";
    if (prop.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
  
  private String RiskRechazarToText(String prop)
  {
    String text = "";
    if (prop.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
  
  private String RiskTransferToText(String prop)
  {
    String text = "";
    if (prop.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
  
  private String RiskReductionToText(String prop)
  {
    String text = "";
    if (prop.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
  
  private String RiskAprovecharToText(String prop)
  {
    String text = "";
    if (prop.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
  
  private String RiskAcceptanceToText(String prop)
  {
    String text = "";
    if (prop.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
  
  private String ControlSOXControlToText(String prop)
  {
    String text = "";
    if (prop.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
  
  private String ControlKeyControlToText(String ControlKey)
  {
    String text = "";
    if (ControlKey.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
  
  private String KeyRiskToText(String keyrisk)
  {
    String text = "";
    if (keyrisk.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
  
  private String reputationToText(String impactsReputation)
  {
    String text = "";
    if (impactsReputation.trim().equals("1")) {
      text = "~B0Ls5jzaPXfF[Opci�n Respuesta-S�]";
    } else {
      text = "~J2LskjzaPTlF[Opci�n Respuesta-No]";
    }
    return text;
  }
}
