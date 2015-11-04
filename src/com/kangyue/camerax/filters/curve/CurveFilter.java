package com.kangyue.camerax.filters.curve;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.interpolation.UnivariateInterpolator;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;

import com.kangyue.camerax.filters.Filter;

public class CurveFilter implements Filter{

	// The lookup table.
	private final Mat mLUT = new MatOfInt();
	
	public CurveFilter(
			final double[] vValIn , final double[] vValOut,
			final double[] rValIn , final double[] rValOut,
			final double[] gValIn , final double[] gValOut,
			final double[] bValIn , final double[] bValOut){
		
		// Create the interpolation functions.
		UnivariateFunction vFunc = newFunc(vValIn,vValOut);
		UnivariateFunction rFunc = newFunc(vValIn,vValOut);
		UnivariateFunction bFunc = newFunc(vValIn,vValOut);
		UnivariateFunction gFunc = newFunc(vValIn,vValOut);
		
		// Create and populate the lookup functions.
		mLUT.create(256,1, CvType.CV_8UC4);
		
		for(int i = 0; i < 256; i++){
			final double v = vFunc.value(i);
			final double r = rFunc.value(v);
			final double g = gFunc.value(v);
			final double b = bFunc.value(v);
			mLUT.put(i, 0,r,g,b,i); // alpha is unchanged
		}
		
	}
	
	@Override
	public void apply(Mat src, Mat dst) {
		// Apply the lookup table.
		Core.LUT(src, mLUT, dst);
		
	}
	
	private UnivariateFunction newFunc(final double[] valIn,
			final double[] valOut){
		
		UnivariateInterpolator interpolator;
		
		if(valIn.length >2){
			 interpolator = new SplineInterpolator();
		}else{
			 interpolator = new LinearInterpolator();
		}
		  return interpolator.interpolate(valIn, valOut);
	}

}
