package despairs.smscleaner.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Despairs on 19.01.16.
 */
public abstract class AsyncActivity extends AppCompatActivity {

    protected abstract String getProgressDialogMessage();

    private ProgressDialog progressDialog;

    protected void showAsyncTaskProgress(boolean show) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getProgressDialogMessage());
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        if (show) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    protected void showAsyncTaskError(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ошибка!")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Окей",
                        (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}
