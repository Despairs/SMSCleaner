package despairs.smscleaner.app.view;

/**
 * Created by Despairs on 09.03.16.
 */
public interface AsyncView {

    void showErrorAlert(String message);

    void showProgress(boolean show);
}
