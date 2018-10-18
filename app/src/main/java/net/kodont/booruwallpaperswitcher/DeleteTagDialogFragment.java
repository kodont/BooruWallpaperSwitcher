package net.kodont.booruwallpaperswitcher;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;

public class DeleteTagDialogFragment extends DialogFragment {

    private Context mContext;

    public DeleteTagDialogFragment() {

    }

    public static DeleteTagDialogFragment getInstance(Context context) {

        DeleteTagDialogFragment fragment = new DeleteTagDialogFragment();
        fragment.setContext(context);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Delete tag?");
        builder.setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "Simulating deletion",
                        Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "No simulation",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return builder.create();
    }

    private void setContext(Context context) {
        mContext = context;
    }
}
