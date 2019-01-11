package com.snz.rskj.android.model.listview;

import android.view.View;

public interface IViewReclaimer {
	/**
	 * reclaim view
	 * 
	 * @param view
	 */
	public void reclaimView(View view);
}
