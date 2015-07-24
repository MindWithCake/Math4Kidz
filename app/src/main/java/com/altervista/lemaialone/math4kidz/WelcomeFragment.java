package com.altervista.lemaialone.math4kidz;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.altervista.lemaialone.math4kidz.math.ExactDivision;
import com.altervista.lemaialone.math4kidz.math.IntegerSum;
import com.altervista.lemaialone.math4kidz.math.Operation;
import com.altervista.lemaialone.math4kidz.math.PositiveDifference;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends Fragment implements View.OnClickListener {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState){
		View root = inflater.inflate(R.layout.fragment_welcome, container, false);

		Context ctx = root.getContext();
		LinearLayout layout = (LinearLayout)root.findViewById(R.id.container);

		LayoutParams params = new LayoutParams(MATCH_PARENT, WRAP_CONTENT);
		params.setMargins(20, 10, 20, 10);
		MyOp[] ops = MyOp.values();

		for(MyOp myOp: ops){
			TextView tv = new TextView(ctx);
			tv.setLayoutParams(params);
			tv.setBackgroundResource(R.drawable.azure_rounded_rect);
			tv.setPadding(30, 10, 30, 10);
			tv.setText(myOp.labelRes);
			tv.setTag(myOp.op);
			tv.setGravity(Gravity.CENTER);
			tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
			tv.setOnClickListener(this);
			layout.addView(tv);
		}

		return root;
	}

	@Override
	public void onClick(View view){
		Operation op = (Operation)view.getTag();
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.fragment,  LearnActivityFragment.newInstance(op))
				.addToBackStack(null)
				.commit();
	}

	private enum MyOp{
		PLUS(new IntegerSum(), R.string.operation_sum),
		MIUNS(new PositiveDifference(), R.string.operation_diff),
		DIVIDE(new ExactDivision(), R.string.operation_divide)/*,
		TIMES(op, label)*/;

		public final Operation<Integer> op;
		public final int labelRes;

		MyOp(Operation<Integer> op, int labelRes){this.op = op;
			this.labelRes = labelRes;
		}
	}
}
