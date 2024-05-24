package com.example.productAPIExtended.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.productAPIExtended.R;
import com.example.productAPIExtended.databinding.FragmentHomeBinding;
import com.example.productAPIExtended.models.ModelM;
import com.example.productAPIExtended.remote_data.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    JemAdapter adapter;
    HomeViewModel homeViewModel;
    NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Call<List<ModelM>> apiCall= RetrofitClient.getInstance().getApi().getStoreProducts();
        apiCall.enqueue(new Callback<List<ModelM>>() {
            @Override
            public void onResponse(@NonNull Call<List<ModelM>> call, @NonNull Response<List<ModelM>> response) {
                if(response.body()!=null){
                    adapter=new JemAdapter();
                    binding.rvCatalogM.setAdapter(adapter);
                    adapter.setList(response.body());
                }
            }
            @Override
            public void onFailure(@NonNull Call<List<ModelM>> call, @NonNull Throwable throwable) {
                Toast.makeText(requireActivity(),"NO DATA",Toast.LENGTH_SHORT).show();
            }
        });

        homeViewModel.getModelMResponseLiveData().observe(getViewLifecycleOwner(), new Observer<List<ModelM>>() {
            @Override
            public void onChanged(List<ModelM> modelMS) {
                binding.progressBar.setVisibility(View.INVISIBLE);

                adapter = new JemAdapter(requireActivity(), modelMS);
                binding.rvCatalogM.setAdapter(adapter);
            }
        });
        setUpOnBackPressed();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host);


        binding.basketBtn.setOnClickListener(v -> {
            PopupMenu popup = new PopupMenu(requireActivity(), binding.basketBtn);
            popup.getMenuInflater().inflate(R.menu.action_menu, popup.getMenu());

            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getTitle().toString()) {
                        case "Добавить в корзину":
                            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                            Bundle bundle = new Bundle();
                            bundle.putParcelableArrayList("keysss_basket", adapter.getSelected_intoBasketList());
                            navController.navigate(R.id.navigation_basket, bundle);
                            break;
                        case "Пометить":
                            Toast.makeText(requireContext(), "Marked", Toast.LENGTH_SHORT).show();
                            break;
                        default:
                            Toast.makeText(requireContext(), "default", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
            });
            popup.show();
        });
        binding.signInSignOut.setOnClickListener(v1->{
            navController=Navigation.findNavController(requireActivity(),R.id.nav_host);
            navController.navigate(R.id.action_navigation_home_to_navigation_registration);
        });
    }

    private void setUpOnBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (isEnabled()) {
                    requireActivity().finish();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}











//public class HomeFragment extends Fragment {
//
//    private FragmentHomeBinding binding;
//    JemAdapter adapter;
//    HomeViewModel homeViewModel;
//    NavController navController;
//
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//        homeViewModel =
//                new ViewModelProvider(this).get(HomeViewModel.class);
//
//        binding = FragmentHomeBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        Call<List<ModelM>> apiCall = RetrofitClient.getInstance().getApi().getStoreProducts();
//        apiCall.enqueue(new Callback<List<ModelM>>() {
//            @Override
//            public void onResponse(Call<List<ModelM>> call, Response<List<ModelM>> response) {
//                if (response.body()!=null) {
//                    adapter = new JemAdapter();
//                    binding.rvCatalogM.setAdapter(adapter);
//                    adapter.setList(response.body());
//                } else {
//                    Toast.makeText(requireActivity(), "NO Ability DATA from Baitur", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ModelM>> call, Throwable throwable) {
//                Toast.makeText(requireActivity(), "NO DATA", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        setUpOnBackPressed();
//        return root;
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        binding.basketBtn.setOnClickListener(v -> {
//            PopupMenu popup = new PopupMenu(requireActivity(), binding.basketBtn);
//            popup.getMenuInflater().inflate(R.menu.action_menu, popup.getMenu());
//
//            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    switch (item.getTitle().toString()) {
//                        case "Добавить в корзину":
//                            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
//
//                            Bundle bundle = new Bundle();
//                            bundle.putParcelableArrayList("keysss_basket", adapter.getSelected_intoBasketList());
//                            navController.navigate(R.id.navigation_basket, bundle);
//                            break;
//                        case "Пометить":
//                            Toast.makeText(requireContext(), "Marked", Toast.LENGTH_SHORT).show();
//                            break;
//                        default:
//                            Toast.makeText(requireContext(), "default", Toast.LENGTH_SHORT).show();
//                    }
//                    return true;
//                }
//            });
//            popup.show();
//        });
////        binding.signInSignOut.setOnClickListener(v1 -> {
////            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
////            navController.navigate(R.id.action_navigation_home_to_navigation_registration);
////        });
//    }
//
//    private void setUpOnBackPressed() {
//        requireActivity().getOnBackPressedDispatcher().addCallback(new OnBackPressedCallback(true) {
//            @Override
//            public void handleOnBackPressed() {
//                if (isEnabled()) {
//                    requireActivity().finish();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }
//}