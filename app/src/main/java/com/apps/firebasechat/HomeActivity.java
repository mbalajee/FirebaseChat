package com.apps.firebasechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsers();
    }

    private void getUsers() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> users = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                     User user = snapshot.getValue(User.class);
                     users.add(user);
                }
                updateList(users);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, "Failed to get users " + error.getDetails(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateList(List<User> users) {
        RecyclerView recyclerView = findViewById(R.id.listUsers);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        UserListAdapter userListAdapter;
        if (adapter == null) {
            userListAdapter = new UserListAdapter(users);
            recyclerView.setAdapter(userListAdapter);
        }  else {
            userListAdapter = (UserListAdapter) adapter;
            userListAdapter.updateUsers(users);
        }
    }

}
