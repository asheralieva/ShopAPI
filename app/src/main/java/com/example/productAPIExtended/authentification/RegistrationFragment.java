package com.example.productAPIExtended.authentification;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.productAPIExtended.R;
import com.example.productAPIExtended.databinding.FragmentRegistretionBinding;
import com.example.productAPIExtended.models.User;
import com.example.productAPIExtended.remote_data.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends Fragment {
    FragmentRegistretionBinding binding;
    User newUser;
    NavController navController;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegistretionBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnRegistration.setOnClickListener(v->{
            binding.progressBar.setVisibility(View.VISIBLE);
            if(!IsEmptyEditTextRegistration()){
                registerToTable();
            }
        });
        binding.textViewLoginNow.setOnClickListener(v1->{
//            navController = NavController.findNavController(requireActivity(),
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            //register
            navController.navigate(R.id.action_navigation_basket_to_navigation_home);
        });
    }

    private void registerToTable() {
        newUser = new User(binding.nameUser.getText().toString(),
                binding.email.getText().toString(),
                binding.password.getText().toString()
                );
        Call<User> callCreateUser = RetrofitClient.getInstance().getApi().registrationNewUser(newUser);

        callCreateUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(requireActivity(), "Registration is Success", Toast.LENGTH_SHORT).show();

                        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor prefsLoginEdit = preferences.edit();
                        prefsLoginEdit.putBoolean("registration", true);
                        prefsLoginEdit.commit();
                    }
                }else{
                    Log.e("fail", "fail");
                    Toast.makeText(requireActivity(), "Registration is not available now", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                Log.e("FAILURE: ", "Regisatration is failed");
                Toast.makeText(requireActivity(), "Regisatration is failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean IsEmptyEditTextRegistration() {
        if(binding.nameUser.getText().toString().isEmpty() ||
                binding.email.getText().toString().isEmpty() ||
                binding.password.getText().toString().isEmpty() ) {
            Toast toast = Toast.makeText(getActivity(), "Заполните поля для регистрации", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            return true;
        } else{
            return false;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}