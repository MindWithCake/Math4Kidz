package com.altervista.lemaialone.math4kidz.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * View used by the GridLayout in the SubCategoryFragment to have square cells.
 * Created by Ilario Sanseverino on 29/04/2015.
 */
public class SquareTextView extends TextView {
	public SquareTextView(Context context){
		super(context);
	}

	public SquareTextView(Context context, AttributeSet attrs){
		super(context, attrs);
	}

	public SquareTextView(Context context, AttributeSet attrs, int defStyleAttr){
		super(context, attrs, defStyleAttr);
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
		final int width = getDefaultSize(getSuggestedMinimumWidth(),widthMeasureSpec);
		//noinspection SuspiciousNameCombination
		setMeasuredDimension(width, width); // force height same as width
	}

	@Override
	protected void onSizeChanged(final int w, final int h, final int oldW, final int oldH) {
		super.onSizeChanged(w, w, oldW, oldH); // set height same as width
	}
}
