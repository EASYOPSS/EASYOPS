package com.tsoap.sat.fragments.logging;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseFile;
import com.parse.ParseUser;
import com.tsoap.sat.businessobject.Efficiency;
import com.tsoap.sat.businessobject.UserProfile;
import com.tsoap.sat.easyops.R;
import com.tsoap.sat.fragments.fragmentInteraction.UserLoggingInteraction;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.tsoap.sat.fragments.fragmentInteraction.UserLoggingInteraction} interface
 * to handle interaction events.
 * Use the {@link LoggingEfficiencyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoggingEfficiencyFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;

    // directory name to store captured images and videos
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";

    private Uri fileUri; // file url to store image/video

    String fileName;

    byte[] img = null;


    SimpleDateFormat dateformat3;
    Calendar cal;
    {
        dateformat3 = new SimpleDateFormat("yyyyMMdd");
        cal = Calendar.getInstance();
    }

    @Bind(R.id.meter_reading)
    EditText meterReading;

    @Bind(R.id.attachment)
    Button attachment;

    @Bind(R.id.attachment_viewer)
    ImageView imgPreview;

    @Bind(R.id.scrollView)
    ScrollView scrollView;

    @Bind(R.id.submit)
    Button submit;

    @Bind(R.id.select_date_img)
    Button selectDateImg;

    @Bind(R.id.select_date)
    TextView selectDate;

    String selectDateStr;



    private UserLoggingInteraction mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment LoggingEfficiencyFragment.
     */
    public static LoggingEfficiencyFragment newInstance() {
        LoggingEfficiencyFragment fragment = new LoggingEfficiencyFragment();
        return fragment;
    }

    public LoggingEfficiencyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.logging_efficiency_fragment, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @OnClick(R.id.submit)
    public void submit() {
        Efficiency efficiencyData = validateForm();

        if (mListener != null && efficiencyData != null) {
            mListener.submit(efficiencyData);
        }
    }

    private Efficiency validateForm() {
        meterReading.setError(null);
        boolean cancel = false;
        View focusView = null;

        String reading = meterReading.getText().toString();

        if (TextUtils.isEmpty(reading)) {
            meterReading.setError(getString(R.string.error_field_required));
            focusView = meterReading;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Efficiency data = new Efficiency();
            data.setMeterReading(reading);


            if(data!=null){
                ParseFile imgFile = new ParseFile("braveImg.png", img);
                imgFile.saveInBackground();
                data.setMeterReadingAttachment(imgFile);
            }

            if(selectDateStr != null){
                data.setCreatedOn(selectDateStr);
            }

            return data;
        }

        return null;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (UserLoggingInteraction) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        cal.set(year, monthOfYear, dayOfMonth);
        selectDateStr = dateformat3.format(cal.getTime());
        monthOfYear = monthOfYear + 1;
        selectDate.setText(dayOfMonth + "-" + monthOfYear + "-" + year);
    }

    @OnClick(R.id.select_date)
    public void setSelectDate() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                LoggingEfficiencyFragment.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
        dpd.setAccentColor(R.color.route_theme);


    }

  /*  *//**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     *//*
    public interface OnEfficiencyDataSubmitListener {
        public void submitEfficiencyData(Efficiency efficiency);
    }*/

    @OnClick(R.id.attachment)
    public void clickPicture() {
        captureImage();
    }


    private void captureImage() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

        // start the image capture Intent
        getActivity().startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }

    /**
     * Receiving activity result method will be called after closing the camera
     * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        //super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // successfully captured the image
                // display it in image view
                previewCapturedImage();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // user cancelled Image capture
                Toast.makeText(getActivity().getApplicationContext(),
                        "User cancelled image capture", Toast.LENGTH_SHORT)
                        .show();
            } else {
                // failed to capture image
                Toast.makeText(getActivity().getApplicationContext(),
                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void previewCapturedImage() {
        try {

            imgPreview.setVisibility(View.VISIBLE);

            // bimatp factory
            BitmapFactory.Options options = new BitmapFactory.Options();

            // downsizing image as it throws OutOfMemory Exception for larger
            // images
            options.inSampleSize = 8;

            final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
                    options);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            img = stream.toByteArray();

            imgPreview.setImageBitmap(bitmap);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }


    /**
     * returning image / video
     */
    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
       /* if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }*/

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

}
