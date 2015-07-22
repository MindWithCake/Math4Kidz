package com.altervista.lemaialone.math4kidz.math;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Binary integer operation.
 * Created by Ilario Sanseverino on 16/07/15.
 */
public interface Operation<T extends Number> extends Parcelable {

	T apply(Expression<T> expr);

	String toString(Expression<T> expr);

	Expression<T> generate(int level);

	T pseudoResult(int level);

	class Expression<N extends Number>{
		private N[] operands;

		public Expression(N... operands){this.operands = operands;}

		public N[] getOperands(){
			return operands;
		}

		public void setOperands(N... operands){
			this.operands = operands;
		}
	}
}
