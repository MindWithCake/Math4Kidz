package com.altervista.lemaialone.math4kidz.math;

import android.os.Parcel;
import android.support.annotation.NonNull;

import static com.altervista.lemaialone.math4kidz.LearnActivityFragment.RAND;

/**
 * Subtraction which result is always >= 0.
 * Created by Ilario Sanseverino on 23/07/15.
 */
public class PositiveDifference implements Operation<Integer> {
	public final static Creator<PositiveDifference> CREATOR = new Creator<PositiveDifference>() {
		@Override
		public PositiveDifference createFromParcel(Parcel parcel){
			return new PositiveDifference();
		}

		@Override
		public PositiveDifference[] newArray(int i){
			return new PositiveDifference[i];
		}
	};

	@Override
	public Integer apply(@NonNull Expression<Integer> expr){
		try{
			Integer[] operands = expr.getOperands();
			return operands[0] - operands[1];
		}
		catch(ArrayIndexOutOfBoundsException e){
			throw new IllegalArgumentException("Expression must be binary");
		}
	}

	@Override
	public String toString(@NonNull Expression<Integer> expr){
		try{
			Integer[] operands = expr.getOperands();
			return String.format("%d - %d", operands[0], operands[1]);
		}
		catch(ArrayIndexOutOfBoundsException e){
			throw new IllegalArgumentException("Expression must be binary");
		}
	}

	@Override
	public Expression<Integer> generate(int level){
		int left = RAND.nextInt(4 * level);
		return new Expression<>(left, RAND.nextInt(left+1));
	}

	@Override
	public Integer pseudoResult(int level){
		return RAND.nextInt(4*level);
	}

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int i){}
}
