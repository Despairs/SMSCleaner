package despairs.smscleaner.app.view;

/**
 * Created by Despairs on 09.03.16.
 */
public interface AsyncView {

    public void showErrorAlert(String message);

    public void showProgress(boolean show);
}
