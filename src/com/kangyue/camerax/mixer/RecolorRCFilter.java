package com.kangyue.camerax.mixer;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import com.kangyue.camerax.filters.Filter;


public final class RecolorRCFilter implements Filter {

	private final ArrayList<Mat> mChannels = new ArrayList<Mat>(4);
	
	@Override
	public void apply(Mat src, Mat dst) {
		Core.split(src, mChannels);
		
		final Mat g = mChannels.get(1);
		final Mat b = mChannels.get(2);
		
		Core.addWeighted(g, 0.5, b, 0.5, 0.0, g);
	
		mChannels.set(2, g);
		
		Core.merge(mChannels, dst);
	}
}
