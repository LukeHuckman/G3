package com.g3;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.switchmaterial.SwitchMaterial;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    SQLHelper sqlHelper;
    UserSettings settings;
    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        /* TODO: Fix bug in SQLite implementation causing settings to reset.
        *  For some reason the settings database gets reset every time
        *  after leaving the settings fragment, causing initSettings()
        *  to be called again.
        */

        sqlHelper = new SettingsSQL(getActivity());
        try {
            sqlHelper.getSettings(1);
        } catch(android.database.CursorIndexOutOfBoundsException ex){
            sqlHelper.initSettings();
        }
        settings = sqlHelper.getSettings(1);
        SwitchMaterial toggleTTNotify = view.findViewById(R.id.ToggleTTNotify);
        toggleTTNotify.setChecked(settings.getTimeNotify() == 1);
        SwitchMaterial.OnCheckedChangeListener TTNotify = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setTimeNotify(isChecked ? 1:0);
                sqlHelper.updateSettings(1, settings);
            }
        };
        toggleTTNotify.setOnCheckedChangeListener(TTNotify);

        SwitchMaterial toggleTTAlarm = view.findViewById(R.id.ToggleTTAlarm);
        toggleTTAlarm.setChecked(settings.getTimeAlarm() == 1);
        SwitchMaterial.OnCheckedChangeListener TTAlarm = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setTimeAlarm(isChecked ? 1:0);
                sqlHelper.updateSettings(1, settings);
            }
        };
        toggleTTAlarm.setOnCheckedChangeListener(TTAlarm);

        SwitchMaterial toggleTSNotify = view.findViewById(R.id.ToggleTSNotify);
        toggleTSNotify.setChecked(settings.getTaskNotify() == 1);
        SwitchMaterial.OnCheckedChangeListener TSNotify = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setTaskNotify(isChecked ? 1:0);
                sqlHelper.updateSettings(1, settings);
            }
        };
        toggleTSNotify.setOnCheckedChangeListener(TSNotify);

        SwitchMaterial toggleTSAlarm = view.findViewById(R.id.ToggleTSAlarm);
        toggleTSAlarm.setChecked(settings.getTaskAlarm() == 1);
        SwitchMaterial.OnCheckedChangeListener TSAlarm = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setTaskAlarm(isChecked ? 1:0);
                sqlHelper.updateSettings(1, settings);
            }
        };
        toggleTSAlarm.setOnCheckedChangeListener(TSAlarm);

        SwitchMaterial toggleDarkMode = view.findViewById(R.id.ToggleDarkMode);
        toggleDarkMode.setChecked(settings.getDarkMode() == 1);
        SwitchMaterial.OnCheckedChangeListener DarkMode = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                settings.setDarkMode(isChecked ? 1:0);
                sqlHelper.updateSettings(1, settings);
            }
        };
        toggleDarkMode.setOnCheckedChangeListener(DarkMode);
    }
}