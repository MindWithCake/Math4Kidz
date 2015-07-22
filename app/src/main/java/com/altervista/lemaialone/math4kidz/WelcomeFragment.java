package com.altervista.lemaialone.math4kidz;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.altervista.lemaialone.math4kidz.math.IntegerSum;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState){
		View root = inflater.inflate(R.layout.fragment_welcome, container, false);

		View sumButton = root.findViewById(R.id.sum_button);
		sumButton.setOnClickListener(this);

		return root;
	}

	@Override
	public void onClick(View view){
		FragmentTransaction trans = getFragmentManager().beginTransaction();
		switch(view.getId()){
		case R.id.sum_button:
			trans.replace(R.id.fragment, LearnActivityFragment.newInstance(new IntegerSum()));
			break;
		default:
			throw new IllegalArgumentException("Unknown view: "+view);
		}
		trans.addToBackStack(null).commit();
	}
}
