package com.g3;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTaskFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddTaskFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddTaskFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTaskFragment newInstance(String param1, String param2) {
        AddTaskFragment fragment = new AddTaskFragment();
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
        return inflater.inflate(R.layout.fragment_add_task, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.TBMainAct);
        toolbar.setTitle("Add Task");

        TextInputEditText add_task_name=getActivity().findViewById(R.id.add_task_name);
        TextInputEditText add_task_tags=getActivity().findViewById(R.id.add_task_tags);

        String initColor = "#F56969";
        Button add_task_color_view=getActivity().findViewById(R.id.add_task_color_view);
        add_task_color_view.setBackgroundColor(Color.parseColor(initColor));
        TextInputEditText add_task_color=getActivity().findViewById(R.id.add_task_color);
        add_task_color.setText(initColor);

        int currentMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int currentDay = Calendar.getInstance().get(Calendar.DATE);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);

        TextInputEditText add_task_start_date = getActivity().findViewById(R.id.add_task_start_date);
        add_task_start_date.setText(currentDay+"-"+currentMonth+"-"+currentYear);
        TextInputEditText add_task_start_time = getActivity().findViewById(R.id.add_task_start_time);
        add_task_start_time.setText(currentHour+":"+currentMinute);
        add_task_start_time.setText(currentHour+":"+currentMinute);
        TextInputEditText add_task_end_date = getActivity().findViewById(R.id.add_task_end_date);
        TextInputEditText add_task_end_time = getActivity().findViewById(R.id.add_task_end_time);

        //https://github.com/QuadFlask/colorpicker
        add_task_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPickerDialogBuilder
                        .with(getContext())
                        .setTitle("Choose color")
                        .initialColor(Color.parseColor(initColor))
                        .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                        .density(12)
                        .setOnColorSelectedListener(new OnColorSelectedListener() {
                            @Override
                            public void onColorSelected(int selectedColor) {
                                Log.i("onColorSelected: 0x", Integer.toHexString(selectedColor));
                                add_task_color.setText("#"+Integer.toHexString(selectedColor));
                                add_task_color_view.setBackgroundColor(Color.parseColor("#"+Integer.toHexString(selectedColor)));
                            }
                        })
                        .setPositiveButton("ok", new ColorPickerClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                                //changeBackgroundColor(selectedColor);
                                //initColor=Integer.toHexString(selectedColor);
                                Log.i("selectedColor", Integer.toHexString(selectedColor));
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .build()
                        .show();
            }
        });


        add_task_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment("add_task_start_date");
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


        add_task_start_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment("add_task_start_time");
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });


        add_task_end_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment("add_task_end_date");
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
            }
        });


        add_task_end_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment("add_task_end_time");
                newFragment.show(getActivity().getSupportFragmentManager(), "timePicker");
            }
        });

        //set Add and Cancel button\
        FloatingActionButton add_task_cancel_btn = view.findViewById(R.id.add_task_cancel_btn);
        add_task_cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        FloatingActionButton add_task_btn = view.findViewById(R.id.add_task_btn);
        add_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get to be submitted data
                String name=add_task_name.getEditableText().toString();
                String tags=add_task_tags.getEditableText().toString();
                String color=add_task_color.getEditableText().toString();
                String start_date=add_task_start_date.getEditableText().toString();
                String start_time=add_task_start_time.getEditableText().toString();
                String end_date=add_task_end_date.getEditableText().toString();
                String end_time=add_task_end_time.getEditableText().toString();

                Log.i("submit", name);
                Log.i("submit", tags);
                Log.i("submit", color);

                //submit set data
                SettingsDB settingsDB =((MainActivity)getActivity()).getSettingsDB();
                Task task = new Task(
                        0, name, tags, color, start_date, start_time, end_date,  end_time
                );
                settingsDB.addTask(task);
                TaskAdapter taskAdapter=((MainActivity)getActivity()).getTaskAdapter();
                //set data in task list fragment
                taskAdapter.addTask(task);
                if (getFragmentManager().getBackStackEntryCount() != 0) {
                    //String item = "Pig";
                    //int insertIndex = 2;

                    getFragmentManager().popBackStack();
                }
            }
        });

    }
}