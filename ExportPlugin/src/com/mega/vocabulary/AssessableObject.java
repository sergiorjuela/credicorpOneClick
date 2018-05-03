package com.mega.vocabulary;

public interface AssessableObject extends ElementWithQuestionGroup,AssessmentForecastedElement {
/*MetaClass ID*/
    public static String ID = "~adwh93nhDHtG[Assessable Object]";
/*MetaAttributes*/
    public static final String MA_Comment = "~f10000000b20[X]";
    public static final String MA_Log = "~f20000000b60[X]";
    public static final String MA_CommentBody = "~m20000000170[X]";
/*MetaAssociationEnds*/
    public static final String MAE_QuestionGroupSpecific = "~YUgykboHHncR[Assessable Object.Question Group Specific]";
    public static final String MAE_AssessmentSession = "~LbwhgH3iDjnH[Assessable Object.Assessment Session]";
    public static final String MAE_AssessmentNode = "~zGsYd9vKEviv[Assessable Object.Assessment Node]";
    public static final String MAE_UsedAssessmentInstrument = "~1QrFyI9UEneI[Assessable Object.Used Assessment Instrument]";

}
