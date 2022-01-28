package com.g3;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sun.bob.mcalendarview.CellConfig;
import sun.bob.mcalendarview.MarkStyle;
import sun.bob.mcalendarview.listeners.OnMonthChangeListener;
import sun.bob.mcalendarview.views.ExpCalendarView;
import sun.bob.mcalendarview.vo.DateData;
import sun.bob.mcalendarview.vo.MarkedDates;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List marked_dates;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.TBMainAct);
        toolbar.setTitle("Calendar");

        String[] months = { "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" };

        ExpCalendarView calendarView = ((ExpCalendarView) getActivity().findViewById(R.id.calendar_exp));



        TextView current_view_year=getActivity().findViewById((R.id.current_view_year));
        TextView current_view_month=getActivity().findViewById((R.id.current_view_month));

        int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR)+1;

        current_view_month.setText(months[currentMonth-1]);
        current_view_year.setText(" "+Integer.toString(currentYear));

        final boolean[] isShrink = {false};
        CellConfig.Month2WeekPos = CellConfig.middlePosition;


        ImageButton trigger_expand_btn = getActivity().findViewById(R.id.trigger_expand_btn);
        trigger_expand_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isShrink[0]){
                    int id = getResources().getIdentifier("@android:drawable/arrow_up_float", null, null);
                    trigger_expand_btn.setImageResource(id);
                    isShrink[0] =false;
                    CellConfig.ifMonth = true;
                    calendarView.expand();
                }
                else{
                    int id = getResources().getIdentifier("@android:drawable/arrow_down_float", null, null);
                    trigger_expand_btn.setImageResource(id);
                    isShrink[0] =true;
                    CellConfig.ifMonth = false;
                    calendarView.shrink();
                }
            }
        });

        //expandIV.setImageResource(R.mipmap.icon_arrow_down);

        // Lookup the recyclerview in activity layout

        RecyclerView rv = (RecyclerView) getActivity().findViewById(R.id.recycler);

        // Initialize tasks
        //tasks = Task.createTaskList();
        // Create adapter passing in the sample task data
        //TaskAdapter adapter = new TaskAdapter(tasks);
        TaskAdapter adapter = ((MainActivity)getActivity()).getTaskAdapter();
        List<Task> tasks=adapter.getTasks();
        // Attach the adapter to the recyclerview to populate items
        rv.setAdapter(adapter);
        // Set layout manager to position the items
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        // That's all!

        MarkedDates markedDates = calendarView.getMarkedDates();
        ArrayList markData = markedDates.getAll();
        for (int k=0; k<markData.size();k++){
            calendarView.unMarkDate((DateData) markData.get(k));
        }
        for(int j=0; j<tasks.size(); j++){
            Task task=tasks.get(j);
            //String[] datetimeArr=task.getEndDate().split(" ");
            String date=task.getEndDate();
            String time=task.getEndTime();
            String[] dateArr=date.split("-");
            int day=Integer.parseInt(dateArr[0]);
            int month=Integer.parseInt(dateArr[1]);
            int year=Integer.parseInt(dateArr[2]);


            calendarView.markDate(
                    new DateData(year, month, day).setMarkStyle(new MarkStyle(MarkStyle.BACKGROUND, Color.parseColor(task.getColor())))
            );
        }

        calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChange(int year, int month) {
                current_view_month.setText(months[month-1]);
                current_view_year.setText(" "+Integer.toString(year));
            }
        });

        RecyclerView recyclerView = getActivity().findViewById(R.id.recycler);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        FragmentManager fm = getFragmentManager();
                        Bundle bundle = new Bundle();
                        TextView task_id=getActivity().findViewById(R.id.task_id);
                        bundle.putInt("task_id", Integer.parseInt((String)task_id.getText()));
                        Log.i("Calendar", ""+task_id.getText());

                        /*Fragment fragment = new EditTaskFragment();
                        fragment.setArguments(bundle);
                        fm.beginTransaction()
                                .replace(view.getId(), fragment)
                                .commit();*/

                        Navigation.findNavController(view).navigate(R.id.editTaskFragment, bundle);
                    }


                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            /*@Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }*/
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.addTaskFragment);
            }
        });

        /*String[] stringArray = {"Android","IPhone","WindowsMobile","Blackberry",
                "WebOS","Ubuntu","Windows7","Max OS X"};
        ArrayAdapter adapter=new ArrayAdapter<String>(getContext(), R.layout.task_listview, stringArray);

        ListView listView = (ListView) getActivity().findViewById(R.id.task_list);
        listView.setAdapter(adapter);*/


    }
}