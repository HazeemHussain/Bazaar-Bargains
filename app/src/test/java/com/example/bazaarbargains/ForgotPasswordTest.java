package com.example.bazaarbargains;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.text.method.PasswordTransformationMethod;
import android.widget.TextView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import androidx.test.core.app.ApplicationProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Config.OLDEST_SDK})
public class ForgotPasswordTest {
    @Mock
    private CheckBox mockShowPassword;
    @Mock
    private EditText mockInputPassword;
    @Mock
    private EditText mockInputUsername;
    @Mock
    private ForgotPasswordActivity checking;
    @Mock
    private DatabaseReference mockDatabaseReference;
    @Mock
    private DataSnapshot mockDataSnapshot;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockShowPassword = mock(CheckBox.class);
        mockInputPassword = mock(EditText.class);
        mockInputUsername = mock(EditText.class);
        mockDatabaseReference = mock(DatabaseReference.class);
        mockDataSnapshot = mock(DataSnapshot.class);
        checking = Robolectric.setupActivity(ForgotPasswordActivity.class);
        Context context = ApplicationProvider.getApplicationContext();
        // Set the mock objects on the view
        View view = LayoutInflater.from(context).inflate(R.layout.activity_forgot_password, null);
        ((ViewGroup) view).addView(mockShowPassword); // Add the checkbox to the view hierarchy
        ((ViewGroup) view).addView(mockInputPassword); // Add the input password field to the view hierarchy
        ((ViewGroup) view).addView(mockInputUsername);

    }

    @Test
    public void testShowPassword_Checked() {
        when(mockShowPassword.isChecked()).thenReturn(true);

        checking.showPassword();

        if(!mockShowPassword.isChecked()){
            verify(mockInputPassword).setTransformationMethod(null);
        }
    }

    @Test
    public void testShowPassword_Unchecked() {
        // Set the initial state of the checkbox to unchecked
        when(mockShowPassword.isChecked()).thenReturn(false);

        // Call the method under test
        checking.showPassword();

        mockShowPassword.performClick();

        verify(mockShowPassword).performClick();

        if(mockShowPassword.isChecked()){
            verify(mockInputPassword).setTransformationMethod(eq(PasswordTransformationMethod.getInstance()));
        }

    }
    @Test
    public void testUpdatingPassword_UsernameExists() {
        MockitoAnnotations.initMocks(this);


        String username = "exampleUser";
        String password = "newPassword";


        when(mockDataSnapshot.exists()).thenReturn(true);


        checking.updatingPassword(username, password);


        verify(mockDatabaseReference).child("Users");
        verify(mockDatabaseReference).child(username);
        verify(mockDatabaseReference).child("password");
        verify(mockDatabaseReference).setValue(password);




    }
}
