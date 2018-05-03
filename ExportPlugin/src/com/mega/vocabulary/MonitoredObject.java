package com.mega.vocabulary;

public interface MonitoredObject extends ReportValue {
/*MetaClass ID*/
    public static String ID = "~C51eU2Ae6T70[Monitored Object]";
/*MetaAttributes*/
    public static final String MA_Comment = "~f10000000b20[X]";
    public static final String MA_Log = "~f20000000b60[X]";
    public static final String MA_CommentBody = "~m20000000170[X]";
    public static final String MA_AggregatedStatus = "~lfGkEeBZ6910[X]";
    public static final String MAV_AggregatedStatus_Operational = "4";
    public static final String MAV_AggregatedStatus_Warning = "3";
    public static final String MAV_AggregatedStatus_Unsatisfactory = "2";
    public static final String MAV_AggregatedStatus_Critical = "1";
    public static final String MAV_AggregatedStatus_Unknown = "-1";
    public static final String MAV_AggregatedStatus_Failed = "0";
/*MetaAssociationEnds*/
    public static final String MAE_MonitoringObjective = "~kcd4B4pk6PE0[Monitored Object.Monitoring Objective]";
    public static final String MAE_MonitoringIndicator = "~R41eu4Ae6jA0[Monitored Object.Monitoring Indicator]";

}
