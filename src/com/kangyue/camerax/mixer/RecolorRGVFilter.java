package com.kangyue.camerax.mixer;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;

import com.kangyue.camerax.filters.Filter;

public class RecolorRGVFilter implements Filter {

	private final ArrayList<Mat> mChannels = new ArrayList<Mat>(4);

	@Override
	public void apply(Mat src, Mat dst) {
		Core.split(src, mChannels);

		final Mat r = mChannels.get(0);
		final Mat g = mChannels.get(1);
		final Mat b = mChannels.get(2);

		Core.min(b, r, b);
		Core.min(b, g, b);

		Core.merge(mChannels, dst);
	}

}
