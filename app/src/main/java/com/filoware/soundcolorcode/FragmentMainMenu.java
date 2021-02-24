package com.filoware.soundcolorcode;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

public class FragmentMainMenu extends Fragment {

    //View Model variable
    private MyViewModel viewModel;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        //Initializing view model
        viewModel = ViewModelProviders.of((FragmentActivity) getActivity()).get(MyViewModel.class);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_menu, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setSoundCode("classic3");
//                NavHostFragment.findNavController(FragmentMainMenu.this)
//                        .navigate(R.id.action_FragmentMainMenu_to_FragmentLesson);
                NavHostFragment.findNavController(FragmentMainMenu.this)
                        .navigate(R.id.action_FragmentMainMenu_to_FragmentExplore);

            }
        });
        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setSoundCode("classic10");
//                NavHostFragment.findNavController(FragmentMainMenu.this)
//                        .navigate(R.id.action_FragmentMainMenu_to_FragmentQuiz);
                NavHostFragment.findNavController(FragmentMainMenu.this)
                        .navigate(R.id.action_FragmentMainMenu_to_FragmentExplore);

            }
        });
        view.findViewById(R.id.button_third).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.setSoundCode("achromatic");
                NavHostFragment.findNavController(FragmentMainMenu.this)
                        .navigate(R.id.action_FragmentMainMenu_to_FragmentExplore);
            }
        });
    }
}