package com.example.productAPIExtended.ui.payment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.example.productAPIExtended.databinding.FragmentPaymentBinding;
import com.example.productAPIExtended.models.Order;
import com.example.productAPIExtended.remote_data.RetrofitClient;
import com.example.productAPIExtended.ui.dashboard.BasketFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentFragment extends Fragment {
    FragmentPaymentBinding binding;
    NavController navController;
    List<Order> payed_list;
    SharedPreferences preferences;


    public PaymentFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();

        preferences = getActivity()
                .getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        if (getArguments() != null) {
            payed_list = getArguments().getParcelableArrayList("payed");
        }
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cardVisa.setOnClickListener(v -> {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://cis.visa.com/"));
            startActivity(myIntent);
        });
        binding.cardPaypal.setOnClickListener(v -> {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.paypal.com/"));
            startActivity(myIntent);
        });
        binding.cardMbank.setOnClickListener(v -> {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mbank.kg/"));
            startActivity(myIntent);
        });
        binding.cardOdengi.setOnClickListener(v -> {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=kg.o.nurtelecom/"));
            startActivity(myIntent);
        });

        binding.btnBack.setOnClickListener(v -> {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            navController.navigate(R.id.action_navigation_payment_to_navigation_home);
        });
        binding.btnPayFinally.setOnClickListener(v1 -> {
            binding.progressBar.setVisibility(View.VISIBLE);
            try {
                for (int i = 0; i < payed_list.size(); i++) {

                    if (payed_list.get(i) != null) {
                        Call<Order> apiCall = RetrofitClient.getInstance().getApi().createNewOrder(payed_list.get(i));
                        apiCall.enqueue(new Callback<Order>() {
                            @Override
                            public void onResponse(Call<Order> call, Response<Order> response) {
                                if (response.isSuccessful()) {
                                    if (response.body() != null) {
                                        binding.progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(requireActivity(), "Order SUCCESS!", Toast.LENGTH_LONG).show();

                                        SharedPreferences.Editor prefLoginEdit = preferences.edit();
                                        prefLoginEdit.putBoolean("Order", true);
                                        prefLoginEdit.commit();
                                        binding.tvOtvet.setText("Ваш заказ в обработке, доставка составляет от 5 до 12 дней.");
                                    }
                                } else {
                                    Log.e("fail", "fail");
                                    Toast.makeText(requireActivity(), "Заказ в настоящее время недоступен", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Order> call, Throwable throwable) {
                                Toast.makeText(requireActivity(), "Не удалось создать заказ", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }
            }catch (Exception e) {
                Toast.makeText(requireActivity(), "Выберите товар", Toast.LENGTH_LONG).show();
            }
        });
    }
}

//        binding.btnPayFinally.setOnClickListener(v1 -> {
//
//
//            for (int i = 0; i < payed_list.size(); i++) {
//
//                Call<Order> apiCall = RetrofitClient.getInstance().getApi().createNewOrder(payed_list.get(i));
//                apiCall.enqueue(new Callback<Order>() {
//                    @Override
//                    public void onResponse(Call<Order> call, Response<Order> response) {
//                        if (response.isSuccessful()) {
//                            if (response.body() != null) {
//                                binding.progressBar.setVisibility(View.INVISIBLE);
//                                Toast.makeText(requireActivity(), "Order SUCCESS!", Toast.LENGTH_LONG).show();
//
//                                SharedPreferences preferences = getActivity()
//                                        .getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
//                                SharedPreferences.Editor prefLoginEdit = preferences.edit();
//                                prefLoginEdit.putBoolean("Order", true);
//                                prefLoginEdit.commit();
//                            }
//                        } else {
//                            Log.e("fail", "fail");
//                            Toast.makeText(requireActivity(), "Order is not available now", Toast.LENGTH_LONG).show();
//                        }
//
//                    }
//
//                    @Override
//                    public void onFailure(Call<Order> call, Throwable throwable) {
//
//                    }
//                });
//            }
//            binding.tvOtvet.setText("Ваш заказ в обработке, доставка  составляет от 5 до 12 дней." );
//        });
//
//
//    }
//}
