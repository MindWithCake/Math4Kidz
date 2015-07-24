package com.altervista.lemaialone.math4kidz.math;

import android.os.Parcel;
import android.support.annotation.NonNull;

import static com.altervista.lemaialone.math4kidz.LearnActivityFragment.RAND;

/**
 * Integer Sum. The generation algorithm simply create the sum of 2 random numbers in the interval
 * {@code [0 - 3*difficulty[}.
 * Created by Ilario Sanseverino on 22/07/15.
 */
public class IntegerSum implements Operation<Integer> {
	public final static Creator<IntegerSum> CREATOR = new Creator<IntegerSum>() {
		@Override
		public IntegerSum createFromParcel(Parcel parcel){
			return new IntegerSum();
		}

		@Override
		public IntegerSum[] newArray(int i){
			return new IntegerSum[i];
		}
	};

	@Override
	public Integer apply(@NonNull Expression<Integer> expr){
		int result = 0;
		for(int i: expr.getOperands())
			result += i;
		return result;
	}

	@Override
	public String toString(@NonNull Expression<Integer> expr){
		Integer[] operands = expr.getOperands();
		String toReturn = operands[0].toString();
		for(int i = 1; i < operands.length; ++i)
			toReturn += " + "+operands[i];
		return toReturn;
	}

	@Override
	public Expression<Integer> generate(int level){
		return new Expression<>(RAND.nextInt(level*3), RAND.nextInt(level*3));
	}

	@Override
	public Integer pseudoResult(int level){
		return RAND.nextInt(level*6);
	}

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i){}
}
