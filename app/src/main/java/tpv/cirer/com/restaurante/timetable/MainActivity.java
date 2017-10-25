package tpv.cirer.com.restaurante.timetable;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import tpv.cirer.com.restaurante.R;

public class MainActivity extends AppCompatActivity
{
	private static final int GENERATED_AMOUNT = 20;
	private TimeTable timeTable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_timetable);
		
		timeTable = (TimeTable) findViewById(R.id.time_table);
		timeTable.setItems(generateSamplePlanData(this));
	}
	
	private static List<EmployeePlanItem> generateSamplePlanData(Context context)
	{
		List<EmployeePlanItem> planItems = new ArrayList<>();
		for(int i = 0; i < GENERATED_AMOUNT; i++)
			planItems.add(EmployeePlanItem.generateSample(context));
		
		return planItems;
	}
}
