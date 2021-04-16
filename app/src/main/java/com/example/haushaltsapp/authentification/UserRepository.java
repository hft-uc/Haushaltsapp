package com.example.haushaltsapp.authentification;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.haushaltsapp.types.UserDetail;
import com.example.haushaltsapp.types.UserSummary;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

@SuppressWarnings("ConstantConditions")
public class UserRepository {

    public static final String USERS_COLLECTION = "users";
    private static final String TAG = UserRepository.class.getCanonicalName();

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public LiveData<Boolean> signIn(String email, String password) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isCanceled()) {
                    Log.w(TAG, "signIn failed", task.getException());
                } else {
                    Log.i(TAG, "signIn successful");
                }

                result.setValue(task.isSuccessful());
            });

        return result;
    }

    public LiveData<Boolean> register(String email, String password, String name) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        auth.createUserWithEmailAndPassword(email, password)
            .continueWith(task -> {
                if (task.isSuccessful()) {
                    final FirebaseUser firebaseUser = task.getResult().getUser();

                    UserDetail user = new UserDetail(firebaseUser.getUid(), name, email);

                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build();
                    firebaseUser.updateProfile(profileUpdates)
                        .addOnCompleteListener(displayNameTask -> {
                            if (displayNameTask.isSuccessful()) {
                                Log.i(TAG, "Updated display name successfully");
                            } else {
                                Log.i(TAG, "Failed to update display name", displayNameTask.getException());
                            }
                        });

                    return db.collection(USERS_COLLECTION)
                        .document(user.getId())
                        .set(user, SetOptions.merge());
                } else {
                    Log.w(TAG, "register failed", task.getException());

                    return task;
                }
            })
            .addOnCompleteListener(task -> {
                if (task.isCanceled()) {
                    Log.w(TAG, "register failed", task.getException());
                }

                result.setValue(task.isSuccessful());
            });

        return result;
    }

    public UserSummary getCurrentUser() {
        FirebaseUser user = auth.getCurrentUser();
        return new UserSummary(user.getUid(), user.getDisplayName());
    }

    public boolean alreadySignedIn() {
        return auth.getCurrentUser() != null;
    }
}
