package com.example.productAPIExtended.ui.dashboard;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.productAPIExtended.R;
import com.example.productAPIExtended.databinding.FragmentBasketBinding;
import com.example.productAPIExtended.models.ModelM;
import com.example.productAPIExtended.models.Order;
import com.example.productAPIExtended.ui.home.JemAdapter;

import java.util.ArrayList;
import java.util.Objects;


public class BasketFragment extends Fragment {
    BasketAdapter basketAdapter;

    public FragmentBasketBinding binding;
    public ArrayList<ModelM> basket_products;
    NavController navController;
    String userAdr, userName;
    ArrayList<Order> orders_list;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentBasketBinding
                .inflate(inflater, container, false);
        View root = binding.getRoot();

        if (getArguments() != null) {
            basket_products = new ArrayList<>();
            basket_products = getArguments().getParcelableArrayList("keysss_basket");
        }
        if (basket_products != null) {
            binding.placeHolder.setVisibility(View.GONE);

            basketAdapter = new BasketAdapter(requireActivity(), basket_products);
            binding.rvBasket.setAdapter(basketAdapter);
        } else {
            binding.placeHolder.setVisibility(View.VISIBLE);
        }
        return root;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnMakeOrder.setOnClickListener(v1 -> {
            if (!(basket_products == null)) {

                if (!(binding.editUsersAddress.getText().toString().isEmpty())
                        && !(binding.editUsersName.getText().toString().isEmpty())) {

                    userAdr = binding.editUsersAddress.getText().toString();
                    userName = binding.editUsersName.getText().toString();
                    orders_list = new ArrayList<>();
                    try {
                        for (int i = 0; i < basket_products.size(); i++) {
                            orders_list.add(new Order(
                                    userName,
                                    userAdr,
                                    basket_products.get(i).getModelImage(),
                                    basket_products.get(i).getModelTitle(),
                                    String.valueOf(basket_products.get(i).getPrice_d()),
                                    basket_products.get(i).getCounterProduct()));
                        }
                    } catch (NullPointerException ex) {
                        Toast.makeText(requireActivity(), "Выберите товары в каталоге", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(requireActivity(), "Введите имя, адрес для доставки", Toast.LENGTH_SHORT).show();
                }




            } else {
                Toast.makeText(requireActivity(), "Выберите товары в каталоге", Toast.LENGTH_SHORT).show();
            }
            try {
                binding.tvOtvet.setText("Ваш заказ в обработке, доставка  составляет от 5 до 12 дней. "
                        + "    \nДоставка будет осуществлена после оплаты. "
                        + "    \nВАШ  ЗАКАЗ СОДЕРЖИТ:  "
                        + "    \n                            "
                        + ",   \nИМЯ:    " + orders_list.get(orders_list.size() - 1).getUsername()
                        + ",   \nАДРЕС:   " + orders_list.get(orders_list.size() - 1).getAddressUser()
                        + "    \nПРОДУКТ:   " + orders_list.get(orders_list.size() - 1).getNameProduct()
                        + ",   \nЦЕНА:    " + orders_list.get(orders_list.size() - 1).getPriceProduct() + " $ "
                        + ",   \nЕДИНИЦ:   " + basketAdapter.binding.productsCounter.getText().toString());
            }catch (NullPointerException ex){
                binding.btnToPay.setVisibility(View.INVISIBLE);
                binding.tvOtvet.setText("Вы не выбрали товары, если хотите сделать заказ,"+
                        "вернитесь на страницу каталога и выберите товары для заказа");
            }

            if (!(binding.tvOtvet.getText().toString()).isEmpty()) {

                binding.btnMakeOrder.setVisibility(View.INVISIBLE);
                binding.btnToPay.setVisibility(View.VISIBLE);

            }
        });

        binding.btnToPay.setOnClickListener(v2 -> {
            Bundle bundle_order_list = new Bundle();
            bundle_order_list.putParcelableArrayList("payed", orders_list);

            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            navController.navigate(R.id.action_navigation_basket_to_navigation_payment, bundle_order_list);
        });
        binding.btnBack.setOnClickListener(v3 -> {
            navController = Navigation.findNavController(requireActivity(), R.id.nav_host);
            navController.navigate(R.id.action_navigation_basket_to_navigation_home);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}