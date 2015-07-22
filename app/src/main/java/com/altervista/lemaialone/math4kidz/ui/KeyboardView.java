package com.altervista.lemaialone.math4kidz.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import com.altervista.lemaialone.math4kidz.R;

import static android.widget.LinearLayout.LayoutParams.*;

/**
 * Keyboard with 3 columns and variable number of rows.
 * Created by Ilario Sanseverino on 21/07/15.
 */
public class KeyboardView extends LinearLayout implements View.OnClickListener {
	private final static int COLUMNS = 3; // TODO obtain from xml

	private int numChildren = 0;
	private LinearLayout currentRow;
	private final LayoutParams rowParams = new LayoutParams(0, WRAP_CONTENT, 1f);
	{
		rowParams.setMargins(10, 0, 10, 0);
	}

	private OnButtonClicked buttonListener;

	public KeyboardView(Context context){
		this(context, null);
	}

	public KeyboardView(Context context, AttributeSet attrs){
		this(context, attrs, 0);
	}

	public KeyboardView(Context context, AttributeSet attrs, int defStyleAttr){
		super(context, attrs, defStyleAttr);
		setOrientation(VERTICAL);
		initRow();
	}

	@Override
	public void addView(@NonNull View child){
		child.setLayoutParams(rowParams);
		currentRow.removeViewAt(numChildren % COLUMNS);
		currentRow.addView(child, numChildren % COLUMNS, rowParams);
		child.setOnClickListener(this);
		child.setTag(numChildren++);
		if(numChildren % 3 == 0)
			initRow();
	}

	public void setButtonListener(OnButtonClicked buttonListener){
		this.buttonListener = buttonListener;
	}

	public void setButtons(Object[] buttons){
		removeAllViews();
		numChildren = 0;
		initRow();
		for(Object button: buttons){
			TextView view = new SquareTextView(getContext());
			view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
			view.setPadding(30, 20, 30, 20);
			view.setText(button.toString());
			view.setGravity(Gravity.CENTER);
			view.setBackgroundResource(R.drawable.azure_rounded_rect);
			view.setTextColor(getResources().getColor(R.color.orange));
			addView(view);
		}
	}

	private void initRow(){
		currentRow = new LinearLayout(getContext());
		currentRow.setOrientation(HORIZONTAL);
		currentRow.setPadding(10, 10, 10, 10);
		for(int i = 0; i < COLUMNS; ++i)
			currentRow.addView(new Space(getContext()));
		super.addView(currentRow);
	}

	@Override
	public void onClick(View view){
		if(buttonListener != null)
			buttonListener.onButtonClicked((Integer)view.getTag());
	}

	public interface OnButtonClicked{
		void onButtonClicked(int buttonIndex);
	}
}
