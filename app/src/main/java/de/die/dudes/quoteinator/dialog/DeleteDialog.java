package de.die.dudes.quoteinator.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import de.die.dudes.quoteinator.R;

/**
 * Created by Phil on 23.08.2016.
 */
public class DeleteDialog extends DialogFragment {

    private DeleteDialogListener clickListener;

    public interface DeleteDialogListener {
        void onPositiveClick(Bundle bundle);

        void onNegativeClick();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            clickListener = (DeleteDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString()
                    + " must implement DeleteDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle(R.string.deleteDialogTitle);
        builder.setMessage(R.string.deleteDialogMessage);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickListener.onPositiveClick(getArguments());
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                clickListener.onNegativeClick();
            }
        });


        return builder.create();
    }
}
