package com.example.madproject.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.madproject.HomeActivity;
import com.example.madproject.MainActivity;
import com.example.madproject.R;
import com.example.madproject.classes.Post;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import static android.content.ContentValues.TAG;

public class ProfileFragment extends Fragment {
    public ProfileFragment() {

    }
    TextView userName,userEmail;
    ImageView userProfile;
    Button signoutbtn;
    private List<Post> postList;
    RecyclerView recyclerView;
    ProfileListAdapter profileListAdapter;



    public static com.example.madproject.fragment.HomeFragment newInstance(String param1, String param2) {
        com.example.madproject.fragment.HomeFragment fragment = new com.example.madproject.fragment.HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        signoutbtn = view.findViewById(R.id.signout);
        userEmail=view.findViewById(R.id.userEmail);
        userName=view.findViewById(R.id.userName);
        userProfile=view.findViewById(R.id.userProfilePhoto);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());
        Glide.with(this).load(user.getPhotoUrl()).into(userProfile);

        signoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        postList =new ArrayList<>();
        recyclerView = view.findViewById(R.id.recyclerViewProfile);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("photo").whereEqualTo("uid", user.getUid()).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if(e!=null){
                    Log.d(TAG,"ERROR");
                }
                for(DocumentChange doc:queryDocumentSnapshots.getDocumentChanges()){
                    if(doc.getType()==DocumentChange.Type.ADDED){
                        Post post =doc.getDocument().toObject(Post.class);
                        postList.add(post);
                        profileListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        profileListAdapter = new ProfileListAdapter(postList,getActivity());
        recyclerView.setAdapter(profileListAdapter);



        return view;
    }
}
