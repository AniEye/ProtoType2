package com.bbv.prototype1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ReadTxtFiles {
	private ShowContent _Activity;
	private boolean _bTitle, _bNextOrLast, _bReferenceList, _bListFound,
			_bList, _bText, _bImage;
	private AssetManager _AssetManager;
	private InputStream _InputStream;
	private BufferedReader _BufferedReader;
	private StringBuffer _StringBuffer;
	private int _TextSize;
	private String _currentFoundReferenceList;
	private ArrayList<String> _StringArray, _ReferenceArray;
	private ArrayList<NavigationDrawerItemContent> _NavigatorItemContentList;

	private static final String KEY_LOGCAT = "ReadTxtFiles";

	public final static String KEY_OVING = "SOving";
	public final static String KEY_PROS_TEORI = "BProsTeori";

	public final static String KEY_CHAPTER = "Teori_Chapter";
	public final static String KEY_CHAPTERPART1 = "Teori_ChapterPart1";
	public final static String KEY_CHAPTERPART2 = "Teori_ChapterPart2";

	public void readTXTDocument(ShowContent activity) {
		_Activity = activity;
		try {
			Log.e(KEY_LOGCAT, "Start to do readTXTDocument");
			String str = "";
			_bTitle = _bNextOrLast = _bReferenceList = _bListFound = _bList = false;
			setAssetManager();
			setInputStream(_Activity._filePath + "_.txt");
			setBufferedReader();
			newStringBuffer();

			if (_InputStream != null) {
				while ((str = _BufferedReader.readLine()) != null) {

					if (str.contains("<title>")) {
						_bTitle = true;
						newStringBuffer();
						continue;
					} else if (str.contains("</title")) {
						_bTitle = false;
						_Activity.setTitle(_StringBuffer.toString());
						Log.i(KEY_LOGCAT, "Set the title in the action bar");
						continue;
					} else if (str.contains("<next>") || str.contains("<last>")) {
						_bNextOrLast = true;
						newStringBuffer();
						continue;
					} else if (str.contains("</next>")) {
						_bNextOrLast = false;
						if (_Activity.getCurrentIndex() == 0) {
							_Activity
									.setNextBundle(createNewTeoriBundle(_StringBuffer
											.toString()));
						} else if (_Activity.getCurrentIndex() == 1) {
							_Activity
									.setNextBundle(createNewOvingBundle(_StringBuffer
											.toString()));
						}
						Log.i(KEY_LOGCAT,
								"Created the bundle for the next page");
						continue;
					} else if (str.contains("</last>")) {
						_bNextOrLast = false;
						if (_Activity.getCurrentIndex() == 0) {
							_Activity
									.setPreviousBundle(createNewTeoriBundle(_StringBuffer
											.toString()));
						} else if (_Activity.getCurrentIndex() == 1) {
							_Activity
									.setPreviousBundle(createNewOvingBundle(_StringBuffer
											.toString()));
						}
						Log.i(KEY_LOGCAT,
								"Created the bundle for the previous page");
						continue;
					} else {

						if (_Activity.getCurrentIndex() == _Activity
								.getPriorityIndex()) {
							if (str.contains("<list>")) {
								_bList = true;
								_StringArray = new ArrayList<String>();
								_NavigatorItemContentList = new ArrayList<NavigationDrawerItemContent>();
								continue;
							} else if (str.contains("</list>")) {
								_bList = false;
								if (!_StringArray.isEmpty()) {
									String[] stringarray = arrayListToStringArray(_StringArray);

									Log.i(KEY_LOGCAT,
											"Length of stored string: "
													+ stringarray.length);
									for (int i = 0; i < stringarray.length; i++) {
										NavigationDrawerItemContent item = new NavigationDrawerItemContent();
										item.setTitle(stringarray[i]);
										_NavigatorItemContentList.add(item);
									}
									_bListFound = true;

								}
								continue;
							} else {
								if (_bListFound) {
									if (_StringArray.size() != 0) {
										for (int i = 0; i < _StringArray.size(); i++) {
											if (str.contains("<"
													+ _StringArray.get(i) + ">")) {
												_bReferenceList = true;
												_ReferenceArray = new ArrayList<String>();
												_currentFoundReferenceList = "<"
														+ _StringArray.get(i)
														+ ">";
												break;
											}
											if (str.contains("</"
													+ _StringArray.get(i) + ">")) {

												_NavigatorItemContentList
														.get(i)
														.setStringList(
																arrayListToStringArray(_ReferenceArray));
												_currentFoundReferenceList = "</"
														+ _StringArray.get(i)
														+ ">";
												break;
											}
										}
									}
								}
							}
						}
					}

					if (_bTitle || _bNextOrLast) {
						_StringBuffer.append(str);
					} else if (_bList) {
						if (!str.isEmpty()) {
							_StringArray.add(str);
						}
					} else if (_bReferenceList
							&& !str.contains(_currentFoundReferenceList)) {
						if (!str.isEmpty()) {
							_ReferenceArray.add(str);
						}
					}
				}
				// setting the list after reading the hole file looking for all
				// the references
				if (!_NavigatorItemContentList.isEmpty())
					_Activity
							.setNavigationDrawerContent(_NavigatorItemContentList);

			}
			_InputStream.close();
		} catch (IOException e) {
//			_Activity.showToast("txt file does not exist");
			Log.e(KEY_LOGCAT,
					"Didn't open or find _.txt, wrong database reference");
		}
		Log.i(KEY_LOGCAT, "Finishing decodeDataDocument");
		Log.i(KEY_LOGCAT, "  ");
	}

	

	public void readContentDocument(Activity activity) {

		try {
			String str = "";
			_bText = _bImage = false;
			setAssetManager2(activity);
//			setInputStream(_ViewDocument._FilePath + "_content.txt");
			setBufferedReader();
			newStringBuffer();

			if (_InputStream != null) {
				while ((str = _BufferedReader.readLine()) != null) {

					if (str.contains("<h1>")) {
						_TextSize = activity.getResources()
								.getDimensionPixelSize(R.dimen.H1Size);
					} else if (str.contains("<h2>")) {
						_TextSize = activity.getResources()
								.getDimensionPixelSize(R.dimen.H2Size);
					} else if (str.contains("<h3>")) {
						_TextSize = activity.getResources()
								.getDimensionPixelSize(R.dimen.H3Size);
					} else if (str.contains("<h4>")) {
						_TextSize = activity.getResources()
								.getDimensionPixelSize(R.dimen.H4Size);
					} else if (str.contains("<h5>")) {
						_TextSize = activity.getResources()
								.getDimensionPixelSize(R.dimen.H5Size);
					}

					if (str.contains("<text>")) {
						_bText = true;
						newStringBuffer();
						continue;
					} else if (str.contains("</text>")) {
						_bText = false;
//						_ViewDocument._linlay.addView(createTextView(
//								_StringBuffer.toString(), Color.CYAN,
//								_TextSize, Gravity.NO_GRAVITY));
						continue;
					} else if (str.contains("<image>")) {
						_bImage = true;
						newStringBuffer();
						continue;
					} else if (str.contains("</image>")) {
						_bImage = false;
//						_ViewDocument._linlay
//								.addView(createImageView(_ViewDocument._FilePath
//										+ _StringBuffer.toString()));
						continue;
					}

					if (_bText) {
						_StringBuffer.append(str);
						_StringBuffer.append("\n");
					} else if (_bImage) {
						_StringBuffer.append(str);
					}
				}
			}
			_InputStream.close();
		} catch (IOException e) {
			Log.e("FileView", "Didn't open or find _.txt");
			Toast.makeText(_Activity, "Could not find content file",
					Toast.LENGTH_LONG).show();
		}
	}

	private void setInputStream(String filepath) throws IOException {
		_InputStream = _AssetManager.open(filepath);
	}

	/**
	 * Creates a new Instance of a StringBuffer
	 */
	private void newStringBuffer() {
		_StringBuffer = new StringBuffer();
	}

	/**
	 * Takes an InputStream to create a new BufferedReader
	 * 
	 * @param InputStream
	 *            input
	 */
	private void setBufferedReader() {
		_BufferedReader = new BufferedReader(
				new InputStreamReader(_InputStream));
	}

	private void setAssetManager() {
		_AssetManager = _Activity.getAssets();
	}

	private void setAssetManager2(Activity act) {
		_AssetManager = act.getAssets();
	}

	private Bundle createNewTeoriBundle(String filepath) {
		Bundle newBundle = new Bundle();
		filepath = filepath.trim();
		String[] filePathDevided = filepath.split("/");
		newBundle.putString(KEY_CHAPTER, filePathDevided[0]);
		newBundle.putString(KEY_CHAPTERPART1, null);
		newBundle.putString(KEY_CHAPTERPART2, null);
		if (filePathDevided.length > 1) {// maybe also check if the content in
											// the presiding indexes aren't
											// empty
			newBundle.putString(KEY_CHAPTERPART1, filePathDevided[1]);
			if (filePathDevided.length > 2) {
				newBundle.putString(KEY_CHAPTERPART2, filePathDevided[2]);
			}
		}
		return newBundle;
	}

	private Bundle createNewOvingBundle(String newfilepath) {
		Bundle newBundle = new Bundle();
		newfilepath = newfilepath.trim();
		newBundle.putString(KEY_OVING, newfilepath);
		return newBundle;

	}

	private String[] arrayListToStringArray(ArrayList<String> list) {
		int length = list.size();
		String[] array = new String[length];
		for (int i = 0; i < length; i++) {
			array[i] = list.get(i);
		}
		return array;
	}

//	protected TextView createTextView(String text, int color, int TextSize,
//			int Gravity) {
////		TextView addTitle = new TextView(_ViewDocument.getActivity());
//		addTitle.setText(text);
//		addTitle.setTextColor(color);
//		addTitle.setGravity(Gravity);
//		addTitle.setTextSize(TextSize);
//		return addTitle;
//	}

//	protected ImageView createImageView(String ImagePath) {
//		try {
//			InputStream iis = _AssetManager.open(ImagePath);
////			ImageView image = new ImageView(_ViewDocument.getActivity());
//			image.setPadding(0, 10, 0, 10);
//			Bitmap thePicture = BitmapFactory.decodeStream(iis);
//			image.setImageBitmap(thePicture);
//			return image;
//		} catch (Exception e) {
//			Log.println(Log.ERROR, "Image", "Image not found");
//			return null;
//		}
//	}
}
