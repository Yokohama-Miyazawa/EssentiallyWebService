package apps.cardina1.red.essentiallywebservice;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectFragment extends Fragment {
    private static final String FRAGMENT_TAG = "SelectFragment";
    private static final String ARG_TABLE_NAMES = "tableNames";
    private static final String BUNDLE_FROM_CHECKS = "fromChecks";
    private static final String BUNDLE_FROM_TEXTS = "fromTexts";

    private ArrayList<String> tableNames;

    private OnFragmentInteractionListener mListener;

    public SelectFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SelectFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectFragment newInstance(ArrayList<String> tableNames) {
        SelectFragment fragment = new SelectFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_TABLE_NAMES, tableNames);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tableNames = getArguments().getStringArrayList(ARG_TABLE_NAMES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_select, container, false);

        // Add UI components for `FROM` clause.
        TableLayout tableFrom = (TableLayout) view.findViewById(R.id.table_from);
        Map<String, Boolean> fromChecks = new HashMap<>();
        Map<String, String> fromTexts = new HashMap<>();
        if (savedInstanceState != null) {
            fromChecks = (HashMap<String, Boolean>) savedInstanceState.getSerializable(BUNDLE_FROM_CHECKS);
            fromTexts = (HashMap<String, String>) savedInstanceState.getSerializable(BUNDLE_FROM_TEXTS);
        }
        int row_i = 0;
        for (String name: tableNames) {
            inflater.inflate(R.layout.sql_table_from_row, tableFrom);
            TableRow row = (TableRow) tableFrom.getChildAt(row_i);
            CheckBox check = (CheckBox) row.getChildAt(0);
            check.setText(name);
            check.setChecked(fromChecks.getOrDefault(name, false));
            ((EditText) row.getChildAt(2)).setText(fromTexts.getOrDefault(name, ""));
            row_i++;
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        TableLayout tableFrom = (TableLayout) getView().findViewById(R.id.table_from);
        HashMap<String, Boolean> fromChecks = new HashMap<>();
        HashMap<String, String> fromTexts = new HashMap<>();
        int numChildren = tableFrom.getChildCount();
        for (int row_i = 0; row_i < numChildren; row_i++) {
            TableRow row = (TableRow) tableFrom.getChildAt(row_i);
            String name = ((CheckBox) row.getChildAt(0)).getText().toString();
            fromChecks.put(name, ((CheckBox) row.getChildAt(0)).isChecked());
            fromTexts.put(name, ((EditText) row.getChildAt(2)).getText().toString());
        }
        savedInstanceState.putSerializable(BUNDLE_FROM_CHECKS, fromChecks);
        savedInstanceState.putSerializable(BUNDLE_FROM_TEXTS, fromTexts);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onFragmentInteraction(FRAGMENT_TAG, null);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                + " must implement OnFragmentInteractionListener<Object>");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
