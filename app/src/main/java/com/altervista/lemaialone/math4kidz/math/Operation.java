package com.altervista.lemaialone.math4kidz.math;

import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Binary integer operation.
 * Created by Ilario Sanseverino on 16/07/15.
 */
public interface Operation<T extends Number> extends Parcelable {

	T apply(@NonNull Expression<T> expr);

	String toString(@NonNull Expression<T> expr);

	Expression<T> generate(int level);

	T pseudoResult(int level);

	class Expression<N extends Number>{
		private N[] operands;

		@SafeVarargs
		public Expression(N... operands){this.operands = operands;}

		public N[] getOperands(){
			return operands;
		}
	}
}
