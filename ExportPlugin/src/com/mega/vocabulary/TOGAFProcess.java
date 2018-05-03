package com.mega.vocabulary;

public interface TOGAFProcess extends TOGAFStandard,ReportValue,TOGAFDecompositionElement,TOGAFProductProducer,TOGAFEventProcessor,TOGAFEventGenerator,TOGAFGenericObject {
/*MetaClass ID*/
    public static String ID = "~3bEpvFYEBvz3[TOGAF Process]";
/*MetaAttributes*/
    public static final String MA_Comment = "~f10000000b20[X]";
    public static final String MA_Log = "~f20000000b60[X]";
    public static final String MA_CommentBody = "~m20000000170[X]";
    public static final String MA_ProcessCriticality = "~)cEppGYEBv14[X]";
    public static final String MA_ManualOrAutomated = "~udEpEHYEBn34[X]";
    public static final String MAV_ManualOrAutomated_Manual = "Manual";
    public static final String MAV_ManualOrAutomated_Automated = "Automated";
    public static final String MA_ProcessVolumetrics = "~hcEpFIYEBvA4[X]";
/*MetaAssociationEnds*/
    public static final String MAE_Follows = "~KaEpXQYEB9G4[TOGAF Process.Follows]";
    public static final String MAE_Precedes = "~LaEpXQYEBDG4[TOGAF Process.Precedes]";
    public static final String MAE_IsGuidedBy = "~4(g(e3xfBTjU[TOGAF Process.Is Guided By]";
    public static final String MAE_Involves = "~dLVpIF(eBLV3[TOGAF Process.Involves]";
    public static final String MAE_IsSupportedBy = "~VMVpjR(eBn04[TOGAF Process.Is Supported By]";
    public static final String MAE_Orchestrates = "~MKVpSV(eBD94[TOGAF Process.Orchestrates]";

}
