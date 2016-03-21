package de.bxservice.bxpos.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import de.bxservice.bxpos.R;

/**
 * Created by Diego Ruiz on 17/03/16.
 */
public class ConfirmationPinDialogFragment extends DialogFragment {

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface ConfirmationPinDialogListener {
        void onDialogPositiveClick(ConfirmationPinDialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    ConfirmationPinDialogListener mListener;
    private String reason = "";
    private String pinCode = "";
    private int noItems = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.confirmation_pin_dialog, null);

        final TextView voidSummaryText = (TextView) view.findViewById(R.id.void_summary);

        voidSummaryText.setText(getString(R.string.void_approval_message, noItems, reason));

        final EditText enteredCode = (EditText) view.findViewById(R.id.pin_text);

        enteredCode.requestFocus();

        builder.setTitle(R.string.approve_void);
        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.void_item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Nothing on purpose to avoid closing the dialog when the reason is not filled out
                        pinCode = enteredCode.getText().toString();
                        mListener.onDialogPositiveClick(ConfirmationPinDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConfirmationPinDialogFragment.this.getDialog().cancel();
                    }
                });



        // Create the AlertDialog object and return it
        return builder.create();
    }

    // Override the Fragment.onAttach() method to instantiate the GuestNumberDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mListener = (ConfirmationPinDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement ConfirmationPinDialogListener");
        }
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public void setNoItems(int noItems) {
        this.noItems = noItems;
    }
}