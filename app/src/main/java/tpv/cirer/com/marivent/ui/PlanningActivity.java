package tpv.cirer.com.marivent.ui;

/**
 * Created by JUAN on 20/03/2017.
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.marivent.R;
import tpv.cirer.com.marivent.herramientas.TimeTable;
import tpv.cirer.com.marivent.modelo.EmployeePlanItem;

import static tpv.cirer.com.marivent.ui.ActividadPrincipal.mesasList;

public class PlanningActivity extends AppCompatActivity
{
    private static final int GENERATED_AMOUNT = 20;
    private static TimeTable timeTable;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(this));

        setContentView(R.layout.planning_activity);

        timeTable = (TimeTable) findViewById(R.id.time_table);
        timeTable.setItems(generateSamplePlanData(this));

    }

    private static List<EmployeePlanItem> generateSamplePlanData(Context context)
    {
        List<EmployeePlanItem> planItems = new ArrayList<>();
        for(int i = 0; i < mesasList.size(); i++)
            planItems.add(EmployeePlanItem.generateSample(context, i));

        return planItems;
    }
}
