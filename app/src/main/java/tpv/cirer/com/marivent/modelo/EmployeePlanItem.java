package tpv.cirer.com.marivent.modelo;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import tpv.cirer.com.marivent.herramientas.AbstractGridItem;
import tpv.cirer.com.marivent.herramientas.TimeRange;

public class EmployeePlanItem extends AbstractGridItem
{
    private String employeeName, projectName;
    private String planName = "-";
    private TimeRange timeRange;
    private Context context;
    private Date fechaDia;

    public EmployeePlanItem() {}

    public EmployeePlanItem(Context context, String employeeName, String projectName, Date planStart, Date planEnd, String planName, Date fechaDia)
    {
        this.context = context;
        this.employeeName = employeeName;
        this.projectName = projectName;
        this.timeRange = new TimeRange(planStart, planEnd);
        this.planName = planName;
        this.fechaDia = fechaDia;
    }

    public static EmployeePlanItem generateSample(Context context)
    {
        final String[] firstNameSamples = {"Kristeen", "Carran", "Lillie", "Marje", "Edith", "Steve", "Henry", "Kyle", "Terrence"};
        final String[] lastNameSamples = {"Woodham", "Boatwright", "Lovel", "Dennel", "Wilkerson", "Irvin", "Aston", "Presley"};
        final String[] projectNames = {"Roof Renovation", "Mall Construction", "Demolition old Hallway"};
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd"); //format it as per your requirement
///        String dateNow = formatter.format(currentDate.getTime());

        // Generate a date range between now and 30 days
        Random rand = new Random();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        Calendar hoy = Calendar.getInstance();
        int r1 = -rand.nextInt(12);
        int r2 = rand.nextInt(12);

        start.add(Calendar.DATE, r1);
        end.add(Calendar.DATE, r2);
 //       Log.i("Rand",start.toString()+ " " + end.toString());
        hoy.setTime(start.getTime());
        hoy.add(Calendar.DATE, -1);  // number of days to add

        return new EmployeePlanItem(context, firstNameSamples[rand.nextInt(firstNameSamples.length)] + " " +
                lastNameSamples[rand.nextInt(lastNameSamples.length)],
                projectNames[rand.nextInt(projectNames.length)],
                start.getTime(),
                end.getTime(),
                formatter.format(hoy.getTime()),
                hoy.getTime());
    }

    @Nullable
    @Override
    public TimeRange getTimeRange()
    {
        return timeRange;
    }

    @Override
    public String getName()
    {
        return planName;
    }


    @Override
    public String getPersonName()
    {
        return employeeName;
    }


    /**
     * OPTIONAL
     *
     * @return a random color for demo purposes.
     */
    @Override
    public int getItemColor()
    {
        return Color.argb(48, 0,0,255);
    }

    @Override
    public void onClick(View view)
    {
        // Do something here!
        Toast.makeText(context, "Plan name: " + planName + "\n" + "Project name: " + projectName+ "\n" + "Time: " , Toast.LENGTH_LONG).show();
// A date-time specified in milliseconds since the epoch.
        Calendar c = Calendar.getInstance();
        c.setTime(fechaDia);

        long startMillis = c.getTimeInMillis();
        Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
        builder.appendPath("time");
        ContentUris.appendId(builder, startMillis);
        Intent intent = new Intent(Intent.ACTION_VIEW)
                .setData(builder.build());
        context.startActivity(intent);

    }
}
/*public class EmployeePlanItem implements IGridItem
{
    private String employeeName, projectName;
    private TimeRange timeRange;
    private Context context;

    public EmployeePlanItem() {}

    public EmployeePlanItem(Context context, String employeeName, String projectName, Date planStart, Date planEnd)
    {
        this.context = context;
        this.employeeName = employeeName;
        this.projectName = projectName;
        this.timeRange = new TimeRange(planStart, planEnd);
    }
    public static EmployeePlanItem generateSample(Context context)
    {
        final String[] firstNameSamples = {"Kristeen", "Carran", "Lillie", "Marje", "Edith", "Steve", "Henry", "Kyle", "Terrence"};
        final String[] lastNameSamples = {"Woodham", "Boatwright", "Lovel", "Dennel", "Wilkerson", "Irvin", "Aston", "Presley"};
        final String[] projectNames = {"Roof Renovation", "Mall Construction", "Demolition old Hallway"};

        // Generate a date range between now and 30 days
        Random rand = new Random();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        int r1 = -rand.nextInt(12);
        int r2 = rand.nextInt(12);
        start.add(Calendar.DATE, r1);
        end.add(Calendar.DATE, r2);

        return new EmployeePlanItem(context, firstNameSamples[rand.nextInt(firstNameSamples.length)] + " " +
                lastNameSamples[rand.nextInt(lastNameSamples.length)],
                projectNames[rand.nextInt(projectNames.length)],
                start.getTime(),
                end.getTime());
    }

    @Override
    public TimeRange getTimeRange()
    {
        return timeRange;
    }

    @Override
    public String getName()
    {
        return projectName;
    }

    @Override
    public String getPersonName()
    {
        return employeeName;
    }
}*/