package com.app.workout_app.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.app.workout_app.ExercisesActivity;
import com.app.workout_app.MainActivity;
import com.app.workout_app.R;
import com.app.workout_app.constants.SharedIndexes;
import com.app.workout_app.databinding.FragmentLoginBinding;
import com.app.workout_app.models.Auth;
import com.app.workout_app.models.User;
import com.app.workout_app.services.AuthenticationService;


public class LoginFragment extends Fragment {
    EditText usernameED, passwordED;
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usernameED = (EditText) binding.editTextTextPersonName;
        passwordED = (EditText) binding.editTextTextPassword;
        SharedPreferences preferences = this.getActivity().getSharedPreferences(SharedIndexes.loggedUserIndex, Context.MODE_PRIVATE);

        if (!preferences.getString(SharedIndexes.loggedUserNameKey, "").isEmpty()) {
            Intent i = new Intent(this.getActivity(), ExercisesActivity.class);
            startActivity(i);
        }

        binding.button.setOnClickListener(view1 -> {
            System.out.println(usernameED);
            String username = usernameED.getText().toString();
            String password = passwordED.getText().toString();

            if (!username.isEmpty() && !password.isEmpty()) {
                new AuthenticationService().login(new Auth(username, password),
                    this.getActivity().getApplicationContext(),
                    response -> {
                    if (response != null) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(SharedIndexes.loggedUserNameKey, response.getName());
                        editor.apply();
                        Intent i = new Intent(this.getActivity(), ExercisesActivity.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(
                                LoginFragment.this.getActivity(),
                                "Usuário não encontrado ou senha invalida.",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            } else {
                Toast.makeText(LoginFragment.this.getActivity(),"Usuário e senha obrigatórios!",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public interface VolleyUserCallBack {
        void onSuccess(User user);
    }


}