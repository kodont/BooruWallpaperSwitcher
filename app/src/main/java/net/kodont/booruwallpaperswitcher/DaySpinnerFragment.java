package net.kodont.booruwallpaperswitcher;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DaySpinnerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DaySpinnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DaySpinnerFragment
        extends Fragment
        implements Spinner.OnItemSelectedListener
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Spinner daysSpinner;

    private OnFragmentInteractionListener mListener;

    public DaySpinnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DaySpinnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DaySpinnerFragment newInstance(String param1, String param2) {
        DaySpinnerFragment fragment = new DaySpinnerFragment();
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
        return inflater.inflate(R.layout.fragment_day_spinner, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        //if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        //}
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        if (context instanceof Activity) {
            ((Activity) context).setContentView(R.layout.activity_fourth);
            daysSpinner = ((Activity) context).findViewById(R.id.dayOfWeekSpinner);
            ArrayAdapter<CharSequence> adapter =
                    ArrayAdapter.createFromResource(
                            context,
                            R.array.days_of_week,
                            android.R.layout.simple_spinner_dropdown_item
                    );
            adapter.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
            );
            daysSpinner.setAdapter(adapter);
        }


    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        daysSpinner = null;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Object obj = parent.getItemAtPosition(position);
        mListener.onFragmentInteraction(obj.getClass().getName());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String str);
    }
}
