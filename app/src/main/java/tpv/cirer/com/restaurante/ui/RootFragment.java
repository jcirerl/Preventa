package tpv.cirer.com.restaurante.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tpv.cirer.com.restaurante.R;

/**
 * Example about replacing fragments inside a ViewPager. I'm using
 * android-support-v7 to maximize the compatibility.
 * 
 * @author Dani Lao (@dani_lao)
 * 
 */
public class RootFragment extends Fragment {

	private static final String TAG = "RootFragment";
	public static RootFragment newInstance() {
		RootFragment fragment = new RootFragment();
		return fragment;
	}

	public RootFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		/* Inflate the layout for this fragment */
		View view = inflater.inflate(R.layout.root_fragment, container, false);

		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();
		/*
		 * When this container fragment is created, we fill it with our first
		 * "real" fragment
		 */
//		transaction.replace(R.id.root_frame, new FirstFragment());

		transaction.replace(R.id.root_frame, new EditOpenSeccionFragment());
		transaction.commit();

		return view;
	}

}
