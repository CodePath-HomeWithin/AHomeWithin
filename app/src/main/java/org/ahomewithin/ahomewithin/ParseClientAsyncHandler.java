package org.ahomewithin.ahomewithin;

/**
 * Created by xiangyang_xiao on 3/13/16.
 */
public interface ParseClientAsyncHandler {

   void onSuccess(Object obj);
   void onFailure(String error);

}
