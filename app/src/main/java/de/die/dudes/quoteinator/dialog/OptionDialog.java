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
public class OptionDialog extends DialogFragment {

    private OptionDialogListener clickListener;

    public interface OptionDialogListener {
        void onDeleteClick(Bundle bundle);

        void onEditClick(Bundle bundle);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            clickListener = (OptionDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(getTargetFragment().toString()
                    + " must implement OptionDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle(R.string.optionDialogTitle);

        builder.setItems(R.array.optionsDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        clickListener.onEditClick(getArguments());
                        break;
                    case 1:
                        clickListener.onDeleteClick(getArguments());
                        break;
                }
            }
        });


        return builder.create();
    }
}
