package com.example.recycler_project.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.recycler_project.ListRequestDetail;
import com.example.recycler_project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdminRequestDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdminRequestDetailsFragment extends Fragment {

    Button backRequestDetail;
    RequestQueue requesQueue;
    List<ListRequestDetail> requestDetails = new ArrayList<>();
    RecyclerView recyclerView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AdminRequestDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdminRequestDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AdminRequestDetailsFragment newInstance(String param1, String param2) {
        AdminRequestDetailsFragment fragment = new AdminRequestDetailsFragment();
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

        View root = inflater.inflate(R.layout.fragment_admin_request_details, container, false);

        backRequestDetail = root.findViewById(R.id.backRequestDetails);
        recyclerView = root.findViewById(R.id.recyclerViewRequestDetails);

        backRequestDetail.setOnClickListener(this::onBackPress);

        return root;
    }

    public void onBackPress(View v){
        FragmentManager frm = getActivity().getSupportFragmentManager();
        frm.beginTransaction()
                .replace(R.id.navigationAdmin, new AdminRequestFragment())
                .addToBackStack(null)
                .commit();
    }

    private void getAllRequestDetail(){

    }

}