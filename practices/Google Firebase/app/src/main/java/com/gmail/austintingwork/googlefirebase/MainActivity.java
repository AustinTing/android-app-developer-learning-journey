package com.gmail.austintingwork.googlefirebase;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "debug";
    TextView text;
    FirebaseDatabase myDatabase;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.textView);
//
        // Write a message to the database
        mDatabase = FirebaseDatabase.getInstance().getReference();
        myDatabase =FirebaseDatabase.getInstance();


        // Read from the database
//        mDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                String value = dataSnapshot.getValue(String.class);
////                text.setText(value);
////                Log.d(TAG, "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });

//        Save user
//        User user = new User("Stan", "stan@mail");
//        mDatabase.child("user").child("id2").setValue(user);

//        setValue
//        mDatabase.child("user").child("id2").child("username").setValue("MAX");

//        Update specific fields
//        writeNewPost("id2", "MAX", "test", "Hello");

//        Delete data
//        mDatabase.child("user").child("id2").removeValue();

//        Test Listener
//        mDatabase.child("user")
//                .child("id2")
//                .child("username").setValue("MAX1111rrr13333", new DatabaseReference.CompletionListener() {
//            @Override
//            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                if (databaseError != null) {
//                    Log.d(TAG, "DatabaseError: "+ databaseError.getMessage());
//                }
//            }
//        });

//        Save data as transactions
//        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("posts").child("-KOE4-ZaTqpy9jUk8TwQ");
//        onStarClicked(postRef, "id21");
//        onStarClicked(postRef, "id22");
//        onStarClicked(postRef, "id333333");

//        Retrieve Data
//        retrieveData()

//        Child events
//        childEvents();

//        Detecting Connection State
        detectConnectionState();
    }




    private void writeNewPost(String userId, String username, String title, String body) {
        // Create new post at /user-posts/$userid/$postid and at
        // /posts/$postid simultaneously
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }

    private void onStarClicked(DatabaseReference postRef, final String uid) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post p = mutableData.getValue(Post.class);
                if (p == null) {
                    Log.d(TAG, "doTransaction: P == null");
                    return Transaction.success(mutableData);
                }
                Log.d(TAG, "doTransaction: P != null");

                Log.d(TAG, "MainActivity: doTransaction: " + p.getAuthor() + "\n" + p.getBody() + "\n" + p.getTitle() + "\n" + p.getUid());
//
//
                if (p.stars.containsKey(uid)) {
                    // Unstar the post and remove self from stars
                    Log.d(TAG, "MainActivity: doTransaction: startCount -1");
                    p.starCount = p.starCount - 1;
                    p.stars.remove(uid);
                } else {
                    // Star the post and add self to stars
                    Log.d(TAG, "MainActivity: doTransaction: startCount +1");
                    p.starCount = p.starCount + 1;
                    p.stars.put(uid, true);
                }

                // Set value and report transaction success
                mutableData.setValue(p);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
    private void retrieveData() {
        DatabaseReference postRef = FirebaseDatabase.getInstance().getReference("posts").child("-KOE4-ZaTqpy9jUk8TwQ");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Post p = dataSnapshot.getValue(Post.class);
                if (p != null) {
                    Log.d(TAG, "Post: " + p.getAuthor() + "\n" + p.getBody() + "\n" + p.getTitle() + "\n" + p.getUid());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        postRef.addValueEventListener(postListener);
    }
    private void childEvents() {
        DatabaseReference postsRef = myDatabase.getReference("posts");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());

                Post p = dataSnapshot.getValue(Post.class);
                if (p != null) {
                    Log.d(TAG, "onChildAdded: " + p.getAuthor() + "\n" + p.getBody() + "\n" + p.getTitle() + "\n" + p.getUid());
                }

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

                Post p = dataSnapshot.getValue(Post.class);
                if (p != null) {
                    Log.d(TAG, "onChildChanged: " + p.getAuthor() + "\n" + p.getBody() + "\n" + p.getTitle() + "\n" + p.getUid());
                }

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved:" + dataSnapshot.getKey());

                Post p = dataSnapshot.getValue(Post.class);
                if (p != null) {
                    Log.d(TAG, "onChildRemoved: " + p.getAuthor() + "\n" + p.getBody() + "\n" + p.getTitle() + "\n" + p.getUid());
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());

                Post p = dataSnapshot.getValue(Post.class);
                if (p != null) {
                    Log.d(TAG, "onChildMoved: " + p.getAuthor() + "\n" + p.getBody() + "\n" + p.getTitle() + "\n" + p.getUid());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled: ", databaseError.toException());
            }
        };
        postsRef.addChildEventListener(childEventListener);

    }

    private void detectConnectionState() {
        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {
                    Log.d(TAG, "detectConnectionState: "+ "connect");
                } else {
                    Log.d(TAG, "detectConnectionState: "+ "disconnect");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.err.println("Listener was cancelled");
            }
        });
    }
}
