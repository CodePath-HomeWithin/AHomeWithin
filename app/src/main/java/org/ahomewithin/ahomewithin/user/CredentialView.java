package org.ahomewithin.ahomewithin.user;

import android.support.design.widget.TextInputLayout;
import android.widget.EditText;

/**
 * Created by xiangyang_xiao on 3/6/16.
 */
public class CredentialView {

  public CredentialView(EditText editText, TextInputLayout textInputLayout) {
    this.editText = editText;
    this.textInputLayout = textInputLayout;
  }

  public EditText getEditText() {
    return editText;
  }

  public TextInputLayout getTextInputLayout() {
    return textInputLayout;
  }

  EditText editText;
  TextInputLayout textInputLayout;
}
