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

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ConstantConditions")
public class AuthRepository {

    public static final String USERS_COLLECTION = "users";
    private static final String TAG = AuthRepository.class.getCanonicalName();

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public LiveData<Boolean> signIn(String email, String password) {
        MutableLiveData<Boolean> result = new MutableLiveData<>();

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.i(TAG, "signIn successful");
                } else {
                    Log.w(TAG, "signIn failed", task.getException());
                }

                result.setValue(task.isSuccessful());
            });

        return result;
    }

    public void logout() {
        auth.signOut();
    }

    public LiveData<String> register(String email, String password, String name) {
        MutableLiveData<String> result = new MutableLiveData<>();

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(task -> {
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

                    db.collection(USERS_COLLECTION)
                        .document(user.getId())
                        .set(user, SetOptions.merge())
                        .addOnCompleteListener(another -> {
                            if (another.isSuccessful()) {
                                result.setValue(null);
                            } else {
                                Log.w(TAG, "register failed", task.getException());
                                result.setValue(task.getException().getLocalizedMessage());
                            }
                        });
                } else {
                    Log.w(TAG, "register failed", task.getException());
                    result.setValue(task.getException().getLocalizedMessage());
                }
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

    void updateStatus(String status) {
        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("status", status);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            db.collection(USERS_COLLECTION).document(currentUser.getUid()).update(hashMap);
        } else {
            Log.w(TAG, "Tried to update user, but on user is logged in");
        }
    }
}
