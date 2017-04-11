package despairs.smscleaner.app.presenter;

/**
 * Created by Despairs on 09.03.16.
 */
public class BasePresenter<View> {

    protected View view;

    public void bindView(View view) {
        this.view = view;
    }

    public void unbindView() {
        view = null;
    }

    public boolean isBinded() {
        return view != null;
    }
}
