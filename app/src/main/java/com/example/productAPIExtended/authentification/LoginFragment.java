package com.example.productAPIExtended.authentification;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.productAPIExtended.R;
import com.example.productAPIExtended.databinding.FragmentLoginBinding;
import com.example.productAPIExtended.databinding.FragmentRegistretionBinding;
import com.example.productAPIExtended.models.CurrentUser;
import com.example.productAPIExtended.models.LoginResponse;
import com.example.productAPIExtended.models.User;
import com.example.productAPIExtended.remote_data.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;
    AuthViewModel viewModel;
    String emailUserIdentify;
    NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogin.setOnClickListener(v->{
            binding.progressBar.setVisibility(View.VISIBLE);
            if(!isEmptyEditTextLogin()){
                loginUser(new CurrentUser(binding.email.getText().toString(), binding.password.getText().toString()));

            }
        });

        binding.tvRegistration.setOnClickListener(v->{
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            navController.navigate(R.id.action_navigation_login_to_navigation_registration);
        });
    }

    private Boolean isEmptyEditTextLogin() {
        if(binding.email.getText().toString().isEmpty() || binding.password.getText().toString().isEmpty()) {
            Toast toast = Toast.makeText(getActivity(), "Empty EditText", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return true;
        }else{
            return false;
        }
    }

    private void loginUser(CurrentUser currentUser) {
        try{
        Call<LoginResponse> response = RetrofitClient.getInstance().getApi()
                .checkLoginUser(currentUser);
        response.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {

                    String token_n = response.body().getAccess_token();

                    emailUserIdentify = binding.email.getText().toString();

                    Bundle bundle = new Bundle();
                    bundle.putString("identify", emailUserIdentify);

                    try {
                        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefLoginEdit = preferences.edit();
                        prefLoginEdit.putBoolean("loggedin", true);
                        prefLoginEdit.putString("token", token_n);
                        prefLoginEdit.commit();

                        navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
                        navController.navigate(R.id.action_navigation_login_to_navigation_home, bundle);
                    } catch (Exception e) {
                        Log.d("API", "Token  error: " + e.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.d("API", toString());
            }
        });
    }catch (Exception e){
        Log.d("API", e.toString());
            Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show();
        }
    }
}


















