package com.usman.student;

import com.usman.utilities.CallBack;
import javafx.scene.control.*;

/**
 * Created by Man Of Action on 9/2/2019.
 */
public interface StudentInterface {

    void filterSearchData();
    void filterData();
    void filteringDataPartWise(String part);
    void filteringData(String course,String part);
    void filteringData(String course);

}
