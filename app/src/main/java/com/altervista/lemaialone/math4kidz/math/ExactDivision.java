package com.altervista.lemaialone.math4kidz.math;

import android.os.Parcel;
import android.support.annotation.NonNull;

import static com.altervista.lemaialone.math4kidz.LearnActivityFragment.RAND;

/**
 * Integer division whose result does not require rounding.
 * Created by Ilario Sanseverino on 24/07/15.
 */
public class ExactDivision implements Operation<Integer> {
	public final static Creator<ExactDivision> CREATOR = new Creator<ExactDivision>() {
		@Override
		public ExactDivision createFromParcel(Parcel parcel){
			return new ExactDivision();
		}

		@Override
		public ExactDivision[] newArray(int i){
			return new ExactDivision[i];
		}
	};

	@Override
	public Integer apply(@NonNull Expression<Integer> expr){
		try{
			Integer[] operands = expr.getOperands();
			return operands[0] / operands[1];
		}
		catch(ArrayIndexOutOfBoundsException e){
			throw new IllegalArgumentException("Expression must be binary");
		}
	}

	@Override
	public String toString(@NonNull Expression<Integer> expr){
		try{
			Integer[] operands = expr.getOperands();
			return String.format("%d \u00F7 %d", operands[0], operands[1]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			throw new IllegalArgumentException("Expression must be binary");
		}
	}

	@Override
	public Expression<Integer> generate(int level){
		int right = RAND.nextInt(4*level)+1;
		return new Expression<>(pseudoResult(level)*right, right);
	}

	@Override
	public Integer pseudoResult(int level){
		return RAND.nextInt(3*level);
	}

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i){ }
}
