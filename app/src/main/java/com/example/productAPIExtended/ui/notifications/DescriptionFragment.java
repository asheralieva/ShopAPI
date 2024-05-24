package com.example.productAPIExtended.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.productAPIExtended.R;
import com.example.productAPIExtended.databinding.FragmentDescriptionBinding;
import com.example.productAPIExtended.models.ModelM;

import java.util.ArrayList;

public class DescriptionFragment extends Fragment {

    private FragmentDescriptionBinding binding;
    ArrayList<ModelM> d_list = new ArrayList<>();
    DescAdapter adapter;
    NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDescriptionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            d_list = getArguments().getParcelableArrayList("see more");
        } else {
            Toast.makeText(requireActivity(), " There are nothing", Toast.LENGTH_SHORT).show();
        }

        adapter = new DescAdapter(requireActivity(), d_list);
        binding.rvDescription.setAdapter(adapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnBack.setOnClickListener(v -> {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            navController.navigate(R.id.action_navigation_description_to_navigation_home);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
