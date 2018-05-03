package com.mega.vocabulary;

public interface LibraryElement extends ElementWithPosts,ModelBuildingBlock {
/*MetaClass ID*/
    public static String ID = "~1Knf6iQA8D20[Library Element]";
/*MetaAttributes*/
    public static final String MA_Comment = "~f10000000b20[X]";
    public static final String MA_Log = "~f20000000b60[X]";
    public static final String MA_CommentBody = "~m20000000170[X]";
/*MetaAssociationEnds*/
    public static final String MAE_OwnerLibrary = "~tYUyJ9nA8HW0[Library Element.Owner Library]";
    public static final String MAE_OwnerArchitectureContainer = "~I7gBsG3NMXxB[Library Element.Owner Architecture Container]";
    public static final String MAE_ImportingArchitectureContainer = "~3O(LgRSkMHuW[Library Element.Importing Architecture Container]";

}
