package com.altervista.lemaialone.math4kidz;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.altervista.lemaialone.math4kidz.math.Operation;
import com.altervista.lemaialone.math4kidz.math.Operation.Expression;
import com.altervista.lemaialone.math4kidz.ui.KeyboardView;
import com.altervista.lemaialone.math4kidz.ui.KeyboardView.OnButtonClicked;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class LearnActivityFragment<N extends Number>
		extends Fragment implements OnButtonClicked, TimerThread.OnTimeout {
	public final static Random RAND = new Random();

	private final static String ARG_OP = "LearnActivityFragment.ARG_OP";
	private final static int NUM_BUTTONS = 12;

	private KeyboardView keyboardView;
	private TextView expressionBox;
	private ProgressBar timeBar;

	private Operation<N> operation;
	private Expression<N> currentExpression;
	private int level = 1;
	private int lives = 3;
	private int consecutiveGuesses = 0;
	private ArrayList<N> buttons;

	private TimerThread timerThread;

	public static <T extends Number> Fragment newInstance(Operation<T> op){
		Fragment fragment = new LearnActivityFragment<T>();
		Bundle arg = new Bundle();
		arg.putParcelable(ARG_OP, op);
		fragment.setArguments(arg);
		return fragment;
	}

	@Override
	public void onSaveInstanceState(Bundle outState){
		super.onSaveInstanceState(outState);
		outState.putParcelable(ARG_OP, operation);
	}

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		if(savedInstanceState == null)
			savedInstanceState = getArguments();

		operation = savedInstanceState.getParcelable(ARG_OP);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState){
		View rootView = inflater.inflate(R.layout.fragment_learn, container, false);

		keyboardView = (KeyboardView)rootView.findViewById(R.id.keyboard);
		expressionBox = (TextView)rootView.findViewById(R.id.question_box);
		timeBar = (ProgressBar)rootView.findViewById(R.id.timer_bar);

		keyboardView.setButtonListener(this);
		initQuestion();

		return rootView;
	}

	@Override
	public void onButtonClicked(int buttonIndex){
		timerThread.interrupt();
		N answer = buttons.get(buttonIndex);
		if(answer.equals(operation.apply(currentExpression)))
			correctAnswer();
		else
			wrongAnswer();

		initQuestion();
	}

	private void initQuestion(){
		currentExpression = operation.generate(level);
		N result = operation.apply(currentExpression);
		buttons = new ArrayList<>(NUM_BUTTONS);
		for(int i = 1; i < NUM_BUTTONS; ++i)
			buttons.add(operation.pseudoResult(level));
		buttons.add(result);
		Collections.shuffle(buttons, RAND);

		expressionBox.setText(operation.toString(currentExpression));
		keyboardView.setButtons(buttons.toArray());

		timerThread = new TimerThread(timeBar, this, (int)Math.exp(level));
		timerThread.start();
	}

	private void correctAnswer(){
		++consecutiveGuesses;
		if(consecutiveGuesses % 5 == 0)
			++level;
	}

	private void wrongAnswer(){
		consecutiveGuesses = 0;
		if(--lives < 0)
			showResult();
	}

	private void showResult(){
		//TODO
		Toast.makeText(getActivity(), "You reached level "+level, Toast.LENGTH_LONG).show();
		getFragmentManager().popBackStack();
	}

	@Override
	public void onTimeout(){
		wrongAnswer();
		initQuestion();
	}
}
